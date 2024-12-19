package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insertActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private EditText etProductName, etProductBrand, etProductPrice, etProductWeight, etProductDescription, etProductQuantity;
    private ImageView ivProductImage;
    private Button btnSubmit, btnViewProduct, btnViewProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_product_staff);

        // 初始化Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 連接介面元件
        etProductName = findViewById(R.id.etProductName);
        etProductBrand = findViewById(R.id.etProductBrand);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductWeight = findViewById(R.id.etProductWeight);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnViewProduct = findViewById(R.id.btnViewProduct);
        btnViewProductList = findViewById(R.id.btnViewProductList);

        // 設定提交按鈕點擊事件
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewProduct();
            }
        });

        // 設定查看商品按鈕點擊事件
        btnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(insertActivity.this, EditProductActivity.class);
                startActivity(intent);
            }
        });

        // 設定查看商品列表按鈕點擊事件
        btnViewProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(insertActivity.this, DeleteProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void writeNewProduct() {
        // 獲取輸入的數據
        String name = etProductName.getText().toString();
        String brand = etProductBrand.getText().toString();
        String price = etProductPrice.getText().toString();
        String weight = etProductWeight.getText().toString();
        String description = etProductDescription.getText().toString();
        String quantity = etProductQuantity.getText().toString();
        String imageUrl = ""; // 你需要實現圖片上傳並獲取圖片URL

        // 建立商品物件
        Product product = new Product(name, brand, price, weight, description, quantity, imageUrl);

        // 將商品資料寫入Firebase Realtime Database
        mDatabase.child("products").push().setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // 寫入成功
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // 寫入失敗
                    }
                });
    }

    // 定義商品類
    public static class Product {
        public String name, brand, price, weight, description, quantity, imageUrl;

        public Product() {}

        public Product(String name, String brand, String price, String weight, String description, String quantity, String imageUrl) {
            this.name = name;
            this.brand = brand;
            this.price = price;
            this.weight = weight;
            this.description = description;
            this.quantity = quantity;
            this.imageUrl = imageUrl;
        }
    }
}
