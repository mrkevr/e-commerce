package dev.mrkevr.ecommerce.entity;

import org.hibernate.annotations.GenericGenerator;

import dev.mrkevr.ecommerce.entity.generator.GeneticEntityIdentifierGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "CartItem")
@Table(name = "cart_items")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends GenericEntity {
	
	@Id
	@GenericGenerator(name = "cart_items_id_seq", type = GeneticEntityIdentifierGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_items_id_seq")
	@Column(name = "cart_item_id", updatable = false)
	private String id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(
		name = "shopping_cart_id", 
		referencedColumnName = "shopping_cart_id",
		foreignKey = @ForeignKey(name = "fk_cart_items_shopping_cart_id"))
	private ShoppingCart shoppingCart;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
		name = "product_id",
		referencedColumnName = "product_id", 
		foreignKey = @ForeignKey(name = "fk_cart_items_product_id"))
	private Product product;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "unit_price")
	private double unitPrice;
	
	@Override
	public String getIdPrefix() {
		return "CITM";
	}
}
