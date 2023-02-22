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

import it.polimi.db2.telco.entities.Package;
import it.polimi.db2.telco.services.PackageService;
import it.polimi.db2.telco.services.ScheduleService;
import it.polimi.db2.telco.entities.User;
import it.polimi.db2.telco.entities.Activation_Schedule;
import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.services.OrderService;

/**
 * Servlet implementation class GoToHomePage
 */
@WebServlet("/GoToHomePage")
public class GoToHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	@EJB(name = "it.polimi.db2.telco.services/PackageService")
	private PackageService	pService;
	
	@EJB(name = "it.polimi.db2.telco.services/OrderService")
	private OrderService	oService;
	
	@EJB(name = "it.polimi.db2.telco.services/ScheduleService")
	private ScheduleService	asService;
	
	public GoToHomePage() {
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
		HttpSession session = request.getSession();
		List<Order> orders = new ArrayList<Order>();
		List<Activation_Schedule> schedules=new ArrayList<Activation_Schedule>();
		if (!session.isNew() || session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			orders=oService.findRejectedOrder(user);
			schedules = asService.findAllSchedule(user);

		}
		
		List<Package> packages = pService.findAllPackages();
		// Redirect to the Home page and add missions to the parameters
		String path = "WEB-INF/Home.html";
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		ctx.setVariable("packages", packages);
		ctx.setVariable("orders", orders);
		ctx.setVariable("schedules", schedules);

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
