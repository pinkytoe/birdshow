package com.github.pinkytoe.birdshow.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

	public void start() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.setExecutor(null); // creates a default executor
		server.createContext("/", new RootHandler());
		server.createContext("/img", new ImageHandler());
		server.start();

	}

	static class RootHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange ex) throws IOException {
			String response = "some <b>response</b> <img src=\"/img\" />";
			ex.getResponseHeaders().set("Content-Type", "text/html;charset=utf-8");
			ex.sendResponseHeaders(200, response.length());
			OutputStream os = ex.getResponseBody();
			os.write(response.getBytes());
			os.close();

		}

	}

	static class ImageHandler implements HttpHandler {

		//http://stackoverflow.com/questions/16228109/how-can-you-stream-m-jpeg-from-a-java-httpserver
		@Override
		public void handle(HttpExchange ex) throws IOException {

			File file = new File("C:\\Users\\D058643\\Pictures\\Cookie_monster.jpg").getCanonicalFile();
			ex.sendResponseHeaders(200, 0);
			OutputStream os = ex.getResponseBody();
			FileInputStream fs = new FileInputStream(file);
			final byte[] buffer = new byte[0x10000];
			int count = 0;
			while ((count = fs.read(buffer)) >= 0) {
				os.write(buffer, 0, count);
			}
			fs.close();
			os.close();

		}
	}
}
