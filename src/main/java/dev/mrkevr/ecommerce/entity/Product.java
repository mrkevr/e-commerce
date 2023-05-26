package dev.mrkevr.ecommerce.entity;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Product")
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", initialValue = 222001, allocationSize = 1)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends GenericEntity {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "product_id_seq")
	@Column(name = "product_id", updatable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "current_quantity")
	private int currentQuantity;

	@Column(name = "cost_price")
	private double costPrice;

	@Column(name = "sale_price")
	private double salePrice;

	@Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;
	
	@Column(name = "is_activated")
	private boolean isActivated;

	@Column(name = "is_deleted")
	private boolean isDeleted;
}
