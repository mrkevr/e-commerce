package dev.mrkevr.ecommerce.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateConverter implements Converter<LocalDate, String> {

	@Override
	public String convert(LocalDate source) {
		return source.format(DateTimeFormatter.ofPattern("MMMM-dd-yyyy"));
	}

	public LocalDate convert(String source) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(source, formatter);
	}

}
