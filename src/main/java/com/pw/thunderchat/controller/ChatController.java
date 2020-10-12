package com.pw.thunderchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.Chat;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.service.ChatService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	/**
	 * Manda uma mensagem de chat
	 * @param msg
	 */
	@MessageMapping("/send-message")
	public void sendMessage(Messages msg) {
		this.chatService.sendMessageChat(msg);
	}

	/**
	 * Pega as mensagens trocadas entre dois usuários 
	 * @param userOne
	 * @param userTwo
	 * @return
	 */
	@GetMapping("/{idUserOne}/{idUserTwo}")
	@ApiOperation("Rota resposavel por pegar todas as mensagens trocadas entre dois usuário, não existe "
			+ "diferença na ordem que os ids são inseridos na url!")
	public Chat getAllMessagesFromTwoUser(@PathVariable("idUserOne") String userOne,
			@PathVariable("idUserTwo") String userTwo) {
		return this.chatService.getMsgsFromUsers(userOne, userTwo);

	}
}
