package com.pw.thunderchat.model;

import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(value = "chats")
@NoArgsConstructor
public class Chat {

	@Id
	private String _id;

	private List<Messages> messages;

	@DBRef
	private User memberOne;
	
	@DBRef
	private User memberTwo;

	public Chat(List<Messages> messages, User memberOne, User memberTwo) {
		this.messages = messages;
		this.memberOne = memberOne;
		this.memberTwo = memberTwo;
	}

}
