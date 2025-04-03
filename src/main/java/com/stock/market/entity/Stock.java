package com.stock.market.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stocks")
public class Stock {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String companyName;
	
	@Column
	private String marketName;

	@Column
	private LocalDate foundedDate;
	
	@Column
	private Double stockPrice;

	public Stock() {

	}

	public Stock(Long id, String companyName, String marketName, LocalDate foundedDate, Double stockPrice) {
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

	@Override
	public String toString() {
		return "Stock [id=" + id + ", companyName=" + companyName + ", marketName=" + marketName + ", foundedDate="
				+ foundedDate + ", stockPrice=" + stockPrice + "]";
	}

}
