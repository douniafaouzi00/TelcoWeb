package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "average_product", schema = "telco_db")
@NamedQuery(name="Average_Product.findAll", 
query="SELECT a FROM Average_Product a")
public class Average_Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int packageId;
	private String name;
	private float average_product;
	private int sum_product;
	private int num_orders;
	
	public int getPackageId() {
		return this.packageId;
	}

	public String getName() {
		return name;
	}

	public float getAverage_product() {
		return average_product;
	}

	public int getSum_product() {
		return sum_product;
	}

	public int getNum_orders() {
		return num_orders;
	}


}