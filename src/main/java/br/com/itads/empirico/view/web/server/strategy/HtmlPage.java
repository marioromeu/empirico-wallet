package br.com.itads.empirico.view.web.server.strategy;

import java.util.List;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;

public interface HtmlPage {

	String handle(Object o);

	Optional<List<String>> fillStringParams(HttpExchange t);
	
}
