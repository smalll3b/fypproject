package com.example.fypproject;

public class OrderItem {
    private String orderId;
    private String productName;
    private String orderDate;

    // 默認構造函數（Firebase 必須）
    public OrderItem() {}

    public OrderItem(String orderId, String productName, String orderDate) {
        this.orderId = orderId;
        this.productName = productName;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
