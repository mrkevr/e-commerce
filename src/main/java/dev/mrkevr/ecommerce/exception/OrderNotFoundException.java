package dev.mrkevr.ecommerce.exception;

public class OrderNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException(Long id) {
		super("Could not find order with id number " + id);
	}

}
