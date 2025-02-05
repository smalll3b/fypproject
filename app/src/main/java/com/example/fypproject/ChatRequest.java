package com.example.fypproject;

import java.util.List;

public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int max_tokens;

    public ChatRequest(String model, List<Message> messages, int maxTokens) {
        this.model = model;
        this.messages = messages;
        this.max_tokens = maxTokens;
    }

    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
