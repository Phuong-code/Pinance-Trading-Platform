package com.fdm.Pinance.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fdm.Pinance.dal.CryptoDataRepository;
import com.fdm.Pinance.model.CryptoData;

/**
 * Service class that provides business logic and operations for managing CryptoData entities.
 */
@Service
public class CryptoDataService {
    private final CryptoDataRepository cryptoDataRepository;
    private final RestTemplate restTemplate;
    private List<String> symbolList;

    /**
     * Constructor for CryptoDataService.
     *
     * @param cryptoDataRepository The repository to access and manage CryptoData entities.
     * @param restTemplate        The RestTemplate to make API requests.
     */
    public CryptoDataService(CryptoDataRepository cryptoDataRepository, RestTemplate restTemplate) {
        this.cryptoDataRepository = cryptoDataRepository;
        this.restTemplate = restTemplate;
        
        // Initialize the list of cryptocurrency symbols to fetch and save prices for.
        symbolList =new ArrayList<String>();
    	symbolList.add("BTCUSDT");
    	symbolList.add("ETHUSDT");
    	symbolList.add("XRPUSDT");
    	symbolList.add("BNBUSDT");
    	symbolList.add("DOGEUSDT");
    	symbolList.add("LUNCUSDT");
    }

    /**
     * Fetches cryptocurrency prices from an external API and saves them in the database.
     */
    public void fetchAndSaveSymbolPrices() {	
        ResponseEntity<CryptoData[]> response = restTemplate.getForEntity(
                "https://api.binance.com/api/v3/ticker/price",
                CryptoData[].class);

        CryptoData[] symbolPrices = response.getBody();
        if (symbolPrices != null) {
            // Save the prices of cryptocurrencies present in the symbolList.
        	for (CryptoData symbolPrice : symbolPrices) {
            	if(symbolList.contains(symbolPrice.getSymbol())) {
            		String symbolRemovedUSDT = symbolPrice.getSymbol().replace("USDT", "");
            		symbolPrice.setSymbol(symbolRemovedUSDT);
            		cryptoDataRepository.save(symbolPrice);
            	}
        	}
        }
    }
    
    /**
     * Retrieves a list of all CryptoData entities.
     *
     * @return A list of all CryptoData entities in the database.
     */
    public List<CryptoData> getAllCryptoData() {
        return cryptoDataRepository.findAll();
    }
    
    /**
     * Retrieves a CryptoData entity by its symbol.
     *
     * @param symbol The symbol of the cryptocurrency to retrieve.
     * @return The CryptoData entity with the specified symbol, or null if not found.
     */
    public CryptoData getCryptoDataBySymbol(String symbol) {
        Optional<CryptoData> cryptoDataOptional = cryptoDataRepository.findById(symbol);
        return cryptoDataOptional.orElse(null);
    }
    
    /**
     * Saves a new CryptoData entity or updates an existing one.
     *
     * @param cryptoData The CryptoData entity to save or update.
     */
    public void saveCryptoData(CryptoData cryptoData) {
        cryptoDataRepository.save(cryptoData);
    }
    
    /**
     * Deletes a CryptoData entity by its symbol.
     *
     * @param symbol The symbol of the cryptocurrency to delete.
     */
    public void deleteCryptoDataBySymbol(String symbol) {
        cryptoDataRepository.deleteById(symbol);
    }
}
