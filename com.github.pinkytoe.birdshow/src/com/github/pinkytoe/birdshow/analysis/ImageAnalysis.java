package com.github.pinkytoe.birdshow.analysis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class ImageAnalysis {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM d yyyy  HH:mm:ss");
		VideoCapture cap = new VideoCapture(0);

		if (!cap.isOpened()) {
			System.exit(-1);
		}

		Mat image = new Mat();
		TestFrame frame = new TestFrame();
		frame.setVisible(true);

		while (true) {
			cap.read(image);
			if (image != null) {

				Imgproc.putText(image, LocalDateTime.now().format(format), new Point(10, 10), Core.FONT_HERSHEY_PLAIN,
						new Double(1), new Scalar(255));
				frame.render(image);
			} else {
				System.out.println("image is null");
				break;
			}
		}

	}

}
