package dev.mrkevr.ecommerce.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import dev.mrkevr.ecommerce.entity.generator.GeneticEntityIdentifierGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends GenericEntity {

	@Id
	@GenericGenerator(name = "orders_id_seq", type = GeneticEntityIdentifierGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
	@Column(name = "order_id", updatable = false)
	private String id;

	@Column(name = "order_date")
	private LocalDate orderDate;

	@Column(name = "delivery_date")
	private LocalDate deliveryDate;
	
	@Column(name = "delivery_address")
	private String deliveryAddress;
	
	@Column(name = "order_status", 
		columnDefinition = "ENUM('PENDING', 'CANCELLED','ACCEPTED','DENIED','IN_PROGRESS','TO_SHIP','TO_RECEIVE','COMPLETED','RETURNED')")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "total_items")
    private int totalItems;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "is_accepted")
	private boolean isAccepted;
	
	@Column(name = "message")
	private String message;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(
		name = "user_id", 
		referencedColumnName = "user_id",
		foreignKey = @ForeignKey(name = "fk_orders_user_id"))
	private User user;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderItem> orderItems;

	@Override
	public String getIdPrefix() {
		return "ORDR";
	}

	@Override
	public void prePersist() {
		super.prePersist();
		orderDate = LocalDate.now();
		orderStatus = OrderStatus.PENDING;
		isAccepted = false;
	}
}
