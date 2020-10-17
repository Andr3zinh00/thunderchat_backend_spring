package com.pw.thunderchat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pw.thunderchat.errorhandler.NotFoundException;
import com.pw.thunderchat.model.Chat;
import com.pw.thunderchat.model.Messages;
import com.pw.thunderchat.model.User;
import com.pw.thunderchat.model.responsehandler.ContactMessageResponse;
import com.pw.thunderchat.model.responsehandler.Response;
import com.pw.thunderchat.repository.ChatRepository;
import com.pw.thunderchat.repository.ContactRepository;
import com.pw.thunderchat.service.ContactChatService;

@Service
public class ContactChatImpl implements ContactChatService {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ChatRepository chatRepository;

	@Override
	public Response<List<ContactMessageResponse>> getContactsWithLastMessage(String id) {

		contactRepository.findContactByUserId(id)
				.orElseThrow(() -> new NotFoundException("Não existe usuário de id:" + id));

		Optional<List<Chat>> chats = chatRepository.findAllChatsByUserId(id);
		if (!chats.isPresent())
			return new Response<List<ContactMessageResponse>>("O usuário não possui nenhum contato", new ArrayList<>());

		List<ContactMessageResponse> contactWithMsg = new ArrayList<ContactMessageResponse>();

		chats.get().forEach((chat) -> {
			User memberOne = chat.getMemberOne();
			System.out.println(memberOne.get_id() + "IIIIIDDDDD");
			User user = memberOne.get_id().equals(id) ? chat.getMemberTwo() : memberOne;
			List<Messages> msgs = chat.getMessages();
			System.out.println(msgs + "msgsssssss");
			contactWithMsg.add(
					new ContactMessageResponse(msgs.size() == 0 ? null : msgs.get(msgs.size() - 1), user));
		});

		return new Response<List<ContactMessageResponse>>("Contatos localizados com sucesso", contactWithMsg);
	}

}
