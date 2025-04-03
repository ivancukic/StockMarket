package com.stock.market.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.stock.market.exception.BadRequestAlertException;
import com.stock.market.exception.ResourceNotFoundException;
import com.stock.market.exception.StockDataNotFoundException;
import com.stock.market.exception.StockNotFoundException;

public class GlobalExceptionHandler {
	
	@ExceptionHandler(StockNotFoundException.class)
	@ResponseStatus(code = org.springframework.http.HttpStatus.NOT_FOUND)
	public String handleStockNotFoundException(StockNotFoundException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(StockDataNotFoundException.class)
	@ResponseStatus(code = org.springframework.http.HttpStatus.NOT_FOUND)
	public String handleStockDataNotFoundException(StockDataNotFoundException exception) {
		return exception.getMessage();
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException exception) {
		Map<String, Object> errorResponse = new HashMap<String, Object>();
		
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", HttpStatus.NOT_FOUND.value());
		errorResponse.put("error", "Resource Not Found");
		errorResponse.put("message", exception.getMessage());
		
		return new ResponseEntity<Map<String,Object>>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestAlertException.class)
	public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestAlertException exeption) {
	    Map<String, Object> errorResponse = new HashMap<>();
	    errorResponse.put("timestamp", LocalDateTime.now());
	    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
	    errorResponse.put("error", "Bad Request");
	    errorResponse.put("message", exeption.getMessage());

	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
