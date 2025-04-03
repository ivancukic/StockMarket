package com.stock.market.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stock.market.dto.PeriodDTO;
import com.stock.market.dto.ResponseDTO;
import com.stock.market.dto.StockDTO;
import com.stock.market.dto.StockDataDTO;
import com.stock.market.service.StockDataService;

public class StockDataControllerTest {
	
	@Mock
	private StockDataService stockDataService;

	@InjectMocks 
	private StockDataController stockDataController;
	
	private StockDataDTO sampleStockData;
	private StockDTO sampleStock;
	
	@BeforeEach
	void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleStock = new StockDTO(1L, "Apple", "NASDAQ", LocalDate.of(1976, 4, 1), 150.0);
        sampleStockData = new StockDataDTO(1L, LocalDate.of(2024, 9, 12), 150.0, 155.0, 149.0, 152.0, 151.0, 1000L, sampleStock);
    }
	
	
	@Test
	void testFindAll() {
		when(stockDataService.findAll()).thenReturn(List.of(sampleStockData));
		
		ResponseEntity<List<StockDataDTO>> response = stockDataController.findAll();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertFalse(response.getBody().isEmpty());
		assertEquals(1, response.getBody().size());
		
		verify(stockDataService, times(1)).findAll();
	}
	
	@Test
	void testGetStockData() {
		when(stockDataService.findById(1L)).thenReturn(Optional.of(sampleStockData));
		
		ResponseEntity<StockDataDTO> response = stockDataController.getStockData(1L);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        
        verify(stockDataService, times(1)).findById(1L);
	}
	
	@Test
	void testCreateStockData() {
		when(stockDataService.save(any(StockDataDTO.class))).thenReturn(sampleStockData);
		
		ResponseEntity<StockDataDTO> response = stockDataController.createStockData(sampleStockData);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        
        verify(stockDataService, times(1)).save(any(StockDataDTO.class));
	}
	
	@Test
	void testUpdateStockData() {
		when(stockDataService.update(eq(1L), any(StockDataDTO.class))).thenReturn(sampleStockData);
		
		ResponseEntity<StockDataDTO> response = stockDataController.updateStockData(1L, sampleStockData);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());

        verify(stockDataService, times(1)).update(eq(1L), any(StockDataDTO.class));
	}
	
	@Test
    void testDeleteStockData() {
        doNothing().when(stockDataService).delete(1L);

        ResponseEntity<Void> response = stockDataController.deleteStockData(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(stockDataService, times(1)).delete(1L);
    }
	
	@Test
    void testGetStockDataByStock() {
        StockDTO stockDTO = new StockDTO(1L, "Apple", "NASDAQ", LocalDate.of(1976, 4, 1), 150.0);
        when(stockDataService.findStockDataByStock(any(StockDTO.class))).thenReturn(List.of(sampleStockData));

        ResponseEntity<List<StockDataDTO>> response = stockDataController.getStockDataByStock(stockDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());

        verify(stockDataService, times(1)).findStockDataByStock(any(StockDTO.class));
    }
	
	@Test
	void testGetCompanyStockForThreePeriods() {
		PeriodDTO previousPeriod = new PeriodDTO();
	    previousPeriod.setMinDate(LocalDate.of(2024, 9, 7));
	    previousPeriod.setMinPrice(140.0);
	    previousPeriod.setMaxDate(LocalDate.of(2024, 9, 9));
	    previousPeriod.setMaxPrice(160.0);
	    previousPeriod.setProfitByDate(20.0);
	    previousPeriod.setMaxProfitByDate(25.0);
	    
	    PeriodDTO askedPeriod = new PeriodDTO();
	    askedPeriod.setMinDate(LocalDate.of(2024, 9, 10));
	    askedPeriod.setMinPrice(145.0);
	    askedPeriod.setMaxDate(LocalDate.of(2024, 9, 12));
	    askedPeriod.setMaxPrice(170.0);
	    askedPeriod.setProfitByDate(25.0);
	    askedPeriod.setMaxProfitByDate(30.0);
	    
	    PeriodDTO nextPeriod = new PeriodDTO();
	    nextPeriod.setMinDate(LocalDate.of(2024, 9, 13));
	    nextPeriod.setMinPrice(150.0);
	    nextPeriod.setMaxDate(LocalDate.of(2024, 9, 15));
	    nextPeriod.setMaxPrice(180.0);
	    nextPeriod.setProfitByDate(30.0);
	    nextPeriod.setMaxProfitByDate(35.0);
	    
	    ResponseDTO responseDTO = new ResponseDTO();
	    responseDTO.setPrevious(previousPeriod);
	    responseDTO.setAsked(askedPeriod);
	    responseDTO.setNext(nextPeriod);
	    
	    when(stockDataService.getCompanyStockForThreePeriods("Apple", LocalDate.of(2024, 9, 10), LocalDate.of(2024, 9, 12)))
        .thenReturn(responseDTO);
	    
	    ResponseEntity<ResponseDTO> response = stockDataController.getCompanyStockForThreePeriods("Apple", LocalDate.of(2024, 9, 10), LocalDate.of(2024, 9, 12));
	
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    
	    assertNotNull(response.getBody());
	    assertNotNull(response.getBody().getPrevious());
	    assertNotNull(response.getBody().getAsked());
	    assertNotNull(response.getBody().getNext());
	    
	    assertEquals(LocalDate.of(2024, 9, 7), response.getBody().getPrevious().getMinDate());
	    assertEquals(140.0, response.getBody().getPrevious().getMinPrice());
	    assertEquals(LocalDate.of(2024, 9, 9), response.getBody().getPrevious().getMaxDate());
	    assertEquals(160.0, response.getBody().getPrevious().getMaxPrice());
	    
	    assertEquals(LocalDate.of(2024, 9, 10), response.getBody().getAsked().getMinDate());
	    assertEquals(145.0, response.getBody().getAsked().getMinPrice());
	    assertEquals(LocalDate.of(2024, 9, 12), response.getBody().getAsked().getMaxDate());
	    assertEquals(170.0, response.getBody().getAsked().getMaxPrice());
	    
	    assertEquals(LocalDate.of(2024, 9, 13), response.getBody().getNext().getMinDate());
	    assertEquals(150.0, response.getBody().getNext().getMinPrice());
	    assertEquals(LocalDate.of(2024, 9, 15), response.getBody().getNext().getMaxDate());
	    assertEquals(180.0, response.getBody().getNext().getMaxPrice());
	    
	    verify(stockDataService, times(1)).getCompanyStockForThreePeriods("Apple", LocalDate.of(2024, 9, 10), LocalDate.of(2024, 9, 12));
	}
}
