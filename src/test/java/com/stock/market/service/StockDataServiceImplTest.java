package com.stock.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

import com.stock.market.dto.PeriodDTO;
import com.stock.market.dto.ResponseDTO;
import com.stock.market.dto.StockDTO;
import com.stock.market.dto.StockDataDTO;
import com.stock.market.entity.Stock;
import com.stock.market.entity.StockData;
import com.stock.market.mapper.StockMapper;
import com.stock.market.repository.StockDataRepository;
import com.stock.market.repository.StockRepository;

@ExtendWith(MockitoExtension.class)
public class StockDataServiceImplTest {
	
	@Mock
	private StockDataRepository stockDataRepository;
	
	@Mock
	private StockRepository stockRepository;
	
	@InjectMocks
	private StockDataServiceImpl stockDataServiceImpl;
	
	
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
	    stock2.setCompanyName("Google");
	    stock2.setMarketName("NASDAQ");
	    stock2.setFoundedDate(LocalDate.of(1998, 9, 4));
	    stock2.setStockPrice(2500.0);
	    
	    StockData stockData1 = new StockData();
	    stockData1.setId(1L);
	    stockData1.setDate(LocalDate.now());
	    stockData1.setOpen(150.0);
	    stockData1.setClose(160.0);
	    stockData1.setStock(stock1);
	    
	    StockData stockData2 = new StockData();
	    stockData2.setId(2L);
	    stockData2.setDate(LocalDate.now().plusDays(1));
	    stockData2.setOpen(2500.0);
	    stockData2.setClose(2600.0);
	    stockData2.setStock(stock2);
	    
	    when(stockDataRepository.findAll()).thenReturn(Arrays.asList(stockData1, stockData2));

	    List<StockDataDTO> result = stockDataServiceImpl.findAll();

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
	    
		StockData stockData = new StockData();
		stockData.setId(1L);
        stockData.setDate(LocalDate.now());
        stockData.setOpen(150.0);
        stockData.setClose(160.0);
        stockData.setStock(stock);
        
		when(stockDataRepository.findById(1L)).thenReturn(Optional.of(stockData));
		
		Optional<StockDataDTO> result = stockDataServiceImpl.findById(1L);
		
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
        
        StockDataDTO dto = new StockDataDTO();
        dto.setId(1L);
        dto.setDate(LocalDate.now());
        dto.setOpen(150.0);
        dto.setClose(160.0);
        dto.setStock(stockDTO);
		
        Stock stock = new Stock();
	    stock.setId(1L);
	    stock.setCompanyName("Apple");
	    stock.setMarketName("NASDAQ");
	    stock.setFoundedDate(LocalDate.of(1976, 4, 1));
	    stock.setStockPrice(150.0);
	    
		when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
		StockData savedStockData = new StockData();
		savedStockData.setStock(stock);
		
		when(stockDataRepository.save(any(StockData.class))).thenReturn(savedStockData);
		
		StockDataDTO result = stockDataServiceImpl.save(dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void testUpdate() {
		StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setCompanyName("Apple");
        stockDTO.setMarketName("NASDAQ");
        stockDTO.setFoundedDate(LocalDate.of(1976, 4, 1));
        stockDTO.setStockPrice(150.0);
        
        StockDataDTO dto = new StockDataDTO();
        dto.setId(1L);
        dto.setDate(LocalDate.now());
        dto.setOpen(200.0);
        dto.setClose(210.0);
        dto.setStock(stockDTO);
        
        Stock stock = new Stock();
	    stock.setId(1L);
	    stock.setCompanyName("Apple");
	    stock.setMarketName("NASDAQ");
	    stock.setFoundedDate(LocalDate.of(1976, 4, 1));
	    stock.setStockPrice(150.0);
	    
	    StockData stockData = new StockData();
		stockData.setId(1L);
        stockData.setDate(LocalDate.now());
        stockData.setOpen(150.0);
        stockData.setClose(160.0);
        stockData.setStock(stock);
        
		when(stockDataRepository.findById(1L)).thenReturn(Optional.of(stockData));
		when(stockDataRepository.save(any(StockData.class))).thenReturn(stockData);
		
		StockDataDTO result = stockDataServiceImpl.update(1L, dto);
		
		assertNotNull(result);
	}
	
	@Test
	public void testDelete() {
		when(stockDataRepository.existsById(1L)).thenReturn(true);
		doNothing().when(stockDataRepository).deleteById(1L);
		
		stockDataServiceImpl.delete(1L);
		
		verify(stockDataRepository, times(1)).deleteById(1L);
	}
	
	@Test
    public void testFindStockDataByStock() {
		StockDTO stockDTO = new StockDTO();
        stockDTO.setId(1L);
        stockDTO.setCompanyName("Apple");
        stockDTO.setMarketName("NASDAQ");
        stockDTO.setFoundedDate(LocalDate.of(1976, 4, 1));
        stockDTO.setStockPrice(150.0);
        
        Stock stock = StockMapper.convertToEntity(stockDTO);
        
        StockData stockData1 = new StockData();
	    stockData1.setId(1L);
	    stockData1.setDate(LocalDate.now());
	    stockData1.setOpen(150.0);
	    stockData1.setClose(160.0);
	    stockData1.setStock(stock);
	    
	    StockData stockData2 = new StockData();
	    stockData2.setId(2L);
	    stockData2.setDate(LocalDate.now().plusDays(1));
	    stockData2.setOpen(2500.0);
	    stockData2.setClose(2600.0);
	    stockData2.setStock(stock);
	    
	    when(stockDataRepository.findStockDataByStock(any(Stock.class))).thenReturn(Arrays.asList(stockData1, stockData2));

        List<StockDataDTO> result = stockDataServiceImpl.findStockDataByStock(stockDTO);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetCompanyStockForThreePeriods() {
        Stock stock = new Stock();
        when(stockRepository.findByCompanyName("Apple")).thenReturn(stock);
        
        StockData stockData = new StockData();
		stockData.setId(1L);
        stockData.setDate(LocalDate.now());
        stockData.setOpen(150.0);
        stockData.setClose(160.0);
        stockData.setStock(stock);
        
        when(stockDataRepository.findByStockAndDateBetweenOrderByDate(any(Stock.class), any(LocalDate.class), any(LocalDate.class)))
        						.thenReturn(Arrays.asList(stockData));

        ResponseDTO result = stockDataServiceImpl.getCompanyStockForThreePeriods("Apple", LocalDate.now(), LocalDate.now().plusDays(10));

        assertNotNull(result);
        assertNotNull(result.getAsked());
        assertNotNull(result.getPrevious());
        assertNotNull(result.getNext());
    }

    @Test
    public void testGetCompanyStockByDate() {
        Stock stock = new Stock();
	    stock.setId(1L);
	    stock.setCompanyName("Apple");
	    stock.setMarketName("NASDAQ");
	    stock.setFoundedDate(LocalDate.of(1976, 4, 1));
	    stock.setStockPrice(150.0);
        when(stockRepository.findByCompanyName("Apple")).thenReturn(stock);
        
        StockData stockData1 = new StockData();
	    stockData1.setId(1L);
	    stockData1.setDate(LocalDate.now());
	    stockData1.setOpen(150.0);
	    stockData1.setClose(160.0);
	    stockData1.setStock(stock);
	    
	    StockData stockData2 = new StockData();
	    stockData2.setId(2L);
	    stockData2.setDate(LocalDate.now().plusDays(1));
	    stockData2.setOpen(2500.0);
	    stockData2.setClose(2600.0);
	    stockData2.setStock(stock);
        
	    when(stockDataRepository.findByStockAndDateBetweenOrderByDate(any(Stock.class), any(LocalDate.class), any(LocalDate.class)))
        .thenReturn(Arrays.asList(stockData1, stockData2));

        List<StockDataDTO> result = stockDataServiceImpl.getCompanyStockByDate("Apple", LocalDate.now(), LocalDate.now().plusDays(10));

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPeriodData() {
    	StockDataDTO stockDataDTO1 = new StockDataDTO();
    	stockDataDTO1.setId(1L);
    	stockDataDTO1.setDate(LocalDate.now());
    	stockDataDTO1.setOpen(150.0);
    	stockDataDTO1.setClose(160.0);
        
    	StockDataDTO stockDataDTO2 = new StockDataDTO();
    	stockDataDTO2.setId(2L);
    	stockDataDTO2.setDate(LocalDate.now());
    	stockDataDTO2.setOpen(250.0);
    	stockDataDTO2.setClose(360.0);
    	
    	List<StockDataDTO> stockDataDTOList = Arrays.asList(stockDataDTO1, stockDataDTO2);
    	
        PeriodDTO result = stockDataServiceImpl.getPeriodData(stockDataDTOList);
        
        assertNotNull(result);
    }

    @Test
    public void testMaxProfitByDate() {
    	StockDataDTO stockDataDTO1 = new StockDataDTO();
    	stockDataDTO1.setId(1L);
    	stockDataDTO1.setDate(LocalDate.now());
    	stockDataDTO1.setOpen(150.0);
    	stockDataDTO1.setClose(160.0);
        
    	StockDataDTO stockDataDTO2 = new StockDataDTO();
    	stockDataDTO2.setId(2L);
    	stockDataDTO2.setDate(LocalDate.now());
    	stockDataDTO2.setOpen(250.0);
    	stockDataDTO2.setClose(360.0);
    	
    	List<StockDataDTO> stockDataDTOList = Arrays.asList(stockDataDTO1, stockDataDTO2);
    	
        double result = stockDataServiceImpl.maxProfitByDate(stockDataDTOList);
        
        assertEquals(200.00, result);
    }

}
