package dev.mrkevr.ecommerce.exception;

public class CategoryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8343660364214381359L;

	public CategoryNotFoundException() {
		super("Could not find category");
	}

	public CategoryNotFoundException(long id) {
		super("Could not find category with id number " + id);
	}
}
