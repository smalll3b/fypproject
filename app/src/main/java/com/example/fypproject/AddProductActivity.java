package com.example.fypproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {

    Uri uri;

    Button btnViewProduct;
    Button btnViewProductList;
    Button btnPickImage;
    Button btnSubmit;
    ImageView ivProductImage;
    EditText etProductName;
    EditText etProductBrand;
    EditText etProductPrice;
    EditText etProductWeight;
    EditText etProductQuantity;
    EditText etProductDescription;

//    String[][] ets = {
//            {"etProductName",        "String"},
//            {"etProductBrand",       "String"},
//            {"etProductPrice",       "int"},
//            {"etProductWeight",      "float"},
//            {"etProductQuantity",    "int"},
//            {"etProductDescription", "String"},
//    };

    UploadMedia uploadMedia = new UploadMedia();
    UploadData uploadData = new UploadData();

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), imgUri -> {
                if (imgUri != null) {
                    ContentResolver CR = this.getContentResolver();
                    String type = CR.getType(imgUri);
                    ivProductImage.setImageURI(imgUri);
                    uri = imgUri;
                } else {
                    //callback
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_product_staff);

        ivProductImage = findViewById(R.id.ivProductImage);
        etProductName = (EditText)findViewById(R.id.etProductName);
        etProductBrand = findViewById(R.id.etProductBrand);
        etProductPrice = findViewById(R.id.etProductPrice);
        etProductWeight = findViewById(R.id.etProductWeight);
        etProductQuantity = findViewById(R.id.etProductQuantity);
        etProductDescription = findViewById(R.id.etProductDescription);

        // 查看商品按钮
        btnViewProduct = findViewById(R.id.btnViewProduct);
        btnViewProduct.setOnClickListener(view -> {
            Intent intent = new Intent(AddProductActivity.this, ProductDetailActivity.class);
            startActivity(intent);
        });

        // 查看商品列表按钮
        btnViewProductList = findViewById(R.id.btnViewProductList);
        btnViewProductList.setOnClickListener(view -> {
            Intent intent = new Intent(AddProductActivity.this, ProductListActivity.class);
            startActivity(intent);
        });

        //user pick product image
        btnPickImage = findViewById(R.id.btnPickImage);
        btnPickImage.setOnClickListener(view -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());

        });

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(view -> {
            uploadMedia.uploadImage(uri);
            uploadData.createProduct(new Product(
                etProductName.getText().toString(),
                etProductBrand.getText().toString(),
                Float.parseFloat(etProductPrice.getText().toString()),
                Float.parseFloat(etProductWeight.getText().toString()),
                Integer.parseInt(etProductQuantity.getText().toString()),
                etProductDescription.getText().toString(),
                uri.toString()
                )
            );
            startActivity(new Intent(AddProductActivity.this, ProductListActivity.class));
        });
    }
}