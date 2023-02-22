package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "suspended_order", schema = "telco_db")
@NamedQuery(name="Suspended_Order.findAll", query="SELECT a FROM Suspended_Order a")
public class Suspended_Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int orderId;
	@Temporal(TemporalType.DATE)
	private Date date;
	private Time time;
	private Float amount;
	@Temporal(TemporalType.DATE)
	private Date start_date;
	private String username;
	private String name;
	private int month;

	public Time getTime() {
		return time;
	}

	public Float getAmount() {
		return amount;
	}

	public String getUsername() {
		return username;
	}
	public int getId() {
		return orderId;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Date getStartDate() {
		return start_date;
	}

	public String getName() {
		return name;
	}

	public int getMonth() {
		return month;
	}
	

}
