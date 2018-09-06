/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.xml.XMLLayout;

/**
* This class contains methods to store the logs during execution.
* @author Rohith Mallikarjuna - Charter
*/
public class LogManager {

	public static String logAppender;
	public static String logLayout;
	public static String logPattern;
	public static String scenarioName;
	public static String logExtension;
	public static String logFolder;
	public static Logger logger;
	protected static ArrayList<String> output = new ArrayList<String>();

	/**
	* This constructor will set values to all the variables.
	* @param logAppender String - logs to be appended
	* @param logLayout String - layout of the Log
	* @param logPattern String - Pattern to store the Logs
	* @param scenarioName String - scenario name for which logs are to be recorded
	*/
	public LogManager(String logAppender, String logLayout,
			String logPattern, String scenarioName) {

		this.logAppender = logAppender;
		this.logLayout = logLayout;
		if (this.logLayout.equals("HTMLLayout")) {
			logExtension = ".html";
			logFolder = "B.html";
		} else if (this.logLayout.equals("PatternLayout")) {
			logExtension = ".log";
			logFolder = "B.Log";
		} else if (this.logLayout.equals("XMLLayout")) {
			logExtension = ".xml";
			logFolder = "B.xml";
		}
		this.scenarioName = scenarioName;
		if (logPattern.equals("DEFAULT")) {
			this.logPattern = "%d{MM-dd-yyyy HH:mm:ss}     [%p]   %l  -   %m%n";
		} else {
			this.logPattern = logPattern;
		}

	}

	/**
	* This constructor will display logs based on the logAppender.
	* @return Logger - it will return object of Logger class
	*/
	public Logger publishLog() {

		if (logAppender.equals("FileAppender")) {

			URL rootFolder = LogManager.class.getProtectionDomain()
					.getCodeSource().getLocation();
			File rootFolderPath = new File(rootFolder.getPath());
			System.setProperty("Pyramid.Root", rootFolderPath.getParentFile()
					.getParentFile().getAbsolutePath());
			// Logger logger = Logger.getLogger(ExcelRunner.class.getName());
			Logger.getRootLogger().getLoggerRepository().resetConfiguration();
			String TimeStamp = String.format("%tc", new Date());
			String formatedTimeStamp = TimeStamp.replace(":", "-");
			String LogFileName = scenarioName + "-" + formatedTimeStamp
					+ logExtension;
			// String LogFileName = scenarioName + "-" + String.valueOf((int)
			// new Date().getTime()) +logExtension ;
			String fileLogPath = rootFolderPath.getParentFile().getParentFile()
					.getAbsolutePath()
					+ "\\BRunResults\\" + logFolder + "\\" + LogFileName;

			FileAppender fileAppender = new FileAppender();
			String filelogPattern = logPattern;
			fileAppender.setName("Pyramid.RuntimeFileAppender");
			//Setting up log layout.
			if (logLayout.equals("HTMLLayout")) {
				HTMLLayout html_Layout = new HTMLLayout();
				html_Layout.setTitle(scenarioName);
				fileAppender.setLayout(html_Layout);

			} else if (logLayout.equals("PatternLayout")) {
				fileAppender.setLayout(new PatternLayout(filelogPattern));
			} else if (logLayout.equals("XMLLayout")) {
				XMLLayout xmlLayout = new XMLLayout();
				fileAppender.setLayout(xmlLayout);
			}
			//Appending all detains to file appender
			fileAppender.setThreshold(Level.DEBUG);
			fileAppender.setFile(fileLogPath);
			fileAppender.setAppend(true);
			fileAppender.activateOptions();
			Logger.getRootLogger().addAppender(fileAppender);
			logger = Logger.getRootLogger();

		} else if (logAppender.equals("ConsoleAppender")) {

			URL rootFolder = LogManager.class.getProtectionDomain()
					.getCodeSource().getLocation();
			File rootFolderPath = new File(rootFolder.getPath());
			System.setProperty("Pyramid.Root", rootFolderPath.getParentFile()
					.getParentFile().getAbsolutePath());
			// Logger logger = Logger.getLogger(ExcelRunner.class.getName());
			Logger.getRootLogger().getLoggerRepository().resetConfiguration();

			ConsoleAppender consoleAppender = new ConsoleAppender();
			String filelogPattern = logPattern;
			consoleAppender.setName("Pyramid.RuntimeConsoleAppender");
			consoleAppender.setLayout(new PatternLayout(filelogPattern));
			consoleAppender.setThreshold(Level.DEBUG);
			consoleAppender.activateOptions();
			Logger.getRootLogger().addAppender(consoleAppender);
			logger = Logger.getRootLogger();

		}
		return logger;

	}

	/**
	* This method will add passed Info to the logs.
	* @param msg String - info to be added to the log
	*/
	public void info(String msg) {
		if (logger.isInfoEnabled()) {
			output.add(msg);
		}
		logger.info(msg);
	}

	/**
	* This method will add passed error message to the logs.
	* @param msg String - error message to be added to the log
	*/
	public void error(String msg) {
		output.add(msg);
		logger.error(msg);
	}

	/**
	* This method will add passed warning message to the logs.
	* @param msg String - Warning message to be added to the log
	*/
	public void warn(String msg) {
		output.add(msg);
		logger.warn(msg);
	}

	/**
	* This method will add passed Debug message to the logs.
	* @param msg String - Debug message to be added to the log
	*/
	public void debug(String msg) {
		if (logger.isDebugEnabled()) {
			output.add(msg);
		}
		logger.debug(msg);
	}

	/**
	* This method will add passed trace message to the logs.
	* @param msg String - Trace message to be added to the log
	*/
	public void trace(String msg) {
		if (logger.isTraceEnabled()) {
			output.add(msg);
		}
		logger.trace(msg);
	}

	/**
	* This method will add passed fatal message to the logs.
	* @param msg String - Fatal message to be added to the log
	*/
	public void fatal(String msg) {
		output.add(msg);
		logger.fatal(msg);
	}

}
