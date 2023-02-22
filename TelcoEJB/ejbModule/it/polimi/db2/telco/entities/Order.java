package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "order", schema = "telco_db")
@NamedQuery(name = "Order.rejectedOrder", 
query = "SELECT r FROM Order r WHERE r.user_fk = :user and r.status = 'rejected'")

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	

	private LocalTime time;
	private Float amount;
	
	@Temporal(TemporalType.DATE)
	private Date start_date;
	
	private int rejections;
	
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_fk", referencedColumnName="userId")
	private User user_fk;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="package_fk")
	private Package package_fk;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="validity_fk")
	private Validity validity_fk;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="order_product", schema="telco_db" , 
	joinColumns=@JoinColumn(name="order_fk"), inverseJoinColumns=@JoinColumn(name="product_fk"))
	private List<Optional_Product> products = new ArrayList<Optional_Product>();
	
	public Order() {}
	
	public int getId() {
		return orderId;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public int getRejections() {
		return rejections;
	}
	

	public void setRejections(int rejections) {
		this.rejections = rejections;
	}
	
	public User getUser() {
		return this.user_fk;
	}
	
	public void setUser(User u) {
		this.user_fk=u;
	}
	
	public Package getPackage() {
		return this.package_fk;
	}
	
	public void setPackage(Package p) {
		this.package_fk=p;
	}
	
	public Validity getValidity() {
		return this.validity_fk;
	}
	
	public void setValidity(Validity v) {
		this.validity_fk=v;
	}
	
	public List<Optional_Product> getProducts() {
		return this.products;
	}
	
	public void setProducts(List<Optional_Product> op) {
		this.products=op;
	}
	
	public void addProduct(Optional_Product p) {
		getProducts().add(p);
		p.addOrder(this);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getStartDate() {
		return start_date;
	}

	public void setStartDate(Date date) {
		this.start_date = date;
	}
}
