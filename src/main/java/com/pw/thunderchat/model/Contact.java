package com.pw.thunderchat.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contacts")
public class Contact {
	
	@Id
	private String  _id;
	
	private String userId;

	@DBRef
	List<User> contactsList;
}
