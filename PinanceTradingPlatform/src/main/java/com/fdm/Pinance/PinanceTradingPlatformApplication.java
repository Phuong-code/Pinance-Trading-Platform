package com.fdm.Pinance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PinanceTradingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(PinanceTradingPlatformApplication.class, args);	
	}
	
}
