package com.messaging.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.messaging.model.ChatRoom;
import com.messaging.repository.ChatRoomRepository;

@Service
public class ChatRoomServiceImpl {
	
	 @Autowired private ChatRoomRepository chatRoomRepository;

	    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {

	    	Optional<ChatRoom> chatRoomOpt = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);  
	    	if(!chatRoomOpt.isEmpty()) return Optional.of(chatRoomOpt.get().getChatId());
	    	if(chatRoomOpt.isEmpty() && !createIfNotExist) {
	    		return  Optional.empty();
	    	}
	    	 var chatId = String.format("%s_%s", senderId, recipientId);
	    	 ChatRoom senderRecipient = new ChatRoom(chatId,senderId,recipientId);
	    	 ChatRoom recipientSender = new ChatRoom(chatId, recipientId, senderId);
	    	 chatRoomRepository.save(senderRecipient);
	    	 chatRoomRepository.save(recipientSender);
	    	 return Optional.of(chatId);
	    }
	    
}
