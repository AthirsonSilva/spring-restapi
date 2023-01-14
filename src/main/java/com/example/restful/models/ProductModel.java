package com.example.restful.models;

import java.math.BigDecimal;

public class ProductModel {
	private final String id;

	public String getId() {
		return id;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ProductModel(
			String id,
			String description,
			BigDecimal price) {
		this.id = id;
		this.description = description;
		this.price = price;
	}

}
