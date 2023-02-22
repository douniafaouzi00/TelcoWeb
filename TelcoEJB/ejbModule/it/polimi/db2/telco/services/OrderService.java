package it.polimi.db2.telco.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.time.DateUtils;
import java.time.LocalTime;

import it.polimi.db2.telco.entities.Activation_Schedule;
import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.entities.Package;
import it.polimi.db2.telco.entities.User;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.exceptions.NotFound;
import it.polimi.db2.telco.exceptions.TooLate;
import it.polimi.db2.telco.exceptions.WrongStartDate;

@Stateless
public class OrderService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;
	
	private ScheduleService sService= new ScheduleService();
	
	public OrderService() {}
	
	public Order findById(int orderId) {
		return em.find(Order.class, orderId);
	}

	public List<Order> findRejectedOrder(User userId) {

		return em.createNamedQuery("Order.rejectedOrder", Order.class).setParameter("user", userId).getResultList();

	}
	public List<Order> findValidOrder(int userId) throws NotFound {
		List<Order> orders = null;
		try {
			orders = em.createQuery("Select o from Order o where u.username = :user and status='valid'", Order.class).setParameter("user", userId).getResultList();
		} catch (PersistenceException e) {
			throw new NotFound("Could not found valid orders");
		}
		return orders;
	}
	
	public Order createOrder(User usr, Package p, List<Optional_Product> products, Validity v, Date start_date, String status) throws WrongStartDate, NotFound {
		Order o = new Order(); 
		float sum=0;
	    long millis=System.currentTimeMillis();  
		if(p!=null) {
			for(Optional_Product op : products) {
				sum+=op.getMonthly_fee();
			}
			if(start_date.before(Calendar.getInstance().getTime())) {
				throw new WrongStartDate("The selected start date is already past!");
			}
			java.sql.Date date = new java.sql.Date(millis);    
			o.setDate(date);
			LocalTime now = LocalTime.of(LocalTime.now().getHour(),LocalTime.now().getMinute(),LocalTime.now().getSecond());
			o.setTime(now);
			o.setAmount((v.getMonthly_fee()+sum)*v.getMonth());
			o.setPackage(p);
			o.setUser(usr);
			o.setValidity(v);
			o.setStartDate(start_date);
			if(products!=null) {
				o.setProducts(products);
				for(Optional_Product op: products)
					op.addOrder(o);
			}
			if(status.compareTo("rejected")==0)
			{
				o.setRejections(1);
				o.setStatus("rejected");
			}
			else if(status.compareTo("valid")==0) {
				o.setRejections(0);
				o.setStatus("valid");
				Date end_date= DateUtils.addMonths(o.getStartDate(), o.getValidity().getMonth());
				Activation_Schedule as= sService.createSchedule(usr, o.getPackage().getServices(), o.getProducts(), o.getStartDate(), end_date);
				em.persist(as);
			}
			
			em.persist(o);
			em.flush();
			return o;
		}
		else {
			throw new NotFound("Package not Found!");
		}
	} 
	public Order updateOrder(User usr, Order o, String status) throws NotFound, TooLate {
	    long millis=System.currentTimeMillis();  
		if (o!=null){
			if (o.getStartDate().after(new java.sql.Date(millis)))
			{
				if(status.compareTo("rejected")==0)
				{
					o.setRejections(o.getRejections()+1);
					o.setStatus("rejected");
				}
				else if (status.compareTo("valid")==0){
					o.setRejections(0);
					o.setStatus("valid");
					Date end_date= DateUtils.addMonths(o.getStartDate(), o.getValidity().getMonth());
					Activation_Schedule as= sService.createSchedule(usr, o.getPackage().getServices(), o.getProducts(), o.getStartDate(), end_date);
					em.persist(as);
				}
				if (!em.contains(o)) {
				    o = em.merge(o);
				}
				em.flush();
				return o;
			}	
			else {
				if (!em.contains(o)) {
				    o = em.merge(o);
				}
				em.remove(o);
				throw new TooLate("it's too late to buy this order the start date is passed!");
			}
		}
		else {
			throw new NotFound("Order not found");
		}	
	}
	
	public void deleteOrder(Order o) throws TooLate {
	    long millis=System.currentTimeMillis();  

		if (o!=null){
			if(o.getStatus().compareTo("rejected")==0)
			{
				if (!em.contains(o)) {
				    o = em.merge(o);
				}
				em.remove(o);
			}
			else if(o.getStatus().compareTo("valid")==0) {
				if (o.getStartDate().after(new java.sql.Date(millis))) {

					if (!em.contains(o)) {
					    o = em.merge(o);
					}
					em.remove(o);
				}
				else { 
					throw new TooLate("it's too late to delete this order!");
				}
			}
				
		}
	}
	
}
