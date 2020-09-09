package com.pw.thunderchat.model;

import lombok.Data;

/**
 * @author André
 * Model das mensagens entre usuário ou de notificações
 * Ainda em produção...
 */
@Data
public class Messages {

	private String content;
	private String from;
	private String to;
	
}
