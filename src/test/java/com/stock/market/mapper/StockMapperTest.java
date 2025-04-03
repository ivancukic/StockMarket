package com.stock.market.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.stock.market.dto.StockDTO;
import com.stock.market.entity.Stock;

public class StockMapperTest {
	
	@Test
	public void testConvertToDto() {
		Stock stock = new Stock();
		stock.setId(1L);
		stock.setCompanyName("Apple");
		stock.setMarketName("NASDAQ");
		stock.setFoundedDate(LocalDate.of(1976, 4, 1));
		stock.setStockPrice(150.0);
		
		StockDTO dto = StockMapper.convertToDto(stock);
		
		assertEquals(1L, dto.getId());
		assertEquals("Apple", dto.getCompanyName());
		assertEquals("NASDAQ", dto.getMarketName());
		assertEquals(LocalDate.of(1976, 4, 1), dto.getFoundedDate());
		assertEquals(150.0, dto.getStockPrice());
	}
	
	@Test
	public void testConvertToEntity() {
		StockDTO dto = new StockDTO(1L, "Apple", "NASDAQ", LocalDate.of(1976, 4, 1), 150.0);
		
		Stock stock = StockMapper.convertToEntity(dto);
		
		assertEquals(1L, stock.getId());
		assertEquals("Apple", stock.getCompanyName());
		assertEquals("NASDAQ", stock.getMarketName());
		assertEquals(LocalDate.of(1976, 4, 1), stock.getFoundedDate());
		assertEquals(150.0, stock.getStockPrice());
	}

}
