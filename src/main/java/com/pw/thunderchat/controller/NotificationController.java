package com.pw.thunderchat.controller;

import java.util.Collections;
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
import com.pw.thunderchat.model.Notification;
import com.pw.thunderchat.service.NotificationService;

import io.swagger.annotations.ApiOperation;

@RestController
public class NotificationController {

	@Autowired
	NotificationService notificationService;

	/**
	 * Controller que manda notificações de pedido de amizade
	 * 
	 * @param notification
	 * @throws Exception
	 */
	@MessageMapping("/send-notification")
	public void send(@Payload Messages notification) throws Exception {
		System.out.println(notification);
		this.notificationService.registerNotification(notification);
	}

	/**
	 * Pega todas as notificações de um usuário
	 * 
	 * @param id do usuário
	 * @return o array de todas as notificações do usuário
	 */
	@GetMapping("/notifications/{id}")
	public Notification getAllNotificationsFromUser(@PathVariable String id) {
		return this.notificationService.getAllNotificationById(id);
	}

	/**
	 * Deleta uma notificação
	 * 
	 * @param id da notificação
	 * @return uma mensagem de sucesso
	 */
	@PostMapping("/notifications/{id-notificacao}")
	@ApiOperation("Use a rota /message/{notification-id}/{message-id} a parte de delete agora sera feita por lá, "
			+ "além disso ela te retorna um array atualizado das msgs de notificões")
	@Deprecated
	public Map<String, String> delete(@PathVariable("id-notificacao") String id, @RequestBody Messages msg) {
		return Collections.singletonMap("message", this.notificationService.delete(id, msg));
	}

}
