package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Employee;
import it.polimi.db2.telco.entities.Optional_Product;
import it.polimi.db2.telco.entities.Package;
import it.polimi.db2.telco.entities.Service;
import it.polimi.db2.telco.entities.Validity;
import it.polimi.db2.telco.exceptions.CredentialsException;
import it.polimi.db2.telco.exceptions.NotAllowed;
import it.polimi.db2.telco.exceptions.NotFound;


@Stateless
public class PackageService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;
	public PackageService(){}
	
	public Package findById(int packageId) {
		return em.find(Package.class, packageId);
	}


	public List<Package> findAllPackages() {

		return em.createNamedQuery("Package.findAll", Package.class).getResultList();

	}

	public Package createPackage(Employee emp, String name, List<Service> services, List<Optional_Product> products, List<Validity> validities) throws NotAllowed, NonUniqueResultException, CredentialsException {
		Package p = new Package();
		if (emp!=null){
			p.setName(name);
			p.setEmployee(emp);
			p.setServices(services);
			p.setProducts(products);
			p.setValidity(validities);
			em.persist(p);
			em.flush();
			return p;
		}
		else {
			throw new NotAllowed("You are not an Employee you can't create a package");
		}
	}
	
	public Package addService(String username, int packageId, int serviceId) throws NotAllowed, NotFound, NonUniqueResultException, CredentialsException{
		EmployeeService e= new EmployeeService();
		Employee emp= e.findByUsername(username);
		if (emp!=null){
			Package p= em.find(Package.class, packageId);
			if(p!=null) {
				Service s=em.find(Service.class,serviceId);
				if(s!=null) {
					p.addService(s);
					em.persist(p);
					em.flush();
					return p;
				}
				else {
					throw new NotFound("Service Not Found");

				}			
			}
			else {
				throw new NotFound("Package Not Found");

			}
		}
		else {
			throw new NotAllowed("You are not an Employee you can't change a package");
		}
	}
	
	public Package addProduct(String username, int packageId, int productId) throws NotAllowed, NotFound, NonUniqueResultException, CredentialsException{
		EmployeeService e= new EmployeeService();
		Employee emp= e.findByUsername(username);
		if (emp!=null){
			Package p= em.find(Package.class, packageId);
			if(p!=null) {
				Optional_Product op=em.find(Optional_Product.class,productId);
				if(op!=null) {
					p.addProduct(op);
					em.persist(p);
					em.flush();
					return p;
				}
				else {
					throw new NotFound("Product Not Found");

				}			
			}
			else {
				throw new NotFound("Package Not Found");

			}
		}
		else {
			throw new NotAllowed("You are not an Employee you can't change a package");
		}
	}
	
	public Package addValidity(String username, int packageId, int validityId) throws NotAllowed, NotFound, NonUniqueResultException, CredentialsException{
		EmployeeService e= new EmployeeService();
		Employee emp= e.findByUsername(username);
		if (emp!=null){
			Package p= em.find(Package.class, packageId);
			if(p!=null) {
				Validity v=em.find(Validity.class,validityId);
				if(v!=null) {
					p.addValidity(v);
					em.persist(p);
					em.flush();
					return p;
				}
				else {
					throw new NotFound("Validity Not Found");

				}			
			}
			else {
				throw new NotFound("Package Not Found");

			}
		}
		else {
			throw new NotAllowed("You are not an Employee you can't change a package");
		}
	}
	

	public void deletePackage(String username, int packageId) throws NotAllowed, NotFound, NonUniqueResultException, CredentialsException{
		EmployeeService e= new EmployeeService();
		Employee emp= e.findByUsername(username);
		if (emp!=null){
			Package p = em.find(Package.class, packageId);
			if (p == null)
				throw new NotFound("Package Not Found");
			else 
				em.remove(p);
		}
		else {
			throw new NotAllowed("You are not an Employee you can't delete a package");
		}
	}
}
