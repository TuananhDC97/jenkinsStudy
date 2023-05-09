package com.nashtech.tshape.pages.agoda;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nashtech.tshape.base.*;

public class LoginPage {
	CommonFunctions commonFunctions;
	WebDriver driver;
	String testName;
	
	@FindBy(id = "email")
	WebElement txtEmail;
	
	@FindBy(id = "password")
	WebElement txtPassword;
	
	@FindBy(xpath = "//button[@data-cy='signin-button']")
	WebElement btnSignIn;
	
	
	public LoginPage(String testName, WebDriver driver) {
		this.driver = driver;
		this.commonFunctions = new CommonFunctions(driver);
		this.testName = testName;
		PageFactory.initElements(driver, this);
	}
	
	public void login() {
		commonFunctions.waitPageLoadComplete(driver);
		driver.switchTo().frame(0);
		commonFunctions.inputText(txtEmail, "tuananhdc.forwork@gmail.com");
		commonFunctions.inputText(txtPassword, "Tuananh123");
		commonFunctions.clickWebElement(btnSignIn);
		driver.switchTo().defaultContent();
	}
}
