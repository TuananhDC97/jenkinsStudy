package com.nashtech.tshape.tests.FinalAssessment;

import com.nashtech.tshape.base.*;
import com.nashtech.tshape.pages.agoda.*;
import org.testng.annotations.Test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Agoda_ID02 extends BaseSetup{
	WebDriver driver;
	HomePage homePage;
	SearchResultsPage searchPage;
	HotelPage hotelPage;
	LoginPage loginPage;
	String testSuitName = this.getClass().getSimpleName();
	
	@BeforeClass
	public void initializeTest() throws IOException {
		super.initializeTest(testSuitName, Constants.TEST_SUIT_DESCRIPTION, testSuitName);
		driver = getDriver();
	}
	
	@Test
	public void testCase02() throws Exception{
		String testName = new Throwable().getStackTrace()[0].getMethodName();
		homePage = new HomePage(testName, driver);
		searchPage = new SearchResultsPage(testName, driver);
		hotelPage = new HotelPage(testName, driver);
		loginPage = new LoginPage(testName, driver);
		
		homePage.closeCouponPopup();
		homePage.inputDestination();
		homePage.selectDateRange();
		homePage.selectOccupancy(2, 4);
		homePage.clickSearchButton();
		searchPage.verifyLocationOfResults();
		searchPage.filterHotelWithSwimmingPool();
		searchPage.clickOnFirstHotel();
		hotelPage.verifyHotelHaveSwimmingPool();
		hotelPage.addHotelFavourite();
		loginPage.login();
		hotelPage.addHotelFavourite();
		hotelPage.verifyAddFavouriteHotel();
	}
	
	@AfterClass
	public void tearDown() {
		super.tearDown();
	}
}
