package com.pw.thunderchat.service;

import java.util.List;

import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.responsehandler.Response;

public interface MessageService {

	Response<List<Messages>> deleteChatMessages(String messageId, String chatId);

	Response<List<Messages>> deleteNotificationMessages(String messageId, String notificationId);

	Response<List<Messages>> updatedReadFieldNotification(String messageId, String notificationId);
}
