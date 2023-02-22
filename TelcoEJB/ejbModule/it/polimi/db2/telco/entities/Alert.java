
package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
//import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "alert", schema = "telco_db")
@NamedQuery(name="Alert.findAll", query="SELECT a FROM Alert a")
public class Alert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userId_fk", referencedColumnName="userId")
	private User userId_fk;
	
	private String username;
	
	private String email;
	
	private int amount;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	

	private Time time;
	
	
	public Alert() {}
	
	public Alert(User user, int amount, Date date) {
		this.userId_fk=user;
		this.setUsername(user.getUsername());
		this.setEmail(user.getEmail());
		this.setAmount(amount);
		this.date=date;
	}
	public User getId() {
		return this.userId_fk;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setUser(User user) {
		this.userId_fk = user;
	}
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
	
	
	
}