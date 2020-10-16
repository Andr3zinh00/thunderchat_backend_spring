package com.pw.thunderchat.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.service.NotificationService;

import io.swagger.annotations.ApiOperation;

@RestController
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	/**
	 * Controller que manda pedidos de notificações
	 * @param notification
	 * @throws Exception
	 */
	@MessageMapping("/send-notification")
	public void send(@Payload Messages notification) throws Exception {
		System.out.println(notification+"cheguei");
		this.notificationService.registerNotification(notification);
	
	}

	/**
	 * Pega todas as notificações de um usuário 
	 * @param id do usuário
	 * @return o array de todas as notificações do usuário 
	 */
	@GetMapping("/notifications/{id}")
	public Map<String, List<Messages>> getAllNotificationsFromUser(@PathVariable String id) {
		return Collections.singletonMap("notifications", this.notificationService.getAllNotificationById(id));
	}
	
	/**
	 * Deleta uma notificação
	 * 
	 * @param id da notificação 
	 * @return uma mensagem de sucesso
	 */
	@PostMapping("/notifications/{id-notificacao}")
	@ApiOperation("AUYHSDYUAHSUDIYHUIAHDUHAUSD, É JOAO, VAI TER QUE USAR POST PRA FAZER DELETE. Esse metodo deleta uma msg")
	public Map<String, String> delete(@PathVariable("id-notificacao") String id, @RequestBody Messages msg){
		return Collections.singletonMap("message", this.notificationService.delete(id, msg));
	}
	
	

}
