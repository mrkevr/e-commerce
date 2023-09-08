package dev.mrkevr.ecommerce.exception;

import dev.mrkevr.ecommerce.entity.User;

public class CartItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5639970243980373695L;

	public CartItemNotFoundException() {
		super("Could not find cart item");
	}

	public CartItemNotFoundException(String id) {
		super("Could not find cart item with id " + id);
	}

	public CartItemNotFoundException(User user, String id) {
		super("Could not find cart item with id " + id + " associated to the user with id " + user.getId());
	}
}
