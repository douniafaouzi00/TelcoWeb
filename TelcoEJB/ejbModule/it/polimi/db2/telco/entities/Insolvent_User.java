package it.polimi.db2.telco.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "insolvent_user", schema = "telco_db")
@NamedQuery(name="Insolvent_User.findAll", query="SELECT a FROM Insolvent_User a")
public class Insolvent_User implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Id
		private int userId;
		
		private String username;

		private String email;

		public int getId() {
			return this.userId;
		}

		public String getUsername() {
			return username;
		}

		public String getEmail() {
			return email;
		}		
		
}
