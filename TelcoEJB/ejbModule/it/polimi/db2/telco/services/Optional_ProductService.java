package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.exceptions.NotAllowed;
import it.polimi.db2.telco.exceptions.NotFound;

@Stateless
public class Optional_ProductService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;
	
	public Optional_ProductService() {}
	
	public Optional_Product findById(int productId) {
		return em.find(Optional_Product.class, productId);
	}

	public List<Optional_Product> findAllOptional_Product() {

		return em.createNamedQuery("Optional_Product.findAll", Optional_Product.class).getResultList();

	}

	public Optional_Product createOptional_Product(Employee emp, String name, float mf) throws NotAllowed, NonUniqueResultException, CredentialsException {
		Optional_Product op = new Optional_Product();
		if (emp!=null){
			op.setName(name);
			op.setMonthly_fee(mf);
			op.setEmployee(emp);
			em.persist(op);
			em.flush();
			return op;
		}
		else {
			throw new NotAllowed("You are not an Employee you can't create an Optional_Product");
		}
	}
	
	public void deleteProduct(String username, int id) throws NotFound, NotAllowed, NonUniqueResultException, CredentialsException {
		EmployeeService e= new EmployeeService();
		Employee emp= e.findByUsername(username);
		if (emp!=null){
			Optional_Product op = em.find(Optional_Product.class, id);
			if (op == null)
				throw new NotFound("Optional_Product Not Found");
			else 
				em.remove(op);
		}
		else {
			throw new NotAllowed("You are not an Employee you can't delete an Optional_Product");
		}
	}
}
