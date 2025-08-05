package br.com.itads.empirico.view.web.server;

import java.util.UUID;

import br.com.itads.empirico.application.core.domain.Wallet;

public class HttpSessionThreadLocal extends ThreadLocal<Wallet> {

	public static final HttpSessionThreadLocal INSTANCE = new HttpSessionThreadLocal();
	
	private HttpSessionThreadLocal() {
	} 
	
	public UUID defaultWallet() {
		if (this.get() == null) {
			return UUID.fromString("d88cf443-4534-4e56-90ab-93bbf9e4a1b5");
		}
		return this.get().getUuid();
	}
	
}
