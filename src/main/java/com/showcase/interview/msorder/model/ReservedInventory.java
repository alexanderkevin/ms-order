package com.showcase.interview.msorder.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReservedInventory extends BaseModel implements Serializable {

	private Long id;

	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
