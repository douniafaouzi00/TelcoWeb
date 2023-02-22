package it.polimi.db2.telco.services;



import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;

import it.polimi.db2.telco.entities.Order;
import it.polimi.db2.telco.entities.User;
import it.polimi.db2.telco.exceptions.*;
import java.util.List;

@Stateless
public class UserService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;

	public UserService() {
	}
	
	public User findByUsername(String username) throws CredentialsException {
		List<User> uList = null;
		try {
			uList = em.createQuery("Select u from Employee u where u.username = :user ", User.class).setParameter("user", username).getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");
	}
	
	public User checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}
	
	public User CreateProfile(String username, String email, String password) throws CredentialsException, NonUniqueResultException {
		List<User> uList = null;
		try {
			uList = em.createQuery("Select u from User u where u.username = :user ", User.class).setParameter("user", username).getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		User u= new User();
		if (uList.isEmpty()) {
			u.setUsername(username);
			u.setEmail(email);
			u.setPassword(password);
    		em.persist(u);	}	
    	else if (uList.size() > 1)
			throw new NonUniqueResultException("More than one user registered with same credentials");
		return u;
    	
    }
	
	public List<Order> findOrders(String username) throws CredentialsException{
		User usr= findByUsername(username);
		return usr.getOrders();
	}
}
