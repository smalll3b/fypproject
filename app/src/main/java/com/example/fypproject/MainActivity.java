package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.snapshot.Index;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserLogin.setLoggingState(false);
        Navigator.startActivityAndFinish(MainActivity.this, IndexUserActivity.class);
        finish();
    }
}