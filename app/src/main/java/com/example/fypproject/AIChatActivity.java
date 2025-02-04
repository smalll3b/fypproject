package com.example.fypproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class AIChatActivity extends AppCompatActivity {
    private static final String ENDPOINT = "https://gintest16634463010.openai.azure.com/openai/deployments/gpt-4o/chat/completions?api-version=2024-08-01-preview";
    private static final String API_KEY = "0f521e1051c74a7f84ad20af5546ddf6";

    private EditText userInputEditText;
    private Button sendButton;
    private TextView chatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_chat);

        userInputEditText = findViewById(R.id.etUserInput);
        sendButton = findViewById(R.id.btnSend);
        chatTextView = findViewById(R.id.tvResponse);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userInputEditText.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    sendMessageToAPI(userInput);
                }
            }
        });
    }

    private void sendMessageToAPI(String userInput) {
        OkHttpClient client = new OkHttpClient();

        Moshi moshi = new Moshi.Builder().add(new KotlinJsonAdapterFactory()).build();
        ChatRequest chatRequest = new ChatRequest(userInput);
        JsonAdapter<ChatRequest> jsonAdapter = moshi.adapter(ChatRequest.class);
        String json = jsonAdapter.toJson(chatRequest);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> chatTextView.setText("Failed to get response: " + e.getMessage()));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    runOnUiThread(() -> chatTextView.setText(responseBody));
                } else {
                    runOnUiThread(() -> chatTextView.setText("Failed to get response"));
                }
            }
        });
    }

    private static class ChatRequest {
        private final String userInput;

        public ChatRequest(String userInput) {
            this.userInput = userInput;
        }
    }
}
