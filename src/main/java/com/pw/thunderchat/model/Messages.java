package com.pw.thunderchat.model;

import java.util.Date;

import org.bson.types.ObjectId;

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

	public Messages(String content, String from, String to, EMessageType type, Date time, Boolean read) {
		this.content = content;
		this.from = from;
		this.to = to;
		this.type = type;
		this.time = time;
		this.read = read;
		this._id = new ObjectId().toString();
	}

	private String _id;
	private String content;
	private String from;
	private String to;
	private EMessageType type;
	private Date time;
	private Boolean read;

}
