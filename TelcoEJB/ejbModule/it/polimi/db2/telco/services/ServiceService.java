package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.Mobile_Phone;
import it.polimi.db2.telco.entities.Telephone_Phone;
import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.Mobile_Internet;
import it.polimi.db2.telco.entities.Telephone_Internet;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.exceptions.NotAllowed;
import it.polimi.db2.telco.exceptions.NotFound;

@Stateless
public class ServiceService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;
	
	public ServiceService(){}
	
	public Service findById(int serviceId) {
		return em.find(Service.class, serviceId);
	}

	public List<Service> findAllServices() {

		return em.createNamedQuery("Service.findAll", Service.class).getResultList();

	}

	public Service createService(Employee emp, String type, int mg, float feemg, int sms, float feesms) throws NotAllowed, NonUniqueResultException, CredentialsException {
		Service s = new Service();
		if (emp!=null){
			s.setType(type);
			if(type.compareTo("mp")==0) {
				Mobile_Phone mp= new Mobile_Phone(s,mg,feemg,sms,feesms);
				s.setMP(mp);
			} else if(type.compareTo("tp")==0) { 
				Telephone_Phone tp= new Telephone_Phone(s);
				s.setTP(tp);
			}else if(type.compareTo("mi")==0) {
				Mobile_Internet mi= new Mobile_Internet(s,mg,feemg);
				s.setMI(mi);
			}else if(type.compareTo("ti")==0) {
				Telephone_Internet ti= new Telephone_Internet(s,mg,feemg);
				s.setTI(ti);
			}
			em.persist(s);
			em.flush();
			return s;
		}
		else {
			throw new NotAllowed("You are not an Employee you can't create a Service");
		}
		
	}

	public void deleteService(String username, int id) throws NotFound, NotAllowed, NonUniqueResultException, CredentialsException {
		EmployeeService e= new EmployeeService();
		Employee emp= e.findByUsername(username);
		if (emp!=null){
			Service s = em.find(Service.class, id);
			if (s == null)
				throw new NotFound("Service Not Found");
			else 
				em.remove(s);
		}
		else {
			throw new NotAllowed("You are not an Employee you can't delete a Service");
		}
	}
	
}
