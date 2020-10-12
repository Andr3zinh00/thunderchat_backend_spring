package com.pw.thunderchat.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model das mensagens entre usuário ou de notificações
 * 
 * @author André
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

	private String content;
	private String from;
	private String to;
	private EMessageType type;
	private Date time;

}
