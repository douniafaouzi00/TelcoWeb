package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "activation_schedule", schema = "telco_db")
@NamedQuery(name = "Activation_Schedule.allSchedule", 
query = "SELECT r FROM Activation_Schedule r  WHERE r.user_fk = :user ")
public class Activation_Schedule implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	
	@Temporal(TemporalType.DATE)
	private Date date_activation;
	
	@Temporal(TemporalType.DATE)
	private Date date_deactivation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_fk", referencedColumnName="userId")
	private User user_fk;
	
	@ManyToMany(fetch = FetchType.LAZY,  cascade= {CascadeType.REMOVE})
	@JoinTable(name="schedule_product", joinColumns=@JoinColumn(name="schedule_fk"), 
	inverseJoinColumns=@JoinColumn(name="product_fk"))
	private List<Optional_Product> products= new ArrayList<Optional_Product>();
	
	@ManyToMany(fetch = FetchType.LAZY,  cascade= {CascadeType.REMOVE})
	@JoinTable(name="schedule_service", joinColumns=@JoinColumn(name="schedule_fk"), 
	inverseJoinColumns=@JoinColumn(name="service_fk"))
	private List<Service> services = new ArrayList<Service>();
	
	public Activation_Schedule(){}
	
	public Date getDateActivation() {
		return date_activation;
	}

	public void setDateActivation(Date date) {
		this.date_activation = date;
	}
	
	public Date getDateDeactivation() {
		return date_deactivation;
	}

	public void setDateDectivation(Date date) {
		this.date_deactivation = date;
	}
	
	
	public List<Service> getServices(){
		return this.services;
	}
	
	public List<Optional_Product> getProducts(){
		return this.products;
	}

	public User getUser() {
		return this.user_fk;
	}
	
	public void setServices(List<Service> s){
		this.services=s;
	}
	
	public void addServices(Service s){
		getServices().add(s);
	}
	
	public void setProducts(List<Optional_Product> p){
		this.products=p;
	}
	
	public void addProduct(Optional_Product op){
		getProducts().add(op);
	}

	public void setUser(User u) {
		this.user_fk=u;
	}
}
