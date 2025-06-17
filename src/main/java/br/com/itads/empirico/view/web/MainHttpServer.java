package br.com.itads.empirico.view.web;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import br.com.itads.empirico.view.web.server.SimpleHttpHandler;


public class MainHttpServer {

	public static void main(String[] args) throws IOException {

		int port = 8888;
		
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

		server.createContext("/empirico", new SimpleHttpHandler());
		server.setExecutor(null); // usa o executor padrão

		server.start();

		System.out.println("Servidor iniciado em http://localhost:"+port);

	}

}
