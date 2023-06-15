package dev.mrkevr.ecommerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponse {

	private String id;

	private UserProfileResponse userProfileResponse;

	private double totalPrice;

	private int totalItems;

	private List<CartItemResponse> cartItemResponses;
}
