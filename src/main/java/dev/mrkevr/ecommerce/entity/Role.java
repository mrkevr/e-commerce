package dev.mrkevr.ecommerce.entity;

import org.hibernate.annotations.GenericGenerator;

import dev.mrkevr.ecommerce.entity.generator.GeneticEntityIdentifierGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "roles")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends GenericEntity {

	@Id
	@GenericGenerator(name = "role_id_seq", type = GeneticEntityIdentifierGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
	@Column(name = "role_id", updatable = false)
	private String id;

	@Column(name = "role")
	private String role;

	public Role(String role) {
		this.role = role;
	}

	@Override
	public String getIdPrefix() {
		return "ROLE";
	}
}
