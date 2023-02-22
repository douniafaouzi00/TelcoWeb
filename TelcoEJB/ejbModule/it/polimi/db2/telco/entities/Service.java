package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
* The persistent class for the service database table.
* 
*/

@Entity
@Table(name = "service", schema = "telco_db")
@NamedQuery(name="Service.findAll", query="SELECT a FROM Service a")
public class Service  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int serviceId;
	
	private String type;
	
	@ManyToMany
	private List<Package> Packages;
	
	@ManyToMany(mappedBy="services", fetch = FetchType.LAZY, 
			cascade= {CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Package> packages;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "service_fk", cascade = 
		{ CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private Mobile_Phone mobile_phone;
	
	@OneToOne(mappedBy = "service_fk", fetch = FetchType.EAGER,  cascade = 
		{ CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private Mobile_Internet mobile_internet;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "service_fk", cascade = 
		{ CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private Telephone_Phone telephone_phone;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "service_fk", cascade = 
		{ CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
	private Telephone_Internet telephone_internet;
	
	@ManyToMany(mappedBy="services", fetch = FetchType.LAZY)
	private List<Activation_Schedule> schedules;
	
	
	public Service() {}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getId() {
		return serviceId;
	}
	
	public void setId(int id) {
		this.serviceId=id;
	}
	
	public Mobile_Phone getMP() {
		return mobile_phone;
	}
	
	public void setMP(Mobile_Phone mp) {
		this.mobile_phone=mp;
	}
	
	public Mobile_Internet getMI() {
		return mobile_internet;
	}
	
	public void setMI (Mobile_Internet mi) {
		this.mobile_internet=mi;
	}
	
	public void setTP(Telephone_Phone tp) {
		this.telephone_phone=tp;
	}
	
	public Telephone_Internet getTI() {
		return telephone_internet;
	}
	
	public void setTI(Telephone_Internet ti) {
		this.telephone_internet=ti;
	}
	
	public void addPackage(Package p) {
		packages.add(p);
	}
}
