package com.pw.thunderchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	UserRepository userRepo;

	@Override
	public void registerNotification(Messages msg) {
		User user = this.userRepo.findUserByName(msg.getTo());

		if (user == null)
			return;

		Notification notifications = this.notificationRepository.getByUserId(user.get_id())
				.orElseThrow(() -> new NotFoundException("Usuario nao encontrado"));

		List<Messages> list = notifications.getNotificationContent();

		if (messageAlreadyExists(list, msg))
			return;

		list.add(msg);

		this.notificationRepository.save(notifications);

	}

	@Override
	public List<Messages> getAllNotificationById(String id) {
		Notification notif = this.notificationRepository.getByUserId(id)
				.orElseThrow(() -> new NotFoundException("Notificações não encontradas"));

		List<Messages> notifications = notif.getNotificationContent();
		if (notifications.size() == 0) {
			throw new NotFoundException("Não possui notificações");
		}
		return notifications;
	}

	public boolean messageAlreadyExists(List<Messages> list, Messages msg) {
		return list.stream().anyMatch(message -> {
			return message.getFrom().equals(msg.getFrom()) && msg.getType().equals(message.getType());
		});
	}

}
