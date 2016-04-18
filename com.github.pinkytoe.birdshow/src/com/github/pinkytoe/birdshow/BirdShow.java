package com.github.pinkytoe.birdshow;

import java.io.IOException;

import com.github.pinkytoe.birdshow.web.WebServer;

public class BirdShow {

	public static void main(String[] args) {
		
		try {
			WebServer webServer = new WebServer();
			webServer.start();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
