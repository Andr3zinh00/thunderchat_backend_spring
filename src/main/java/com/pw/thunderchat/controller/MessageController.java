package com.pw.thunderchat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.responsehandler.Response;
import com.pw.thunderchat.service.MessageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	MessageService messageService;

	/**
	 * Metodo que deleta uma mensagem de chat
	 * 
	 * @param chatId
	 * @param messageId
	 * @return a lista de mensagens atualizada
	 */
	@DeleteMapping("/chat/{chat-id}/{message-id}")
	@ApiOperation("Primeiro passe o id do chat e depois o id da mensagem")
	public Response<List<Messages>> deleteChatMessage(@PathVariable(value = "chat-id") String chatId,
			@PathVariable(value = "message-id") String messageId) {
		return this.messageService.deleteChatMessages(messageId, chatId);
	}

	/**
	 * Metodo que delete uma mensagem de notificação
	 * 
	 * @param notificationId
	 * @param messageId
	 * @return
	 */
	@ApiOperation("Primeiro o id da notificação e depois da mensagem, as mensagens a partir de agora possuem id!")
	@DeleteMapping("/notification/{notification-id}/{message-id}")
	public Response<List<Messages>> deleteNotificationMessage(
			@PathVariable(value = "notification-id") String notificationId,
			@PathVariable(value = "message-id") String messageId) {
		return this.messageService.deleteNotificationMessages(messageId, notificationId);
	}

	/**
	 * Atualiza o campo 'read' de uma notificação como true
	 * 
	 * @param notificationId
	 * @param messageId
	 * @return a lista de mensagens atualizada
	 */
	@PutMapping("/{notification-id}/{message-id}")
	public Response<List<Messages>> updateReadField(@PathVariable(value = "notification-id") String notificationId,
			@PathVariable(value = "message-id") String messageId) {
		return this.messageService.updatedReadFieldNotification(messageId, notificationId);
	}
	
}
