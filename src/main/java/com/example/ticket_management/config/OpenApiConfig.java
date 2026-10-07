package com.example.ticket_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ticketApiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CMA CGM — Ticket Management API")
                        .description("Enterprise RESTful API for managing support and operational tickets with PostgreSQL persistence, layered architecture, Jakarta validation, and centralized exception handling.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("")
                                .email("")));
    }
}
