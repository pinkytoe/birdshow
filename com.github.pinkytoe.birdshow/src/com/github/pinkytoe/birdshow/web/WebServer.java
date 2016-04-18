package com.github.pinkytoe.birdshow.web;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import javax.imageio.ImageIO;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

	public void start() throws IOException{
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.setExecutor(null); // creates a default executor
		server.createContext("/", new RootHandler());
		server.start();
		
	}
	
	static class RootHandler implements HttpHandler{

		@Override
		public void handle(HttpExchange ex) throws IOException {
			String response = "some response";
			ex.sendResponseHeaders(200, response.length());
			OutputStream os = ex.getResponseBody();
            os.write(response.getBytes());
            os.close();
			
		}
		
	}
	
	static class ImageHandler implements HttpHandler{

		@Override
		public void handle(HttpExchange arg0) throws IOException {
			// TODO Auto-generated method stub
//			ex.sendResponseHeaders(200, response.length());
//			OutputStream os = ex.getResponseBody();
//			ImageIO.write(im, formatName, output);
//			os.close();
		}
		
	}
}
