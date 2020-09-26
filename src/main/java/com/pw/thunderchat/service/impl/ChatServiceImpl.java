package com.pw.thunderchat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.model.Chat;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.repository.ChatRepository;
import com.pw.thunderchat.repository.UserRepository;
import com.pw.thunderchat.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SimpMessagingTemplate simpMessageTemplate;

	@Override
	public void create(User userOne, User userTwo) {
		this.chatRepository.save(new Chat(new ArrayList<Messages>(), userOne, userTwo));
	}

	@Override
	public void sendMessageChat(Messages msg) {

		// boa velocidade de comunicação ou boa persistencia?

		// boa velocidade de comunicação
		// this.simpMessageTemplate.convertAndSendToUser(msg.getTo(), "/queue/chat-msg",
		// msg);

		User member = this.userRepository.findUserByName(msg.getTo());

		if (member == null)
			return;

		Optional<Chat> chatOpt = this.chatRepository.findChatByMember(member);

		if (chatOpt.isPresent()) {
			Chat chat = chatOpt.get();

			List<Messages> messages = chat.getMessages();
			messages.add(msg);
			this.chatRepository.save(chat);

			// boa persistencia
			this.simpMessageTemplate.convertAndSendToUser(msg.getTo(), "/queue/chat-msg", msg);
		}

	}

}
