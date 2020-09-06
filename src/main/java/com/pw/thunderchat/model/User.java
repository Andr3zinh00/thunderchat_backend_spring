package com.pw.thunderchat.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;


@Document(collection = "users")
@AllArgsConstructor
@Data
public class User  {

	@Id
	private String _id;

	private String name;

	private String mention;

	private String password;

	private String email;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate birth_date;
	

}
