package com.messaging.service.impl;

import org.springframework.stereotype.Service;

import com.messaging.model.Message;
import com.messaging.model.MessageStatus;
import com.messaging.repository.MessagingRepository;
import com.messaging.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	
	private final MessagingRepository repo;
	
	public MessageServiceImpl(MessagingRepository messagingRepository) {
		this.repo = messagingRepository;
	}
	
	@Override
	public Message addMessage(Message message) {
		message.setStatus(MessageStatus.RECEIVED);
		return repo.save(message);
	}

}
