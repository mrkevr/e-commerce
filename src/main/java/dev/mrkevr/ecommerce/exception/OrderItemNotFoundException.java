package dev.mrkevr.ecommerce.exception;

public class OrderItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7012697891147135174L;
	
	public OrderItemNotFoundException() {
		super("Could not find order tiem");
	}

	public OrderItemNotFoundException(String id) {
		super("Could not find order item with id " + id);
	}
}
