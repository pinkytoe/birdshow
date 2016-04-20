package com.github.pinkytoe.birdshow.cam;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class MockCam implements Cam {
	boolean toggle;
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public File currentFrame() {
		URL resource = this.getClass().getResource(toggle?"Cookie_Monster.jpg":"Bert1970.jpg");
		toggle = !toggle;
		try {
			File file = new File(resource.toURI());
			return file;

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}


}
