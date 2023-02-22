package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "optional_product", schema = "telco_db")
@NamedQuery(name="Optional_Product.findAll", query="SELECT a FROM Optional_Product a")
public class Optional_Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	
	private String name;
	private float monthly_fee;
	private float tot_sales;
	
	@ManyToMany(mappedBy="products", fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<Order>();
	
	@ManyToMany(mappedBy="products", fetch = FetchType.LAZY, 
			cascade= {CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Package> packages = new ArrayList<Package>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employee_fk")
	private Employee employee_fk;
	
	@ManyToMany(mappedBy="products", fetch = FetchType.LAZY)
	private List<Activation_Schedule> schedules = new ArrayList<Activation_Schedule>();
	
	public Optional_Product(){}
	
	public int getId() {
		return productId;
	}
	
	public void setId(int id) {
		this.productId=id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getMonthly_fee() {
		return monthly_fee;
	}
	public void setMonthly_fee(float monthly_fee) {
		this.monthly_fee = monthly_fee;
	}
	public float getTot_sales() {
		return tot_sales;
	}
	public void setTot_sales(float tot_sales) {
		this.tot_sales = tot_sales;
	}
	
	public void setEmployee(Employee e) {
		this.employee_fk=e;
	}
	public List<Package> getPackages(){
		return this.packages;
	}
	public void addPackage(Package p) {
		getPackages().add(p);
	}
	
	public void setPackage(List<Package> p) {
		this.packages=p;
	}
	
	public List<Order> getOrders(){
		return this.orders;
	}
	public void addOrder(Order o) {
		getOrders().add(o);
	}
	
	public void setOrders(List<Order> o) {
		this.orders=o;
	}
	
	
	
}
