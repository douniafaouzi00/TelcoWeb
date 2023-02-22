package it.polimi.db2.telco.exceptions;

public class WrongStartDate  extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongStartDate(String message) {
		super(message);
	}
}
