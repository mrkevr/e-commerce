package dev.mrkevr.ecommerce.service;

import dev.mrkevr.ecommerce.dto.ContactMessageDto;

public interface EmailService {
	
	void sendContactMessage(ContactMessageDto dto);
	
}
