package video.Buffer.HTTPLiveStreamFromOpenCV;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OpenCVCameraStream {

	// region Properties
	public static Mat frame = null;
	public static Mat frame1 = null;
	private static HttpStreamServer httpStreamService;
	static VideoCapture videoCapture;
	static VideoCapture videoCapture1;
	static Timer tmrVideoProcess;
	// endregion

	// region Methods

	public static void start() {

		videoCapture = new VideoCapture();
		videoCapture1 = new VideoCapture();
		videoCapture.open(0);
		videoCapture1.open(0);
		if (!videoCapture.isOpened()) {
			return;
		}

		frame = new Mat();
		String currentUsersHomeDir = System.getProperty("user.home");
		System.out.println(currentUsersHomeDir);
		File tempFile = new File(currentUsersHomeDir + "/a.txt");
		File outputfile = new File(currentUsersHomeDir + "/b.png");
		httpStreamService = new HttpStreamServer(frame);
		new Thread(httpStreamService).start();

		tmrVideoProcess = new Timer(100, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!videoCapture.read(frame)) {
					tmrVideoProcess.stop();
				}
				if (tempFile.exists()) {
					frame1 = new Mat();
					videoCapture.read(frame1);
					MatOfByte bytemat = new MatOfByte();
					Imgcodecs.imencode(".png", frame1, bytemat);
					byte[] bytes = bytemat.toArray();
					InputStream in = new ByteArrayInputStream(bytes);
					BufferedImage img = null;
					try {
						img = ImageIO.read(in);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					System.out.println(outputfile.getAbsolutePath());
					try {
						ImageIO.write(img, "png", outputfile);
						tempFile.delete();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				// procesed image
				httpStreamService.imag = frame;
			}
		});
		tmrVideoProcess.start();

	}

	public static void main(String[] args) {
		System.out.println(new File("Libs/DLL/opencv_java340.dll").getAbsolutePath());
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);// Load opencv native library
		start();
	}

	// endregion

}
