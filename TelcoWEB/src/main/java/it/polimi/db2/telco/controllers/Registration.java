package it.polimi.db2.telco.controllers;

import java.io.IOException;


import javax.ejb.EJB;
import javax.persistence.NonUniqueResultException;
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

import it.polimi.db2.telco.entities.User;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.services.UserService;

@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/UserService")
	private UserService uService;
	
	public Registration() {
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
		// If the user is not logged in (not present in session) redirect to the login		
		String username=null;
		String password=null;
		String email=null;
		
		try {
			username = StringEscapeUtils.escapeJava(request.getParameter("username"));
			email = StringEscapeUtils.escapeJava(request.getParameter("email"));
			password = StringEscapeUtils.escapeJava(request.getParameter("password"));
			if (username == null || email == null || password == null || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
			return;
		}
		User user;
		try {
			user = uService.CreateProfile(username, email, password);
		} catch (CredentialsException | NonUniqueResultException e){
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to create user");
			return;
		}
		
		String path;
		if (user.getUsername() == null) {
			ServletContext servletContext = getServletContext();
			final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
			ctx.setVariable("errorMsg", "Existing username try a different one");
			path = "/Registration.html";
			templateEngine.process(path, ctx, response.getWriter());
		} else{
			
			path ="/TelcoWEB/index.html";
			response.sendRedirect(path);
		} 
		
	}

	public void destroy() {
	}

}