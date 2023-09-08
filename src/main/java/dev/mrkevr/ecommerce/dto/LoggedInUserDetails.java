package dev.mrkevr.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoggedInUserDetails {

	private String id;
	
	private String username;
	
	private long totalCartItems;
	
	private long totalActiveOrders;
}
