package com.fdm.Pinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fdm.Pinance.service.CryptoDataService;

@Configuration
@EnableScheduling
public class CryptoDataScheduler {
    private final CryptoDataService cryptoDataService;

    public CryptoDataScheduler(CryptoDataService cryptoDataService) {
        this.cryptoDataService = cryptoDataService;
    }

    /**
     * Scheduled task that fetches and saves cryptocurrency symbol prices.
     * 
     * <p>This method will be executed periodically based on the specified fixed delay.
     * The fixedDelay parameter specifies the time interval between the end of the previous
     * execution and the start of the next execution of this method.</p>
     * 
     * <p>Note: The delay is specified in milliseconds. For example, a fixedDelay of 500 means
     * the method will run every 0.5 seconds.</p>
     */
    @Scheduled(fixedDelay = 500)
    public void fetchAndSaveSymbolPrices() {
    	cryptoDataService.fetchAndSaveSymbolPrices();
    }
}
