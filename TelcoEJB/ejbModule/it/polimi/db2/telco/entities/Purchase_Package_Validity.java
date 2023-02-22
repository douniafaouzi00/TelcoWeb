package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "purchase_package_validity", schema = "telco_db")
@NamedQuery(name="Purchase_Package_Validity.findAll", 
query="SELECT a FROM Purchase_Package_Validity a")
public class Purchase_Package_Validity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int packageId;
	private int validityId;
	private String name;
	private int month;
	private int tot_purchase;
	
	public int getPackageId() {
		return this.packageId;
	}
	
	public int getValidityId() {
		return this.validityId;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTot_purchase() {
		return tot_purchase;
	}

	public int getMonth() {
		return month;
	}

}