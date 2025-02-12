package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginUserActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin, switchUserButton;
    private ImageButton imgbtnReturn;
    private TextView tvRegister, tvSwitchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        imgbtnReturn = findViewById(R.id.imgbtnReturn);
        tvRegister = findViewById(R.id.tvRegister);
        tvSwitchUser = findViewById(R.id.tvSwitchUser);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUserActivity.this, IndexUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUserActivity.this, IndexUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUserActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tvSwitchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUserActivity.this, LoginStaffActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}