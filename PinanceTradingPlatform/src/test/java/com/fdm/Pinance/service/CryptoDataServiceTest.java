package com.fdm.Pinance.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdm.Pinance.dal.CryptoDataRepository;
import com.fdm.Pinance.model.CryptoData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@SpringBootTest
class CryptoDataServiceTest {
	
    @Mock
    private CryptoDataRepository cryptoDataRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CryptoDataService cryptoDataService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAndSaveSymbolPrices_ValidResponse_SaveToRepository() {
        CryptoData[] symbolPrices = {
                new CryptoData("BTCUSDT", new BigDecimal("35000.00")),
                new CryptoData("ETHUSDT", new BigDecimal("2200.00")),
                new CryptoData("XRPUSDT", new BigDecimal("1.50")),
                new CryptoData("BNBUSDT", new BigDecimal("300.00")),
                new CryptoData("DOGEUSDT", new BigDecimal("0.30")),
                new CryptoData("LUNCUSDT", new BigDecimal("10.00"))
        };

        ResponseEntity<CryptoData[]> responseEntity = new ResponseEntity<>(symbolPrices, HttpStatus.OK);
        when(restTemplate.getForEntity("https://api.binance.com/api/v3/ticker/price", CryptoData[].class))
                .thenReturn(responseEntity);

        cryptoDataService.fetchAndSaveSymbolPrices();

        verify(cryptoDataRepository, times(6)).save(any());
    }

    @Test
    public void testFetchAndSaveSymbolPrices_EmptyResponse_NoSaveToRepository() {
        ResponseEntity<CryptoData[]> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity("https://api.binance.com/api/v3/ticker/price", CryptoData[].class))
                .thenReturn(responseEntity);

        cryptoDataService.fetchAndSaveSymbolPrices();

        verify(cryptoDataRepository, never()).save(any());
    }

    @Test
    public void testGetAllCryptoData_ReturnsListOfCryptoData() {
        List<CryptoData> expectedCryptoDataList = new ArrayList<>();
        expectedCryptoDataList.add(new CryptoData("BTC", new BigDecimal("35000.00")));
        expectedCryptoDataList.add(new CryptoData("ETH", new BigDecimal("2200.00")));
        expectedCryptoDataList.add(new CryptoData("XRP", new BigDecimal("1.50")));
        expectedCryptoDataList.add(new CryptoData("BNB", new BigDecimal("300.00")));
        expectedCryptoDataList.add(new CryptoData("DOGE", new BigDecimal("0.30")));
        expectedCryptoDataList.add(new CryptoData("LUNC", new BigDecimal("10.00")));

        when(cryptoDataRepository.findAll()).thenReturn(expectedCryptoDataList);

        List<CryptoData> actualCryptoDataList = cryptoDataService.getAllCryptoData();

        assertEquals(expectedCryptoDataList, actualCryptoDataList);
    }

    @Test
    public void testGetCryptoDataBySymbol_SymbolExists_ReturnsCryptoData() {
        String symbol = "BTC";
        CryptoData expectedCryptoData = new CryptoData(symbol, new BigDecimal("35000.00"));

        when(cryptoDataRepository.findById(symbol)).thenReturn(Optional.of(expectedCryptoData));

        CryptoData actualCryptoData = cryptoDataService.getCryptoDataBySymbol(symbol);

        assertEquals(expectedCryptoData, actualCryptoData);
    }

    @Test
    public void testGetCryptoDataBySymbol_SymbolDoesNotExist_ReturnsNull() {
        String symbol = "INVALID";
        when(cryptoDataRepository.findById(symbol)).thenReturn(Optional.empty());

        CryptoData actualCryptoData = cryptoDataService.getCryptoDataBySymbol(symbol);

        assertEquals(null, actualCryptoData);
    }
    
    @Test
    public void testSaveCryptoData() {
        // Create a sample CryptoData object
        CryptoData cryptoData = new CryptoData("BTCUSDT", BigDecimal.valueOf(40000));

        // Call the method to be tested
        cryptoDataService.saveCryptoData(cryptoData);

        // Verify that the save method of the repository was called with the correct CryptoData object
        verify(cryptoDataRepository, times(1)).save(cryptoData);
    }

    @Test
    public void testDeleteCryptoDataBySymbol() {
        // Define a symbol to delete
        String symbol = "BTCUSDT";

        // Call the method to be tested
        cryptoDataService.deleteCryptoDataBySymbol(symbol);

        // Verify that the deleteById method of the repository was called with the correct symbol
        verify(cryptoDataRepository, times(1)).deleteById(symbol);
    }

}
