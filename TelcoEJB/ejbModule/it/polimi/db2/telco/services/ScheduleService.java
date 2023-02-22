package it.polimi.db2.telco.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Activation_Schedule;
import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.User;


@Stateless
public class ScheduleService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;
	
	public ScheduleService() {}
	
	public Activation_Schedule findById(int scheduleId) {
		return em.find(Activation_Schedule.class, scheduleId);
	}

	public List<Activation_Schedule> findAllSchedule(User userId) {

		return em.createNamedQuery("Activation_Schedule.allSchedule", Activation_Schedule.class).setParameter("user", userId).getResultList();

	}

	public Activation_Schedule createSchedule(User user, List<Service> services, List<Optional_Product> products, Date start_date, Date end_date) {
		Activation_Schedule schedule = new Activation_Schedule();
		schedule.setDateActivation(start_date);
		schedule.setDateDectivation(end_date);
		schedule.setUser(user);
		if(!products.isEmpty())
			schedule.setProducts(products);
		schedule.setServices(services);
		return schedule;
	}
}
