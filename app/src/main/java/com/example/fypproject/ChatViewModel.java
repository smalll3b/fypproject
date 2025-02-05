package com.example.fypproject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatViewModel extends ViewModel {
    private final MutableLiveData<String> responseText = new MutableLiveData<>();
    private final ChatService chatService;

    public ChatViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gintest16634463010.openai.azure.com/")  // 確保 baseUrl 以 "/" 結尾
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatService = retrofit.create(ChatService.class);
    }

    public LiveData<String> getResponseText() {
        return responseText;
    }

    public void sendMessage(String userMessage) {
        List<ChatRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatRequest.Message("user", userMessage));

        ChatRequest request = new ChatRequest("gpt-4o", messages, 200);

        chatService.sendMessage(request).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    responseText.setValue(response.body().getChoices().get(0).getMessage().getContent());
                } else {
                    responseText.setValue("Error: Response not successful");
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                responseText.setValue("Error: " + t.getMessage());
            }
        });
    }
}
