package dev.mrkevr.ecommerce.dto;

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

	private String address;

	private String email;

	private String phone;
}
