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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_items")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends GenericEntity {

	@Id
	@GenericGenerator(name = "order_items_id_seq", type = GeneticEntityIdentifierGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_id_seq")
	@Column(name = "order_item_id", updatable = false)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(
		name = "order_id", 
		referencedColumnName = "order_id", 
		foreignKey = @ForeignKey(name = "fk_order_items_order_id"))
	private Order order;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(
		name = "product_id", 
		referencedColumnName = "product_id",
		foreignKey = @ForeignKey(name = "fk_order_items_product_id"))
	private Product product;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "unit_price")
	private double unitPrice;

	@Override
	public String getIdPrefix() {
		return "OITM";
	}
}
