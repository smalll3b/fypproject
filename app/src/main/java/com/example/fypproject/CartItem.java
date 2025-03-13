package com.example.fypproject;

public class CartItem {

    public String cartId; // 新增字段，存儲 cartId
    public String productId;
    public String productName;
    public int quantity;
    public double price;
    public String productImageUrl;
    public String productBrand;

    public CartItem() {
        // 默認構造函數（Firebase 要求）
    }

    public CartItem(String cartId, String productId, String productName, int quantity, double price, String productImageUrl, String productBrand) {
        this.cartId = cartId; // 初始化 cartId
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.productImageUrl = productImageUrl;
        this.productBrand = productBrand;
    }
}
