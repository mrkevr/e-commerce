package dev.mrkevr.ecommerce.exception;

import java.util.Collection;
import java.util.stream.Collectors;

import dev.mrkevr.ecommerce.error.InsufficientStockError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class InsufficientStockException extends RuntimeException {

	private static final long serialVersionUID = -2698019400843625829L;
	
	private Collection<InsufficientStockError> stockErrors;
	
	public InsufficientStockException(String productId) {
		super("Insufficient stock for the product with id number " + productId);
	}
	
	public InsufficientStockException(Collection<InsufficientStockError> stockErrors) {
		super("Insufficient stock for the following products : " + stockErrors.stream().map(e -> e.getProductName()).collect(Collectors.joining(",")));
		this.stockErrors = stockErrors;
	}
}
