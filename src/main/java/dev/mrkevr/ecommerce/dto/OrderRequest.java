package dev.mrkevr.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
	
	@NotBlank(message = "Delivery Address must not be blank.")
	private String deliveryAddress;
	
	@NotBlank(message = "Payment Method must not be blank.")
	private String paymentMethod;
	
	private String message;
}