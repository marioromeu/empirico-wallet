package br.com.itads.empirico.view.console;

import br.com.itads.empirico.application.core.domain.User;

public class SessionThreadLocal extends ThreadLocal<User> {

	public static final SessionThreadLocal INSTANCE = new SessionThreadLocal();
	
	private SessionThreadLocal() {
	} 
	
}
