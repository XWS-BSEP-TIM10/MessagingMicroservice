package com.messaging.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ChatRoom {
	@Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    
    
	public ChatRoom() {
	}


	public ChatRoom(String chatId, String senderId, String recipientId) {
		super();
		this.chatId = chatId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}


	public String getId() {
		return id;
	}


	public String getChatId() {
		return chatId;
	}


	public String getSenderId() {
		return senderId;
	}


	public String getRecipientId() {
		return recipientId;
	}
    
    
}
