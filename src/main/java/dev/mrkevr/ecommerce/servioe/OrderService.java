package dev.mrkevr.ecommerce.servioe;

import dev.mrkevr.ecommerce.dto.OrderRequest;

public interface OrderService {
	
	OrderRequest previewOrderRequest(String userId, String shoppingCartId);
	
}
