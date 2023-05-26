package dev.mrkevr.ecommerce.entity;

import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
@SequenceGenerator(name = "category_id_seq", sequenceName = "category_id_seq", initialValue = 333001, allocationSize = 1)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "category_id_seq")
	@Column(name = "category_id", updatable = false)
	private long id;

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
