package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Find the Home Page Button (主頁按鈕)
        Button btnHomePage = findViewById(R.id.btnHomePage);
        btnHomePage.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, IndexUserActivity.class);
            startActivity(intent);
        });

        // Find the Order Data Button (訂單資料按鈕)
        Button orderDataButton = findViewById(R.id.orderDataButton);
        orderDataButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, OrderDetailActivity.class);
            startActivity(intent);
        });

        // Find the Logout Button (登出按鈕)
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
            // Clear any necessary session or user data here (optional)

            // Redirect to LoginUserActivity
            Intent intent = new Intent(ProfileActivity.this, LoginUserActivity.class);
            startActivity(intent);
            finish(); // Close ProfileActivity to prevent returning on back press
        });
    }
}
