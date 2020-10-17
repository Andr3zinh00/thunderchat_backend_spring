package com.pw.thunderchat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.lang.String;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * Classe de modelo para o Usuário da aplicação
 * 
 * @author André
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User {

	@Id
	private String _id;
	
	
	private String name;
	
	private String mention;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private Date birth_date;
	
	private String email;

}
