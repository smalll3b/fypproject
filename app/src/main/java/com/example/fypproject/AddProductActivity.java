package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_product_staff);

        // 查看商品按钮
        Button btnViewProduct = findViewById(R.id.btnViewProduct);
        btnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }
        });
        // 查看商品列表按钮
        Button btnViewProductList = findViewById(R.id.btnViewProductList);
        btnViewProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });
    }
}