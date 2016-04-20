package com.github.pinkytoe.birdshow.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

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
		server.createContext("/stream", new StreamHandler());
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

		// http://stackoverflow.com/questions/16228109/how-can-you-stream-m-jpeg-from-a-java-httpserver
		@Override
		public void handle(HttpExchange ex) throws IOException {
			File frame = cam.currentFrame();
			ex.getResponseHeaders().set("Content-Type", "image/jpeg");

			ex.getResponseHeaders().set("Content-Length", Long.toString(frame.length()));
			ex.sendResponseHeaders(200, 0);
			OutputStream os = ex.getResponseBody();
			final byte[] buffer = new byte[0x10000];
			int count = 0;
			FileInputStream inputStream = new FileInputStream(frame);
			while ((count = inputStream.read(buffer)) >= 0) {
				os.write(buffer, 0, count);
			}
			inputStream.close();

			os.close();

		}
	}

	class StreamHandler implements HttpHandler {

		private static final String BOUNDARY = "--boundary";
		private static final String RN = "\r\n";
		
		// http://stackoverflow.com/questions/16228109/how-can-you-stream-m-jpeg-from-a-java-httpserver
		@Override
		public void handle(HttpExchange ex) throws IOException {

			OutputStream os = ex.getResponseBody();
			ex.getResponseHeaders().set("Cache-Control", "no-cache");
			ex.getResponseHeaders().set("Cache-Control", "private");
			ex.getResponseHeaders().set("Content-Type", "multipart/x-mixed-replace;boundary=" + BOUNDARY);
			ex.sendResponseHeaders(200, 0);
			InputStream inputStream;
			boolean b = true;
			while (b) {

				File frame = cam.currentFrame();
				os.write(BOUNDARY.concat(RN).getBytes(StandardCharsets.UTF_8));
				os.write("Content-Type: image/jpeg".concat(RN).getBytes(StandardCharsets.UTF_8));
				os.write("Content-Length: ".concat(Long.toString(frame.length())).concat(RN).concat(RN).getBytes(StandardCharsets.UTF_8));
				
				final byte[] buffer = new byte[0x10000];
				int count = 0;
				inputStream = new FileInputStream(frame);
				while ((count = inputStream.read(buffer)) >= 0) {
					os.write(buffer, 0, count);
				}
				inputStream.close();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			os.close();

		}
	}
}
