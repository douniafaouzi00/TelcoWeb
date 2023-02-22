package it.polimi.db2.telco.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import it.polimi.db2.telco.services.OrderService;
import it.polimi.db2.telco.services.PackageService;
import it.polimi.db2.telco.services.ValidityService;

import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.entities.Package;
import it.polimi.db2.telco.entities.User;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.exceptions.TooLate;
import it.polimi.db2.telco.exceptions.WrongStartDate;

@WebServlet("/Buy")
public class Buy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;

	@EJB(name = "it.polimi.db2.telco.services/OrderService")
	private OrderService oService;

	@EJB(name = "it.polimi.db2.telco.services/PackageService")
	private PackageService pService;
	
	@EJB(name = "it.polimi.db2.telco.services/Optional_ProductService")
	private Optional_ProductService opService;
	
	@EJB(name = "it.polimi.db2.telco.services/ValidityService")
	private ValidityService vService;
	
	public Buy() {
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
		Date startDate = null;
		int pId = -1;
		List<Integer> opId =new ArrayList<Integer>();
		int vId = -1;
		String status = null;
		String oId=null;
		String[] values=null;
		try {
			pId = Integer.parseInt(request.getParameter("package"));
			vId = Integer.parseInt(request.getParameter("validity"));
			oId = StringEscapeUtils.escapeJava(request.getParameter("order"));
			values = request.getParameterValues("products");
			if(values!=null)
				for(String val : values)
					opId.add(Integer.parseInt(val));
			status = StringEscapeUtils.escapeJava(request.getParameter("status"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			startDate = (Date) sdf.parse(request.getParameter("start_date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		User user = (User) session.getAttribute("user");
		Package p;
		List<Optional_Product> op = new ArrayList<Optional_Product>();
		Validity v;
		
		p=pService.findById(pId);
		v=vService.findById(vId);
		if(values!=null)
			for(int i: opId)
				op.add(opService.findById(i));
	
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		String path;
		
		Order o;
		
		if(oId==null) {
			try {
				o = oService.createOrder(user, p, op,v,startDate,status);
			}catch(WrongStartDate e) {
				ctx.setVariable("errorMsg", "Wrong start date selected");
				ctx.setVariable("package", p);
				ctx.setVariable("products", op);
				ctx.setVariable("validity", v);
				ctx.setVariable("start_date", startDate);
				path = "WEB-INF/ConfirmationPage.html";
				templateEngine.process(path, ctx, response.getWriter());
				return;
			}catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to create Order");
				return;
			}
		}else{
			o = oService.findById(Integer.parseInt(oId));
			try {
				o = oService.updateOrder(user, o , status);
			}catch(TooLate e) {
				ctx.setVariable("errorMsg", "Too late to update this order, the order has been deleted");
				ctx.setVariable("package", p);
				ctx.setVariable("products", op);
				ctx.setVariable("validity", v);
				path = "WEB-INF/ConfirmationPage.html";
				templateEngine.process(path, ctx, response.getWriter());
				return;
			}catch (Exception e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not possible to update Order");
				return;
			}
		}
		if(o.getStatus().compareTo("rejected")==0) {
			ctx.setVariable("errorMsg", "You're payment was rejected try again");
			ctx.setVariable("package", p);
			ctx.setVariable("products", op);
			ctx.setVariable("validity", v);
			ctx.setVariable("order", o);
			ctx.setVariable("start_date", startDate);
			path = "WEB-INF/ConfirmationPage.html";
			templateEngine.process(path, ctx, response.getWriter());
		}
		else {
			path = getServletContext().getContextPath() + "/GoToHomePage";
			response.sendRedirect(path);
		}

	}

	public void destroy() {
	}

}
