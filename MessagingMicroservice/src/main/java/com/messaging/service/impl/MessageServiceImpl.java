package com.messaging.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.messaging.model.Message;
import com.messaging.model.MessageStatus;
import com.messaging.repository.MessagingRepository;
import com.messaging.service.ChatRoomService;
import com.messaging.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{
	
	private final MessagingRepository repo;
	
	private final ChatRoomService chatRoomService;
	@Autowired private MongoOperations mongoOperations;
	
	@Autowired
	public MessageServiceImpl(MessagingRepository messagingRepository, ChatRoomService chatRoomService) {
		this.repo = messagingRepository;
		this.chatRoomService =chatRoomService;
	}
	
	@Override
	public Message addMessage(Message message) {
		message.setStatus(MessageStatus.RECEIVED);
		return repo.save(message);
	}
	
	@Override
	 public List<Message> findChatMessages(String senderId, String recipientId) {
	        var chatId = chatRoomService.getChatId(senderId, recipientId, false);
	        if(chatId.isEmpty()) return new ArrayList<Message>();
	        List<Message> messages = new ArrayList<Message>();
	        messages = repo.findByChatId(chatId.get());
	        
	        if(messages.size() > 0) {
	            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
	        }
	        
	        
	        return messages;
	    }
	 
	 private void updateStatuses(String senderId, String recipientId, MessageStatus status) {
	        Query query = new Query(
	                Criteria
	                        .where("senderId").is(senderId)
	                        .and("recipientId").is(recipientId));
	        Update update = Update.update("status", status);
	        mongoOperations.updateMulti(query, update, Message.class);
	    }

}
