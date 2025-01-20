package com.example.fypproject;

public class Product {
    public String name;
    public String brand;
    public String price;  // 将数据类型改为String以匹配Firebase数据
    public String weight; // 将数据类型改为String以匹配Firebase数据
    public String quantity;
    public String description;
    public String imageUrl;

    // No-argument constructor required for Firebase
    public Product() {}

    public Product(String name, String brand, String price, String weight, String quantity, String description, String imageUrl) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.weight = weight;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
