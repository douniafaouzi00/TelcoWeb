package it.polimi.db2.telco.exceptions;

public class NotFound extends Exception {
	private static final long serialVersionUID = 1L;

	public NotFound(String message) {
		super(message);
	}
}
