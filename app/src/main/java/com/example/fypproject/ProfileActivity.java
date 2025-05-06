package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Button btnHomePage, btnAIChat, btnShoppingCart, logoutButton;
    private LinearLayout orderDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnHomePage = findViewById(R.id.btnHomePage);
        btnAIChat = findViewById(R.id.btnAIChat);
        btnShoppingCart = findViewById(R.id.btnShoppingCart);
        logoutButton = findViewById(R.id.logoutButton);
        LinearLayout orderData = findViewById(R.id.orderData);

        // Find the Home Page Button (主頁按鈕)
        btnHomePage.setOnClickListener(view -> {
            Navigator.startActivityAndFinish(ProfileActivity.this, IndexUserActivity.class);
        });

        btnAIChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.startActivityAndFinish(ProfileActivity.this, AIChatActivity.class);
            }
        });

        btnShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.startActivityAndFinish(ProfileActivity.this, ShoppingCartActivity.class);
            }
        });

        // Find the Order Data Button (訂單資料按鈕)
        orderData.setOnClickListener(view -> {
            Navigator.startActivityAndFinish(ProfileActivity.this, OrderDetailActivity.class);
        });

        // Find the Logout Button (登出按鈕)
        logoutButton.setOnClickListener(view -> {
            // Clear any necessary session or user data here (optional)
            UserLogin.setLoggingState(false);
            // Redirect to LoginUserActivity
            Navigator.startActivityAndFinish(ProfileActivity.this, LoginUserActivity.class);
        });
    }
}
