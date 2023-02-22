package it.polimi.db2.telco.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.services.Optional_ProductService;
import it.polimi.db2.telco.entities.Employee;


@WebServlet("/CreateProduct")
public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.telco.services/Optional_ProductService")
	private Optional_ProductService	opService;

	
	public CreateProduct() {
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
		HttpSession session = request.getSession();
		
		String name=null;
		String fee=null;
		try {
			name = StringEscapeUtils.escapeJava(request.getParameter("name"));
			fee = StringEscapeUtils.escapeJava(request.getParameter("monthlyFee"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		String path;
		
		float monthlyFee = -1;
		try {
			monthlyFee= Float.parseFloat(fee);
		}catch(Exception e) {
			ctx.setVariable("errorProductMsg", "Insert only numbers");
			path = "WEB-INF/CreateAdditional.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
		Employee emp = (Employee) session.getAttribute("employee");
		try {
			opService.createOptional_Product(emp, name, monthlyFee);
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't create Product");
			return;
		}
		ctx.setVariable("errorProductMsg", "Created Product");
		path = "WEB-INF/CreateAdditional.html";
		templateEngine.process(path, ctx, response.getWriter());
	}

	public void destroy() {
	}

}