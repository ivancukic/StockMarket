package com.stock.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stock.market.dto.StockDTO;
import com.stock.market.entity.Stock;
import com.stock.market.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {
	
	@Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockServiceImpl;
    
    
    @Test
    public void testFindAll() {
    	Stock stock1 = new Stock();
	    stock1.setId(1L);
	    stock1.setCompanyName("Apple");
	    stock1.setMarketName("NASDAQ");
	    stock1.setFoundedDate(LocalDate.of(1976, 4, 1));
	    stock1.setStockPrice(150.0);
	    
	    Stock stock2 = new Stock();
	    stock2.setId(2L);
	    stock2.setCompanyName("Facebbok");
	    stock2.setMarketName("MSDG");
	    stock2.setFoundedDate(LocalDate.of(2000, 4, 1));
	    stock2.setStockPrice(250.0);
	    
	    when(stockRepository.findAll()).thenReturn(Arrays.asList(stock1, stock2));
	    
	    List<StockDTO> result = stockServiceImpl.findAll();
	    
	    assertEquals(2, result.size());
    }
    
    @Test
    public void testFindById() {
    	Stock stock = new Stock();
	    stock.setId(1L);
	    stock.setCompanyName("Apple");
	    stock.setMarketName("NASDAQ");
	    stock.setFoundedDate(LocalDate.of(1976, 4, 1));
	    stock.setStockPrice(150.0);
	    
	    when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
	    
	    Optional<StockDTO> result = stockServiceImpl.findById(1L);
	    
	    assertTrue(result.isPresent());
    }
    
    @Test
    public void testSave() {
    	StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setCompanyName("Apple");
        stockDTO.setMarketName("NASDAQ");
        stockDTO.setFoundedDate(LocalDate.of(1976, 4, 1));
        stockDTO.setStockPrice(150.0);
        
        Stock stock = new Stock();
	    stock.setId(1L);
	    stock.setCompanyName("Apple");
	    stock.setMarketName("NASDAQ");
	    stock.setFoundedDate(LocalDate.of(1976, 4, 1));
	    stock.setStockPrice(150.0);
	    
	    when(stockRepository.save(any(Stock.class))).thenReturn(stock);
	    
	    StockDTO result = stockServiceImpl.save(stockDTO);
	    
	    assertNotNull(result);
    }
    
    @Test
    public void testUpdate() {
    	StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setCompanyName("Apple");
        stockDTO.setMarketName("NASDAQ");
        stockDTO.setFoundedDate(LocalDate.of(1976, 4, 1));
        stockDTO.setStockPrice(200.0);
        
        Stock stock = new Stock();
	    stock.setId(1L);
	    stock.setCompanyName("Apple");
	    stock.setMarketName("NASDAQ");
	    stock.setFoundedDate(LocalDate.of(1976, 4, 1));
	    stock.setStockPrice(150.0);
	    
	    when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
	    
	    when(stockRepository.save(any(Stock.class))).thenReturn(stock);
	    
	    StockDTO result = stockServiceImpl.update(1L, stockDTO);
	    
	    assertNotNull(result);
        assertEquals(200.0, result.getStockPrice());
    }
    
    @Test
    public void testDelete() {
    	when(stockRepository.existsById(1L)).thenReturn(true);
    	
    	stockServiceImpl.delete(1L);
    	
    	verify(stockRepository, times(1)).deleteById(1L);
    }

}
