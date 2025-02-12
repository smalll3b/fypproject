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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProductEditActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ImageView productImage;
    private TextView productName, productBrand, productPrice, productWeight, productDescription, productQuantity;
    private Button editButton;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_item_edit);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Connect UI components
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productBrand = findViewById(R.id.product_brand);
        productPrice = findViewById(R.id.product_price);
        editButton = findViewById(R.id.edit_button);

        // Get product ID from intent
        productId = getIntent().getStringExtra("productId");

        // Check if productId is null
        if (productId == null) {
            Log.e("EditActivity", "Product ID is null");
            finish(); // Close the activity if productId is null
            return;
        }

        // Load product information from Firebase
        loadProductInfo();

        // Set edit button click event
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to EditProductActivity
                Intent intent = new Intent(ProductEditActivity.this, EditProductActivity.class);
                intent.putExtra("productId", productId);
                startActivity(intent);
            }
        });
    }

    private void loadProductInfo() {
        mDatabase.child("products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                if (product != null) {
                    productName.setText(product.name);
                    productBrand.setText(product.brand);
                    productPrice.setText(product.price);
                    // If there's an image URL, load the image
                } else {
                    Log.e("editActivity", "Product data is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("editActivity", "Failed to load product info", databaseError.toException());
            }
        });
    }
}