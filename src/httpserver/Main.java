package httpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import httpserver.server.RequestHandler;

public class Main {

	public static void main(String[] args) throws IOException {

		HttpServer server;
		String serverContext = "/";

		server = HttpServer.create(new InetSocketAddress(8096), 10);
		System.out.println("Server Created succesfully!");
		
//		server.createContext(serverContext, new RequestHandler());
		server.createContext("/", new RequestHandler());
		System.out.println("Context created at: " + serverContext);
		
		server.setExecutor(null);
		server.start();
		System.out.println("Server Started succesfully!");
		
	}

}
