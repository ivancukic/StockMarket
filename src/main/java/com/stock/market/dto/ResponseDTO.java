package com.stock.market.dto;

public class ResponseDTO {
	
	private PeriodDTO previous;
	
	private PeriodDTO asked;
	
	private PeriodDTO next;
	

	public PeriodDTO getPrevious() {
		return previous;
	}

	public void setPrevious(PeriodDTO previous) {
		this.previous = previous;
	}

	public PeriodDTO getAsked() {
		return asked;
	}

	public void setAsked(PeriodDTO asked) {
		this.asked = asked;
	}

	public PeriodDTO getNext() {
		return next;
	}

	public void setNext(PeriodDTO next) {
		this.next = next;
	}

}
