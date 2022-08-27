package com.nphase.entity;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final BigDecimal pricePerUnit;
    private final int quantity;

    private String category;
    private Double discountPercentage;

    public Product(String name, BigDecimal pricePerUnit, int quantity,
                   String category, Double discountPercentage) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.category = category;
        this.discountPercentage = discountPercentage;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public Double getDiscountPercentage(){
        return discountPercentage;
    }
}
