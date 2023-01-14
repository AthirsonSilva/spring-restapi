package com.example.restful.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restful.controllers.models.ProductID;
import com.example.restful.models.ProductModel;

@RestController()
public class ProductController {
	ArrayList<ProductModel> products = new ArrayList<ProductModel>();

	@GetMapping()
	public String index() {
		return "Hello World!";
	}

	@PostMapping("product")
	public ResponseEntity<ProductID> create(@RequestBody final ProductModel product) {
		ProductID result = new ProductID(UUID.randomUUID().toString());

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON)
				.body(result);
	}

	@GetMapping("product")
	public List<ProductModel> list() {
		for (int i = 0; i < 10; i++) {
			products.add(new ProductModel(UUID.randomUUID().toString(), "Product " + i, new java.math.BigDecimal("1.00")));
		}

		return products;
	}

	@GetMapping("product/{id}")
	public ResponseEntity<ProductModel> get(@PathVariable final ProductID id) {
		if (id == null) {
			return ResponseEntity
					.status(Response.SC_NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}

		for (ProductModel product : products) {
			if (product.getId().equals(id.getId())) {
				return ResponseEntity
						.status(Response.SC_OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(product);
			}
		}

		return ResponseEntity
				.status(Response.SC_NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body(null);
	}

	@PatchMapping("product/{id}")
	public void update(@PathVariable final ProductID id, @RequestBody ProductModel updatedProduct) {
		if (id == null) {
			throw new RuntimeException("An ID is required");
		}

		ProductModel productToUpdate = this.get(id).getBody();

		if (productToUpdate == null) {
			throw new RuntimeException("Product not found");
		}

		productToUpdate.setDescription(updatedProduct.getDescription());
		productToUpdate.setPrice(updatedProduct.getPrice());

		int index = products.indexOf(productToUpdate);

		products.remove(productToUpdate);
		products.add(index, productToUpdate);
	}

	@DeleteMapping("product/{id}")
	public void delete(@PathVariable final ProductID id) {
		if (id == null) {
			throw new RuntimeException("An ID is required");
		}

		ProductModel productToDelete = this.get(id).getBody();

		if (productToDelete == null) {
			throw new RuntimeException("Product not found");
		}

		products.remove(productToDelete);
	}
}
