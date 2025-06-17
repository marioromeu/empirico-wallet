package br.com.itads.empirico.view.web.server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.com.itads.empirico.adapters.out.repository.file.config.FilePathConfig;
import br.com.itads.empirico.view.web.server.strategy.HtmlPage;

public class SimpleHttpHandler implements HttpHandler {

	private String htmlPageString = FilePathConfig.getFilePath("htmlPageString");

	@Override
	public void handle(HttpExchange t) throws IOException {
		OutputStream os = null;		
		HtmlPage page = getPageFromRequest(t);
		os = sendToThisPage(t, os, page);
		os.close();
	}

	private HtmlPage getPageFromRequest(HttpExchange t) {
		String htmlPageName = getIndexName(t);
		return PageBuilderStrategy.INSTANCE.getStrategyBy( htmlPageName );
	}

	private OutputStream sendToThisPage(HttpExchange t, OutputStream os, HtmlPage page) throws IOException {
		String htmlPageName = getFileName(t);
		
		Path path = Paths.get(htmlPageName);
		byte[] templateHtml = Files.readAllBytes(path);

		String response = "";
		
		String handleContent = page.handle(t); 
		
		if (handleContent.equals("")) {
				Optional<List<String>> params = page.fillStringParams(t);
				response = fillVarsOnPage(new String(templateHtml), params);
		} else {
			response = handleContent;
		}

		t.sendResponseHeaders(200, response.length());

		os = t.getResponseBody();
		os.write(response.getBytes());

		return os;

	}

	private String getIndexName(HttpExchange t) {		
		String path = t.getRequestURI().getPath();
		if (Objects.nonNull( path ) ) {
			if(path.replace("/empirico", "").equals("/") || path.replace("/empirico", "").equals("")) {
				return "index";
			}
		}
		return path.replace("/empirico/", "").replace(".html", "");
	}
	
	private String getFileName(HttpExchange t) {		
		String path = t.getRequestURI().getPath();
		if (Objects.nonNull( path ) ) {
			if(path.replace("/empirico", "").equals("/") || path.replace("/empirico", "").equals("")) {
				return htmlPageString.replace("[]", "index");
			}
		}
		return htmlPageString.replace("[]", path.replace("/empirico/", "").replace(".html", ""));
	}

	private String fillVarsOnPage(String page, Optional<List<String>> list ) {
		if (list.isPresent()) {
			for (int i = 0; i < list.get().size(); i++) {
				page = page.replace("${"+i+"}", list.get().get(i) );
			}
			System.out.println(page);
		} 
		return page;
	}

}
