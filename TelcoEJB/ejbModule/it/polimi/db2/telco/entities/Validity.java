package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "validity", schema = "telco_db")
@NamedQuery(name="Validity.findAll", query="SELECT a FROM Validity a")
public class Validity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int validityId;
	
	private int month;
	
	private float monthly_fee;
	
	@OneToMany(mappedBy="validity_fk", fetch = FetchType.LAZY)
	private List<Order> orders;
	
	@ManyToMany(mappedBy="validities", fetch = FetchType.LAZY, 
			cascade= {CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Package> packages;
	
	public Validity() {}

	public int getId() {
		return validityId;
	}
	
	
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public float getMonthly_fee() {
		return monthly_fee;
	}

	public void setMonthly_fee(float monthly_fee) {
		this.monthly_fee = monthly_fee;
	}
	
	public void addPackage(Package p) {
		packages.add(p);
	}
}