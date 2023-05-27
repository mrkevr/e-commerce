package dev.mrkevr.ecommerce.entity;

import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import dev.mrkevr.ecommerce.entity.generator.GeneticEntityIdentifierGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "ShoppingCart")
@Table(name = "shopping_carts")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart extends GenericEntity {

	@Id
	@GenericGenerator(name = "shopping_cart_id_seq", type = GeneticEntityIdentifierGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_cart_id_seq")
	@Column(name = "shopping_cart_id", updatable = false)
	private String id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	@Column(name = "total_price")
	private double totalPrice;

	@Column(name = "total_items")
    private int totalItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItem> cartItems;
    
    
	@Override
	public String getIdPrefix() {
		return "SHCT";
	}
}
