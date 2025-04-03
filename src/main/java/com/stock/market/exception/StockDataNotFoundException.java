package com.stock.market.exception;

public class StockDataNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StockDataNotFoundException(String message) {
		super(message);
	}

}
