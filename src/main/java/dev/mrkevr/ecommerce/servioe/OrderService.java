package dev.mrkevr.ecommerce.servioe;

import java.util.List;

import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.dto.OrderResponse;

public interface OrderService {
	
	OrderRequest previewOrderRequest();
	
	List<OrderResponse> getAllByUserId(String userId);
	
	OrderResponse addOrder(OrderRequest orderRequest);
	
	
}
