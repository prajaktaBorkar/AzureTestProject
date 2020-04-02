package com.perficient.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.Status;

/**
 * Updated PageManager Class with some additional Methods 
 * @author pooja.manna
 *
 */
public class PageManager {
	private WebDriver driver;
	protected String browserFlag;
	protected ExtentTest test;
	protected Log log = LogFactory.getLog(this.getClass());

	public PageManager(WebDriver driverRemote, String browser, ExtentTest extentTest) {
		driver = driverRemote;
		browserFlag = browser;
		test = extentTest;
		PageFactory.initElements(driver, this);
	}

	/**
	 * This Method Switches the focus to default window.
	 */
	public void switchToDefaultContent() {
		try {
			driver.switchTo().defaultContent();
			test.log(Status.PASS, "Switched to default Window.");
		} catch (Exception exception) {
			exceptionPrintError(exception, null);
		}
	}

	/**
	 * This methods haults the application for the given time period.
	 * @throws InterruptedException 
	 * 
	 */
	public void sleep(long milliSeconds) throws InterruptedException{
		Thread.sleep(milliSeconds);
	}
	
	/**
	 * Dismiss the popup alert
	 */
	public void alertDismiss() {
		try {
			Alert alert = driver.switchTo().alert();
			String AlertMessage = alert.getText();
			System.out.println("Alert message is: " + AlertMessage);
			alert.dismiss();
			test.log(Status.PASS, "Alert Dismiss");
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
	}

	/**
	 * This method selects option from drop down by using index of data.
	 * @param ele
	 * @param index
	 */
	public void dropDownHandlingByIndex(WebElement element, int index) {
		try {
			Select drop = new Select(element);
			drop.selectByIndex(index);
			test.log(Status.PASS, "Input in DropDown is Selected.");
		}catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * Click on the element using JavaScriptExecutor
	 * @param element
	 */
	public void clickByJavaScriptExecutor(WebElement element){
		try{
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		test.log(Status.PASS, "Click on: " + element);
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}
	
	/**
	 * This Method scrolls down the webpage to a particular element.
	 * @param element
	 */
	public void ScrollWebPage(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
			test.log(Status.PASS, "Webpage is scrolled to element: " + element);
		}catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * This Method Switches the focus to Window number passed as parameter.
	 * @param i
	 */
	public void switchpreviousWindow(int i) {
		try {
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(i));
			test.log(Status.PASS, "Focused is switched to window number: " + i);
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
	}

	/**
	 * This Method selects option form drop down with respect to the Visible
	 * text of drop down.
	 * @param ele
	 * @param value
	 */
	public void dropDownHandlingByText(WebElement element, String value) {
		try {
			Select drop = new Select(element);
			drop.selectByVisibleText(value);
			test.log(Status.PASS, "Input in drop down is selected.");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * This Method selects option form drop down with respect to the value of drop down.
	 * @param ele
	 * @param value
	 */
	public void dropDownHandlingByValue(WebElement element, String value) {
		try {
			Select drop = new Select(element);
			drop.selectByValue(value);
			test.log(Status.PASS, "Input in drop down is selected.");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * This Method Handles the grid elements 
	 * @param ele1
	 * @param ele2
	 * @param value
	 * @throws InterruptedException
	 */
	public void gridHandling(WebElement element, List<WebElement> ele2, String value) throws InterruptedException {
		try {
			WebElement table = element;
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			System.out.println("no of rows are:::" + rows.size());
			List<WebElement> cols = table.findElements(By.tagName("td"));
			System.out.println("no of columns are:::" + cols.size());
			Thread.sleep(3000);
			List<WebElement> ele = ele2;
			ele.get(1).clear();
			ele.get(1).sendKeys(value);
			ele.get(1).sendKeys(Keys.ENTER);
			test.log(Status.PASS, "Elements in the grid is Handled");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * Will click on the element with the index i in the list of the element
	 * @param element
	 * @param elements
	 * @param i
	 */
	public void clickByActionSubMenu(WebElement element, List<WebElement> elements, int i) {
		try {
			until(element, 100);
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.moveToElement(elements.get(i)).click().build().perform();
			test.log(Status.PASS, "Click on the Element in list");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			exceptionPrintError(exception,elements.get(i));
			elementPrintError(exception, elements);
		} 
	}

	/**
	 * This Method returns the object of WebDriver Class.
	 * @return
	 */
	public WebDriver getDriver() {
		WebDriver driver = null;
		try {
			driver = this.driver;
			test.log(Status.PASS, "WebDriver instance is created");
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
		return driver;
	}

	/**
	 * This Method returns the Browser Flag.
	 * @return
	 */
	public String getBrowserFlag() {
		String str = null ;
		try{
		test.log(Status.PASS, "Browser Flag is returned");
		 str = browserFlag;
		}
		catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
		return str;
	}

	/**
	 * This Method navigates to a Webpage pointed by given URL.
	 * @param url
	 */
	public void navigate(String url) {
		try {
			driver.navigate().to(url);
			test.log(Status.PASS, "Page navigates to " + url);
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
	}

	/**
	 * This Method returns title of a WebPage.
	 * @return
	 */
	public String getTitle() {
		String t = null;
		try {
			t = driver.getTitle();
			test.log(Status.PASS, "Page Title is " + t);
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
		return t;
	}

	/**
	 * This Method switches focus of the Web Driver to an iframe with help of
	 * iframe name.
	 * @param frame
	 */
	public void switchToFrameByName(String frame) {
		try {
			driver.switchTo().frame(frame);
			test.log(Status.PASS, "Switch to Frame " + frame.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
	}

	/**
	 * This Method switches focus of the Web Driver to an iframe.
	 * @param frame
	 */
	public void switchToFrame(WebElement frame) {
		try {
			driver.switchTo().frame(frame);
			test.log(Status.PASS, "Switch to Frame " + frame.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
	}

	/**
	 * This Method Switches the focus of Web Driver to new Window.
	 * @throws InterruptedException
	 */
	public void switchToNewWindow() throws InterruptedException {
		try {
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
			Thread.sleep(4000);
			test.log(Status.PASS, "Switch to a new window.");
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
		} 
	}

	/**
	 * This Method Clicks on a Web element.
	 * @param element
	 */
	public void click(WebElement element) {
		try {
			element.click();
			test.log(Status.PASS, "Click " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * This Method Enters value in a text box Field.
	 * @param element
	 * @param keys
	 */
	public void sendKeys(WebElement element, String keys) {
		try {
			element.clear();
			element.sendKeys(keys);

			test.log(Status.PASS, "Send " + keys + " to " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * This Method waits for the element to be clickable and then enters the
	 * text in it.
	 * @param ele
	 * @param keys
	 * @param i
	 */
	public void waitAndSendkeys(WebElement element, String keys, int i) {
		try {
			WebDriverWait wait4 = new WebDriverWait(driver, i);
			wait4.until(ExpectedConditions.elementToBeClickable(element));
			element.sendKeys(keys);
			test.log(Status.PASS, "Send " + keys + " to " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
	}

	/**
	 * This Method returns the link text and then clicks on that element.
	 * @param element
	 * @param name
	 * @return
	 */
	public String verifyElementTextAndClick(WebElement element, String name) {
		String text = null;
		try {
			text = element.getText();
			System.out.println("Element text is " + text.toString());
			if (text.equalsIgnoreCase(name)) {
				element.click();
			}
			test.log(Status.PASS, "Get Text " + text + " of " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
		return text;
	}

	/**
	 * This Method returns the link text of an element.
	 * @param element
	 * @return
	 */
	public String getText(WebElement element) {
		String t = null;
		try {
			t = element.getText();
			test.log(Status.PASS, "Get Text " + t + " of " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
		return t;
	}

	/**
	 * This Method returns the data present in attribute 'Value' of an element.
	 * 
	 * @param element
	 * @return
	 */
	public String getTextValue(WebElement element) {
		String t = null;
		try {
			t = element.getAttribute("Value");
			System.out.println("Get Text value is" + t);
			test.log(Status.PASS, "Get Text " + t + " of " + element.toString());
		}catch (Exception exception) {
			exceptionPrintError(exception,element);
		} 
		return t;
	}

	/**
	 * This Method handles Alert pop up and selects accept option.
	 * 
	 * @param ele
	 */
	public void alertHandleOnWebelement(WebElement element) {
		try {
			waitAndClick(element, 100);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			test.log(Status.PASS, "Alert Handled: " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * This Method clicks on Element by using Actions Class.
	 * 
	 * @param element
	 */
	public void clickByAction(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();
			test.log(Status.PASS, "clickByAction " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * This Method Maximizes the size of browser window.
	 */
	public void maximizeBrowser() {
		try {
			driver.manage().window().maximize();
			test.log(Status.PASS, "Browser Window Maximized.");
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
			
		} 
	}

	/**
	 * This method double clicks on an element.
	 * @param element
	 */
	public void doubleClick(WebElement element) {
		try {
			Actions action = new Actions(driver).doubleClick(element);
			action.build().perform();
			test.log(Status.PASS, "Double click at: " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * This Methods Drag and Drops that element from source element to
	 * destination element.
	 * @param sourceElement
	 * @param destinationElement
	 */
	public void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
		try {
			Actions action = new Actions(driver);
			action.dragAndDrop(sourceElement, destinationElement).build().perform();
			test.log(Status.PASS, "Drag: " + sourceElement.toString() + " to: " + destinationElement.toString());
		} catch (StaleElementReferenceException exception) {

		} catch (Exception exception) {
			exceptionPrintError(exception,sourceElement);
			exceptionPrintError(exception,destinationElement);
		} 
	}

	/**
	 * This method enters data in element from backend.
	 * @param ele
	 * @param data
	 */
	public void insertDataFromBackend(WebElement element, String data) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].setAttribute('value', '" + data + "')", element);
			test.log(Status.PASS, "Send Data to input field: " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * This Method selects option from right click menu by its index.
	 * @param element
	 * @param i
	 */
	public void rightClickAndSelectOption(WebElement element, int i) {
		try {
			Actions builder = new Actions(driver);
			while (i > 0) {
				builder.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
				i--;
			}
			test.log(Status.PASS, "Select " + i + " option from right click menu.");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * This Method scrolls to an element and highlights it.
	 * @param element
	 */
	public void scrollAndHighlightElement(WebElement element) {

		try {
//			Coordinates coordinate = ((Locatable) element).getCoordinates();
//			coordinate.onPage();
//			coordinate.inViewPort();

			for (int i = 0; i < 4; i++) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
						"color: yellow; border: 4px solid blue,;");
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element);
			}
			test.log(Status.PASS, "Scroll and Highlight: " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * handle element which is not available in one time , (solve the stale
	 * element reference exception problem) it will search 4 times same element
	 * still it is available
	 * @param element
	 * @return
	 */
	public boolean retryingForSameElement(WebElement element) {
		boolean result = false;
		try {
			int attempts = 0;
			while (attempts < 4) {
				try {
					element.click();
					result = true;
					break;
				} catch (StaleElementReferenceException exception) {
					exception.printStackTrace();
				}
				attempts++;
			}
			test.log(Status.PASS, "Retrying for Same Element: " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
		return result;
	}

	/**
	 * This Method Handles List Box present on webpage
	 * @param element
	 */
	public void listBox(WebElement element) {
		try {
			element.click();
			List<WebElement> liList = driver.findElements(By.cssSelector("click on drop down list"));
			for (int i = 0; i < liList.size(); i++) {
				liList.get(i).click();
			}
			test.log(Status.PASS, "List Box Handled: " + element.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * Wait until the timeout given
	 * @param element
	 * @param timeout
	 */
	public void until(WebElement element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOf(element));
			test.log(Status.PASS, "Wait until " + element.toString() + "is visible.");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * Wait until the element is visible
	 * 
	 * @param element
	 */
	public void untilAvalible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.visibilityOf(element));

			test.log(Status.PASS, "Wait until " + element.toString() + "is visible.");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 

	}

	/**
	 * wait until the list of Element is visible
	 * @param element
	 */
	public void untilAvailable(List<WebElement> element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 100);
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
			test.log(Status.PASS, "Wait until " + element.toString() + "is visible.");
		} catch (Exception exception) {
			elementPrintError(exception, element);
			
		} 

	}

	/**
	 * Switch to new Url.
	 * @param i
	 * @param url
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void switchToNewURL(int i, String url) throws InterruptedException, AWTException {
		try {
			Thread.sleep(6000);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_T);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyRelease(KeyEvent.VK_T);
			Thread.sleep(1000);
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			// switches to new tab
			driver.switchTo().window(tabs.get(i));
			driver.get(url);
			test.log(Status.PASS, "Navigated " + url.toString());
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
			
		} 
	}

	/**
	 * Wait until the element is clickable
	 * @param element
	 * @param timeout
	 */

	public void untilClickable(WebElement element, int timeout) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
			test.log(Status.PASS, "Wait until " + element.toString() + "is clickable.");

		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
	}

	/**
	 * Wait until the element is clickable and then click on that element
	 * @param ele
	 * @param time
	 */
	public void waitAndClick(WebElement element, int time) {
		try {
			WebDriverWait wait4 = new WebDriverWait(driver, time);
			wait4.until(ExpectedConditions.elementToBeClickable(element)).click();
			test.log(Status.PASS, "Wait until " + element.toString() + "is clickable");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 

	}

	/**
	 * Click on list of element with the index 'i'
	 * @param ele
	 * @param i
	 */
	public void ClickOnElementList(List<WebElement> element, int i) {
		WebElement element1 = null;
		try {
			List<WebElement> eleList = element;
			element1 = eleList.get(i);
			element1.click();
			test.log(Status.PASS, "Click " + element.toString());
		} catch (Exception exception) {
			elementPrintError(exception, element);
			
		} 
	}

	/**
	 * Click on element after mouse hover
	 * @param ele
	 * @param ele1
	 * @throws InterruptedException
	 */
	public void menuSelection(WebElement element, WebElement element1) throws InterruptedException {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.moveToElement(element1).click().build().perform();
			test.log(Status.PASS, "Menu selection " + element1.toString() + "is Selected");
			Thread.sleep(1000);
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 

	}

	
	/**
	 * This method check for the element till its visible
	 * @param element
	 * @return
	 * @throws IOException
	 */
	public boolean isElementVisible(WebElement element, String stepData) throws IOException {
		try {
			element.isDisplayed();
			test.log(Status.PASS, element.toString() + " is visible.");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 
		return element.isDisplayed();
	}

	/**
	 * Method to capture ScreenShot
	 * @param drivername
	 * @return
	 */
	public String snapshot(TakesScreenshot drivername) {
		String currentPath = "./test-output/errorImages";
		String returnPath = "./errorImages";
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		try {
			log.info("save snapshot path is:" + currentPath + "/" + getDatetime() + ".png");
			FileUtils.copyFile(scrFile, new File(currentPath + "\\" + getDatetime() + ".png"));
			FileUtils.copyFile(scrFile, new File(returnPath + "\\" + getDatetime() + ".png"));
		} catch (IOException exception) {
			System.out.println("Can't save screenshot");
			return "";
		} finally {
			System.out.println("screen shot finished, it's in " + currentPath + " folder");
			return returnPath + "/" + getDatetime() + ".png";
		}
	}

	/**
	 * Method returns the current date
	 * @return
	 */
	public String getDatetime() {

		SimpleDateFormat date = new SimpleDateFormat("yyyymmdd_hhmmss");
		test.log(Status.PASS, "Cuurent Date");
		return date.format(new Date());

	}

	/**
	 * This method print the exceptions message according as per the condition satisfies. 
	 * @param e
	 * @param element
	 */
	public void exceptionPrintError(Exception exception, WebElement element ) {
		String path = snapshot((TakesScreenshot) driver);
		try {
			if (exception.toString().contains("StaleElementReferenceException" ) && element != null ){
			test.log(Status.FAIL,  ErrorType.ELEMENT_STALE + element.toString() + exception.toString() + "\nScreencast below: " + test.addScreenCaptureFromPath(path));
			}
			else if (exception.toString().contains("StaleElementReferenceException" ) && element == null) {
				test.log(Status.FAIL,  ErrorType.ELEMENT_STALE +exception.toString() + "\nScreencast below: " + test.addScreenCaptureFromPath(path));
				
			}
			else if(exception.toString().contains("NoSuchElementException")&& element != null ){
				test.log(Status.FAIL, ErrorType.ELEMENT_NOTFOUND + element.toString() + exception.toString() + "\nScreencast below: " + test.addScreenCaptureFromPath(path));
			}
			else if(exception.toString().contains("NoSuchElementException")&& element == null ){
				test.log(Status.FAIL, ErrorType.ELEMENT_NOTFOUND + exception.toString() + "\nScreencast below: " + test.addScreenCaptureFromPath(path));
			}
			else if(exception.toString().contains("WebDriverException")&& element != null){
				test.log(Status.FAIL,  element.toString() + exception.toString() + "\nScreencast below: " + test.addScreenCaptureFromPath(path));
			}
			else if(exception.toString().contains("WebDriverException")&& element == null){
				test.log(Status.FAIL,  exception.toString() + "\nScreencast below: " + test.addScreenCaptureFromPath(path));
			}
			
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	
	/**
	 * Method to Identify the type of the error occured.
	 */
	public enum ErrorType {
		ELEMENT_NOTFOUND("Element was not found, "), ELEMENT_STALE(
				"Element was no longer located in the DOM and has become stale, "), WAIT_TIMEOUT(
						"Wait timeout occured, ");
		private final String errorMsg;

		private ErrorType(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	}

	/**
	 * Close the current window in focus
	 * @throws AWTException
	 */
	public void closeCurrrentWindow() throws AWTException {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_W);
			test.log(Status.PASS, "Current Window is closed");
		} catch (Exception exception) {
			exceptionPrintError(exception,null);
			
		} 

	}

	/**
	 * This method used to handle the grid elements ele = gridWebElement
	 * List<WebElement> = Accepts the elements within the grid int i = Accepts
	 * the particular element index in the grid
	 * @param ele
	 * @param ele1
	 * @param i
	 * @throws InterruptedException
	 */
	public void gridHandlingToClickOnElement(WebElement element, List<WebElement> elements, int i) {
		try {
			untilAvailable(elements);
			WebElement table = element;
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			System.out.println(rows.size());
			List<WebElement> cols = table.findElements(By.tagName("td"));
			System.out.println(cols.size());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException exception) {
				System.out.println("Element not Visible Time Out ");
				exception.printStackTrace();
			}
			elements.get(i).click();
			test.log(Status.PASS, "Element " + elements.get(i).toString() + "is Clicked");
		} catch (Exception exception) {
			exceptionPrintError(exception,element);
			
		} 

	}

	/**
	 * Find the Broken images links in the page.
	 * @param elements
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void findBrokenImages(WebElement element) throws ClientProtocolException, IOException {
		int counter = 0;
		List<WebElement> allImg = element.findElements(By.tagName("img"));
		System.out.println("Number of image tags - " + allImg.size());

		for (int i = 0; i < allImg.size(); i++) {
			allImg = element.findElements(By.tagName("img"));
			try {
				int responseCode = Request.Get(allImg.get(i).getAttribute("src")).execute().returnResponse()
						.getStatusLine().getStatusCode();
				String code = String.valueOf(responseCode);
				String txt = allImg.get(i).getText();
				String imgLink = allImg.get(i).getAttribute("src");

				// Writing in the excel file.
				System.out.println(i + ". Text " + txt);
				System.out.println(i + ". Link " + imgLink);
				System.out.println(i + ". Status " + code);
				System.out.println();

				if (code == "404") {
					counter += 1;
					test.log(Status.PASS, "Image Link " + element.toString() + "is Broken");
				}
			} catch (Exception exception) {
				exceptionPrintError(exception,allImg.get(i));
				exceptionPrintError(exception, null);
				continue;
				
			} 
		}
		System.out.println("Total Number of Broken Images - " + counter);
	}

	/**
	 * Method to print the error message when exception occurs in list of
	 * elements and capture the screenshots.
	 * @param e
	 * @param elements
	 */
	public void elementPrintError(Object e, List<WebElement> elements) {
		String path = snapshot((TakesScreenshot) driver);
		try {
			test.log(Status.FAIL, ErrorType.ELEMENT_NOTFOUND + elements.toString() + "\n" + e + "\nScreencast below: "
					+ test.addScreenCaptureFromPath(path));
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	/**
	 * Find the Broken links in the page
	 * Supply the parent tag of the broken links as parameter.
	 * @param element
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void findBrokenLinks(WebElement element) throws ClientProtocolException, IOException {
		int counter = 0;
		List<WebElement> allLinks = element.findElements(By.tagName("a"));
		
		int responseCode=0;
		for (WebElement webElement : allLinks) {
			
			allLinks = element.findElements(By.tagName("a"));
			try {
				String href = webElement.getAttribute("href");
	            if (href.equals("#"))
	                continue;
	            if (href.startsWith("\\/")) {
	                String BaseUrl = new URL(driver.getCurrentUrl()).getHost();
	                href = BaseUrl + href;
	            
				responseCode = Request.Get(webElement.getAttribute("href")).execute().returnResponse()
						.getStatusLine().getStatusCode();
				String code = String.valueOf(responseCode);
				String txt = webElement.getText();
				String link = webElement.getAttribute("href");

				// Writing in the excel file.
				System.out.println(counter + ". Text " + txt);
				System.out.println(counter + ". Link " + link);
				TestData.set("Broken link"+counter, link);
				System.out.println(counter + ". Status " + code);
				System.out.println();
	            }

				if (responseCode == 404) {
					counter += 1;
					test.log(Status.PASS, " Link " + element.toString() + "is Broken");
				}
			} catch (Exception exception) {
				exceptionPrintError(exception,allLinks.get(counter));
				exceptionPrintError(exception, null);
				continue;
			} 
		}
		System.out.println("Total Number of Broken Items - " + counter);
	}

	/**
	 * Uploading File using Send Keys method of WebDriver
	 * @param element
	 * @param path
	 */
	public void uploadFileUsingSendkeys(WebElement element, String path) {
		try {
			try {

				Thread.sleep(4000);
			} catch (InterruptedException ieException) {
				ieException.printStackTrace();
			}
			element.sendKeys(path);
			test.log(Status.PASS, " File in  " + element.toString() + "is Uploaded");
		} catch (Exception exception) {
		
			exceptionPrintError(exception, element);
			
			
		} 

	}

	/**
	 * Uploading file using the Robot Class
	 * @param element
	 * @param path
	 * @throws AWTException
	 */
	public void uploadFileUsingRobotClass(WebElement element, String path) throws AWTException {
		try {
			element.click();
			StringSelection selection = new StringSelection(path);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, null);
			Robot robot;
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			test.log(Status.PASS, " File in  " + element.toString() + "is Uploaded");
		} catch (Exception exception) {
		
			exceptionPrintError(exception, element);	
		} 
	}

	/**
	 * Uploading File using Java Script Executor.
	 * @param element
	 * @param path
	 */
	public void uploadFileUsingJavaScriptExecutor(WebElement element, String path) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].setAttribute('value', '" + path + "')", element);
			test.log(Status.PASS, " File in  " + element.toString() + "is Uploaded");
		} catch (Exception exception) {
		
			exceptionPrintError(exception, element);
		} 

	}
	
	/**
	 * 
	 * @param strElement
	 * @return
	 * @throws Exception
	 */
	public By getLocator(String strElement) throws Exception {
		String locator = TestData.get(strElement);

		String locatorType = locator.substring(0, locator.indexOf(":"));
		String locatorValue = locator.substring(locator.indexOf(":")+1);

		if (locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return By.className(locatorValue);
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return By.linkText(locatorValue);
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(locatorValue);
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locatorValue);
		else if (locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}
	
	/**
	 * This method identifies and gets the element.
	 * @param strElement
	 * @return
	 * @throws Exception
	 */
	public WebElement getElement(String strElement) throws Exception {
		By by = getLocator(strElement);
		
		if (driver.findElements(by).size() <= 0) {
			Assert.fail(String.format("%s element is not found on the page", strElement));
		}
		
		return driver.findElement(by);
	}

}
