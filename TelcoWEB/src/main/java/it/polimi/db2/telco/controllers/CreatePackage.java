package it.polimi.db2.telco.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.services.Optional_ProductService;
import it.polimi.db2.telco.services.PackageService;
import it.polimi.db2.telco.services.ServiceService;
import it.polimi.db2.telco.services.ValidityService;
import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.entities.Package;


@WebServlet("/CreatePackage")
public class CreatePackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;


	@EJB(name = "it.polimi.db2.telco.services/PackageService")
	private PackageService pService;
	
	@EJB(name = "it.polimi.db2.telco.services/ServiceService")
	private ServiceService	sService;
	
	@EJB(name = "it.polimi.db2.telco.services/Optional_ProductService")
	private Optional_ProductService	opService;
	
	@EJB(name = "it.polimi.db2.telco.services/ValidityService")
	private ValidityService	vService;
	
	public CreatePackage() {
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
		String[] opId=null;
		String[] sId=null;
		String[] vId=null;
		try {
			sId = request.getParameterValues("services");
			vId = request.getParameterValues("validities");
			opId = request.getParameterValues("products");
			name=request.getParameter("name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<Service> services= new ArrayList<Service>();
		List<Optional_Product> products= new ArrayList<Optional_Product>();
		List<Validity> validities= new ArrayList<Validity>();

		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		String path;
		if(sId==null || vId==null)
		{
			services=sService.findAllServices();
			products=opService.findAllOptional_Product();
			validities=vService.findAllValidity();
			ctx.setVariable("errorMsg", "Select at least a service and a validity");
			ctx.setVariable("services", services);
			ctx.setVariable("products", products);
			ctx.setVariable("validities", validities);
			path = "WEB-INF/CreatePackage.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
		else {
			Employee emp = (Employee) session.getAttribute("employee");
			Package p;
			for(String s:sId)
				services.add(sService.findById(Integer.parseInt(s)));
			for(String v:vId)
				validities.add(vService.findById(Integer.parseInt(v)));
			if(opId!=null)
				for(String op:opId)
					products.add(opService.findById(Integer.parseInt(op)));
			try {
				p = pService.createPackage(emp, name, services, products, validities);
			}catch(Exception e)
			{
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to create Order");
				return;
			}
			services=sService.findAllServices();
			products=opService.findAllOptional_Product();
			validities=vService.findAllValidity();
			ctx.setVariable("correctMsg", "Package Created");
			ctx.setVariable("services", services);
			ctx.setVariable("package", p);
			ctx.setVariable("products", products);
			ctx.setVariable("validities", validities);
			path = "WEB-INF/CreatePackage.html";
			templateEngine.process(path, ctx, response.getWriter());
		}

	}

	public void destroy() {
	}

}
