package com.pw.thunderchat.model;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class Connection {
	
	private List<WebSocketSession> connections;
	
}
