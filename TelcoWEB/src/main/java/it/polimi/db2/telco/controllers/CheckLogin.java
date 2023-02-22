package it.polimi.db2.telco.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.services.UserService;
import it.polimi.db2.telco.entities.User;
import it.polimi.db2.telco.services.EmployeeService;
import it.polimi.db2.telco.entities.Employee;



import it.polimi.db2.telco.exceptions.CredentialsException;
import javax.persistence.NonUniqueResultException;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/UserService")
	private UserService usrService;
	
	@EJB(name = "it.polimi.db2.telco.services/EmployeeService")
	private EmployeeService empService;
	
	public CheckLogin() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// obtain and escape params
		String usrn = null;
		String pwd = null;
		try {
			usrn = StringEscapeUtils.escapeJava(request.getParameter("username"));
			pwd = StringEscapeUtils.escapeJava(request.getParameter("pwd"));
			if (usrn == null || pwd == null || usrn.isEmpty() || pwd.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		User user;
		Employee employee;
		try {
			// query db to authenticate for user
			user = usrService.checkCredentials(usrn, pwd);
		} catch (CredentialsException | NonUniqueResultException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check user credentials");
			return;
		}
		
		try {
			// query db to authenticate for user
			employee = empService.checkCredentials(usrn, pwd);
		} catch (CredentialsException | NonUniqueResultException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check employee credentials");
			return;
		}
		
		String path;
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		if (user == null & employee==null) {
			ctx.setVariable("errorMsg", "Incorrect username or password");
			path = "/index.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else if(employee==null & user!=null){
			request.getSession().setAttribute("user", user);
			if(request.getSession().getAttribute("package")==null) {
				path = getServletContext().getContextPath() + "/GoToHomePage";
				response.sendRedirect(path);
			}else
				{
				path = "WEB-INF/ConfirmationPage.html";
				templateEngine.process(path, ctx, response.getWriter());
				}
		} else if(user==null & employee!=null){
			request.getSession().setAttribute("employee", employee);
			path = getServletContext().getContextPath() + "/GoToEmployeeHomePage";
			response.sendRedirect(path);
		}

	}

	public void destroy() {
	}
}