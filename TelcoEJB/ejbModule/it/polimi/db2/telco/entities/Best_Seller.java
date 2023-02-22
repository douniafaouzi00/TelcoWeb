package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "best_seller", schema = "telco_db")
@NamedQuery(name="Best_Seller.findAll", query="SELECT a FROM Best_Seller a")
public class Best_Seller implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int productId;
	
	private String name;
	
	private float tot_sales;
	
	public int getId() {
		return productId;
	}
	
	public String getName() {
		return name;
	}

	public float getTot_sales() {
		return tot_sales;
	}
	
}
