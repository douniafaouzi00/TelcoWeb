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

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import it.polimi.db2.telco.entities.Purchase_Package;
import it.polimi.db2.telco.entities.Purchase_Package_Validity;
import it.polimi.db2.telco.entities.Suspended_Order;
import it.polimi.db2.telco.entities.Totsales;
import it.polimi.db2.telco.services.SaleReportService;

import it.polimi.db2.telco.entities.Alert;
import it.polimi.db2.telco.entities.Average_Product;
import it.polimi.db2.telco.entities.Best_Seller;
import it.polimi.db2.telco.entities.Insolvent_User;


/**
 * Servlet implementation class GoToHomePage
 */
@WebServlet("/GoToEmployeeHomePage")
public class GoToEmployeeHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/SaleReportService")
	private SaleReportService	srService;

	
	public GoToEmployeeHomePage() {
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
		List<Average_Product> ap = new ArrayList<Average_Product>();
		Best_Seller bs;
		List<Insolvent_User> i= new ArrayList<Insolvent_User>();
		List<Alert> a=new ArrayList<Alert>();
		List<Purchase_Package> pp=new ArrayList<Purchase_Package>();
		List<Purchase_Package_Validity> ppv= new ArrayList<Purchase_Package_Validity>();
		List<Suspended_Order> so=new ArrayList<Suspended_Order>();
		List<Totsales> ts=new ArrayList<Totsales>();
		try {
			ap= srService.findAverage_Product();
			i= srService.findInsolventUser();
			bs= srService.findBestSeller();
			a= srService.findAlert();
			pp= srService.findPurchasePackage();
			ppv= srService.findPurchasePackageValidity();
			so= srService.findSuspendedOrder();
			ts=srService.findTotsales();
		}catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to load report");
			return;
		}
		
		// Redirect to the Home page and add missions to the parameters
		String path = "WEB-INF/EmployeeHome.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("average_product", ap);
		ctx.setVariable("insolvent_user", i);
		ctx.setVariable("best_seller", bs);
		ctx.setVariable("alerts", a);
		ctx.setVariable("purchase_package",pp);
		ctx.setVariable("purchase_package_validity",ppv);
		ctx.setVariable("suspended_order",so);
		ctx.setVariable("totsales",ts);
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