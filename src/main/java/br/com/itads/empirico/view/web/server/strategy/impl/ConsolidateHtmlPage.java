package br.com.itads.empirico.view.web.server.strategy.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;

import br.com.itads.empirico.util.AdapterBuilder;
import br.com.itads.empirico.view.web.server.HttpSessionThreadLocal;
import br.com.itads.empirico.view.web.server.strategy.HtmlPage;

public class ConsolidateHtmlPage implements HtmlPage {

	@Override
	public String handle(Object o) {
		return "";
	}

	@Override
	public Optional<List<String>> fillStringParams(HttpExchange t) {
		String[] s = new String[]{
				"Mario Romeu"
		};

		AdapterBuilder.buildWalletAdapter().consolidateWallet(
				HttpSessionThreadLocal.INSTANCE.defaultWallet()
		);
		
		return Optional.of(Arrays.asList(s));
	}

}
