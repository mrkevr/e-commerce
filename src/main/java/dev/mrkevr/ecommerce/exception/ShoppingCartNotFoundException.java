package dev.mrkevr.ecommerce.exception;

public class ShoppingCartNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8643428928973144087L;
	
	public ShoppingCartNotFoundException() {
		super("Could not find shopping cart");
	}

	public ShoppingCartNotFoundException(String id) {
		super("Could not find shopping cart with id " + id);
	}
	
	
}
