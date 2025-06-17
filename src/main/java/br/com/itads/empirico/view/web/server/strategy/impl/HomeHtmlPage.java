package br.com.itads.empirico.view.web.server.strategy.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;

import br.com.itads.empirico.view.web.server.strategy.HtmlPage;

public class HomeHtmlPage implements HtmlPage {

	@Override
	public String handle(Object o) {
		return "";
	}

	@Override
	public Optional<List<String>> fillStringParams(HttpExchange t) {
		String[] s = new String[]{
				"Mario Romeu"
		};
		return Optional.of(Arrays.asList(s));
	}

}
