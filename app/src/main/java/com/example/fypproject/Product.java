package com.example.fypproject;

public class Product {
    public String id;  // Add id field
    public String name;
    public String brand;
    public String price;
    public String weight;
    public String quantity;
    public String description;
    public String imageUrl;

    // No-argument constructor required for Firebase
    public Product() {}

    public Product(String id, String name, String brand, String price, String weight, String quantity, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.weight = weight;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}