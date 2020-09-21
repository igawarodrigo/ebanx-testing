package com.ebanx.homeassigment.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING;

@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

	@Bean
    public ObjectMapper objectMapper () {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.enable(READ_ENUMS_USING_TO_STRING);
	    return objectMapper;
    }
}
