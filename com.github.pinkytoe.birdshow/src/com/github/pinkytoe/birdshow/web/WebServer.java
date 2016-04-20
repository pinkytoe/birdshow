package com.github.pinkytoe.birdshow.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.github.pinkytoe.birdshow.cam.Cam;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {
	
	private Cam cam;

	public WebServer(Cam cam) {
		this.cam = cam;
	}

	public void start() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.setExecutor(null); // creates a default executor
		server.createContext("/", new RootHandler());
		server.createContext("/cam", new ImageHandler());
		server.start();

	}

	
	class RootHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange ex) throws IOException {
			String response = "some <b>response</b> <img src=\"/cam\" />";
			ex.getResponseHeaders().set("Content-Type", "text/html;charset=utf-8");
			ex.sendResponseHeaders(200, response.length());
			OutputStream os = ex.getResponseBody();
			os.write(response.getBytes());
			os.close();

		}

	}

	class ImageHandler implements HttpHandler {

		private static final String BOUNDARY = "boundary";

		//http://stackoverflow.com/questions/16228109/how-can-you-stream-m-jpeg-from-a-java-httpserver
		@Override
		public void handle(HttpExchange ex) throws IOException {
			InputStream inputStream = cam.currentFrame();
			ex.getResponseHeaders().set("Content-Type", "image/jpeg");
//			ex.getResponseHeaders().set("Content-Type", "multipart/x-mixed-replace;boundary=" + BOUNDARY );
			
			ex.getResponseHeaders().set("Content-Length", Long.toString(cam.currentSize()));
			ex.sendResponseHeaders(200, 0);
			OutputStream os = ex.getResponseBody();
			final byte[] buffer = new byte[0x10000];
			int count = 0;
			while ((count = inputStream.read(buffer)) >= 0) {
				os.write(buffer, 0, count);
			}
			inputStream.close();
			
			os.close();

		}
	}
}
