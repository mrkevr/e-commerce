package dev.mrkevr.ecommerce.entity;

import org.hibernate.annotations.GenericGenerator;

import dev.mrkevr.ecommerce.entity.generator.GeneticEntityIdentifierGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Category")
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(name = "unique_category_name", columnNames = {
		"name" }))
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@GenericGenerator(name = "category_id_seq", type = GeneticEntityIdentifierGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
	@Column(name = "category_id", updatable = false)
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "is_activated")
	private boolean isActivated;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	public Category(String name) {
		this.name = name;
		this.isActivated = true;
		this.isDeleted = false;
	}
}
