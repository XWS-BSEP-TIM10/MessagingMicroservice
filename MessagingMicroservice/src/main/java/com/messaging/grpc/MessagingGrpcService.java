package com.messaging.grpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.messaging.model.Event;
import com.messaging.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import com.messaging.model.Message;
import com.messaging.service.ChatRoomService;
import com.messaging.service.LoggerService;
import com.messaging.service.MessageService;
import com.messaging.service.impl.LoggerServiceImpl;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.*;

@GrpcService
public class MessagingGrpcService extends MessagingGrpcServiceGrpc.MessagingGrpcServiceImplBase{
	 private final ChatRoomService chatRoomService;
	 private final SimpleDateFormat iso8601Formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	 private final MessageService messageService;
	 private final LoggerService loggerService;
	 private final EventService eventService;
	 	
	 	@Autowired
	 	public MessagingGrpcService(MessageService messageService, ChatRoomService chatRoomService, EventService eventService) {
	 		this.messageService = messageService;
	 		this.chatRoomService = chatRoomService;
			this.eventService = eventService;
			this.loggerService = new LoggerServiceImpl(this.getClass());
	 	}
	 
	 	@Override
	    public void add(ChatMessageProto request, StreamObserver<ChatMessageResponseProto> responseObserver) {
	 		var chatId = chatRoomService
	                .getChatId(request.getSenderId(), request.getRecipientId(), true);
	 		
	 		ChatMessageResponseProto responseProto;
	 		
	 		try {
				Message newMessage = new Message(chatId.get(),request.getSenderId(),request.getRecipientId(),request.getSenderName(),request.getRecipientName(),request.getContent(),iso8601Formatter.parse(request.getTimestamp()));
				
				Message addedMessage = messageService.addMessage(newMessage);
				
				responseProto = ChatMessageResponseProto.newBuilder().setId(addedMessage.getId()).setChatId(addedMessage.getChatId()).setSenderId(addedMessage.getSenderId()).setRecipientId(addedMessage.getRecipientId()).setSenderName(addedMessage.getSenderName()).setRecipientName(addedMessage.getRecipientName()).setContent(addedMessage.getContent()).setTimestamp(iso8601Formatter.format(addedMessage.getTimestamp())).setStatus(addedMessage.getStatus().toString()).setStatus("200").build();
				eventService.save(new Event("User with id: " + request.getSenderId() + " send message to user with id: " + request.getRecipientId()));
				loggerService.addMessage(addedMessage.getId(), addedMessage.getChatId());
				responseObserver.onNext(responseProto);
		        responseObserver.onCompleted();
	 		} catch (ParseException e) {
				loggerService.addMessageBadDate(chatId.get());
			}


	        
	    }
	 	
	 	@Override
	    public void findChatMessages(FindChatMessagesProto request, StreamObserver<FindChatMessagesResponseProto> responseObserver) {
	 			List<Message> messages = messageService.findChatMessages(request.getSenderId(), request.getRecipientId());
	 			List<FindChatMessageProto> chatMessagesProto = new ArrayList<FindChatMessageProto>();
	 			for(Message message : messages) {
	 				FindChatMessageProto findChatMessageProto = FindChatMessageProto.newBuilder().setId(message.getId()).setChatId(message.getChatId()).setSenderId(message.getSenderId()).setRecipientId(message.getRecipientId()).setSenderName(message.getSenderName()).setRecipientName(message.getRecipientName()).setContent(message.getContent()).setTimestamp(iso8601Formatter.format(message.getTimestamp())).setStatus(message.getStatus().toString()).build();
	 				chatMessagesProto.add(findChatMessageProto);
	 			}	 		
	 			FindChatMessagesResponseProto responseProto = FindChatMessagesResponseProto.newBuilder().addAllMessages(chatMessagesProto).setStatus("200").build();			
				loggerService.getChat(request.getSenderId(), request.getRecipientId());
	 			responseObserver.onNext(responseProto);
		        responseObserver.onCompleted(); 		 	   
	        
	    }

	@Override
	public void getMessagingEvents(MessagingEventProto request, StreamObserver<MessagingEventResponseProto> responseObserver) {

		List<String> events = new ArrayList<>();
		for(Event event : eventService.findAll()){events.add(event.getDescription());}

		MessagingEventResponseProto responseProto = MessagingEventResponseProto.newBuilder().addAllEvents(events).build();

		responseObserver.onNext(responseProto);
		responseObserver.onCompleted();
	}

	 
}
