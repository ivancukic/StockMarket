package com.stock.market.service;

import java.util.List;
import java.util.Optional;

import com.stock.market.dto.StockDTO;

public interface StockService {
	
	List<StockDTO> findAll();
	
	StockDTO save(StockDTO dto);
	
	Optional<StockDTO> findById(Long id);
	
	StockDTO update(Long id, StockDTO dto);
	
	void delete(Long id);
	
	Optional<StockDTO> findByCompanyName(String companyName);

}
