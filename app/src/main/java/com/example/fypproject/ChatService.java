package com.example.fypproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatService {
    @Headers({
            "Content-Type: application/json",
            "api-key: 0f521e1051c74a7f84ad20af5546ddf6"  // 替換成你的 API Key
    })
    @POST("openai/deployments/gpt-4o/chat/completions?api-version=2024-08-01-preview")
    Call<ChatResponse> sendMessage(@Body ChatRequest request);
}
