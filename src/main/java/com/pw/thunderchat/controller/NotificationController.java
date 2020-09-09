package com.pw.thunderchat.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.Notification;

@RestController
public class NotificationController {

	@Autowired
	SimpMessagingTemplate simpMessageTemplate;

	@MessageMapping("/send-notification")
	public void send(Message<Notification> message, @Payload Messages notification) throws Exception {
		System.out.println("entrei");
		Principal pr = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);

		
		System.out.println(pr+ "PRZINNNNNN" );


		simpMessageTemplate.convertAndSendToUser(notification.getTo(), "/queue/sendback", notification);
	}
	
//	@MessageMapping("/all")
//	public void sendAll(@Payload Messages notification) {
//		simpMessageTemplate.sen(destination, payload, postProcessor);
//	}
	

}
