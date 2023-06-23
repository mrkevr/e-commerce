package dev.mrkevr.ecommerce.exception;

public class RoleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7216949701409806099L;
	
	public RoleNotFoundException() {
		super("Could not find role.");
	}
	
	public RoleNotFoundException(String role) {
		super("Could not find category with the name:" +role);
	};
}
