package com.example.fypproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ImageView productImage;
    private TextView productName, productBrand, productPrice, productWeight, productDescription, productQuantity;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        // 初始化 Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 連接 UI 元件
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productBrand = findViewById(R.id.product_brand);
        productPrice = findViewById(R.id.product_price);
        productWeight = findViewById(R.id.product_weight);
        productDescription = findViewById(R.id.product_description);
        productQuantity = findViewById(R.id.product_quantity);

        // 從 Intent 中獲取 productId
        productId = getIntent().getStringExtra("productId");

        // 檢查 productId 是否為 null
        if (productId == null) {
            Log.e("ProductDetailActivity", "Product ID is null");
            finish(); // 若 productId 為 null，則關閉 Activity
            return;
        }

        // 從 Firebase 加載商品資訊
        loadProductInfo();

        // 設置按鈕點擊事件
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
                createOrder();
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
        String cartId = cartRef.push().getKey();
        if (cartId != null) {
            DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("products").child(productId);
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
                        CartItem cartItem = new CartItem(
                                cartId,
                                productId,
                                productName,
                                1,
                                productPrice,
                                productImageUrl,
                                productBrand
                        );
                        cartRef.child(cartId).setValue(cartItem).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.i("addToCart", "產品成功添加到購物車，cartId: " + cartId);
                                Toast.makeText(ProductDetailActivity.this, "已成功加入購物車！", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("addToCart", "添加產品到購物車失敗");
                                Toast.makeText(ProductDetailActivity.this, "添加到購物車失敗，請稍後重試。", Toast.LENGTH_SHORT).show();
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

    private void createOrder() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("orders");
        String orderId = ordersRef.push().getKey();
        if (orderId != null) {
            String orderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            String username = "user1"; // 假設用戶名為 user1

            Map<String, Object> orderData = new HashMap<>();
            orderData.put("orderId", orderId);
            orderData.put("productId", productId);
            orderData.put("productName", productName.getText().toString());
            orderData.put("orderDate", orderDate);
            orderData.put("username", username); // 新增用戶名稱

            ordersRef.child(orderId).setValue(orderData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.i("Order", "訂單已成功新增");
                    Toast.makeText(ProductDetailActivity.this, "訂單已成功建立！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Order", "新增訂單失敗", task.getException());
                    Toast.makeText(ProductDetailActivity.this, "訂單建立失敗，請稍後再試。", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e("createOrder", "無法生成 orderId");
        }
    }
}
