package it.polimi.db2.telco.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "employee", schema = "telco_db")
@NamedQuery(name = "Employee.checkCredentials", 
query = "SELECT r FROM Employee r  WHERE r.username = ?1 and r.password = ?2")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;
	
	private String username;

	private String email;

	private String password;
	
	@OneToMany(mappedBy="employee_fk", fetch = FetchType.LAZY, cascade= {CascadeType.REMOVE})
	private List<Package> packages;
	
	@OneToMany(mappedBy="employee_fk", fetch = FetchType.LAZY, cascade= {CascadeType.REMOVE})
	private List<Optional_Product> products;
	
	public int getId() {
		return this.employeeId;
	}

	public void setId(int id) {
		this.employeeId = id;
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

}
