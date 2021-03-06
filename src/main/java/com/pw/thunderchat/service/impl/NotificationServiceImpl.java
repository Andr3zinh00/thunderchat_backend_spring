package com.pw.thunderchat.service.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.BadRequestException;
import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.Notification;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.NotificationRepository;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	SimpMessagingTemplate simpMessageTemplate;

	@Autowired
	UserRepository userRepo;

	@Override
	public void registerNotification(Messages msg) {

		User user = this.userRepo.findUserByName(msg.getTo());
		if (user == null)
			return;

		Notification notifications = this.notificationRepository.getByUserId(user.get_id())
				.orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));

		List<Messages> list = notifications.getNotificationContent();

		System.out.println(list);
		// só cadastra/manda pro user se a msg não existir no Mongo Atlas
		if (messageAlreadyExists(list, msg))
			return;

		list.add(msg);

		msg.set_id(new ObjectId().toString());
		simpMessageTemplate.convertAndSendToUser(msg.getTo(), "/queue/sendback", msg);

		this.notificationRepository.save(notifications);
//		simpMessageTemplate.convertAndSendToUser(msg.getTo(), "/queue/sendback", msg);

	}

	@Override
	public Notification getAllNotificationById(String id) {
		return this.notificationRepository.getByUserId(id)
				.orElseThrow(() -> new NotFoundException("Notificações não encontradas para o usuário de id: " + id));

	}

	public boolean messageAlreadyExists(List<Messages> list, Messages msg) {
		return list.stream().anyMatch(message -> {
			return message.getFrom().equals(msg.getFrom()) && msg.getType().equals(message.getType());
		});
	}

	@Override
	public String delete(String id, Messages msg) {
		if (id.equals(null))
			throw new BadRequestException("O id não deve ser nulo");

		if (msg.equals(null))
			throw new BadRequestException("A mensagem a ser apagada não pode ser nula");

		Notification notification = this.notificationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Não foi possível encontrar um usuário com o id: " + id));

		if (!notification.getNotificationContent().contains(msg))
			throw new NotFoundException("Notificação não encontrada!");

		notification.getNotificationContent().remove(msg);
		this.notificationRepository.save(notification);
		return "Notificação deletada com sucesso";
	}

	@Override
	public void dispatchNewChatNotification(Messages msg) {
	}

}
