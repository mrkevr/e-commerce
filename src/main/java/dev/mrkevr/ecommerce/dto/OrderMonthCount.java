package dev.mrkevr.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMonthCount {
	
	private String month;
	private int orderCount;
	
}
