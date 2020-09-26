package com.pw.thunderchat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.Messages;

@RestController
@RequestMapping("/chat")
public class ChatController {

	
	@MessageMapping("/send-message")
	public void sendMessage(Messages msg) {
		
	}
}
