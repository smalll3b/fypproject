package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText adminIdEditText, adminPasswordEditText;
    private Button adminLoginButton, switchUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login);

        adminIdEditText = findViewById(R.id.adminIdEditText);
        adminPasswordEditText = findViewById(R.id.adminPasswordEditText);
        adminLoginButton = findViewById(R.id.adminLoginButton);
        switchUserButton = findViewById(R.id.switchUserButton); // Define the switch user button

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle admin login logic here
                Intent intent = new Intent(AdminLoginActivity.this, StaffActivity.class);
                startActivity(intent);
                finish(); // End current activity
            }
        });

        switchUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle switch user logic here
                Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // End current activity
            }
        });
    }
}