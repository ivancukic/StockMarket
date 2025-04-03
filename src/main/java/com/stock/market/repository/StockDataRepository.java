package com.stock.market.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.market.entity.Stock;
import com.stock.market.entity.StockData;

public interface StockDataRepository extends JpaRepository<StockData, Long> {
	
	List<StockData> findStockDataByStock(Stock stock);
	
	List<StockData> findByStockAndDateBetweenOrderByDate(Stock stock, LocalDate startDate, LocalDate endDate);

}
