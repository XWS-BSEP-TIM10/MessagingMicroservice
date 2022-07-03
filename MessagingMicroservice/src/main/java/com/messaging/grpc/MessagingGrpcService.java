package com.messaging.grpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.messaging.model.Message;
import com.messaging.model.MessageStatus;
import com.messaging.service.ChatRoomService;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.ChatMessageProto;
import proto.ChatMessageResponseProto;
import proto.MessagingGrpcServiceGrpc;
import com.messaging.service.MessageService;

@GrpcService
public class MessagingGrpcService extends MessagingGrpcServiceGrpc.MessagingGrpcServiceImplBase{
	 private final ChatRoomService chatRoomService;
	 private final SimpleDateFormat iso8601Formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	 private final MessageService messageService;
	 	
	 	@Autowired
	 	public MessagingGrpcService(MessageService messageService, ChatRoomService chatRoomService) {
	 		this.messageService = messageService;
	 		this.chatRoomService = chatRoomService;
	 	}
	 
	 	@Override
	    public void add(ChatMessageProto request, StreamObserver<ChatMessageResponseProto> responseObserver) {
	 		var chatId = chatRoomService
	                .getChatId(request.getSenderId(), request.getRecipientId(), true);
	 		
	 		ChatMessageResponseProto responseProto;
	 		
	 		try {
				Message newMessage = new Message(chatId.get(),request.getSenderId(),request.getRecipientId(),request.getSenderName(),request.getRecipientName(),request.getContent(),iso8601Formatter.parse(request.getTimestamp()),MessageStatus.valueOf(request.getStatus().toUpperCase()));
				
				Message addedMessage = messageService.addMessage(newMessage);
				
				responseProto = ChatMessageResponseProto.newBuilder().setId(addedMessage.getId()).setChatId(addedMessage.getChatId()).setSenderId(addedMessage.getSenderId()).setRecipientId(addedMessage.getRecipientId()).setSenderName(addedMessage.getSenderName()).setRecipientName(addedMessage.getRecipientName()).setContent(addedMessage.getContent()).setTimestamp(iso8601Formatter.format(addedMessage.getTimestamp())).setStatus(addedMessage.getStatus().toString()).setStatus("200").build();
				responseObserver.onNext(responseProto);
		        responseObserver.onCompleted();
	 		} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	      /*  boolean success = service.remove(request.getId());
	        if (!success) {
	            responseProto = RemoveExperienceResponseProto.newBuilder().setStatus(NOT_FOUND_STATUS).build();
	            loggerService.deleteExperienceNotFound(String.valueOf(request.getId()));
	        } else {
	            responseProto = RemoveExperienceResponseProto.newBuilder().setStatus(OK_STATUS).build();
	            loggerService.deleteExperience(String.valueOf(request.getId()));
	        }*/

	        
	    }
	 
}
