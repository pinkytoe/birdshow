package com.github.pinkytoe.birdshow.analysis;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class ImageAnalysis {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public void test() {
		Mat image = Imgcodecs.imread(getClass().getResource("/lena.png").getPath());
	}

	private static byte[] readStream(InputStream stream) throws IOException {
		// Copy content of the image to byte-array
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = stream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		byte[] temporaryImageInMemory = buffer.toByteArray();
		buffer.close();
		stream.close();
		return temporaryImageInMemory;
	}

	public static void main(String[] args) {
		// Register the default camera
		VideoCapture cap = new VideoCapture(0);

		// Check if video capturing is enabled
		if (!cap.isOpened()) {
			System.exit(-1);
		}

		// Matrix for storing image
		Mat image = new Mat();
		// Frame for displaying image
		TestFrame frame = new TestFrame();
		frame.setVisible(true);

		// Main loop
		while (true) {
			// Read current camera frame into matrix
			cap.read(image);
			// Render frame if the camera is still acquiring images
			if (image != null) {
				frame.render(image);
			} else {
				System.out.println("No captured frame -- camera disconnected");
				break;
			}
		}

	}

}
