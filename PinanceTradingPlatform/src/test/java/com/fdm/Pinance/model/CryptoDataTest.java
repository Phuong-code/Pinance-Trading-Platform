package com.fdm.Pinance.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

@SpringBootTest
class CryptoDataTest {

    @Test
    public void testGettersAndSetters() {
        // Create a test instance of CryptoData
        CryptoData cryptoData = new CryptoData("BTC", new BigDecimal("40000.50"));

        // Test getters
        assertEquals("BTC", cryptoData.getSymbol());
        assertEquals(new BigDecimal("40000.50"), cryptoData.getPrice());

        // Test setters
        cryptoData.setSymbol("ETH");
        cryptoData.setPrice(new BigDecimal("3500.75"));

        assertEquals("ETH", cryptoData.getSymbol());
        assertEquals(new BigDecimal("3500.75"), cryptoData.getPrice());
    }

    @Test
    public void testToString() {
        // Create a test instance of CryptoData
        CryptoData cryptoData = new CryptoData("ETH", new BigDecimal("3500.75"));

        // Test the toString() method
        assertEquals("CryptoData [symbol=ETH, price=3500.75]", cryptoData.toString());
    }

}
