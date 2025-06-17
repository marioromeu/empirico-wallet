package br.com.itads.empirico.view.web.server.strategy.impl;

import java.util.List;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;

import br.com.itads.empirico.view.web.server.strategy.HtmlPage;

public class IndexHtmlPage implements HtmlPage {

	@Override
	public String handle(Object o) {
		return "";
	}

	@Override
	public Optional<List<String>> fillStringParams(HttpExchange t) {
		return Optional.empty();
	}

}
