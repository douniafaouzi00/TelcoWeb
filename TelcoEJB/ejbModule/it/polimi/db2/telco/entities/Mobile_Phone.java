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
@Table(name = "mobile_phone", schema = "telco_db")
public class Mobile_Phone implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="service_fk", referencedColumnName="serviceId")
	private Service service_fk;
	
	
	private int minutes;
	private float fee_minutes;
	private int sms;
	private float fee_sms;
	
	public Mobile_Phone(){}
	
	public Mobile_Phone(Service ser,int m,float fm, int s, float fs){
		this.fee_minutes=fm;
		this.fee_sms=fs;
		this.minutes=m;
		this.service_fk=ser;
		this.sms=s;
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
	
	
	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public float getFee_minutes() {
		return fee_minutes;
	}

	public void setFee_minutes(float fee_minutes) {
		this.fee_minutes = fee_minutes;
	}

	public int getSms() {
		return sms;
	}

	public void setSms(int sms) {
		this.sms = sms;
	}

	public float getFee_sms() {
		return fee_sms;
	}

	public void setFee_sms(float fee_sms) {
		this.fee_sms = fee_sms;
	}
	
}
