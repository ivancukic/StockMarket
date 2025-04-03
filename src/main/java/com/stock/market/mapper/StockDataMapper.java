package com.stock.market.mapper;

import com.stock.market.dto.StockDataDTO;
import com.stock.market.entity.Stock;
import com.stock.market.entity.StockData;

public class StockDataMapper {
	
	public static StockDataDTO convertToDto(StockData stockData) {
		
		return new StockDataDTO(
				stockData.getId(), 
				stockData.getDate(), 
				stockData.getOpen(), 
				stockData.getHigh(), 
				stockData.getLow(),
				stockData.getClose(),
				stockData.getAdjClose(),
				stockData.getVolume(),
				StockMapper.convertToDto(stockData.getStock()));
	}
	
	public static StockData convertToEntity(StockDataDTO dto, Stock stock) {
	 	StockData stockData = new StockData();

	 	stockData.setId(dto.getId());
	 	stockData.setDate(dto.getDate());
	 	stockData.setOpen(dto.getOpen());
	 	stockData.setHigh(dto.getHigh());
	 	stockData.setLow(dto.getLow());
	 	stockData.setClose(dto.getClose());
	 	stockData.setAdjClose(dto.getAdjClose());
	 	stockData.setVolume(dto.getVolume());
	 	stockData.setStock(stock);

	    return stockData;
	}

}
