package com.github.pinkytoe.birdshow.cam;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class MockCam implements Cam {

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream currentFrame() {
		InputStream inputStream = this.getClass().getResourceAsStream("Cookie_Monster.jpg");
		return inputStream;
	}

	@Override
	public long currentSize() {
		URL resource = this.getClass().getResource("Cookie_Monster.jpg");
		try {
			File file = new File(resource.toURI());
			return file.length();

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
