package com.stock.market.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.market.dto.StockDTO;
import com.stock.market.exception.StockNotFoundException;
import com.stock.market.service.StockService;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
	
	private final StockService stockService;

	public StockController(StockService stockService) {
		this.stockService = stockService;
	}
	
	@GetMapping
	public ResponseEntity<List<StockDTO>> findAll() {
		
		return ResponseEntity.ok(stockService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StockDTO> getStock(@PathVariable Long id) {
		return stockService.findById(id)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new StockNotFoundException("Stock with id " + id + " not found"));
	}
	
	@GetMapping("/company-name")
	public ResponseEntity<StockDTO> getStockByName(@RequestParam String companyName) {
	    return stockService.findByCompanyName(companyName)
	           .map(ResponseEntity::ok)
	           .orElseThrow(() -> new StockNotFoundException("Stock with company name " + companyName + " not found"));
	}
	
	@PostMapping
	ResponseEntity<StockDTO> createStock(@RequestBody StockDTO dto) {
	    return ResponseEntity.status(HttpStatus.CREATED).body(stockService.save(dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StockDTO> updateStock(@PathVariable Long id, @RequestBody StockDTO dto) {
		return ResponseEntity.ok(stockService.update(id, dto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
		stockService.delete(id);
		return ResponseEntity
				.noContent()
				.build();
	}

}
