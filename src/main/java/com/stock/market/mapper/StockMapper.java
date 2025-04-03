package com.stock.market.mapper;

import com.stock.market.dto.StockDTO;
import com.stock.market.entity.Stock;

public class StockMapper {
	
	public static StockDTO convertToDto(Stock stock) {
		
		return new StockDTO(
				stock.getId(), 
				stock.getCompanyName(), 
				stock.getMarketName(), 
				stock.getFoundedDate(), 
				stock.getStockPrice());
	}
	
	public static Stock convertToEntity(StockDTO dto) {
	 	Stock stock = new Stock();

	 	stock.setId(dto.getId());
	 	stock.setCompanyName(dto.getCompanyName());
	 	stock.setMarketName(dto.getMarketName());
	 	stock.setFoundedDate(dto.getFoundedDate());
	 	stock.setStockPrice(dto.getStockPrice());

	    return stock;
	}

}
