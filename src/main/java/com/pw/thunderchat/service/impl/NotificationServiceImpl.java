package com.pw.thunderchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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

		// só cadastra/manda pro user se a msg não existir no Mongo Atlas
		if (messageAlreadyExists(list, msg))
			return;

		// Marca que o usuário tem notifição nova pra checar
		// e adiciona a notificao em si
		notifications.setChecked(false);
		list.add(msg);

		this.notificationRepository.save(notifications);
		simpMessageTemplate.convertAndSendToUser(msg.getTo(), "/queue/sendback", msg);

	}

	@Override
	public List<Messages> getAllNotificationById(String id) {
		Notification notif = this.notificationRepository.getByUserId(id)
				.orElseThrow(() -> new NotFoundException("Notificações não encontradas para o usuário de id: " + id));

		return notif.getNotificationContent();
	}

	public boolean messageAlreadyExists(List<Messages> list, Messages msg) {
		return list.stream().anyMatch(message -> {
			return message.getFrom().equals(msg.getFrom()) && msg.getType().equals(message.getType());
		});
	}

}
