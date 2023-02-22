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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.entities.Package;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.services.PackageService;

@WebServlet("/AddDetails")
public class AddDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/PackageService")
	private PackageService	pService;
	

	public AddDetails() {
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
		String value = null;
		try {
			value =  StringEscapeUtils.escapeJava(request.getParameter("select"));
			if (value == null ) {
				throw new Exception("Missing or empty credential value");
			}

		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing value");
			return;
		}
		int id= Integer.parseInt(value);
		Package p = pService.findById(id);
		List<Package> packages = pService.findAllPackages();
		List<Service> s=p.getServices();
		List<Optional_Product> op=p.getProducts();
		List<Validity> v=p.getValidities();
		// Redirect to the Home page and add package to the parameters
		String path = "WEB-INF/BuyPage.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("package",p);
		ctx.setVariable("packages", packages);
		ctx.setVariable("services", s);
		ctx.setVariable("products", op);
		ctx.setVariable("validities", v);
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

