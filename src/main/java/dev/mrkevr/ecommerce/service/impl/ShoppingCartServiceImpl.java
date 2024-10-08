package dev.mrkevr.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.mrkevr.ecommerce.dto.ShoppingCartResponse;
import dev.mrkevr.ecommerce.entity.CartItem;
import dev.mrkevr.ecommerce.entity.Product;
import dev.mrkevr.ecommerce.entity.ShoppingCart;
import dev.mrkevr.ecommerce.entity.User;
import dev.mrkevr.ecommerce.error.InsufficientStockError;
import dev.mrkevr.ecommerce.exception.CartItemNotFoundException;
import dev.mrkevr.ecommerce.exception.ProductNotFoundException;
import dev.mrkevr.ecommerce.exception.ShoppingCartNotFoundException;
import dev.mrkevr.ecommerce.exception.UserNotFoundException;
import dev.mrkevr.ecommerce.mapper.ShoppingCartMapper;
import dev.mrkevr.ecommerce.repository.CartItemRepository;
import dev.mrkevr.ecommerce.repository.ProductRepository;
import dev.mrkevr.ecommerce.repository.ShoppingCartRepository;
import dev.mrkevr.ecommerce.repository.UserRepository;
import dev.mrkevr.ecommerce.service.ShoppingCartService;
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
	public ShoppingCartResponse getById(String id) {
		ShoppingCart shoppingCart = shoppingCartRepo.findById(id).orElseThrow(() -> new ShoppingCartNotFoundException(id));
		return shoppingCartMapper.toResponse(shoppingCart);
	}

	@Override
	public ShoppingCartResponse getByUserId(String id) {
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
	 */
	@Transactional
	@Override
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
		
		Optional<CartItem> commonCartItem = this.findCommonCartItem(cartItems, productId);
		if(commonCartItem.isPresent()) {
			CartItem cartItemToUpdate = commonCartItem.get();
			cartItemToUpdate.setQuantity(cartItemToUpdate.getQuantity() + quantity);
			cartItemToUpdate.setUnitPrice(cartItemToUpdate.getUnitPrice() + unitPrice);
			cartItemRepo.save(cartItemToUpdate);
		} else {
			// Create new cart item and add it to cart items
			CartItem cartItem = CartItem.builder()
				.shoppingCart(shoppingCart)
				.product(product)
				.quantity(quantity)
				.unitPrice(unitPrice)
				.build();
			
			// Add to Users' Shopping Cart
			shoppingCart.addCartItem(cartItem);
			// Persist the cart item
			cartItemRepo.save(cartItem);
		}
		
		// Updating the shopping cart
		shoppingCart.setCartItems(cartItems);
		shoppingCart.setTotalItems(this.computeTotalItems(shoppingCart.getCartItems()));
		shoppingCart.setTotalPrice(this.computeTotalPrice(shoppingCart.getCartItems()));		
		shoppingCart.setUser(user);
		user.setShoppingCart(shoppingCart);
		ShoppingCart savedShoppingCart = shoppingCartRepo.save(shoppingCart);
		
		// Map the persisted entity to dto and return
        return shoppingCartMapper.toResponse(savedShoppingCart);
	}
	
	/**
	 * Modifies {@link CartItem} from the {@link User}'s {@link ShoppingCart}
	 * 
	 * @param userId Id of the {@link User}
	 * @param cartItemId Id of the {@link CartItem} to be updated
	 * @return Instance of {@link ShoppingCartResponse} mapped from the updated {@link ShoppingCart}
	 */
	@Transactional
	@Override
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
        return shoppingCartMapper.toResponse(savedShoppingCart);
	}
	
	/**
	 * Deletes {@link CartItem} from the {@link User}'s {@link ShoppingCart}
	 * 
	 * @param userId Id of the {@link User}
	 * @param cartItemId Id of the {@link CartItem} to be deleted
	 * @return Instance of {@link ShoppingCartResponse} mapped from the updated {@link ShoppingCart}
	 */
	@Transactional
	@Override
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
        return shoppingCartMapper.toResponse(savedShoppingCart);
	}
	
	@Override
	public List<InsufficientStockError> checkProductAvailability(ShoppingCart shoppingCart) 
	{	
		List<InsufficientStockError> errors = new ArrayList<>();
		Set<CartItem> cartItems = shoppingCart.getCartItems();
		cartItems.forEach(item -> {
			int stock = item.getProduct().getCurrentQuantity();
			if(stock < item.getQuantity()) {
				errors.add(new InsufficientStockError(
					item.getId(), 
					item.getProduct().getId(), 
					item.getProduct().getName()));
			}
		});
		return errors;
	}
	
	@Override
	@Transactional
	public void clearShoppingCart(ShoppingCart shoppingCart) {

		Set<CartItem> cartItems = shoppingCart.getCartItems();
		cartItems.clear();
		// Update shopping cart's total items and price
		shoppingCart.setCartItems(cartItems);
		shoppingCart.setTotalItems(0);
		shoppingCart.setTotalPrice(0);
		
		// Save to shopping cart to db
		shoppingCartRepo.save(shoppingCart);		
	}
	
	//-- Helper methods --//
	private int computeTotalItems(Collection<CartItem> cartItems) {
		return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    private double computeTotalPrice(Collection<CartItem> cartItems) {
    	return cartItems.stream().mapToDouble(CartItem::getUnitPrice).sum();
    }

	private Optional<CartItem> findCommonCartItem(Collection<CartItem> cartItems, String productId) {
		if (cartItems == null) {
            return Optional.empty();
        }
		return cartItems.stream()
				.filter(item -> item.getProduct().getId().equals(productId))
				.findAny();
	}
}
