package com.messaging.service;

import org.springframework.stereotype.Service;

import com.messaging.model.Message;
@Service
public interface MessageService {
	 Message addMessage(Message message);
}
