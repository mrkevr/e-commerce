package dev.mrkevr.ecommerce.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsufficientStockError {
	
	private String cartItemId;
	
	private String productId;
	
	private String productName;
}
