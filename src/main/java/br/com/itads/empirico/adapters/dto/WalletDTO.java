package br.com.itads.empirico.adapters.dto;

import java.util.UUID;

import br.com.itads.empirico.application.core.domain.User;
import br.com.itads.empirico.application.core.domain.Wallet;

public class WalletDTO {

	private UUID uuid;
	private User user;
	
	public WalletDTO(UUID uuid,User user) {
		this.user = user;
		this.uuid = uuid;	
	}

	public UUID getUuid() {
		return uuid;
	}

	public User getUser() {
		return user;
	}
	
	public Wallet toDomain() {
		return new Wallet(uuid, user);
	}	
	
}
