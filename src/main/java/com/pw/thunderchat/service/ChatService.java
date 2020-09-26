package com.pw.thunderchat.service;

import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.User;

/**
 * @author André
 *	Contrato do service de chat
 *
 */
public interface ChatService {
	
	void create(User userOne, User userTwo);
	void sendMessageChat(Messages msg);
}
