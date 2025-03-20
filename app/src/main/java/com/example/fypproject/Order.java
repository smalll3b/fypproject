package com.example.fypproject;

public class Order {

    public String orderId; // 訂單 ID
    public String productId; // 產品 ID
    public String productName; // 產品名稱
    public String orderDate; // 訂單日期
    public String username; // 用戶名稱

    // 默認構造函數（Firebase 要求）
    public Order() {}

    // 帶參數的構造函數
    public Order(String orderId, String productId, String productName, String orderDate, String username) {
        this.orderId = orderId;         // 初始化 orderId
        this.productId = productId;     // 初始化 productId
        this.productName = productName; // 初始化 productName
        this.orderDate = orderDate;     // 初始化 orderDate
        this.username = username;       // 初始化 username
    }
}
