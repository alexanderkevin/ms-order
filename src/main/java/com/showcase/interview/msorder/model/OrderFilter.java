package com.showcase.interview.msorder.model;

import java.math.BigDecimal;

public class OrderFilter {

	private String currency;

	private BigDecimal priceMin;
	
	private BigDecimal priceMax;
	
	private int qtyMin;
	
	private int qtyMax;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(BigDecimal priceMin) {
		this.priceMin = priceMin;
	}

	public BigDecimal getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(BigDecimal priceMax) {
		this.priceMax = priceMax;
	}

	public int getQtyMin() {
		return qtyMin;
	}

	public void setQtyMin(int qtyMin) {
		this.qtyMin = qtyMin;
	}

	public int getQtyMax() {
		return qtyMax;
	}

	public void setQtyMax(int qtyMax) {
		this.qtyMax = qtyMax;
	}
	
	
    
}
