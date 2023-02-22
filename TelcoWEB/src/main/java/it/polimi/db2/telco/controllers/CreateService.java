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

import it.polimi.db2.telco.services.ServiceService;
import it.polimi.db2.telco.entities.Employee;


@WebServlet("/CreateService")
public class CreateService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	
	@EJB(name = "it.polimi.db2.telco.services/ServiceService")
	private ServiceService	sService;
	
	public CreateService() {
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
		
		String type=null;
		try {
			type = StringEscapeUtils.escapeJava(request.getParameter("type"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String mgS=null;
		String feeMgS=null;
		String smsS=null;
		String feeSmsS=null;
		if(type.compareTo("mp")==0){
			try
			{
			mgS = StringEscapeUtils.escapeJava(request.getParameter("minutes"));
			feeMgS = StringEscapeUtils.escapeJava(request.getParameter("feeMin"));
			smsS = StringEscapeUtils.escapeJava(request.getParameter("sms"));
			feeSmsS = StringEscapeUtils.escapeJava(request.getParameter("feeSms"));
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
				return;
			}
		}else if(type.compareTo("mi")==0 || type.compareTo("ti")==0) {
			try
			{
			mgS = StringEscapeUtils.escapeJava(request.getParameter("giga"));
			feeMgS = StringEscapeUtils.escapeJava(request.getParameter("feeGiga"));
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value");
				return;
			}
		}
		
		ServletContext servletContext = getServletContext();
		final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
		String path;
		int mg = 0;
		float feeMg = 0;
		int sms = 0 ;
		float feeSms = 0;
		if(type.compareTo("tp")!=0) {
			if(type.compareTo("mp")==0) {			
				try {
					mg=Integer.parseInt(mgS);
					sms=Integer.parseInt(smsS);	
					feeMg= Float.parseFloat(feeMgS);
					feeSms= Float.parseFloat(feeSmsS);
				}catch(Exception e) {
					ctx.setVariable("errorMsg", "Insert only numbers");
					path = "WEB-INF/CreateAdditional.html";
					templateEngine.process(path, ctx, response.getWriter());
					return;
				}
			}
			else {
				try {
					mg=Integer.parseInt(mgS);
					feeMg= Float.parseFloat(feeMgS);
				}catch(Exception e) {
					ctx.setVariable("errorMsg", "Insert only numbers");
					path = "WEB-INF/CreateAdditional.html";
					templateEngine.process(path, ctx, response.getWriter());
					return;
				}
			}
		}
		Employee emp = (Employee) session.getAttribute("employee");
		try {
			sService.createService(emp, type, mg, feeMg, sms, feeSms);
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Couldn't create Service");
			return;
		}
		ctx.setVariable("errorMsg", "Created Service");
		path = "WEB-INF/CreateAdditional.html";
		templateEngine.process(path, ctx, response.getWriter());
	}

	public void destroy() {
	}

}