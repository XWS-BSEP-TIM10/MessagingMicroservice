package com.messaging.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.messaging.service.LoggerService;

public class LoggerServiceImpl implements LoggerService{
	
	private final Logger logger;

	 public LoggerServiceImpl(Class<?> parentClass) {this.logger = LogManager.getLogger(parentClass); }

	@Override
	public void addMessage(String id, String chatId) {
		logger.info("Added message with id: {} to chat: {}",id,chatId);
		
	}

	@Override
	public void addMessageBadDate(String chatId) {
		logger.warn("Failed to add message to chat: {} because creation date was malformatted", chatId);
		
	}

	@Override
	public void getChat(String senderId, String recipientId) {
		logger.info("Gotten chat between user: {} and user: {}", senderId, recipientId);
		
	}

}
