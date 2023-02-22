package it.polimi.db2.telco.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import it.polimi.db2.telco.entities.Alert;
import it.polimi.db2.telco.entities.Average_Product;
import it.polimi.db2.telco.entities.Best_Seller;
import it.polimi.db2.telco.entities.Insolvent_User;
import it.polimi.db2.telco.entities.Purchase_Package;
import it.polimi.db2.telco.entities.Purchase_Package_Validity;
import it.polimi.db2.telco.entities.Suspended_Order;
import it.polimi.db2.telco.entities.Totsales;
import it.polimi.db2.telco.exceptions.CredentialsException;

@Stateless
public class SaleReportService {
	@PersistenceContext(unitName = "telcoEJB")
	private EntityManager em;
	
	public SaleReportService() {}

	public  List<Average_Product> findAverage_Product() throws NonUniqueResultException, CredentialsException {
		return em.createNamedQuery("Average_Product.findAll", Average_Product.class).getResultList();

	}
	
	public Best_Seller findBestSeller() throws NonUniqueResultException, CredentialsException {
		Best_Seller found = null;
		List<Best_Seller> results = em.createNamedQuery("Best_Seller.findAll", Best_Seller.class).getResultList();
		if (results.size() > 0)
			found = results.get(0);
		return found;

	}
	
	public List<Insolvent_User> findInsolventUser() throws NonUniqueResultException, CredentialsException {
		return em.createNamedQuery("Insolvent_User.findAll", Insolvent_User.class).getResultList();
	}
	
	public List<Purchase_Package> findPurchasePackage() throws NonUniqueResultException, CredentialsException {
		return em.createNamedQuery("Purchase_Package.findAll", Purchase_Package.class).getResultList();
	}
	
	public List<Purchase_Package_Validity> findPurchasePackageValidity() throws NonUniqueResultException, CredentialsException {
		return em.createNamedQuery("Purchase_Package_Validity.findAll", Purchase_Package_Validity.class).getResultList();
	}
	
	public List<Suspended_Order> findSuspendedOrder() throws NonUniqueResultException, CredentialsException {
		return em.createNamedQuery("Suspended_Order.findAll", Suspended_Order.class).getResultList();
	}
	
	public List<Totsales> findTotsales() throws NonUniqueResultException, CredentialsException {
			return em.createNamedQuery("Totsales.findAll", Totsales.class).getResultList();
	}
	
	public List<Alert> findAlert() throws  NonUniqueResultException, CredentialsException {
		return em.createNamedQuery("Alert.findAll", Alert.class).getResultList();

	}
	
}
