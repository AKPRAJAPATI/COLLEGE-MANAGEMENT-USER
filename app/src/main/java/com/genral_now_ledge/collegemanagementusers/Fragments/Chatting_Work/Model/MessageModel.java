package com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.Model;

public class MessageModel {
    String message;
    String messageType;
    String imageUrl;
    String messageTime;
    String sendTo;

    public MessageModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MessageModel(String message, String messageType, String messageTime, String sendTo) {
        this.message = message;
        this.messageType = messageType;
        this.messageTime = messageTime;
        this.sendTo = sendTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
