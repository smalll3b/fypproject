package com.example.fypproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class AIChatActivity extends AppCompatActivity {
    private ImageButton imgbtnReturn;
    private ChatViewModel chatViewModel;
    private LinearLayout chatLayout;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        imgbtnReturn = findViewById(R.id.imgbtnReturn);
        EditText inputMessage = findViewById(R.id.inputMessage);
        Button sendButton = findViewById(R.id.sendButton);
        chatLayout = findViewById(R.id.chatLayout);
        scrollView = findViewById(R.id.scrollView);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        // 監聽 AI 回應並顯示在 chatLayout
        chatViewModel.getResponseText().observe(this, response -> {
            addMessageToChat("AI", response);
        });

        sendButton.setOnClickListener(v -> {
            String message = inputMessage.getText().toString();
            if (!message.isEmpty()) {
                addMessageToChat("你", message);  // 顯示用戶訊息
                chatViewModel.sendMessage(message); // 傳送給 AI
                inputMessage.setText(""); // 清空輸入框
            }
        });

        imgbtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AIChatActivity.this, IndexUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 動態新增訊息
    private void addMessageToChat(String sender, String message) {
        TextView textView = new TextView(this);
        textView.setText(sender + ": " + message);
        textView.setPadding(16, 8, 16, 8);
        textView.setTextSize(16);
        chatLayout.addView(textView);

        // 確保 ScrollView 自動滾動到底部
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }
}
