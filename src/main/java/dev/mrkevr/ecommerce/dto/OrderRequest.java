package dev.mrkevr.ecommerce.dto;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
	
	@NotBlank(message = "User Id must not be blank.")
	private String userId;
	
	@NotBlank(message = "Shopping Cart Id must not be blank.")
	private String shoppingCartId;
	
	@NotEmpty(message = "Cart Items must not be empty.")
	private List<CartItemResponse> cartItems;
	
	@NotBlank(message = "Payment Method must not be blank.")
	private String paymentMethod;
	
	@Nullable
	private String message;
	
	@NotBlank(message = "Delivery Address must not be blank.")
	private String deliveryAddress;
}