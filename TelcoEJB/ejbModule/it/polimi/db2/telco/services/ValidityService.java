package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.exceptions.NotAllowed;
import it.polimi.db2.telco.exceptions.NotFound;

@Stateless
public class ValidityService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;
	
	public ValidityService() {}
	
	
	public Validity findById(int validityId) {
		return em.find(Validity.class, validityId);
	}


	public List<Validity> findAllValidity() {

		return em.createNamedQuery("Validity.findAll", Validity.class).getResultList();

	}

	public Validity createValidity(Employee emp, int m, float mf) throws NotAllowed, NonUniqueResultException, CredentialsException {
		Validity v = new Validity();
		if (emp!=null){
			v.setMonth(m);
			v.setMonthly_fee(mf);
			em.persist(v);
			em.flush();
			return v;
		}
		else {
			throw new NotAllowed("You are not an Employee you can't create an Optional_Product");
		}
	}
	
	public void deleteValidity(String username, int id) throws NotFound, NotAllowed, NonUniqueResultException, CredentialsException {
		EmployeeService e= new EmployeeService();
		Employee emp= e.findByUsername(username);
		if (emp!=null){
			Validity v = em.find(Validity.class, id);
			if (v == null)
				throw new NotFound("Validity Not Found");
			else 
				em.remove(v);
		}
		else {
			throw new NotAllowed("You are not an Employee you can't delete a Validity");
		}
	}
}
