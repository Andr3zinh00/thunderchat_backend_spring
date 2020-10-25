package com.pw.thunderchat.service;

import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.Notification;

public interface NotificationService {

	void registerNotification(Messages msg);
	
	void dispatchNewChatNotification(Messages msg);
	
	Notification getAllNotificationById(String id);
	
	String delete(String id, Messages msg);
	
}
