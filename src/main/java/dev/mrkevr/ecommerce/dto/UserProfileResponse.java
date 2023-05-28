package dev.mrkevr.ecommerce.dto;

import dev.mrkevr.ecommerce.entity.embeddable.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

	private String id;

	private String oauth2Id;

	private String username;

	private String firstName;

	private String lastName;

	private Address Address;

	private String email;

	private String phone;
}
