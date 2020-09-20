package com.pw.thunderchat.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author André Model das mensagens entre usuário ou de notificações
 */
@Data
@NoArgsConstructor
public class Messages {

	private String content;
	private String from;
	private String to;
	private EMessageType type;
	
	private Date time;


}
