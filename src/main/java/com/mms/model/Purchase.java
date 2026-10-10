package com.mms.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Supplier supplier;

    private int quantity;
    private LocalDate purchaseDate;

    // getters & setters
    public int getId() { 
    	return id; 
    	}
    public void setId(int id) {
    	this.id = id; 
    	}

    public Product getProduct() {
    	return product;
    	}
    public void setProduct(Product product) { 
    	this.product = product; 
    	}

    public Supplier getSupplier() { 
    	return supplier;
    	}
    public void setSupplier(Supplier supplier) {
    	this.supplier = supplier; 
    	}

    public int getQuantity() { 
    	return quantity;
    	}
    public void setQuantity(int quantity) { 
    	this.quantity = quantity; 
    	}

    public LocalDate getPurchaseDate() { 
    	return purchaseDate; 
    	}
    public void setPurchaseDate(LocalDate purchaseDate) {
    	this.purchaseDate = purchaseDate;
    	}
}