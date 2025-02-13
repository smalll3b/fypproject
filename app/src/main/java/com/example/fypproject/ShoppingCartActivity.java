package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingCartActivity extends AppCompatActivity {
    private Button btnHomePage, btnPersonalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart);

        btnHomePage = findViewById(R.id.btnHomePage);
        btnPersonalInfo = findViewById(R.id.btnPersonalInfo);

        btnHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, IndexUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingCartActivity.this, LoginUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
