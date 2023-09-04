package dev.mrkevr.ecommerce.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import dev.mrkevr.ecommerce.dto.ContactMessageDto;
import dev.mrkevr.ecommerce.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailServiceImpl implements EmailService {
	
	JavaMailSender mailSender;
	TemplateEngine templateEngine;
	
	static String CONTACT_MESSAGE_TEMPLATE = "contact-message-template";
	static String APPLICATION_EMAIL = "dev.mrkevr@gmail.com";
	
	@Override
	@Async
	public void sendContactMessage(ContactMessageDto dto) {
		try {
			// Thymeleaf context
			Context context = new Context();
			
			// Add the model attributes to the context
			context.setVariable("messageDto", dto);
			
			String text = templateEngine.process(CONTACT_MESSAGE_TEMPLATE, context);
			
			MimeMessage message = this.getMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setPriority(1);
			helper.setSubject("Feedback from User");
			helper.setFrom(APPLICATION_EMAIL);
			helper.setTo(APPLICATION_EMAIL);
			helper.setText(text, true);
			
			mailSender.send(message);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private MimeMessage getMimeMessage() {
		return mailSender.createMimeMessage();
	}

}
