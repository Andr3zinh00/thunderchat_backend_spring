package com.pw.thunderchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Chat;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.Notification;
import com.pw.thunderchat.model.responsehandler.Response;
import com.pw.thunderchat.repository.ChatRepository;
import com.pw.thunderchat.repository.NotificationRepository;
import com.pw.thunderchat.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	ChatRepository chatRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public Response<List<Messages>> deleteChatMessages(String messageId, String chatId) {

		Chat chat = chatRepository.getChatByMessageIdAndChatId(messageId, chatId)
				.orElseThrow(() -> new NotFoundException("A Mensagem de id: " + messageId
						+ " não foi encontrada no documento de chat de id: " + chatId));
		chat.getMessages().removeIf(message -> message.get_id().equals(messageId));

		chatRepository.save(chat);

		return new Response<List<Messages>>("Mensagem deletada com sucesso", chat.getMessages());
	}

	@Override
	public Response<List<Messages>> deleteNotificationMessages(String messageId, String notificationId) {

		Notification notification = getNotificationDoc(messageId, notificationId);

		notification.getNotificationContent().removeIf(message -> message.get_id().equals(messageId));

		notificationRepository.save(notification);

		return new Response<List<Messages>>("Mensagem deletada com sucesso", notification.getNotificationContent());
	}

	@Override
	public Response<List<Messages>> updatedReadFieldNotification(String messageId, String notificationId) {
		Notification notification = getNotificationDoc(messageId, notificationId);

		// gera uma stream da lista de mensagens, depois filtra pra a msg que tem id
		// igual ao da requisição e por fim seta o campo read da msg pra false,
		// o metodo getNotificationDoc já valida se a msg existe no documento,
		// então é seguro usar desse jeito
		notification.getNotificationContent().stream().filter(message -> message.get_id().equals(messageId)).findFirst()
				.get().setRead(true);

		notificationRepository.save(notification);

		return new Response<List<Messages>>("Registro atualizado com sucesso", notification.getNotificationContent());
	}

	private Notification getNotificationDoc(String messageId, String notificationId) {
		return notificationRepository.getNotificationByMessageIdAndNotificationId(messageId, notificationId)
				.orElseThrow(() -> new NotFoundException("A Mensagem de id: " + messageId
						+ " não foi encontrada no documento de notificação de id: " + notificationId));
	}
}
