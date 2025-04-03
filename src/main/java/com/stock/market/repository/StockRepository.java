package com.stock.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.market.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
	
	Stock findByCompanyName(String companyName);

}
