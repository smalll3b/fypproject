package com.example.fypproject;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class UserLogin {
    private static boolean logged;

    public static boolean isLogged() {
        return logged;
    }

    public static void setLoggingState(boolean loginState) {
        logged = loginState;
    }

}
