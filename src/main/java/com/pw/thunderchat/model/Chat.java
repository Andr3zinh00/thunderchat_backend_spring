package com.pw.thunderchat.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(value = "chats")
public class Chat {

	@Id
	private String _id;
	
	private List<Messages> messages;
	
	@DBRef
	private User user_1;

	@DBRef
	private User user_2;
	
	
}
