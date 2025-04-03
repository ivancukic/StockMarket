package com.stock.market.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stock.market.dto.StockDTO;
import com.stock.market.service.StockService;

@SpringBootTest
@AutoConfigureMockMvc
public class StockControllerTest {
	
	@Mock
    private StockService stockService;

    @InjectMocks
    private StockController stockController;

    private StockDTO sampleStock;
    
    @BeforeEach
    void setUp() {
    	MockitoAnnotations.openMocks(this);  
        stockService = mock(StockService.class); 
        stockController = new StockController(stockService); 

        sampleStock = new StockDTO(1L, "Apple", "NASDAQ", LocalDate.of(1976, 4, 1), 150.0);
    }
    
    @Test
    void testFindAll() {
    	 when(stockService.findAll()).thenReturn(List.of(sampleStock));

         ResponseEntity<List<StockDTO>> response = stockController.findAll();

         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertNotNull(response.getBody());
         assertEquals(1, response.getBody().size());
         verify(stockService, times(1)).findAll();
    }
    
    @Test
    void testGetStockById() {
    	when(stockService.findById(1L)).thenReturn(Optional.of(sampleStock));
    	
    	ResponseEntity<StockDTO> response = stockController.getStock(1L);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleStock, response.getBody());
        verify(stockService, times(1)).findById(1L);
    }
    
    @Test
    void testGetStockByCompanyName() {
    	when(stockService.findByCompanyName("Apple")).thenReturn(Optional.of(sampleStock));
    	
    	ResponseEntity<StockDTO> response = stockController.getStockByName("Apple");
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleStock, response.getBody());
        verify(stockService, times(1)).findByCompanyName("Apple");
    }
    
    @Test
    void testCreateStock() {
    	when(stockService.save(any(StockDTO.class))).thenReturn(sampleStock);
    	
    	ResponseEntity<StockDTO> response = stockController.createStock(sampleStock);
    	
    	assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleStock, response.getBody());
        verify(stockService, times(1)).save(sampleStock);
    }

    @Test
    void testUpdateStock() {
    	when(stockService.update(eq(1L), any(StockDTO.class))).thenReturn(sampleStock);
    	
    	ResponseEntity<StockDTO> response = stockController.updateStock(1L, sampleStock);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleStock, response.getBody());
        verify(stockService, times(1)).update(eq(1L), any(StockDTO.class));
    }
    
    @Test
    void testDeleteStock() {
    	doNothing().when(stockService).delete(1L);
    	
    	ResponseEntity<Void> response = stockController.deleteStock(1L);
    	
    	assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(stockService, times(1)).delete(1L);
    }
}
