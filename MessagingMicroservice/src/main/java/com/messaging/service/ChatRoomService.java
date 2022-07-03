package com.messaging.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
@Service
public interface ChatRoomService {
	 Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist);
}
