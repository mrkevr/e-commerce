package dev.mrkevr.ecommerce.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class GenericEntity {
	
	@Column(name = "created", columnDefinition = "TIMESTAMP WITH TIME ZONE", updatable = false)
	protected LocalDateTime created;

	@Column(name = "modified", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	protected LocalDateTime modified;

	@PrePersist
	public void prePersist() {
		this.setCreated(LocalDateTime.now());
		this.setModified(LocalDateTime.now());
	}

	@PreUpdate
	public void preUpdate() {
		this.setModified(LocalDateTime.now());
	}
}
