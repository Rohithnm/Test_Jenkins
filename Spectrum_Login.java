/*
* ******************************************************************************
* Copyright 2017-2018 Charter Communications. All Rights Reserved.
* Unauthorized copying of this file, via any medium is strictly prohibited
* Proprietary and confidential
* ******************************************************************************
*/
package SuiteUtils;

import java.awt.AWTException;

import Utility.Utilities;

/**
* This class contains methods to logIn into the Spectrum.
* @author Rohith Mallikarjuna - Charter
*/
public class Spectrum_Login {
	
	
	/**
	* This method will login user into spectrum.
	* @return Boolean - will return true is user successfully login.
	* @throws AWTException - AWT exception might occur
	* @see Utilities.screenFunctions#findScreenImage(String)
	*/
	public boolean spectrum_login() throws AWTException{ 
		Utilities ut = new Utilities();
		int i = 1;
		Utilities.Controller ctrl = ut.new Controller();
		Utilities.screenFunctions screen = ut.new screenFunctions();
		String imgPath = "";
		//screen.captureScreenshot();
		if(true){		
			if (!screen.findScreenImage("img/spectrum_icon_selected.png")){
				ctrl.keyPress("down");			
			}
			try {
				System.out.println("Sleeping for 3 seconds");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ctrl.keyPress("Enter");
			try {
				System.out.println("Sleeping for 5 seconds");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (screen.findScreenImage("img/sign_in_to__live.png")){
				ctrl.keyPress("Enter");
				try {
					System.out.println("Sleeping for 7 seconds");
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ctrl.keyPress("Enter");
			}
			//It will search for image, if image is found, it will click on Enter
			if(screen.findScreenImage("img/spectrum_icon_selected.png")){
				ctrl.keyPress("Enter");
			}
			try {
				System.out.println("Sleeping for 10 seconds");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(screen.findScreenImage("img/typing_on__one.png")){
				ctrl.keyPress("olivette_gold");
				ctrl.keyPress("Tab");
				ctrl.keyPress("Enter");
				ctrl.keyPress("Down");
				try {
					System.out.println("Sleeping for 5 seconds");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ctrl.keyPress("Testing01");
				ctrl.keyPress("Tab");
				ctrl.keyPress("Enter");
				ctrl.keyPress("Down");
				try {
					System.out.println("Sleeping for 5 seconds");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(screen.findScreenImage("img/sign_in.png")){
					ctrl.keyPress("Enter");
				}
				try {
					System.out.println("Sleeping for 5 seconds");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(screen.findScreenImage("img/lic_agree_btn.png")){
					ctrl.keyPress("Enter");
				}
				/*while(!screen.findScreenImage("img/spec_menu_watch_live.png")){
					ctrl.keyPress("Left");
					i++;
					if (i==20){
						break;
					}
				}*/
				System.out.println("Successfully logged in to Spectrum app.");
				screen.captureScreenshot(imgPath);				
			}	
			else{
				System.out.println("Already logged in to  and Spectrum app");							
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	* This method will login to spectrum using the user credentials provided.
	* @param username String - user name
	* @param password String - password
	* @return Boolean - will return true is user successfully login.
	* @throws AWTException the AWT exception
	*/
	public boolean spectrum_login(String username,String password) throws AWTException{ 
		Utilities ut = new Utilities();
		int i = 1;
		Utilities.Controller ctrl = ut.new Controller();
		Utilities.screenFunctions screen = ut.new screenFunctions();
		Utilities.General_Utils gen_utils = new Utilities().new General_Utils();
		String imgPath = "";
		//screen.captureScreenshot();
		if (!screen.findScreenImage("img/_home.png")){
			return true;
		}
		if(true){		
			if (!screen.findScreenImage("img/spectrum_icon_selected.png")){
				ctrl.keyPress("down");			
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ctrl.keyPress("Enter");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (screen.findScreenImage("img/sign_in_to__live.png")){
				ctrl.keyPress("Enter");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ctrl.keyPress("Enter");
			}
			if(screen.findScreenImage("img/spectrum_icon_selected.png")){
				ctrl.keyPress("Enter");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(screen.findScreenImage("img/typing_on__one.png")){
				ctrl.keyPress(username);
				ctrl.keyPress("Tab");
				ctrl.keyPress("Enter");
				ctrl.keyPress("Down");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ctrl.keyPress(password);
				ctrl.keyPress("Tab");
				ctrl.keyPress("Enter");
				ctrl.keyPress("Down");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(screen.findScreenImage("img/sign_in.png")){
					ctrl.keyPress("Enter");
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (screen.findScreenImage("img/invalid_login_message.png")){
					return false;
				}
				
				if(screen.findScreenImage("img/lic_agree_btn.png")){
					ctrl.keyPress("Enter");					
				}
				while(!screen.findScreenImage("img/spec_menu_shown.png")){
					gen_utils.wait(2000);
					i++;
					if (i==20){
						break;
					}
				}
				//screen.captureScreenshot(imgPath);				
			}	
			else{
				System.out.println("Already logged in to  and Spectrum app");							
			}
			return true;
		}
		else{
			return false;
		}
	}
}
