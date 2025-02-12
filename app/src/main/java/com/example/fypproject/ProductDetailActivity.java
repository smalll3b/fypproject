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
}