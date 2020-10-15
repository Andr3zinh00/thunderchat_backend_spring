package com.pw.thunderchat.service;

import com.pw.thunderchat.model.responsehandler.Response;

public interface ContactChatService {
  Response<?> getContactsWithLastMessage(String id);
}
