package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "totsales", schema = "telco_db")
@NamedQuery(name="Totsales.findAll", query="SELECT a FROM Totsales a")
public class Totsales implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int packageId;
	
	private String name;
	private float totsales_with;
	private float totsales_without;
	
	public int getId() {
		return this.packageId;
	}

	public String getName() {
		return name;
	}

	public float getTotsales_with() {
		return totsales_with;
	}

	public float getTotsales_without() {
		return totsales_without;
	}
}
