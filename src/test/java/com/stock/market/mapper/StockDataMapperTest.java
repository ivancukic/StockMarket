package com.stock.market.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.stock.market.dto.StockDTO;
import com.stock.market.dto.StockDataDTO;
import com.stock.market.entity.Stock;
import com.stock.market.entity.StockData;

public class StockDataMapperTest {
	
	@Test
	public void testConvertToDto() {
		StockData stockData = new StockData();
		
		stockData.setId(1L);
		stockData.setDate(LocalDate.of(2023, 1, 1));
		stockData.setOpen(100.0);
		stockData.setHigh(110.0);
		stockData.setLow(95.0);
		stockData.setClose(105.5);
		stockData.setAdjClose(106.0);
		stockData.setVolume(1000000L);
		
		Stock stock = new Stock();
		stock.setId(2L);
		stock.setCompanyName("Apple");
		
		stockData.setStock(stock);
		
		StockDataDTO dto = StockDataMapper.convertToDto(stockData);
		
		assertEquals(1L, dto.getId());
		assertEquals(LocalDate.of(2023, 1, 1), dto.getDate());
		assertEquals(100.0, dto.getOpen());
		assertEquals(110.0, dto.getHigh());
		assertEquals(95.0, dto.getLow());
		assertEquals(105.5, dto.getClose());
		assertEquals(106.0, dto.getAdjClose());
		assertEquals(1000000L, dto.getVolume());
		assertEquals(2L, dto.getStock().getId());
	}
	
	@Test
	public void testConvertToEntity() {
		StockDTO stockDTO = new StockDTO(2L, "Apple", "NASDAQ", LocalDate.of(1976, 4, 1), 150.0);
		StockDataDTO dto = new StockDataDTO(1L, LocalDate.of(2023, 1, 1), 100.0, 110.0, 95.0, 105.5, 106.0, 1000000L, stockDTO);
									
		Stock stock = StockMapper.convertToEntity(stockDTO);
		StockData stockData = StockDataMapper.convertToEntity(dto, stock);
		
		assertEquals(1L, stockData.getId());
		assertEquals(LocalDate.of(2023, 1, 1), stockData.getDate());
		assertEquals(100.0, stockData.getOpen());
		assertEquals(110.0, stockData.getHigh());
		assertEquals(95.0, stockData.getLow());
		assertEquals(105.5, stockData.getClose());
		assertEquals(106.0, stockData.getAdjClose());
		assertEquals(1000000L, stockData.getVolume());
		assertEquals(2L, stockData.getStock().getId());
					
	}

}
