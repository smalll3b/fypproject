package com.example.fypproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class insertActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private EditText etProductName, etProductBrand, etProductPrice, etProductWeight, etProductDescription, etProductQuantity;
    private ImageView ivProductImage;
    private Button btnSubmit, btnViewProduct, btnViewProductList, btnPickImage;
    private String selectedImageUrl;

    private String[] imageUrls = {
            "https://firebasestorage.googleapis.com/v0/b/fypproject-c6b94.appspot.com/o/images%2F1000094648?alt=media&token=496c5ae1-724e-4420-8548-64ed2e9bb7e1",
            "https://firebasestorage.googleapis.com/v0/b/fypproject-c6b94.appspot.com/o/images%2Fimage%3A31?alt=media&token=148c1176-812e-491b-8772-d98998b265df",
            "https://firebasestorage.googleapis.com/v0/b/fypproject-c6b94.appspot.com/o/images%2Fmsf%3A17?alt=media&token=26d15b9e-367c-4645-a04b-51493269192c",
            "https://firebasestorage.googleapis.com/v0/b/fypproject-c6b94.appspot.com/o/images%2Fmsf%3A18?alt=media&token=f8110a34-41d1-4245-a4ff-2d2848993751"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_product_staff);

        // 初始化Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 连接界面组件
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
        btnPickImage = findViewById(R.id.btnPickImage);

        // 设置提交按钮点击事件
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeNewProduct();
            }
        });

        // 设置查看商品按钮点击事件
        btnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(insertActivity.this, EditProductActivity.class);
                startActivity(intent);
            }
        });

        // 设置查看商品列表按钮点击事件
        btnViewProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(insertActivity.this, DeleteProductActivity.class);
                startActivity(intent);
            }
        });

        // 设置选择图片按钮点击事件
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectionDialog();
            }
        });
    }

    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择照片")
                .setItems(imageUrls, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedImageUrl = imageUrls[which];
                        Glide.with(insertActivity.this)
                                .load(selectedImageUrl)
                                .into(ivProductImage);
                    }
                });
        builder.create().show();
    }

    private void writeNewProduct() {
        // 获取输入的数据
        String name = etProductName.getText().toString();
        String brand = etProductBrand.getText().toString();
        String price = etProductPrice.getText().toString();
        String weight = etProductWeight.getText().toString();
        String description = etProductDescription.getText().toString();
        String quantity = etProductQuantity.getText().toString();
        String imageUrl = selectedImageUrl != null ? selectedImageUrl : ""; // 使用选择的图片URL

        // 创建商品对象
        Product product = new Product(name, brand, price, weight, description, quantity, imageUrl);

        // 将商品数据写入Firebase Realtime Database
        mDatabase.child("products").push().setValue(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // 写入成功
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // 写入失败
                    }
                });
    }

    // 定义商品类
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
