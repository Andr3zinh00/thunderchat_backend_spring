package com.pw.thunderchat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.NotFoundException;
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

		// gera um ObjectId do MongoDb para identificar a mensagem depois!
		msg.set_id(new ObjectId().toString());

		// boa velocidade de comunicação ou boa persistencia?
		// boa velocidade de comunicação
		this.simpMessageTemplate.convertAndSendToUser(msg.getTo(), "/queue/get-msg", msg);
		// manda para o proprio remetente pra caso ele estiver usando multiplas abas
		this.simpMessageTemplate.convertAndSendToUser(msg.getFrom(), "/queue/get-msg", msg);

		System.out.println(msg);

		User memberOne = this.userRepository.findUserByName(msg.getTo());
		User memberTwo = this.userRepository.findUserByName(msg.getFrom());

		System.out.println("userone: " + memberOne + " usertwo: " + memberTwo);
		if (memberOne == null || memberTwo == null)
			return;

		Optional<Chat> chatOpt = this.chatRepository.findChatByMember(memberOne.get_id(), memberTwo.get_id());

		if (chatOpt.isPresent()) {
			Chat chat = chatOpt.get();

			List<Messages> messages = chat.getMessages();
			messages.add(msg);
			this.chatRepository.save(chat);

			// boa persistencia, primeiramente salva as msgs no bd e depois manda para os
			// destinatarios
//			this.simpMessageTemplate.convertAndSendToUser(msg.getTo(), "/queue/get-msg", msg);
//			this.simpMessageTemplate.convertAndSendToUser(msg.getFrom(), "/queue/get-msg", msg);
		}

	}

	@Override
	public Chat getMsgsFromUsers(String userOne, String userTwo) {

		return this.chatRepository.findChatByMember(userOne, userTwo).orElseThrow(() -> new NotFoundException(
				"Nenhum chat foi encontrado entre os usuários de id: " + userOne + " e " + userTwo));

	}

	@Override
	public Chat delete(String wantToDel, String toDel) {

		return this.chatRepository.deleteByUsersId(wantToDel, toDel).orElseThrow(() -> new NotFoundException(
				"Nenhum chat foi encontrado entre os usuários de id: " + wantToDel + " e " + toDel));

	}

}
