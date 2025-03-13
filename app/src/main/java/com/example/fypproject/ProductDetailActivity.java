package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ImageView productImage;
    private TextView productName, productBrand, productPrice, productWeight, productDescription, productQuantity;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Connect UI components
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productBrand = findViewById(R.id.product_brand);
        productPrice = findViewById(R.id.product_price);
        productWeight = findViewById(R.id.product_weight);
        productDescription = findViewById(R.id.product_description);
        productQuantity = findViewById(R.id.product_quantity);

        // Get product ID from intent
        productId = getIntent().getStringExtra("productId");

        // Check if productId is null
        if (productId == null) {
            Log.e("ProductDetailActivity", "Product ID is null");
            finish(); // Close the activity if productId is null
            return;
        }

        // Load product information from Firebase
        loadProductInfo();

        // Set up button click listeners
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        Button btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle buy action
            }
        });
    }

    private void loadProductInfo() {
        mDatabase.child("products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                if (product != null) {
                    if (!product.imageUrl.isEmpty()) {
                        Glide.with(ProductDetailActivity.this).load(product.imageUrl).into(productImage);
                    }
                    productName.setText(product.name);
                    productBrand.setText(product.brand);
                    productPrice.setText("$" + product.price);
                    productWeight.setText(product.weight);
                    productDescription.setText(product.description);
                    productQuantity.setText("庫存 " + product.quantity);
                } else {
                    Log.e("ProductDetailActivity", "Product data is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ProductDetailActivity", "Failed to load product info", databaseError.toException());
            }
        });
    }

    private void addToCart() {
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart");

        // 自動生成 cartId
        String cartId = cartRef.push().getKey();

        // 確保 cartId 不為 null
        if (cartId != null) {
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("products").child(productId);

            // 從產品節點獲取數據
            productRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String productName = dataSnapshot.child("name").getValue(String.class);
                    String priceString = dataSnapshot.child("price").getValue(String.class);
                    double productPrice = 0.0;

                    try {
                        productPrice = Double.parseDouble(priceString);
                    } catch (NumberFormatException e) {
                        Log.e("addToCart", "價格轉換失敗: " + e.getMessage());
                    }

                    String productImageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    String productBrand = dataSnapshot.child("brand").getValue(String.class);

                    if (productName != null && productImageUrl != null && productBrand != null) {
                        // 創建 CartItem 對象，並包含 cartId
                        CartItem cartItem = new CartItem(
                                cartId, // 傳入生成的 cartId
                                productId,
                                productName,
                                1, // 預設數量
                                productPrice,
                                productImageUrl,
                                productBrand
                        );

                        // 將 CartItem 添加到 Firebase
                        cartRef.child(cartId).setValue(cartItem).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i("addToCart", "產品成功添加到購物車，cartId: " + cartId);
                            } else {
                                Log.e("addToCart", "添加產品到購物車失敗");
                            }
                        });
                    } else {
                        Log.e("addToCart", "產品數據不完整或為空");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("addToCart", "獲取產品數據失敗", databaseError.toException());
                }
            });
        } else {
            Log.e("addToCart", "無法生成 cartId");
        }
    }
}