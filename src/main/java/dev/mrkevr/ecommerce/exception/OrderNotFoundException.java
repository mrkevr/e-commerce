package dev.mrkevr.ecommerce.exception;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7001506905008015091L;

	public OrderNotFoundException(String id) {
		super("Could not find order with id number " + id);
	}

}
