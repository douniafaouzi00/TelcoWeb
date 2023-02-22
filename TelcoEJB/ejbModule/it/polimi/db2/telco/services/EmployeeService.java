package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.exceptions.CredentialsException;

@Stateless
public class EmployeeService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;

	public EmployeeService() {
	}

	public Employee checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<Employee> eList = null;
		try {
			eList = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (eList.isEmpty())
			return null;
		else if (eList.size() == 1)
			return eList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
	
	public Employee findByUsername(String username) throws CredentialsException, NonUniqueResultException {
		List<Employee> eList = null;
		try {
			eList = em.createQuery("Select u from Employee u where u.username = :user ", Employee.class).setParameter("user", username).getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (eList.isEmpty())
			return null;
		else if (eList.size() == 1)
			return eList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");
	}
	
}
