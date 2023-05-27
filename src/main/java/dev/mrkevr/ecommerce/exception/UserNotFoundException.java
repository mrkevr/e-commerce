package dev.mrkevr.ecommerce.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1239155370835576879L;

	public UserNotFoundException() {
		super("Could not find user");
	}

	public UserNotFoundException(long id) {
		super("Could not find user with id number " + id);
	}
}
