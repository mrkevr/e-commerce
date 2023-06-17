package dev.mrkevr.ecommerce.servioe.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.Product;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.exception.CartItemNotFoundException;
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
		if(user.getShoppingCart() == null) {
			throw new ShoppingCartNotFoundException();
		}
		return shoppingCartMapper.toResponse(user.getShoppingCart());
	}
	
	/**
	 * Add cart item to user's shopping cart
	 * 
	 * @param userId id of the User
	 * @param productId id of the Product
	 * @param quantity quantity of the Product
	 * 
	 */
	@Override
	@Transactional
	public ShoppingCartResponse addCartItem(String userId, String productId, int quantity) 
	{
		// Fetching the user and product
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		Product product = productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
		
		// Fetch the shopping cart, create new instance if it doesn't exist
		ShoppingCart shoppingCart = user.getShoppingCart();
		if(shoppingCart == null) shoppingCart = new ShoppingCart();
		
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
		
		// Persist the cart item
		cartItemRepo.save(cartItem);
		
		// Updating the shopping cart
		shoppingCart.setCartItems(cartItems);
		shoppingCart.setTotalItems(this.computeTotalItems(shoppingCart.getCartItems()));
		shoppingCart.setTotalPrice(this.computeTotalPrice(shoppingCart.getCartItems()));		
		shoppingCart.setUser(user);
		user.setShoppingCart(shoppingCart);
		ShoppingCart savedShoppingCart = shoppingCartRepo.save(shoppingCart);
		
		// Map the persisted entity to dto and return
		ShoppingCartResponse response = shoppingCartMapper.toResponse(savedShoppingCart);
		return response;
	}
	
	/*
	 * Modify the product quantity of cart item
	 */
	@Override
	@Transactional
	public ShoppingCartResponse updateCartItem(String userId, String cartItemId, int quantity) 
	{	
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		ShoppingCart shoppingCart = user.getShoppingCart();
		Set<CartItem> cartItems = shoppingCart.getCartItems();
		CartItem cartItemToUpdate = cartItems.stream()
			.filter(item -> item.getId().equals(cartItemId))
			.findFirst()
			.orElseThrow(() -> new CartItemNotFoundException(user, cartItemId));
		
		// Compute new unit price based on product price and new quantity
		Product product = cartItemToUpdate.getProduct();
		double unitPrice;
		if(product.isOnSale()) {
			unitPrice = product.getSalePrice() * quantity;
		} else {
			unitPrice = product.getCostPrice() * quantity;
		}
		
		// Set new unit price and quantity
		cartItemToUpdate.setUnitPrice(unitPrice);
		cartItemToUpdate.setQuantity(quantity);
			
		// Save cart item to db
		cartItemRepo.save(cartItemToUpdate);
		
		// Update shopping cart's total items and price
		shoppingCart.setTotalItems(this.computeTotalItems(shoppingCart.getCartItems()));
		shoppingCart.setTotalPrice(this.computeTotalPrice(shoppingCart.getCartItems()));
		
		// Save to shopping cart to db
		ShoppingCart savedShoppingCart = shoppingCartRepo.save(shoppingCart);
		// Map the persisted entity to dto and return
		ShoppingCartResponse response = shoppingCartMapper.toResponse(savedShoppingCart);
		return response;
	}
	
	/**
	 * Delete cart item from the user's shopping cart and db
	 * 
	 * @param userId id of the User
	 * @param cartItemId id of the CartItem
	 */
	@Override
	@Transactional
	public ShoppingCartResponse deleteCartItem(String userId, String cartItemId) 
	{	
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		ShoppingCart shoppingCart = user.getShoppingCart();
		Set<CartItem> cartItems = shoppingCart.getCartItems();
		CartItem cartItemToDelete = cartItems.stream()
			.filter(item -> item.getId().equals(cartItemId))
			.findFirst()
			.orElseThrow(() -> new CartItemNotFoundException(user, cartItemId));
		
		// Remove/delete
		cartItemRepo.delete(cartItemToDelete);
		cartItems.remove(cartItemToDelete);
		
		// Update shopping cart's total items and price
		shoppingCart.setCartItems(cartItems);
		shoppingCart.setTotalItems(this.computeTotalItems(shoppingCart.getCartItems()));
		shoppingCart.setTotalPrice(this.computeTotalPrice(shoppingCart.getCartItems()));
		
		// Save to shopping cart to db
		ShoppingCart savedShoppingCart = shoppingCartRepo.save(shoppingCart);
		// Map the persisted shopping cart to dto and return
		ShoppingCartResponse response = shoppingCartMapper.toResponse(savedShoppingCart);
		return response;
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
