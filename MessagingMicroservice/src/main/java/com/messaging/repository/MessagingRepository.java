package com.messaging.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.messaging.model.Message;

public interface MessagingRepository extends MongoRepository<Message, String>{

}
