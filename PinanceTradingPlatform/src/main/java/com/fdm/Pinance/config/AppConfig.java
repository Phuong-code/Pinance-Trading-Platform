package com.fdm.Pinance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class that defines the beans for the application.
 */
@Configuration
public class AppConfig {
	
    /**
     * Bean definition for the RestTemplate.
     * 
     * <p>RestTemplate is a Spring class that provides convenient methods for making HTTP requests.
     * It is used here to create a bean instance that can be autowired in other classes.</p>
     * 
     * @return A new instance of RestTemplate.
     */
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
