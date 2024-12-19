package com.example.fypproject;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EditProductActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText etProductName, etProductBrand, etProductPrice, etProductWeight, etProductDescription, etProductQuantity;
    private ImageView ivProductImage;
    private Button btnUpdate;
    private String productId = "-OEUn_Uq24AaLpEIEgNs"; // 直接設置商品ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

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
        btnUpdate = findViewById(R.id.btnUpdate);

        // 從Firebase讀取商品資訊
        loadProductInfo();

        // 設定更新按鈕點擊事件
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EditProductActivity", "Update button clicked");
                updateProductInfo();
            }
        });
    }

    private void loadProductInfo() {
        mDatabase.child("products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                if (product != null) {
                    etProductName.setText(product.name);
                    etProductBrand.setText(product.brand);
                    etProductPrice.setText(product.price);
                    etProductWeight.setText(product.weight);
                    etProductDescription.setText(product.description);
                    etProductQuantity.setText(product.quantity);
                    // 如果有圖片URL，載入圖片
                } else {
                    Log.e("EditProductActivity", "Product data is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("EditProductActivity", "Failed to load product info", databaseError.toException());
            }
        });
    }

    private void updateProductInfo() {
        String name = etProductName.getText().toString();
        String brand = etProductBrand.getText().toString();
        String price = etProductPrice.getText().toString();
        String weight = etProductWeight.getText().toString();
        String description = etProductDescription.getText().toString();
        String quantity = etProductQuantity.getText().toString();
        String imageUrl = ""; // 需要實現圖片上傳並獲取圖片URL

        // 打印所有獲取的值
        Log.d("EditProductActivity", "Updating product with values: "
                + "Name=" + name + ", Brand=" + brand + ", Price=" + price
                + ", Weight=" + weight + ", Description=" + description
                + ", Quantity=" + quantity + ", ImageUrl=" + imageUrl
                + ", Product ID=" + productId);

        // 建立更新後的商品物件
        Product product = new Product(name, brand, price, weight, description, quantity, imageUrl);

        // 更新Firebase中的商品資訊
        mDatabase.child("products").child(productId).setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // 更新成功
                        Log.d("EditProductActivity", "Product updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("EditProductActivity", "Failed to update product", e);
                    }
                });
    }

    // 商品類
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
