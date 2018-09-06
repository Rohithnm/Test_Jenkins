package Utility;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.DataBuffer;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.sikuli.basics.Settings;
import org.sikuli.script.Env;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Finder;
import org.sikuli.script.Image;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import Config.Constants;
import SuiteUtils.ReportInitializer;

/**
* This class contains other classes and required utility methods
* @author AshithRaj Shetty - Charter
*/
public class Utilities extends ReportInitializer {
	
	String imgPath = "";

	/**
	* This class contains method perform action on the device
	*/
	public class Controller {
		
		Screen s = new Screen();
		screenFunctions screen = new screenFunctions();

		/**
		* Instantiates a new  controller.
		*/
		public Controller() {
			System.out.println("Initializing Controller");
			System.out.println(Env.isLockOn(Key.C_NUM_LOCK));
			if (Env.isLockOn(Key.C_NUM_LOCK)) {
				s.type(Key.NUM_LOCK);
			}
		}

		/**
		* This method initializes development mode for 
		* @return true, if successful
		*/
		public boolean init_dev_mode_companion() {
			System.out.println("Trying to initialize dev mode companion");
			General_Utils gen_utils = new General_Utils();
			s.type(Key.ESC, KeyModifier.CTRL);
			gen_utils.wait(2000);
			s.type("B Dev Mode Companion");
			// gen_utils.wait(2000);
			s.type(Key.ENTER);
			// try {
			// s.click("img/_dev_mode.png");
			// System.out.println("Found running instance of dev mode
			// companion");
			// }
			// catch (FindFailed e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// return false;
			// }

			if (!(screen.findScreenImage("img/B_init_failed1.png") || screen
					.findScreenImage("img/B_init_failed2.png"))) {
				return true;
			} else {
				return false;
			}
		}

		/**
		* This method will perform key press on the device
		* @param key String - key to be pressed
		* @return true, if successful
		*/
		public boolean keyPress(String key) {

			try {
				if (key.toLowerCase().equals("down")) {
					key = Key.DOWN;
				}
				if (key.toLowerCase().equals("up")) {
					key = Key.UP;
				}
				if (key.toLowerCase().equals("right")) {
					key = Key.RIGHT;
				}
				if (key.toLowerCase().equals("left")) {
					key = Key.LEFT;
				}
				if (key.toLowerCase().equals("enter")) {
					key = Key.ENTER;
				}
				if (key.toLowerCase().equals("tab")) {
					key = Key.TAB;
				}
				if (key.toLowerCase().equals("home")) {
					key = Key.HOME;
				}
				s.type(key);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				new screenFunctions().captureScreenshot(imgPath);
				return false;
			}
			// return false;
		}

		/**
		* This method will paste text at the current position.
		*/
		public void Paste() {
			Screen s = new Screen();
			s.type("V", KeyModifier.CTRL);
		}
	}

	/**
	* This Class contains methods to perform screen actions.
	*/
	public class screenFunctions {
		
		/**
		* Instantiates a new screen functions.
		*/
		public screenFunctions() {
		}

		/**
		* THis method will find the region in the screen where image is present
		* @param imgPath String - image path
		* @param similarity String - similarity should be present
		* @param rect int[] - coordinates of the element
		* @return true, if successful
		*/
		public boolean findScreenImage_region(String imgPath, float similarity,
				int rect[]) {
			Region r = new Region(rect[0], rect[1], rect[2], rect[3]);
			r.setAutoWaitTimeout(Constants.autoWaitTimeout);
			Pattern p = new Pattern(imgPath);
			// r.highlight(3);
			if (r.exists(p.similar(similarity), 5) != null) {
				System.out.println(imgPath + " Image Exists");
				return true;
			} else {
				System.out.println(imgPath + " Not Found");
				return false;
			}

		}

		/**
		* This method will search for the given image on the screen
		* @param imgPath String - image path
		* @return true, if image found successfully
		*/
		public boolean findScreenImage(String imgPath) {
			Screen s = new Screen();
			/*
			 * try { Match m = s.find(imgPath); //m.highlight(1); } catch
			 * (FindFailed e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			s.setAutoWaitTimeout(Constants.autoWaitTimeout);
			if (s.exists(imgPath) != null) {
				System.out.println(imgPath + " Image Exists");
				return true;
			} else {
				System.out.println(imgPath + " Not Found");
				return false;
			}

		}

		/**
		* This method will find image on screen and check how much similar it is t local image
		* @param imgPath String - image path
		* @param similarity String - similarity to be checked
		* @return true, if successful
		*/
		public boolean findScreenImage(String imgPath, float similarity) {
			Screen s = new Screen();
			Pattern p = new Pattern(imgPath);
			s.setAutoWaitTimeout(Constants.autoWaitTimeout);
			try {
				Match m = s.find(imgPath);
				m.highlight(1);
			} catch (FindFailed e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (s.exists(p.similar(similarity), 5) != null) {
				System.out.println(imgPath + " Image Exists");
				return true;
			} else {
				System.out.println(imgPath + " Not Found");
				return false;
			}

		}

		/**
		* This method will find image on screen and search for exact image
		* @param imgPath String - image path
		* @param exactMatch Boolean - will take true/false
		* @return true, if successful
		*/
		public boolean findScreenImage(String imgPath, boolean exactMatch) {
			Screen s = new Screen();
			Match m = null;
			// try {
			// m = s.find(imgPath);
			// m.highlight(1);
			s.setAutoWaitTimeout(Constants.autoWaitTimeout);
			if (exactMatch) {
				System.out.println("Doing Exact image match");
				Pattern p = new Pattern(imgPath);
				if (s.exists(p.exact()) != null) {
					System.out.println(imgPath + " Image Exists");
					return true;
				} else {
					System.out.println(imgPath + " Not Found");
					return false;
				}
				// Settings.MinSimilarity = orgMinSimilarity;
			} else {
				if (s.exists(imgPath) != null) {
					System.out.println(imgPath + " Image Exists");
					return true;
				} else {
					System.out.println(imgPath + " Not Found");
					return false;
				}
			}
			/*
			 * if (m!=null){ System.out.println(imgPath+" Image Exists"); return
			 * true; } else{ System.out.println(imgPath+" Not Found"); return
			 * false; }
			 */
			// } catch (FindFailed e) {
			// TODO Auto-generated catch block
			/*
			 * e.printStackTrace(); return false;
			 */
			// }
		}

		/**
		* This method will wait till given time for the image to load
		* @param imgPath String - image path
		* @param waitCounter int - time to wait for image
		* @return string[] - returns String array with pass/fail and other messages
		*/
		public String[] waitUntilImageAppearsOnScreen(String imgPath,
				int waitCounter) {
			Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
			String[] retval = new String[2];
			int errorCounter = 0;
			while (!this.findScreenImage(imgPath, true)) {
				gen_utils.wait(1000);
				if (errorCounter == waitCounter) {
					retval[0] = "Fail";
					retval[1] = "Not found " + imgPath
							+ " on screen even after " + waitCounter
							+ " tries.";
					return retval;
				}
				errorCounter++;
			}
			retval[0] = "Pass";
			retval[1] = "Successfully found " + imgPath + " on screen.";
			return retval;
		}

		/**
		* This method will perform key stroke until image appears on screen.
		* @param imgPath String - image path
		* @param waitCounter int - wait time for image to load
		* @param keyStroke String - key to be pressed
		* @return string[] - returns String array with pass/fail and other messages
		*/
		public String[] performKeyStrokeUntilImageAppearsOnScreen(
				String imgPath, int waitCounter, String keyStroke) {
			Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
			Utilities.Controller ctrl = new Utilities().new Controller();
			String[] retval = new String[2];
			int errorCounter = 0;
			while (!this.findScreenImage(imgPath, true)) {
				gen_utils.wait(1000);
				ctrl.keyPress(keyStroke);
				if (errorCounter == waitCounter) {
					retval[0] = "Fail";
					retval[1] = "Not found " + imgPath
							+ " on screen even after " + waitCounter
							+ " tries.";
					return retval;
				}
				errorCounter++;
			}
			retval[0] = "Pass";
			retval[1] = "Successfully found " + imgPath + " on screen.";
			return retval;
		}

		/**
		* This method will wait until image vanishes from screen.
		* @param imgPath String - image path
		* @param waitCounter int - wait time
		* @return string[] - returns String array with pass/fail and other messages
		*/
		public String[] waitUntilImageVanishesFromScreen(String imgPath,
				int waitCounter) {
			Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
			String[] retval = new String[2];
			int errorCounter = 0;
			while (this.findScreenImage(imgPath, true)) {
				gen_utils.wait(1000);
				if (errorCounter == waitCounter) {
					retval[0] = "Fail";
					retval[1] = "Not found " + imgPath
							+ " on screen even after " + waitCounter
							+ " tries.";
					return retval;
				}
				errorCounter++;
			}
			retval[0] = "Pass";
			retval[1] = "Successfully found " + imgPath + " on screen.";
			return retval;
		}

		/**
		* This method will resize the image by given percentage
		* @param img BufferedImage - image to be resized
		* @param percent double - percent
		* @return BufferedImage - returns the resized image
		*/
		public BufferedImage ImageResize(BufferedImage img, double percent) {
			int scaledWidth = (int) (img.getWidth() * percent);
			int scaledHeight = (int) (img.getHeight() * percent);
			/**
			 * creates output image
			 */
			BufferedImage outputImage = new BufferedImage(scaledWidth,
					scaledHeight, img.getType());
			/**
			 * scales the input image to the output image
			 */
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(img, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();
			/**
			 * returns output image
			 */
			return outputImage;

		}

		/**
		* This method will click on the image.
		* @param imgpath String - image path
		* @return true, if clicked on image successfully
		*/
		public boolean ClickImage(String imgpath) {
			try {
				Screen s = new Screen();
				s.click(imgpath);
				return true;
			} catch (Exception e) {
				logger.info("Failed to click on the image");
				return false;
			}

		}

		/**
		* This method will extract text from image.
		* @param imgPath String - image path
		* @return string  - text found in the image
		* @throws FindFailed - this exception will be thrown if text is not found in the image
		*/
		public String extractTextFromImage(String imgPath) throws FindFailed {
			Settings.OcrTextRead = true;
			Settings.OcrTextSearch = true;
			Screen s = new Screen();
			String Text = s.find(imgPath).text();
			return Text;
		}

		/**
		* This method will extract text from ROI.
		* @param rect int[] - location of element
		* @param enhance Boolean - true/false
		* @return String - extracted text from the given ROI
		* @throws FindFailed - this exception will be thrown if text is not found in the image
		*/
		public String extractTextFromROI(int rect[], boolean enhance)
				throws FindFailed {
			Settings.OcrTextRead = true;
			Settings.OcrTextSearch = true;
			Screen s = new Screen();
			Region rgn = new Region(rect[0], rect[1], rect[2], rect[3]);
			String Text = null;
			if (enhance) {
				Image img = new Image(s.capture(rgn));

				BufferedImage imgResized = img.resize(10);
				BufferedImage imgGrayScale = img
						.convertImageToGrayscale(imgResized);
				RescaleOp rescaleOp = new RescaleOp(1.2f, 15, null);
				rescaleOp.filter(imgGrayScale, imgGrayScale);
				Text = new Image(imgGrayScale).text();
			} else {
				Text = rgn.text();
			}
			return Text;
		}

		/**
		* This method will extract text from ROI.
		* @param rect int[] - location of element
		* @param enhance Boolean - true/false
		* @param highlight - true/false
		* @return String - extracted text from the given ROI
		* @throws FindFailed - this exception will be thrown if text is not found in the image
		*/
		public String extractTextFromROI(int rect[], boolean highlight,
				boolean enhance) throws FindFailed {
			Settings.OcrTextRead = true;
			Settings.OcrTextSearch = true;
			Screen s = new Screen();
			Region rgn = new Region(rect[0], rect[1], rect[2], rect[3]);
			if (highlight) {
				rgn.highlight(1);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String Text = null;
			if (enhance) {
				Image img = new Image(s.capture(rgn));
				BufferedImage imgResized = img.resize(10);
				BufferedImage imgGrayScale = img.convertImageToGrayscale(img
						.get());
				RescaleOp rescaleOp = new RescaleOp(2.2f, 25, null);
				rescaleOp.filter(imgGrayScale, imgGrayScale);
				Text = new Image(imgGrayScale).text();
				File f = new File("grayScale" + Math.random() + ".jpg");
				try {
					ImageIO.write(imgGrayScale, "jpg", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Text = rgn.text();
			}
			return Text;
		}

		/**
		* This method will extract text from screenshots.
		* @param rect int[] - location of the element,ROI details should be passed
		* @param NoOfScreenshots int - number of screenshots
		* @return the string[] - array of string containing all texts present in the screenshot
		* @throws IOException Signals that an I/O exception has occurred.
		*/
		public String[] extractTextFromScreenshots(int rect[],
				int NoOfScreenshots) throws IOException {
			Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
			Settings.OcrTextRead = true;
			Settings.OcrTextSearch = true;
			Screen s = new Screen();
			Region rgn = new Region(rect[0], rect[1], rect[2], rect[3]);
			boolean highlight = false;
			if (highlight) {
				rgn.highlight(1);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**
			 * Create a folder to store all the screenshots captured
			 */
			General_Utils general_Utils = new General_Utils();
			general_Utils.folder_create("Captions_screenshots");
			/**
			 * Capture screenshots of the specified region and save them
			 */
			for (int i = 0; i < NoOfScreenshots; i++) {
				gen_utils.wait(2000);
				Image img = new Image(s.capture(rgn));
				File f = new File("Captions_screenshots/captions_screenshot"
						+ Math.random() + ".png");
				try {
					ImageIO.write(img.get(), "png", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			File folder = new File("Captions_screenshots");
			File[] listOfFiles = folder.listFiles();
			String ResultText[] = new String[listOfFiles.length];
			/**
			 * Retrieve text from all the screenshots
			 */
			for (int i = 0; i < listOfFiles.length; i++) {

				BufferedImage imgGrayScale = ImageIO.read(listOfFiles[i]);
				float[] factors = new float[] { 0.9f, 0.9f, 0.9f };
				float[] offsets = new float[] { 0.0f, 255.0f, 0.0f };
				RescaleOp rescaleOp = new RescaleOp(factors, offsets, null);
				rescaleOp.filter(imgGrayScale, imgGrayScale);
				ResultText[i] = new Image(imgGrayScale).text();
				File f = new File("grayScale" + Math.random() + ".jpg");// set
				// appropriate
				// path

				/**
				 * Remove all the junk characters
				 */
				ResultText[i].replaceAll("[^a-zA-Z]", "");
				if (ResultText[i].length() == 1) {
					ResultText[i] = "";
				}
			}
			/**
			 * Delete the folder created
			 */
			general_Utils.folder_delete("Captions_screenshots");
			/**
			 * return the text retrieved
			 */
			return ResultText;

		}

		/**
		* This method will extract text from screenshots.
		* @param rect int[] - location of element on the screen
		* @param NoOfScreenshots int - number of screenshots
		* @param factors float[] - factors to consider to rescale image
		* @param offsets float[] - offset to be considered rescale image 
		* @param delay int - wait time
		* @param debug Boolean - true/false
		* @return the string[] - extracted text from all screenshots
		* @throws IOException Signals that an I/O exception has occurred.
		*/
		/*
		 * Description: Overloaded OCR extraction tool for better user control
		 * Params: rect, noOfScreenShots, scaleFactor [], color []
		 */
		public String[] extractTextFromScreenshots(int rect[],
				int NoOfScreenshots, float[] factors, float[] offsets,
				int delay, boolean debug) throws IOException {
			Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
			Settings.OcrTextRead = true;
			Settings.OcrTextSearch = true;
			Screen s = new Screen();
			Region rgn = new Region(rect[0], rect[1], rect[2], rect[3]);
			boolean highlight = false;
			if (highlight) {
				rgn.highlight(1);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**
			 * Create a folder to store all the screenshots captured
			 */
			General_Utils general_Utils = new General_Utils();
			general_Utils.folder_create("Captions_screenshots");
			/**
			 * Capture screenshots of the specified region and save them
			 */
			for (int i = 0; i < NoOfScreenshots; i++) {
				gen_utils.wait(delay);
				Image img = new Image(s.capture(rgn));
				File f = new File("Captions_screenshots/captions_screenshot"
						+ Math.random() + ".png");
				try {
					ImageIO.write(img.get(), "png", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			File folder = new File("Captions_screenshots");
			File[] listOfFiles = folder.listFiles();
			String ResultText[] = new String[listOfFiles.length];
			/**
			 * Retrieve text from all the screenshots
			 */
			for (int i = 0; i < listOfFiles.length; i++) {

				BufferedImage imgGrayScale = ImageIO.read(listOfFiles[i]);
				RescaleOp rescaleOp = new RescaleOp(factors, offsets, null);
				rescaleOp.filter(imgGrayScale, imgGrayScale);
				ResultText[i] = new Image(imgGrayScale).text();
				gen_utils.folder_create("testImages");
				File f = new File("testImages/grayScale" + Math.random()
						+ ".jpg");// set
				// appropriate
				// path
				System.out.println("Result Text: " + ResultText[i]);
				if (debug) {
					try {
						ImageIO.write(imgGrayScale, "jpg", f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			/**
			 * Delete the folder created
			 */
			general_Utils.folder_delete("Captions_screenshots");
			/**
			 * return the text retrieved
			 */
			return ResultText;
		}

		/**
		* This method will extract text from screenshots.
		* @param rect int[] - location of element on the screen
		* @param NoOfScreenshots int - number of screenshots
		* @param factors float[] - factors to consider to rescale image
		* @param offsets float[] - offset to be considered rescale image 
		* @param delay int - wait time
		* @param debug Boolean - true/false
		* @param invertImage Boolean - true/false
		* @param resizeFactor double - percentage to resize image
		* @return the string[] - extracted text from all screenshots
		* @throws IOException Signals that an I/O exception has occurred.
		*/
		
		/*
		 * Description: Overloaded OCR extraction tool for better user control
		 * Params: rect, noOfScreenShots, scaleFactor [], color []
		 */
		public String[] extractTextFromScreenshots(int rect[],
				int NoOfScreenshots, float[] factors, float[] offsets,
				int delay, boolean debug, boolean invertImage, double resizeFactor)
				throws IOException {
			Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
			Settings.OcrTextRead = true;
			Settings.OcrTextSearch = true;
			Screen s = new Screen();
			Region rgn = new Region(rect[0], rect[1], rect[2], rect[3]);
			boolean highlight = false;
			if (highlight) {
				rgn.highlight(1);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/**
			 * Create a folder to store all the screenshots captured
			 */
			General_Utils general_Utils = new General_Utils();
			general_Utils.folder_create("Captions_screenshots");
			/**
			 * Capture screenshots of the specified region and save them
			 */
			for (int i = 0; i < NoOfScreenshots; i++) {
				gen_utils.wait(delay);
				Image img = new Image(s.capture(rgn));
				File f = new File("Captions_screenshots/captions_screenshot"
						+ Math.random() + ".png");
				try {
					ImageIO.write(img.get(), "png", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			File folder = new File("Captions_screenshots");
			File[] listOfFiles = folder.listFiles();
			String ResultText[] = new String[listOfFiles.length];
			/**
			 * Retrieve text from all the screenshots
			 */
			for (int i = 0; i < listOfFiles.length; i++) {

				BufferedImage imgGrayScale = ImageIO.read(listOfFiles[i]);				
				if (invertImage) {
					imgGrayScale = this.invertImage(imgGrayScale);
				}
				imgGrayScale = this.ImageResize(imgGrayScale, resizeFactor);
				RescaleOp rescaleOp = new RescaleOp(factors, offsets, null);
				rescaleOp.filter(imgGrayScale, imgGrayScale);
				//imgGrayScale = this.invertImage(imgGrayScale);
				ResultText[i] = new Image(imgGrayScale).text();
				gen_utils.folder_create("testImages");
				File f = new File("testImages/grayScale" + Math.random()
						+ ".jpg");// set
				// appropriate
				// path
				System.out.println("Result Text: " + ResultText[i]);
				if (debug) {
					try {
						ImageIO.write(imgGrayScale, "jpg", f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			/**
			 * Delete the folder created
			 */
			general_Utils.folder_delete("Captions_screenshots");
			/**
			 * return the text retrieved
			 */
			return ResultText;
		}
		
		/**
		* This method will extract text from screenshots.
		* @param rect int[] - location of element on the screen
		* @param NoOfScreenshots int - number of screenshots
		* @param removejunkflag Boolean - true/false,if true removes all special characters from text
		* @return the string[] - extracted text from all screenshots
		* @throws IOException Signals that an I/O exception has occurred.
		*/
		public String[] extractTextFromScreenshots(int rect[],
				int NoOfScreenshots, boolean removejunkflag) throws IOException {
			Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
			Settings.OcrTextRead = true;
			Settings.OcrTextSearch = true;
			Screen s = new Screen();
			Region rgn = new Region(rect[0], rect[1], rect[2], rect[3]);
			/**
			 * Create a folder to store all the screenshots captured
			 */
			General_Utils general_Utils = new General_Utils();
			general_Utils.folder_create("Captions_screenshots");
			/**
			 * Capture screenshots of the specified region and save them
			 */
			for (int i = 0; i < NoOfScreenshots; i++) {
				gen_utils.wait(2000);
				Image img = new Image(s.capture(rgn));
				File f = new File("Captions_screenshots/captions_screenshot"
						+ Math.random() + ".png");
				try {
					ImageIO.write(img.get(), "png", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			File folder = new File("Captions_screenshots");
			File[] listOfFiles = folder.listFiles();
			String ResultText[] = new String[listOfFiles.length];
			/**
			 * Retrieve text from all the screenshots
			 */
			for (int i = 0; i < listOfFiles.length; i++) {
				BufferedImage imgGrayScale = ImageIO.read(listOfFiles[i]);
				float[] factors = new float[] { 0.9f, 0.9f, 0.9f };
				float[] offsets = new float[] { 0.0f, 255.0f, 0.0f };
				RescaleOp rescaleOp = new RescaleOp(factors, offsets, null);
				rescaleOp.filter(imgGrayScale, imgGrayScale);
				ResultText[i] = new Image(imgGrayScale).text();
				File f = new File("grayScale" + Math.random() + ".jpg");
				try {
					ImageIO.write(imgGrayScale, "jpg", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/**
				 * Remove all the junk characters
				 */
				if (removejunkflag) {
					ResultText[i].replaceAll("[^a-zA-Z]", "");
					if (ResultText[i].length() == 1) {
						ResultText[i] = "";
					}
				}
			}
			/**
			 * Delete the folder created
			 */
			general_Utils.folder_delete("Captions_screenshots");
			/**
			 * return the text retrieved
			 */
			return ResultText;

		}

		/**
		* This method will invert the image.
		* @param src BufferdImage - image to be inverted
		* @return BufferedImage - inverted image
		*/
		public BufferedImage invertImage(final BufferedImage src) {
			short[] invertTable;
			invertTable = new short[256];
			for (int i = 0; i < 256; i++) {
				invertTable[i] = (short) (255 - i);
			}
			final int w = src.getWidth();
			final int h = src.getHeight();
			final BufferedImage dst = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);

			final BufferedImageOp invertOp = new LookupOp(new ShortLookupTable(
					0, invertTable), null);
			if (src.getType() == BufferedImage.TYPE_BYTE_INDEXED
					|| src.getType() == 12) {
				BufferedImage newSrc = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				newSrc.getGraphics().drawImage(src, 0, 0, null);
				return invertOp.filter(newSrc, dst);
			} else {
				return invertOp.filter(src, dst);
			}
		}

		/**
		* This method will extract color from one pixel area.
		* @param x int - x coordinate
		* @param y int - y coordinate
		* @return object[] - color details
		*/
		public Object[] extractColorFromOnePixelArea(int x, int y) {
			Object[] retval = new Object[5];
			int red = 0;
			int green = 0;
			int blue = 0;
			try {
				Robot r = new Robot();
				red = r.getPixelColor(x, y).getRed();
				green = r.getPixelColor(x, y).getGreen();
				blue = r.getPixelColor(x, y).getBlue();
				retval[0] = "Pass";
				retval[1] = "Successfully picked color in one pixel area";
				retval[2] = red;
				retval[3] = green;
				retval[4] = blue;
			} catch (Exception e) {
				retval[0] = "Fail";
				retval[1] = "Failed to pick color in one pixel area.";
				retval[2] = red;
				retval[3] = green;
				retval[4] = blue;
				System.out.println(e.getMessage() + "\n");
				e.printStackTrace();
			}
			return retval;
		}

		/**
		* This method will capture screenshot with time stamp.
		* @param imgPath String - path of the image
		* @return string - image path with time stamp
		*/
		public String captureScreenshot(String imgPath) {
			Screen s = new Screen();
			String retval = null;
			Clipboard clipboard = Toolkit.getDefaultToolkit()
					.getSystemClipboard();
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyyMMdd hh mm ss a");
				Calendar now = Calendar.getInstance();
				Robot r = new Robot();
				Rectangle rec = new Rectangle(0, 0, 800, 1000);
				Robot ro = new Robot();
				s.type(Key.PRINTSCREEN, KeyModifier.ALT);
				BufferedImage img = (BufferedImage) clipboard
						.getData(DataFlavor.imageFlavor);
				File f = new File(imgPath + "/"
						+ formatter.format(now.getTime()) + ".jpg");// set
																	// appropriate
																	// path
				ImageIO.write(img, "jpg", f);
				retval = imgPath + "/" + formatter.format(now.getTime())
						+ ".jpg";
				return retval;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return retval;
			}
		}

		/**
		* This method will capture screenshot.
		* @param rect int[] - location of element
		* @return the string
		*/
		public String captureScreenshot(int[] rect) {
			Screen s = new Screen();
			String retval = null;
			Region rgn = new Region(rect[0], rect[1], rect[2], rect[3]);
			SimpleDateFormat formatter = new SimpleDateFormat("hh mm ss a");
			Calendar now = Calendar.getInstance();
			String motionCaptureFolder = Constants.motionCapturePath;
			/**
			 * Create a folder to store all the screenshots captured
			 */
			General_Utils general_Utils = new General_Utils();
			general_Utils.folder_create(motionCaptureFolder);
			Image img = new Image(s.capture(rgn));
			File f = new File(motionCaptureFolder + "/" + System.nanoTime()
					+ ".png");
			try {
				ImageIO.write(img.get(), "png", f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return motionCaptureFolder;
		}

		/**
		* This method will compare image given 2 images.
		* @param fileA File - file containing image1
		* @param fileB File - file containing image2
		* @return float - matching percentage of 2 images
		*/
		public float compareImage(File fileA, File fileB) {
			float percentage = 100;
			System.out.println(fileA.getAbsolutePath() + " , " + fileB.getAbsolutePath());
			try {
				// take buffer data from both image files //
				BufferedImage biA = ImageIO.read(fileA);
				DataBuffer dbA = biA.getData().getDataBuffer();
				int sizeA = dbA.getSize();
				BufferedImage biB = ImageIO.read(fileB);
				DataBuffer dbB = biB.getData().getDataBuffer();
				int sizeB = dbB.getSize();
				int count = 0;
				// compare data-buffer objects //
				if (sizeA == sizeB) {

					for (int i = 0; i < sizeA; i++) {

						if (dbA.getElem(i) == dbB.getElem(i)) {
							count = count + 1;
						}
					}
					percentage = (count * 100) / sizeA;
				} else {
					System.out.println("Both the images are not of same size");
				}
			} catch (Exception e) {
				System.out.println("Failed to compare image files ... " + e);
			}
			return percentage;
		}
		
		
		/**
		* Find image off screen buffer.
		* @param imagePath the image path
		* @return true, if successful
		* @throws FindFailed the find failed
		*/
		public boolean findImageOffScreenBuffer(String imagePath) {
			Screen screen = new Screen();
			Pattern pattern = new Pattern(imagePath);
			try {
				Match match = screen.find(imagePath);
				match.highlight(1);
			} catch (FindFailed e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
			screen.setAutoWaitTimeout(Constants.autoWaitTimeout);
			if (screen.exists(pattern.exact()) != null) {
				System.out.println(imagePath+" Image exists.");
				return true;
			} else {
				System.out.println(imagePath+" Image not found.");
				return false;
			}
		}
		
		/**
		* This method will find image from screen buffer.
		* @param imagePath String - image path
		* @param similarity float- percentage the image should match
		* @return true, if image matches given percentage or more
		*/
		public boolean findImageOffScreenBuffer(String imagePath, float similarity) {
			Screen screen = new Screen();
			Pattern pattern = new Pattern(imagePath);
			screen.setAutoWaitTimeout(Constants.autoWaitTimeout);
			try {
				Match match = screen.find(imagePath);
				match.highlight(1);
			} catch (FindFailed e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (screen.exists(pattern.similar(similarity), 5) != null) {
				System.out.println(imagePath+" Image exists.");
				return true;
			} else {
				System.out.println(imagePath+" Image not found.");
				return false;
			}
		}
		
		/**
		* This method will find image from File.
		* @param imgPath String - image path
		* @param screenShotPath String- Screen shot path
		* @return true, if image matches given percentage or more
		*/
		public boolean findImageInFile(String imgPath, String screenShotPath) throws IOException, InterruptedException {
			Finder f = new Finder(screenShotPath);
			System.out.println(screenShotPath);
			System.out.println(new File(imgPath).getAbsolutePath());
			Match match = null;
			f.find(imgPath);
			Thread.sleep(5000);
			int counter = 0;
			System.out.println(f.hasNext());			
			while (f.hasNext()) {				
				match = f.next(); // objMatch gives you the matching region.
				counter++;
			}
			f.destroy();
			System.out.println(f.hasNext());
			if (counter != 0) {
				System.out.println("Match Found!");
				return true;
			} else {
				System.out.println("Match Not Found!");
				return false;
			}

		}
	}

	/**
	* This Class contains general utility methods.
	*/
	public class General_Utils {
		
		/**
		* This method will wait for given time.
		* @param dur int - wait time
		*/
		public void wait(int dur) {
			try {
				System.out.println("Sleeping for " + dur + " milliseconds");
				Thread.sleep(dur);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}

		/**
		* This method will create new folder.
		* @param path String - path where folder has to be created
		*/
		public void folder_create(String path) {
			File folder_to_create = new File(path);
			if (!folder_to_create.exists()) {
				folder_to_create.mkdirs();
			}
		}

		/**
		* This method will delete the Folder.
		* @param path String - path where folder has to be deleted
		*/
		public void folder_delete(String path) {
			File folder_to_delete = new File(path);
			if (folder_to_delete.exists()) {
				if (folder_to_delete.isDirectory()) {
					for (File sub : folder_to_delete.listFiles()) {
						folder_delete(sub.getAbsolutePath());
					}
				}
				folder_to_delete.delete();
			}
		}

		/**
		* This method will read config file.
		* @param info String - info
		* @return string
		*/
		public String readConfigFile(String info) {
			String retval = "";
			String confFilePath = "";
			return retval;
		}

		/**
		* This method will copy text to clipboard.
		* @param text String - text to be copied
		*/
		public void Copy_to_clipboard(String text) {
			StringSelection stringSelection = new StringSelection(text);
			Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(stringSelection, null);
		}

		/**
		* This method will parse jSON path given and retrieve the data.
		* @param JsonPath String - json path
		* @param key String - key
		* @return int[] - returns Integer array retrived from the file
		*/
		public int[] retrieve_intArr_JSON(String JsonPath, String key) {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = null;
			try {
				jsonObject = (JSONObject) parser
						.parse(new FileReader(JsonPath));
			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONArray keyarr = (JSONArray) jsonObject.get(key);
			int keyL[] = new int[keyarr.size()];
			for (int k = 0; k < keyarr.size(); k++) {
				keyL[k] = Integer.parseInt((String) keyarr.get(k));
			}

			return keyL;
		}

		/**
		* This method will check if string array sorted in ascending order.
		* @param arrayToCompare String[] - array to check for sort
		* @return true, if array is sorted
		*/
		public boolean checkStringArraySortedInAscendingOrder(
				String[] arrayToCompare) {
			boolean retval = true;
			int arrayItr = 0;
			int arrayLength = arrayToCompare.length;
			int strCompareVal = 0;
			for (arrayItr = 0; arrayItr < arrayLength - 1; arrayItr++) {
				strCompareVal = arrayToCompare[arrayItr]
						.compareToIgnoreCase(arrayToCompare[arrayItr + 1]);
				if (strCompareVal > 0) {
					retval = false;
					return retval;
				}
			}
			return retval;
		}

		/**
		* This method will check if given process is running.
		* @param processName String - process name
		* @return true, if is process running
		* @throws IOException Signals that an I/O exception has occurred.
		* @throws InterruptedException the interrupted exception
		*/
		public boolean isProcessRunning(String processName) throws IOException,
				InterruptedException {
			ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
			Process process = processBuilder.start();
			String tasksList = process.getInputStream().toString();
			return tasksList.contains(processName);
		}
	}
}