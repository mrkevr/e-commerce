package dev.mrkevr.ecommerce.servioe;

import dev.mrkevr.ecommerce.dto.OrderRequest;
import dev.mrkevr.ecommerce.dto.OrderResponse;

public interface OrderService {
	
	OrderRequest previewOrderRequest();
	
	OrderResponse addOrder(OrderRequest orderRequest);
}
