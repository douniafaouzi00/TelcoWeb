package it.polimi.db2.telco.entities;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "package", schema = "telco_db")
@NamedQuery(name="Package.findAll", query="SELECT a FROM Package a")
public class Package implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int packageId;
	
	private String name;
	
	@OneToMany(mappedBy="package_fk", fetch = FetchType.LAZY)
	private List<Order> orders;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade= {CascadeType.REMOVE, CascadeType.REFRESH})
	@JoinTable(name="package_validity", joinColumns=@JoinColumn(name="package_fk"), 
	inverseJoinColumns=@JoinColumn(name="validity_fk"))
	private List<Validity> validities;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade= {CascadeType.REMOVE, CascadeType.REFRESH})
	@JoinTable(name="package_service", joinColumns=@JoinColumn(name="package_fk"),
	inverseJoinColumns=@JoinColumn(name="service_fk"))
	private List<Service> services;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
	@JoinTable(name="package_product", joinColumns=@JoinColumn(name="package_fk"), 
	inverseJoinColumns=@JoinColumn(name="product_fk"))
	private List<Optional_Product> products;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employee_fk")
	private Employee employee_fk;
	
	public Package() {
	}
	
	public int getId() {
		return packageId;
	}
	
	public void setId(int id) {
		this.packageId=id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmployee(Employee emp) {
		this.employee_fk = emp;
	}
	
	public void setServices(List<Service> s) {
		this.services=s;
	}
	
	public void setProducts(List<Optional_Product> p) {
		this.products=p;
	}
	
	public void setValidity(List<Validity> v) {
		this.validities=v;
	}
	
	public List<Validity> getValidities(){
		return validities;
	}
	
	public List<Service> getServices(){
		return services;
	}
	
	public List<Optional_Product> getProducts(){
		return products;
	}
	
	public void addService(Service s) {
		getServices().add(s);
		s.addPackage(this);
	}
	
	public void addProduct(Optional_Product p) {
		getProducts().add(p);
		p.addPackage(this);
	}
	
	public void addValidity(Validity v) {
		getValidities().add(v);
		v.addPackage(this);
	}
	
	
	
}
