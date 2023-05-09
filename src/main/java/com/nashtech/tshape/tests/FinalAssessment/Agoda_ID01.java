package com.nashtech.tshape.tests.FinalAssessment;

import com.nashtech.tshape.base.*;
import com.nashtech.tshape.pages.agoda.*;
import org.testng.annotations.Test;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Agoda_ID01 extends BaseSetup{
	WebDriver driver;
	HomePage homePage;
	SearchResultsPage searchPage;
	HotelPage hotelPage;
	String testSuitName = this.getClass().getSimpleName();
	
	@BeforeClass
	public void initializeTest() throws IOException {
		super.initializeTest(testSuitName, Constants.TEST_SUIT_DESCRIPTION, testSuitName);
		driver = getDriver();
	}
	
	@Test
	public void testCase01() throws Exception{
		String testName = new Throwable().getStackTrace()[0].getMethodName();
		homePage = new HomePage(testName, driver);
		searchPage = new SearchResultsPage(testName, driver);
		hotelPage = new HotelPage(testName, driver);
		
		homePage.closeCouponPopup();
		homePage.inputDestination();
		homePage.selectDateRange();
		homePage.selectOccupancy(1, 2);
		homePage.clickSearchButton();
		searchPage.verifyLocationOfResults();
		searchPage.filterHotelWithBreakfast();
		searchPage.clickOnFirstHotel();
		hotelPage.verifyHotelHaveBreakfast();
	}
	
	@AfterClass
	public void tearDown() {
		super.tearDown();
	}
}
