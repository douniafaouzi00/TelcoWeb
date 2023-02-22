package it.polimi.db2.telco.controllers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.services.ServiceService;
import it.polimi.db2.telco.services.ValidityService;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.services.Optional_ProductService;

/**
 * Servlet implementation class GoToHomePage
 */
@WebServlet("/GoToCreatePackage")
public class GoToCreatePackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	
	@EJB(name = "it.polimi.db2.telco.services/ServiceService")
	private ServiceService	sService;
	
	@EJB(name = "it.polimi.db2.telco.services/Optional_ProductService")
	private Optional_ProductService	opService;
	
	@EJB(name = "it.polimi.db2.telco.services/ValidityService")
	private ValidityService	vService;
	
	public GoToCreatePackage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		this.templateEngine = new TemplateEngine();
		this.templateEngine.setTemplateResolver(templateResolver);
		templateResolver.setSuffix(".html");
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Service> s = sService.findAllServices();
		List<Optional_Product> op = opService.findAllOptional_Product();
		List<Validity> v = vService.findAllValidity();
		// Redirect to the Home page and add missions to the parameters
		String path = "WEB-INF/CreatePackage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("products", op);
		ctx.setVariable("validities", v);
		ctx.setVariable("services", s);
		templateEngine.process(path, ctx, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}