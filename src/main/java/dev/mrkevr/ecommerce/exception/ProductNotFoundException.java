package dev.mrkevr.ecommerce.exception;

public class ProductNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3404651003239129945L;

	public ProductNotFoundException() {
		super("Could not find product");
	}

	public ProductNotFoundException(long id) {
		super("Could not find product with id number " + id);
	}
}