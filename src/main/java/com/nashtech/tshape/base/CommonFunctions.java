package com.nashtech.tshape.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFunctions {
	WebDriver driver;
	
	public CommonFunctions(WebDriver driver) {
		this.driver = driver;
	}
	
	public String generateRandomEmail = String.format("%s@%s", String.format("%nashtshape%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000), "gmail.com");
	public static String generateRandomUsername = "tuananhSD4748" + UUID.randomUUID().toString().substring(0, 5);
	
	public void goToWebsite(String url) throws Exception{
		try {
			driver.get(url);
		}
		catch(Exception ex){
			//takeScreenShot();
		}
	}

	public void waitUntilElementClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitUntilElementPresent(By element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}
	
	public void waitUntilElementVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void scrollToElement(WebElement element) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollTo(arguments[0], arguments[1])", element);
		}
		finally{
			Actions actions = new Actions (driver);
			actions.moveToElement(element).perform();
		}
	}
	
	public void acceptAlert(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
	}
	
	public void hoverMouse(WebElement element) {
		waitUntilElementVisible(element);
		Actions actions = new Actions (driver);
		actions.moveToElement(element).perform();
	}
	
	public void takeScreenShot(String testName) throws Exception{
		try
        {
			File scrShotFile =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(Constants.PATH_DEFAULT_SCREENSHOT + testName + "_" + Constants.TIME_NOW + Constants.SCREENSHOT_FORMAT);
			FileUtils.copyFile(scrShotFile, destFile);
			System.out.println("Screenshot is captured and stored in " + Constants.PATH_DEFAULT_SCREENSHOT);
        }
        catch (Exception ex)
        {
            System.out.println("Cannot take screenshot");
            throw ex;
        }
	}
	
	public void failStepInfo(String stepName, String testName) throws Exception {
		takeScreenShot(testName);
		System.out.println("Failed in step: " + stepName.substring(0,1).toUpperCase() + stepName.substring(1));
	}
	
	public void clickWebElement(WebElement element) {
		waitUntilElementVisible(element);
		scrollToElement(element);
		element.click();
	}
	
	public void inputText(WebElement element, String text) {
		scrollToElement(element);
		element.sendKeys(text);
	}
	
	public void getText(WebElement element) {
		element.getText();
	}
	
	public void closeAdsByGoogle(WebElement element1,@Nullable WebElement element2) {
		//Close Ads by Google
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("google_ads_iframe_/24132379/INTERSTITIAL_DemoGuru99_0")));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("ad_iframe")));
    	try {
    		element1.click();
    		element2.click();
    	}
    	catch(Exception e) {
    	}
    	driver.switchTo().defaultContent();
	}
	
	public boolean isExist(WebElement element){
		try {
			if(element != null) {
				return element.isDisplayed();
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
		finally {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
	}
	
	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void verifyWebTitle(String expectedTitle) {
		if(driver.getTitle() != null && driver.getTitle().contains(expectedTitle)){
		  System.out.println("Web title is " + expectedTitle);
		}
		else{
		  System.out.println("Web title is not " + expectedTitle);
		}
	}
	
	public void goBackWebsite() {
		driver.navigate().back();
	}
	
	public void goForwardWebsite() {
		driver.navigate().forward();
	}
	
	public void toWebsite(String url) {
		driver.navigate().to(url);
	}
	
	public void printMessageWebCondition() {
	    JavascriptExecutor jse = (JavascriptExecutor)driver;
		if(jse.executeScript("return document.readyState").equals("complete")) {
			System.out.println("Web opened successfully");
		}
		else {
			System.out.println("Web opened failure");
		}
	}
	
	public void switchTab(int tab, WebDriver driver) {
		    ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		    driver.switchTo().window(tabs.get(tab));
	}
	
	public void closeTab(WebDriver driver) {
	    driver.close();
	}
	
	public void refreshWebsite(WebDriver driver) {
	    driver.navigate().refresh();
	}
	
	public void pressEnter(WebElement element, WebDriver driver) {
	    element.sendKeys(Keys.ENTER);
	}
	
	public void waitPageLoadComplete(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver , 10);
		wait.until(ExpectedConditions.javaScriptThrowsNoExceptions("return document.readyState"));
	}
	
	public String randomString(int len){
		   StringBuilder sb = new StringBuilder(len);
		   SecureRandom rnd = new SecureRandom();
		   for(int i = 0; i < len; i++)
		      sb.append(Constants.RANDOM_CHARACTERS.charAt(rnd.nextInt(Constants.RANDOM_CHARACTERS.length())));
		   return sb.toString();
	}
	
	public int inputRandomNumbers(WebElement element,int len){
			Random rand = new Random();
			int n = rand.nextInt(len);
			n += 1;
		   return n;
	}
	
	public String dataAccess(String fileName, int sheetNumber, int rowNumber , int cellNumber) throws IOException{

		File file = new File(Constants.PATH_DATA + "\\" + fileName);
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		XSSFSheet sheet=wb.getSheetAt(sheetNumber);
		DataFormatter formatter = new DataFormatter();
		XSSFRow row=sheet.getRow(rowNumber);
		String value = formatter.formatCellValue(row.getCell(cellNumber)).trim();
		if (value.length() != 0)
        {
            for (int i = 0; i > 0; i++)
            {
                row = sheet.getRow(rowNumber + i);
                value = row.getCell(cellNumber).getStringCellValue().trim();
                if (value.length() == 0)
                {
                    value = row.getCell(cellNumber - 1).getStringCellValue().trim();
                    break;
                }
            }
        }
		return value;
	}
	
	public void selectByText(WebElement element, String text) {
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(text);
	}
	
	public void hooverElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
	}
}
