package dev.mrkevr.ecommerce.exception;

public class CategoryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8343660364214381359L;

	public CategoryNotFoundException() {
		super("Could not find category");
	}

	public CategoryNotFoundException(String id) {
		super("Could not find category with id " + id);
	}
}
