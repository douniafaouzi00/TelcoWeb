package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_package", schema = "telco_db")
@NamedQuery(name="Purchase_Package.findAll", query="SELECT a FROM Purchase_Package a")
public class Purchase_Package implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int packageId;
	private String name;
	private int tot_purchase;
	
	public int getId() {
		return this.packageId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTot_purchase() {
		return tot_purchase;
	}

}
