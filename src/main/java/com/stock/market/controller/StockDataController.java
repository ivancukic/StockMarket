package com.stock.market.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.market.dto.ResponseDTO;
import com.stock.market.dto.StockDTO;
import com.stock.market.dto.StockDataDTO;
import com.stock.market.exception.StockDataNotFoundException;
import com.stock.market.service.StockDataService;

@RestController
@RequestMapping("/api/stock-data")
public class StockDataController {
	
	private final StockDataService stockDataService;

	public StockDataController(StockDataService stockDataService) {
		this.stockDataService = stockDataService;
	}
	
	@GetMapping
	public ResponseEntity<List<StockDataDTO>> findAll() {
		
		return ResponseEntity.ok(stockDataService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StockDataDTO> getStockData(@PathVariable Long id) {
		return stockDataService.findById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new StockDataNotFoundException("Stock Data with id " + id + " not found"));
	}
	
	@PostMapping
	ResponseEntity<StockDataDTO> createStockData(@RequestBody StockDataDTO dto) {
	    return ResponseEntity.status(HttpStatus.CREATED).body(stockDataService.save(dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StockDataDTO> updateStockData(@PathVariable Long id, @RequestBody StockDataDTO dto) {
		return ResponseEntity.ok(stockDataService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStockData(@PathVariable Long id) {
		stockDataService.delete(id);
		return ResponseEntity
				.noContent()
				.build();
	}
	
	@GetMapping("/search-by-stock")
	public ResponseEntity<List<StockDataDTO>> getStockDataByStock(@RequestBody StockDTO dto) {
		return ResponseEntity.ok(stockDataService.findStockDataByStock(dto));
	}
	
	@GetMapping("/company-stock-by-date")
	public ResponseEntity<ResponseDTO> getCompanyStockForThreePeriods(String companyName, LocalDate startDate, LocalDate endDate) {
		return ResponseEntity.ok(stockDataService.getCompanyStockForThreePeriods(companyName, startDate, endDate));
	}

}
