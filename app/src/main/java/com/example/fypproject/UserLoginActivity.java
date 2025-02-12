package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserLoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin, switchUserButton;
    private ImageButton imgbtnReturn;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        imgbtnReturn = findViewById(R.id.imgbtnReturn);
        tvRegister = findViewById(R.id.tvRegister);
        //switchUserButton = findViewById(R.id.); // Define the switch user button

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, indexActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, indexActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });



//        switchUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}