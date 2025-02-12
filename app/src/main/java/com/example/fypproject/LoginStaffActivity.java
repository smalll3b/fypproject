package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginStaffActivity extends AppCompatActivity {
    private EditText etAdminID, etPassword;
    private Button btnLogin;
    private ImageButton imgbtnReturn;
    private TextView tvSwitchUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login);

        etAdminID = findViewById(R.id.etAdminID);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        imgbtnReturn = findViewById(R.id.imgbtnReturn);
        tvSwitchUser = findViewById(R.id.tvSwitchUser);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginStaffActivity.this, IndexStaffActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgbtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginStaffActivity.this, IndexUserActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvSwitchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginStaffActivity.this, LoginUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}