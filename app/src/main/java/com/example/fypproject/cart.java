package com.example.fypproject;

import java.util.HashMap;
import java.util.Map;

public class cart {
    private Map<String, CartItem> items = new HashMap<>();

    public void addItem(Product product) {
        if (items.containsKey(product.id)) {
            CartItem cartItem = items.get(product.id);
            cartItem.quantity++;
        } else {
            items.put(product.id, new CartItem(product, 1));
        }
    }

    public void removeItem(Product product) {
        if (items.containsKey(product.id)) {
            CartItem cartItem = items.get(product.id);
            if (cartItem.quantity > 1) {
                cartItem.quantity--;
            } else {
                items.remove(product.id);
            }
        }
    }

    public Map<String, CartItem> getItems() {
        return items;
    }

    public static class CartItem {
        public Product product;
        public int quantity;

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }
}