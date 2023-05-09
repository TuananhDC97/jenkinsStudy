package com.nashtech.tshape.pages.agoda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.nashtech.tshape.base.*;

public class HomePage {
	CommonFunctions commonFunctions;
	WebDriver driver;
	String testName;
	static String checkInDate;
	static String checkOutDate;
	static String numberOfRooms;
	static String numberOfAdults;
	
	@FindBy(xpath = "//button[contains(text(), 'No thanks')]")
	WebElement btnNoThanks;
	
	@FindBy(id = "textInput")
	WebElement txtDestination;
	
	@FindBy(xpath = "//li[contains(@class, 'Suggestion__categoryName')]")
	 List<WebElement> ddlSugestions;
	
	@FindBy(id = "occupancy-box")
	WebElement btnOccupancy;
	
	@FindBy(xpath = "//button[@data-selenium='searchButton']")
	WebElement btnSearch;
	
	@FindBy(xpath = "//div[@aria-label = 'Family travelers']")
	WebElement btnFamilyTravelers;
	
	@FindBy(xpath = "//div[@data-selenium='occupancyRooms']/span[@data-selenium='plus'] | //div[@data-selenium='occupancyRooms']/div/button | //div[@data-selenium='plus' and @data-element-name = 'occupancy-selector-panel-rooms']")
	WebElement btnPlusRoom;

	@FindBy(xpath = "//div[@data-selenium='occupancyAdults']/span[@data-selenium='plus'] | //div[@data-selenium='occupancyAdults']/button[@data-selenium = 'plus'] | //div[@data-selenium='plus' and @data-element-name = 'occupancy-selector-panel-adult']")
	WebElement btnPlusAdults;

	@FindBy(xpath = "//div[@data-selenium='occupancyRooms']/div/span[@data-selenium='desktop-occ-room-value'] | //div[@data-selenium='desktop-occ-room-value']/h3")
	WebElement txtNumberOfRoom;

	@FindBy(xpath = "//div[@data-selenium='occupancyAdults']/div/span[@data-selenium='desktop-occ-adult-value'] | //div[@data-selenium='desktop-occ-adult-value']/h3")
	WebElement txtNumberOfAdults;
	
	public HomePage(String testName, WebDriver driver) {
		this.driver = driver;
		this.commonFunctions = new CommonFunctions(driver);
		this.testName = testName;
		PageFactory.initElements(driver, this);
	}
	
	public void closeCouponPopup(){
		commonFunctions.waitPageLoadComplete(driver);
		commonFunctions.clickWebElement(btnNoThanks);
	}
	
	public void inputDestination() {
		commonFunctions.inputText(txtDestination, Constants.DESTINATION);
		commonFunctions.clickWebElement(ddlSugestions.get(0));
	}
	
	public void selectDateRange(){
		Date dt = new Date();
		Calendar currentTime = Calendar.getInstance(); 
		currentTime.setTime(dt); 
		currentTime.add(Calendar.DATE, 1);
		dt = currentTime.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd");  
		String fromDate = dateFormat.format(dt);
		
		currentTime.add(Calendar.DATE, 3);
		dt = currentTime.getTime();
		String toDate = dateFormat.format(dt);
		
		String dateLocator = "//div[contains(@class, 'PriceSurgePicker-Day__circle')]/span[.= '%s']";

		WebElement fromDateElement = driver.findElement(By.xpath(String.format(dateLocator, fromDate)));
		
		commonFunctions.clickWebElement(fromDateElement);
		
		WebElement toDateElement = driver.findElement(By.xpath(String.format(dateLocator, toDate)));

		commonFunctions.clickWebElement(toDateElement);
		
		//Get date value into variable
		Date date = new Date();
		Calendar currentDate = Calendar.getInstance(); 
		currentDate.setTime(date); 
		currentDate.add(Calendar.DATE, 1);
		date = currentDate.getTime();
		DateFormat dateFm = new SimpleDateFormat("dd MMM yyyy");   
		this.checkInDate = dateFm.format(date);
		
		currentDate.add(Calendar.DATE, 3);
		date = currentDate.getTime();
		this.checkOutDate = dateFm.format(date);
	}
	
	public void selectOccupancy(int numberOfRoom, int numberOfAdults) {
		if(commonFunctions.isExist(btnFamilyTravelers)) {
			commonFunctions.clickWebElement(btnFamilyTravelers);
			//click to disable popup message which hide the plus button
			commonFunctions.clickWebElement(btnFamilyTravelers);
		}
		int actualRooms = Integer.parseInt(txtNumberOfRoom.getText());
		int actualAdults = Integer.parseInt(txtNumberOfAdults.getText());
		while(actualRooms != numberOfRoom) {
			commonFunctions.clickWebElement(btnPlusRoom);
			actualRooms += 1;
			if(actualRooms == numberOfRoom) {
					break;
				}
			}
				
		while(actualAdults != numberOfAdults) {
			commonFunctions.clickWebElement(btnPlusAdults);
			actualAdults += 1;
			if(actualAdults == numberOfAdults) {
					break;
				}
			}
		commonFunctions.clickWebElement(btnOccupancy);
		this.numberOfRooms = Integer.toString(numberOfRoom);
		this.numberOfAdults = Integer.toString(numberOfAdults);
	}
	
	public void clickSearchButton() {
		commonFunctions.clickWebElement(btnSearch);
	}
}
