package com.messaging.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.messaging.model.Message;

public interface MessagingRepository extends MongoRepository<Message, String>{
	ArrayList<Message> findByChatId(String chatId);
}
