package com.stock.market.dto;

import java.time.LocalDate;

public class StockDataDTO {
	
	private Long id;
	private LocalDate date;
	private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Double adjClose;
    private Long volume;
    private StockDTO stock;
    
    
	public StockDataDTO() {

	}

	public StockDataDTO(Long id, LocalDate date, Double open, Double high, Double low, Double close, Double adjClose,
			Long volume, StockDTO stock) {
		this.id = id;
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adjClose = adjClose;
		this.volume = volume;
		this.stock = stock;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public Double getOpen() {
		return open;
	}


	public void setOpen(Double open) {
		this.open = open;
	}


	public Double getHigh() {
		return high;
	}


	public void setHigh(Double high) {
		this.high = high;
	}


	public Double getLow() {
		return low;
	}


	public void setLow(Double low) {
		this.low = low;
	}


	public Double getClose() {
		return close;
	}


	public void setClose(Double close) {
		this.close = close;
	}


	public Double getAdjClose() {
		return adjClose;
	}


	public void setAdjClose(Double adjClose) {
		this.adjClose = adjClose;
	}


	public Long getVolume() {
		return volume;
	}


	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public StockDTO getStock() {
		return stock;
	}

	public void setStock(StockDTO stock) {
		this.stock = stock;
	}
    
}
