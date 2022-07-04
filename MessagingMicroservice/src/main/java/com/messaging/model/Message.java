package com.messaging.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Messages")
public class Message {
	   @Id
	   private String id;
	   private String chatId;
	   private String senderId;
	   private String recipientId;
	   private String senderName;
	   private String recipientName;
	   private String content;
	   private Date timestamp;
	   private MessageStatus status;
	   
	   public Message() {
	   }

	public Message(String chatId, String senderId, String recipientId, String senderName,
			String recipientName, String content, Date timestamp) {
		this.chatId = chatId;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.senderName = senderName;
		this.recipientName = recipientName;
		this.content = content;
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public String getChatId() {
		return chatId;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public String getContent() {
		return content;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}
	   
	   
	   
}
