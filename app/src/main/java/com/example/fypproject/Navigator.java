package com.example.fypproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Navigator {
    public static void startActivityAndFinish(Context context, Class<?> targetActivityClass) {
        Intent intent = new Intent(context, targetActivityClass);
        context.startActivity(intent);

        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}