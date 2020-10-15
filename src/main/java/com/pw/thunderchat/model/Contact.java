package com.pw.thunderchat.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model de contatos de usuários
 * 
 * @author André
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contacts")
public class Contact {

	@Id
	private String _id;

	@DBRef
	@NonNull
	private User user;

	@DBRef
	List<User> contactsList;
}
