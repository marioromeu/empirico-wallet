package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 5954348192161040413L;

	/**
	 * Nome do usuário
	 */
	private String name;
	
	/**
	 * username para acesso
	 */
	private String username;
	
	/**
	 * senha para acesso
	 */
	private String password;
	

	public User(String name, String username) {
		this.name = name;
		this.username = username;
	}	
	
	/**
	 * Método para seguir uma recomendação
	 * @param rec
	 * @param trade
	 */
	public void follow(Recommendation rec, Trade trade) {
	}

	public String getName() {
		return name;
	}

	public String getUsername() {
		return username;
	}

}