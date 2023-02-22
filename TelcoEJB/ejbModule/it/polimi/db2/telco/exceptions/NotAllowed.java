package it.polimi.db2.telco.exceptions;

public class NotAllowed extends Exception {
	private static final long serialVersionUID = 1L;

	public NotAllowed(String message) {
		super(message);
	}
}
