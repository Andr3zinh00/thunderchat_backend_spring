package com.pw.thunderchat.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author André
 * @param <T> o tipo de dado retornado na resposta
 */
@Data
@AllArgsConstructor
public class Response<T> {
	private String message;
	private T content;
}
