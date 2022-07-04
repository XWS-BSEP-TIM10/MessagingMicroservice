package com.messaging.repository;

import com.messaging.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface MessagingRepository extends MongoRepository<Message, String> {
    ArrayList<Message> findByChatId(String chatId);
}
