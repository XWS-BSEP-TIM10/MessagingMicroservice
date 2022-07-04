package com.messaging.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.messaging.model.Message;
@Service
public interface MessageService {
	 Message addMessage(Message message);
	 List<Message> findChatMessages(String senderId, String recipientId);
}
