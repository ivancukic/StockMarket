package com.stock.market.dto;

import java.time.LocalDate;

public class PeriodDTO {
	
	private LocalDate minDate;
	
	private Double minPrice;
	
	private LocalDate maxDate;
	
	private Double maxPrice;
	
	private Double profitByDate;
	
	private Double maxProfitByDate;
	

	public LocalDate getMinDate() {
		return minDate;
	}

	public void setMinDate(LocalDate minDate) {
		this.minDate = minDate;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public LocalDate getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(LocalDate maxDate) {
		this.maxDate = maxDate;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Double getProfitByDate() {
		return profitByDate;
	}

	public void setProfitByDate(Double profitByDate) {
		this.profitByDate = profitByDate;
	}

	public Double getMaxProfitByDate() {
		return maxProfitByDate;
	}

	public void setMaxProfitByDate(Double maxProfitByDate) {
		this.maxProfitByDate = maxProfitByDate;
	}

}
