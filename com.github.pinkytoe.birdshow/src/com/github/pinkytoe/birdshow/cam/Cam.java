package com.github.pinkytoe.birdshow.cam;

import java.io.InputStream;

public interface Cam {

	void start();
	void stop();
	InputStream currentFrame();
	long currentSize();
}
