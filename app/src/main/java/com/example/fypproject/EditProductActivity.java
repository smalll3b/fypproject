package com.example.fypproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EditProductActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText etProductName, etProductBrand, etProductPrice, etProductWeight, etProductDescription, etProductQuantity;
    private ImageView ivProductImage;
    private Button btnUpdate, btnPickImage;
    private String productId;
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
        setContentView(R.layout.activity_edit_product);

        // 获取传递的产品ID
        productId = getIntent().getStringExtra("productId");

        // 初始化Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // 连接界面元件
        etProductName = findViewById(R.id.etProductName);
        etProductBrand = findViewById(R.id.etProductBrand);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductWeight = findViewById(R.id.etProductWeight);
        etProductDescription = findViewById(R.id.etProductDescription);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnPickImage = findViewById(R.id.btnPickImage);

        // 从Firebase读取商品信息
        loadProductInfo();

        // 设置更新按钮点击事件
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EditProductActivity", "Update button clicked");
                updateProductInfo();
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
                    // 如果有图片URL，加载图片
                    if (!product.imageUrl.isEmpty()) {
                        Glide.with(EditProductActivity.this).load(product.imageUrl).into(ivProductImage);
                        selectedImageUrl = product.imageUrl;
                    }
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

    private void showImageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择照片")
                .setItems(imageUrls, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedImageUrl = imageUrls[which];
                        Glide.with(EditProductActivity.this)
                                .load(selectedImageUrl)
                                .into(ivProductImage);
                    }
                });
        builder.create().show();
    }

    private void updateProductInfo() {
        String name = etProductName.getText().toString();
        String brand = etProductBrand.getText().toString();
        String price = etProductPrice.getText().toString();
        String weight = etProductWeight.getText().toString();
        String description = etProductDescription.getText().toString();
        String quantity = etProductQuantity.getText().toString();
        String imageUrl = selectedImageUrl != null ? selectedImageUrl : ""; // 使用选择的图片URL

        // 打印所有获取的值
        Log.d("EditProductActivity", "Updating product with values: "
                + "Name=" + name + ", Brand=" + brand + ", Price=" + price
                + ", Weight=" + weight + ", Description=" + description
                + ", Quantity=" + quantity + ", ImageUrl=" + imageUrl
                + ", Product ID=" + productId);

        // 创建更新后的商品对象
        Product product = new Product(productId, name, brand, price, weight, description, quantity, imageUrl);

        // 更新Firebase中的商品信息
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
}
