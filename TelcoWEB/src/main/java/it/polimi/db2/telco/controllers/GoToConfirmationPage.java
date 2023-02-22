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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.entities.Package;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.services.Optional_ProductService;
import it.polimi.db2.telco.services.PackageService;
import it.polimi.db2.telco.services.ValidityService;

@WebServlet("/GoToConfirmationPage")
public class GoToConfirmationPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/PackageService")
	private PackageService	pService;
	@EJB(name = "it.polimi.db2.telco.services/Optional_ProductService")
	private Optional_ProductService	opService;
	
	@EJB(name = "it.polimi.db2.telco.services/ValidityService")
	private ValidityService	vService;
	

	public GoToConfirmationPage() {
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
			value =  StringEscapeUtils.escapeJava(request.getParameter("package"));
			if (value == null ) {
				throw new Exception("Missing or empty credential value");
			}
	
		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing value package");
			return;
		}
		int id = Integer.parseInt(value);
		Package p = pService.findById(id);
		
		List<Optional_Product> op = new ArrayList<Optional_Product>() ;
	    String[] checkboxs  = null;
	    
	    try {
		    checkboxs = request.getParameterValues("checkBox");
			if(checkboxs!=null)
			{
				for (String val: checkboxs) {
					id = Integer.parseInt(val);
					op.add(opService.findById(id));
				}
			}
	    }catch(Exception e) {
	    	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing value checkbox");
			return;
	    }
	    
		try {
			value=StringEscapeUtils.escapeJava(request.getParameter("radio"));
			if (value == null ) {
				throw new Exception("Missing or empty credential value");
			}
	
		} catch (Exception e) {
			// for debugging only e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing value radio");
			return;
		}
		id = Integer.parseInt(value);
		Validity v = vService.findById(id);
		
		request.getSession().setAttribute("package", p);
		request.getSession().setAttribute("products", op);
		request.getSession().setAttribute("validity", v);


		
		// Redirect to the Home page and add missions to the parameters
		String path = "WEB-INF/ConfirmationPage.html";
		ServletContext servletContext = getServletContext();
		
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		//ctx.setVariable("package", p);
		//ctx.setVariable("products",op);
		//ctx.setVariable("validity",v);

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

