package com.pw.thunderchat.model;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author André
 * Classe de modelo para as requisições da aplicação
 * Toda vez que uma requisição for checada, elá usara este model
 * para representar as credenciais do usuário
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {


    /**
	 * Default seraial version
	 */
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;


}