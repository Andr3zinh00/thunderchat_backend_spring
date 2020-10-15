package com.pw.thunderchat.service;

import java.util.List;

import com.pw.thunderchat.model.Messages;

public interface NotificationService {

	void registerNotification(Messages msg);
	
	List<Messages> getAllNotificationById(String id);
	
	String delete(String id, Messages msg);
	
}
