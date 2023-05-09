package com.nashtech.tshape.pages.agoda;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.nashtech.tshape.base.*;

public class HotelPage {
	CommonFunctions commonFunctions;
	WebDriver driver;
	String testName;
	
	@FindBy(className = "HeaderCerebrum__Name")
	WebElement txtHotelName;
	
	@FindBy(xpath = "//span[@data-selenium = 'hotel-address-map']")
	WebElement txtHotelLocation;
	
	@FindBy(xpath = "//i[contains(@class, 'ficon ficon-pool')]")
	WebElement txtHotelSwimmingPool;
	
	@FindBy(xpath = "//i[@data-selenium = 'favorite-heart']")
	WebElement btnAddToFavourite;
	
	@FindBy(xpath = "//i[@data-selenium = 'favorite-heart' and contains(@class, 'is-enabled')]")
	WebElement btnHotelAlreadyFavourited;
	
	@FindBy(xpath = "//span[.='Got it']")
	WebElement btnGotIt;
	
	@FindBy(xpath = "//p[.='Added to saved list']")
	WebElement txtAddSavedListSuccess;
	
	@FindBy(xpath = "//div[@data-selenium = 'checkInText']")
	WebElement txtCheckInDate;
	
	@FindBy(xpath = "//div[@data-selenium = 'checkOutText']")
	WebElement txtCheckOutDate;
	
	@FindBy(xpath = "//div[@data-selenium='roomValue']")
	WebElement txtNumberOfRooms;
	
	@FindBy(xpath = "//span[@data-selenium='adultValue']")
	WebElement txtNumberOfAdults;
	
	@FindBy(xpath = "//div[@data-selenium = 'RoomGridFilter-filter']/div/p/div/div")
	List<WebElement> btnRoomOptions;
	
	public HotelPage(String testName, WebDriver driver) {
		this.driver = driver;
		this.commonFunctions = new CommonFunctions(driver);
		this.testName = testName;
		PageFactory.initElements(driver, this);
	}
	
	public void verifyHotelHaveBreakfast() {
		commonFunctions.switchTab(1, driver);
		commonFunctions.waitPageLoadComplete(driver);
		System.out.println(txtHotelName.getText());
		Assert.assertTrue(txtHotelLocation.getText().contains(Constants.DESTINATION));
		System.out.println(txtHotelLocation.getText());
		for(int i = 0; i < btnRoomOptions.size(); i++) {
			if(btnRoomOptions.get(i).getText().equals(Constants.ROOM_OPTION_BREAKFAST)) {
				break;
			}
		}
	}
	
	public void verifyHotelHaveSwimmingPool() {
		commonFunctions.switchTab(1, driver);
		commonFunctions.waitPageLoadComplete(driver);
		if(commonFunctions.isExist(btnGotIt)) {
			commonFunctions.clickWebElement(btnGotIt);
		}
		System.out.println(txtHotelName.getText());
		Assert.assertTrue(txtHotelLocation.getText().contains(Constants.DESTINATION));
		System.out.println(txtHotelLocation.getText());
		commonFunctions.scrollToElement(txtHotelSwimmingPool);
		Assert.assertTrue(txtHotelSwimmingPool.isDisplayed());
	}
	
	public void addHotelFavourite() {
		commonFunctions.waitPageLoadComplete(driver);
		commonFunctions.sleep(5000);
		if(commonFunctions.isExist(btnHotelAlreadyFavourited)) {
			if(commonFunctions.isExist(btnGotIt)) {
				commonFunctions.clickWebElement(btnGotIt);
			}
			commonFunctions.clickWebElement(btnAddToFavourite);
		}
		commonFunctions.clickWebElement(btnAddToFavourite);
	}
	
	public void verifyAddFavouriteHotel() {
		Assert.assertTrue(commonFunctions.isExist(btnHotelAlreadyFavourited));
		//Verify date, number of rooms and adults
		Assert.assertEquals(txtCheckInDate.getText(), HomePage.checkInDate);
		Assert.assertEquals(txtCheckOutDate.getText(), HomePage.checkOutDate);
		Assert.assertTrue(txtNumberOfRooms.getText().contains(HomePage.numberOfRooms));
		Assert.assertTrue(txtNumberOfAdults.getText().contains(HomePage.numberOfAdults));
	}
}
