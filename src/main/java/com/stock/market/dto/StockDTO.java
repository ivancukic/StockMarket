package com.stock.market.dto;

import java.time.LocalDate;

public class StockDTO {
	
	private Long id;
	
	private String companyName;
	
	private String marketName;
	
	private LocalDate foundedDate;
	
	private Double stockPrice;

	public StockDTO() {

	}

	public StockDTO(Long id, String companyName, String marketName, LocalDate foundedDate, Double stockPrice) {

		this.id = id;
		this.companyName = companyName;
		this.marketName = marketName;
		this.foundedDate = foundedDate;
		this.stockPrice = stockPrice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public LocalDate getFoundedDate() {
		return foundedDate;
	}

	public void setFoundedDate(LocalDate foundedDate) {
		this.foundedDate = foundedDate;
	}

	public Double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(Double stockPrice) {
		this.stockPrice = stockPrice;
	}

}
