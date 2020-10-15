package com.pw.thunderchat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
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
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	@Id
	private String _id;

	private String name;

	private String mention;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate birth_date;

	private String email;

}
