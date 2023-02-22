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
@Table(name = "telephone_phone", schema = "telco_db")
public class Telephone_Phone implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="service_fk", referencedColumnName="serviceId")
	private Service service_fk;
	
	public Telephone_Phone() {}
	
	public Telephone_Phone(Service s) {
		this.service_fk=s;
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
}