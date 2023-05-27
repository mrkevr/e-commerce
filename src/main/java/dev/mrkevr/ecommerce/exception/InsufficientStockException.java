package dev.mrkevr.ecommerce.exception;

public class InsufficientStockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsufficientStockException(Long id) {
		super("Insufficient stock for the product with id number " + id);
	}
	
}
