package com.messaging.service;

public interface LoggerService {
	
	void addMessage(String id, String chatId);
	void addMessageBadDate(String chatId);
	void getChat(String senderId, String recipientId);

}
