package com.nashtech.tshape.pages.agoda;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.nashtech.tshape.base.*;

public class SearchResultsPage {
	CommonFunctions commonFunctions;
	WebDriver driver;
	String testName;
	
	@FindBy(xpath = "//div[@data-selenium='area-city']/span/span")
	List<WebElement> hotelsLocation;
	
	@FindBy(xpath = "//span[@aria-label = 'Breakfast included']")
	WebElement chkBreakfastFilter;
	
	@FindBy(xpath = "//span[@aria-label = 'Swimming pool']")
	WebElement chkSwimmingPool;
	
	@FindBy(xpath = "//li[@data-selenium = 'hotel-item']")
	List<WebElement> lstHotels;
	
	public SearchResultsPage(String testName, WebDriver driver) {
		this.driver = driver;
		this.commonFunctions = new CommonFunctions(driver);
		this.testName = testName;
		PageFactory.initElements(driver, this);
	}
	
	public void verifyLocationOfResults(){
		commonFunctions.waitPageLoadComplete(driver);
		for(int i = 0; i < 5; i++) {
			commonFunctions.scrollToElement(lstHotels.get(i));
			Assert.assertTrue(hotelsLocation.get(i).getText().contains(Constants.DESTINATION));
		}
	}
	
	public void filterHotelWithBreakfast() {
		commonFunctions.clickWebElement(chkBreakfastFilter);
	}
	
	public void clickOnFirstHotel() {
		commonFunctions.clickWebElement(lstHotels.get(0));
	}
	
	public void filterHotelWithSwimmingPool() {
		commonFunctions.clickWebElement(chkSwimmingPool);
	}
}
