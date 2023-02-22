package it.polimi.db2.telco.entities;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "user", schema = "telco_db")
@NamedQuery(name = "User.checkCredentials", 
query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2")

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	private String username;

	private String email;

	private String password;

	private String flag;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "userId_fk", 
			cascade = { CascadeType.REMOVE}, orphanRemoval = true)
	private Alert alert;
	
	@OneToMany(mappedBy="user_fk", fetch = FetchType.LAZY, 
			cascade= {CascadeType.REMOVE, CascadeType.REFRESH})
	private List<Order> orders;
	
	@OneToMany(mappedBy="user_fk", fetch = FetchType.EAGER, 
			cascade= {CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
	private List<Activation_Schedule> schedules;
	
	public User() {
	}

	public int getId() {
		return this.userId;
	}

	public void setId(int id) {
		this.userId = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String mail) {
		this.email = mail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFlag() {
		return this.flag;
	}
	
	public void setFlag(String flag) {
		this.flag=flag;
	}

	public Alert getAlerts() {
		return this.alert;
	}
	
	public List<Order> getOrders() {
		return this.orders;
	}

}