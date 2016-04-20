package com.github.pinkytoe.birdshow;

import java.io.IOException;

import com.github.pinkytoe.birdshow.cam.Cam;
import com.github.pinkytoe.birdshow.cam.MockCam;
import com.github.pinkytoe.birdshow.web.WebServer;

public class BirdShow {

	public static void main(String[] args) {
		
		try {
			Cam cam = new MockCam();
			WebServer webServer = new WebServer(cam);
			webServer.start();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
