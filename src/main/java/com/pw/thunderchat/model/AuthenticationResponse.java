package com.pw.thunderchat.model;

import java.io.Serializable;

/**
 * @author André
 * Model de resposta das verificações JWT da aplicação 
 * Sempre que um User for validado, ele recebera um objeto desta classe 
 * como resposta
 */
public class AuthenticationResponse implements Serializable {

    /**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;
	private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}