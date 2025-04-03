package com.stock.market.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.stock.market.dto.ResponseDTO;
import com.stock.market.dto.StockDTO;
import com.stock.market.dto.StockDataDTO;


public interface StockDataService {
	
	List<StockDataDTO> findAll();
	
	StockDataDTO save(StockDataDTO dto);
	
	Optional<StockDataDTO> findById(Long id);
	
	StockDataDTO update(Long id, StockDataDTO dto);
	
	void delete(Long id);
	
	List<StockDataDTO> findStockDataByStock(StockDTO dto);
	
	ResponseDTO getCompanyStockForThreePeriods(String companyName, LocalDate startDate, LocalDate endDate);

}
