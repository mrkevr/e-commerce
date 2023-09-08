package dev.mrkevr.ecommerce.exception;

public class IllegalRequestException extends RuntimeException {

	private static final long serialVersionUID = -1567766341746521201L;

	public IllegalRequestException() {
		super("Illegal request, check your parameters");
	}

	public IllegalRequestException(String message) {
		super(message);
	}
}
