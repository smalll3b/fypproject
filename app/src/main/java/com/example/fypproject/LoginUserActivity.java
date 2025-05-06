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
                UserLogin.setLoggingState(true);
                Navigator.startActivityAndFinish(LoginUserActivity.this, IndexUserActivity.class);
            }
        });

        imgbtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.startActivityAndFinish(LoginUserActivity.this, IndexUserActivity.class);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.startActivityAndFinish(LoginUserActivity.this, RegisterActivity.class);
            }
        });

        tvSwitchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.startActivityAndFinish(LoginUserActivity.this, LoginStaffActivity.class);
            }
        });
    }
}