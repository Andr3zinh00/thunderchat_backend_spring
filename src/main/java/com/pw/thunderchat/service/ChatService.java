package com.pw.thunderchat.service;

import com.pw.thunderchat.model.Chat;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.User;

/**
 * @author André Contrato do service de chat
 *
 */
public interface ChatService {

	void create(User userOne, User userTwo);

	Chat delete(String wantToDel, String toDel);

	void sendMessageChat(Messages msg);

	Chat getMsgsFromUsers(String userOne, String userTwo);
}
