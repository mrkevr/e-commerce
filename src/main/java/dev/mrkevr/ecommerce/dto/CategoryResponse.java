package dev.mrkevr.ecommerce.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

	private String id;
	private String name;
	private String image;
	private Long count;
	private boolean isActivated;
	private boolean isDeleted;
	private LocalDateTime created;
	private LocalDateTime modified;

	// Constructor for repository
	public CategoryResponse(
			String id, 
			String name, 
			Long count, 
			boolean isActivated, 
			boolean isDeleted,
			LocalDateTime created, 
			LocalDateTime modified) {
		
		this.id = id;
		this.name = name;
		this.count = count;
		this.isActivated = isActivated;
		this.isDeleted = isDeleted;
		this.created = created;
		this.modified = modified;
	}

}