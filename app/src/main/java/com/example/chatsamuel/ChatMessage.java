package com.example.chatsamuel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private String messageTime;
    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        // Initialize to current time
        messageTime = getCurrentTime();
    }
    public ChatMessage(){
    }
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public String getMessageUser() {
        return messageUser;
    }
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
    public String getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // Formato de 24 horas (puedes ajustarlo según tus preferencias)
        String currentTime = sdf.format(new Date());
        return currentTime;
    }
}
