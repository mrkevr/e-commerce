package dev.mrkevr.ecommerce.entity.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	@NotBlank(message = "Street must not be blank.")
	private String street;
	
	@NotBlank(message = "Barangay must not be blank.")
	private String barangay;
	
	@NotBlank(message = "Municipality must not be blank.")
	private String municipality;
	
	@NotBlank(message = "Province must not be blank.")
	private String province;
	
	@NotBlank(message = "Zipcode must not be blank.")
	private String zipcode;
}
