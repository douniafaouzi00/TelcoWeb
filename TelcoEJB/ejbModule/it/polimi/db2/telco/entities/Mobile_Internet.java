package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mobile_internet", schema = "telco_db")
public class Mobile_Internet implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="service_fk", referencedColumnName="serviceId")
	private Service service_fk;
	
	private int giga;
	private float giga_fee;
	
	public Mobile_Internet() {}
	
	public Mobile_Internet(Service id, int g, float f) {
		this.service_fk=id;
		this.giga=g;
		this.giga_fee=f;
	}
	
	public int getId() {
		return Id;
	}
	
	public Service getService() {
		return service_fk;
	}
	
	public void setService(Service s) {
		this.service_fk=s;
	}
	
	public int getGiga() {
		return giga;
	}
	public void setGiga(int giga) {
		this.giga = giga;
	}
	public float getGiga_fee() {
		return giga_fee;
	}
	public void setGiga_fee(float fee_giga) {
		this.giga_fee = fee_giga;
	}
}