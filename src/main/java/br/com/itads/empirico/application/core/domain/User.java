package br.com.itads.empirico.application.core.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 5954348192161040413L;

	/**
	 * Nome do usuário
	 */
	String name;
	
	/**
	 * username para acesso
	 */
	String username;
	
	/**
	 * senha para acesso
	 */
	String password;
	

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
	

}