package dev.mrkevr.ecommerce.servioe.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.Product;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.exception.ProductNotFoundException;
import dev.mrkevr.ecommerce.exception.ShoppingCartNotFoundException;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
import dev.mrkevr.ecommerce.mapper.ShoppingCartMapper;
import dev.mrkevr.ecommerce.repository.CartItemRepository;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.servioe.ShoppingCartService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	private final ShoppingCartRepository shoppingCartRepo;
    private final CartItemRepository cartItemRepo;
    private final UserRepository userRepo;
	private final ProductRepository productRepo;
    
    private final ShoppingCartMapper shoppingCartMapper;
	
	@Override
	public ShoppingCartResponse getById(String id) 
	{
		ShoppingCart shoppingCart = shoppingCartRepo.findById(id).orElseThrow(() -> new ShoppingCartNotFoundException(id));
		return shoppingCartMapper.toResponse(shoppingCart);
	}

	@Override
	public ShoppingCartResponse getByUserId(String id) 
	{
		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		if(user.getCart() == null) {
			throw new ShoppingCartNotFoundException();
		}
		return shoppingCartMapper.toResponse(user.getCart());
	}

	@Override
	@Transactional
	public ShoppingCartResponse addCartItem(String userId, String productId, int quantity) 
	{
		// Fetching the user and product
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
		
		// Fetch the shopping cart, create new instance if it doesnt exist
		ShoppingCart shoppingCart = shoppingCartRepo.findByUser(user).orElseGet(() -> {
			ShoppingCart newCart = new ShoppingCart();
			newCart.setUser(user);
			return newCart;
		});
		
		// Define new cart item set if null
		Set<CartItem> cartItems = shoppingCart.getCartItems();
		if(cartItems == null) cartItems = new HashSet<>();
		
		// Computing the unit price
		double unitPrice;
		if(product.isOnSale()) {
			unitPrice = product.getSalePrice() * quantity;
		} else {
			unitPrice = product.getCostPrice() * quantity;
		}
		
		// Define the cart item and add it to cart items
		CartItem cartItem = CartItem.builder()
			.shoppingCart(shoppingCart)
			.product(product)
			.quantity(quantity)
			.unitPrice(unitPrice)
			.build();
		cartItems.add(cartItem);
		
		// Persist the cart item to database
		cartItemRepo.save(cartItem);
		
		shoppingCart.setCartItems(cartItems);
		shoppingCart.setTotalItems(this.computeTotalItems(shoppingCart.getCartItems()));
		shoppingCart.setTotalPrice(this.computeTotalPrice(shoppingCart.getCartItems()));
		
		ShoppingCart savedShoppingCart = shoppingCartRepo.save(shoppingCart);
		
		return shoppingCartMapper.toResponse(savedShoppingCart);
	}

	@Override
	public ShoppingCartResponse updateCart(String userId, String productId, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCartResponse removeItemFromCart(String userId, String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCartResponse addItemToCartSession(ShoppingCartResponse cartDto, String productId, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCartResponse updateCartSession(ShoppingCartResponse cartDto, String productId, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCartResponse removeItemFromCartSession(ShoppingCartResponse cartDto, String productId,
			int quantity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private int computeTotalItems(Collection<CartItem> cartItems) {
		return cartItems.stream().mapToInt(item -> item.getQuantity()).sum();
    }

    private double computeTotalPrice(Collection<CartItem> cartItems) {
    	return cartItems.stream().mapToDouble(item -> item.getUnitPrice()).sum();
    }

	
}
