package com.cts.ecommerce.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CorsConfig is a configuration class that sets up Cross-Origin Resource Sharing (CORS) for the application
// It allows the application to handle requests from different origins
@Configuration
public class CorsConfig {

	// Defines a bean for WebMvcConfigurer to configure CORS settings.
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
            	
            	// Adds CORS mappings to allow requests from any origin with specified HTTP methods
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*"); // Notation for allowing FrontEnd access to Angular
            }
        };
    }
}