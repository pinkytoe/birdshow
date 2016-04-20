package com.github.pinkytoe.birdshow.cam;

import java.io.File;

public interface Cam {

	void start();
	void stop();
	File currentFrame();
}
