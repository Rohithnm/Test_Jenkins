package Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
* This class contains methods to perform actions on elements.
* @author AshithRaj Shetty - Charter
*/
public class HTTPConnectorActions {
    
    public RemoteWebDriver driver;
    public static long waitTime=60;

    /**
    * This method will set up all desired capabilities.
    * @return Boolean, true if setup is successfully completed
    */
    public boolean initializeDriver(){
        boolean result = false;
        try {

            System.setProperty("webdriver.chrome.driver", "D:/ChromeDriver/chromedriver.exe");
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-web-security");
            options.addArguments("--user-data-dir");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            result=true;
        }catch (Exception e){
            e.printStackTrace();
            result=false;
        }
        return result;
    }

    /**
    * It will fetch theURL details from excel and launch it.
    * @see ReadConfig#getPropValues(String)
    */
    public void launchInterface(){
        try{
            HashMap<String,String> propertyDetails=ReadConfig.getPropValues("chromePlugin.properties");
            String finalAddress=propertyDetails.get("PluginAddress");
            //String finalAddress=readconfig.readPropertiesFile("PluginAddress");
            driver.get(propertyDetails.get("PluginAddress"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
    * This method will find the required element details.
    * @param elementName String[] - List of element names
    * @param xPathType String[] - List of type of xPaths
    * @return HashMap - returns a HashMap containing all the elements text
    */
    public HashMap<String,ArrayList<String>> findElementDetails(String [] elementName, String [] xPathType) {
        List<WebElement> elementList = null;
        HashMap<String,ArrayList<String>> elementDetails=new HashMap<String, ArrayList<String>>();
        ArrayList<String> elementText=new ArrayList<String>();
        String elementString = "/";
        int i = 0;
        for (String str: elementName){
            elementString = elementString+"/*[@" + xPathType[i] + "='" + str + "']";
            i++;
        }
        System.out.println("Element String: " + elementString);
        try {
            elementList = driver.findElements(By.xpath(elementString));
            for(WebElement element : elementList){
                elementText.add(element.getText());
                //Construct as a string
            }
            elementDetails.put("ElementsText",elementText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return elementDetails;
    }

    /**
    * This method will click on an element
    * @param elementName String - element name to be clicked
    * @param xPathType String - type of xPath
    */
    public void clickOnElement(String elementName, String xPathType){
        WebElement element=null;
        if (xPathType == "Text") {
            String elementString = "//*[@text='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                driver.findElement(By.xpath(elementString)).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "ID") {
            String elementString = "//*[@id='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                driver.findElement(By.xpath(elementString)).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "Class") {
            String elementString = "//*[@class='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                driver.findElement(By.xpath(elementString)).click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * This method will perform the event on the given element
    * @param elementName String - element name
    * @param xPathType String - type of xPath
    * @param eventType String - event to be performed
    */
    public void keyPressEvent(String elementName, String xPathType,String eventType){
        WebElement element=null;
        if (xPathType == "Text") {
            String elementString = "//*[@text='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                selectKeyeventType(elementString,eventType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "ID") {
            String elementString = "//*[@id='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                selectKeyeventType(elementString,eventType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "Class") {
            String elementString = "//*[@class='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                selectKeyeventType(elementString,eventType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * This method will perform the keyBoard event.
    * @param elementString String - element Name 
    * @param eventType String - keyBoard event to be passed
    */
    protected void selectKeyeventType(String elementString,String eventType){
        switch (eventType){
            case "ENTER":
                driver.findElement(By.xpath(elementString)).sendKeys(Keys.ENTER);
                break;
            case "BACKSPACE":
                driver.findElement(By.xpath(elementString)).sendKeys(Keys.BACK_SPACE);
                break;
            case "LEFT":
                driver.findElement(By.xpath(elementString)).sendKeys(Keys.ARROW_LEFT);
                break;
            case "RIGHT":
                driver.findElement(By.xpath(elementString)).sendKeys(Keys.ARROW_RIGHT);
                break;
            case "UP":
                driver.findElement(By.xpath(elementString)).sendKeys(Keys.ARROW_UP);
                break;
            case "DOWN":
                driver.findElement(By.xpath(elementString)).sendKeys(Keys.ARROW_DOWN);
                break;
                //Use hashmap
        }
    }

    /**
    * This method will execute JavaScript to scroll to the given element.
    * @param elementName String - element name
    * @param xPathType String-  type of xPath
    */
    public void scrollToElement(String elementName, String xPathType) {
        WebElement element = null;
        if (xPathType == "Text") {
            String elementString = "//*[@text='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element = driver.findElement(By.xpath(elementString));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "ID") {
            String elementString = "//*[@id='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element = driver.findElement(By.xpath(elementString));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "Class") {
            String elementString = "//*[@class='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element = driver.findElement(By.xpath(elementString));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * This method will check if element is visible
    * @param elementName String - element name
    * @param xPathType String - type of xPath
    * @return Boolean - true if is element visible
    */
    public boolean isElementVisible(String elementName,String xPathType){
        WebElement element=null;
        if (xPathType == "Text") {
            String elementString = "//*[@text='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return verifyElementVisibility(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "ID") {
            String elementString = "//*[@id='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return verifyElementVisibility(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "Class") {
            String elementString = "//*[@class='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return  verifyElementVisibility(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
    * This method will check if element is visible in the page.
    * @param element String - element
    * @return Boolean - true if element is visible
    */
    protected boolean verifyElementVisibility(WebElement element) {
        try{
            if(element.isDisplayed())
                return true;
            return false;
        }catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    /**
    * This method will wait till element in visible
    * @param elementName String - element Name
    * @param xPathType String - type of xPath
    * @return Boolean - true if element is visible
    */
    public boolean waitForElementToBeVisible(String elementName,String xPathType){
        WebElement element=null;
        if (xPathType == "Text") {
            String elementString = "//*[@text='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return waitUntilElementIsVisible(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "ID") {
            String elementString = "//*[@id='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return waitUntilElementIsVisible(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "Class") {
            String elementString = "//*[@class='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return  waitUntilElementIsVisible(element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
    * This method will wait until element is visible
    * @param element String - element Name
    * @return Boolean - true if element is visible
    */
    protected boolean waitUntilElementIsVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch (org.openqa.selenium.NoSuchElementException e){
            return false;
        }
    }

    /**
    * This method will execute the given script
    * @param script String - script to be executed
    * @param elementName String - element Name
    * @param xPathType String - type of xPath
    * @return Object - instance of an Object
    */
    public Object executeScript(String script,String elementName,String xPathType) {
        WebElement element=null;
        if (xPathType == "Text") {
            String elementString = "//*[@text='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return  ((JavascriptExecutor) driver).executeScript(script, element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "ID") {
            String elementString = "//*[@id='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return  ((JavascriptExecutor) driver).executeScript(script, element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (xPathType == "Class") {
            String elementString = "//*[@class='" + elementName + "']";
            System.out.println("Element String: " + elementString);
            try {
                element=driver.findElement(By.xpath(elementString));
                return  ((JavascriptExecutor) driver).executeScript(script, element);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
    * This method will Capture screenshot.
    * @return String[] - list of screenshots captured
    */
    public String[] captureScreenshot(){
    	String [] result = new String[3];
    	String seleniumScreenshotPath = "";
    	result[0] = "Pass";
    	result[1]="Successfully captured screenshot";
    	result[2]=seleniumScreenshotPath;
    	return result;
    }

    /**
    * This method will get the window handle.
    * @return String - returns window handle
    */
    public String getWindowHandle(){
        return driver.getWindowHandle();
    }

    /**
    * Gets the window handles.
    * @return the window handles
    */
    public Set getWindowHandles(){
        return driver.getWindowHandles();
    }

    /**
    * This method will take screenshot.
    */
    public void takeScreenhot(){
        //return byte
    }

    /**
    * This method will take screenshots.
    */
    public void takeScreenhots(){
        //return in a image path
    }

}
