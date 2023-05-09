package com.nashtech.tshape.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Constants{
	
	public static final String BROWSER_CHROME = "chrome";
	public static final String BROWSER_FIREFOX = "firefox";
	
	public static final String REMOTE_LOCAL = "Local Remote";
	public static final String REMOTE_BROWSERSTACK = "BrowserStack Remote";
	public static final String LOCAL_BROWSER = "Local Browser";
	
	public static final String URL_GURU99 = "https://demo.guru99.com/v4/index.php";
	public static final String URL_AUTOMATION_PRACTICE = "http://automationpractice.com/index.php";
	public static final String URL_SHOPDEMOQA = "https://shop.demoqa.com/";
	public static final String URL_DEMOQA = "https://demoqa.com/";
	public static final String URL_LOCALREMOTE = "http://192.168.100.13:4444/wd/hub/";
	public static final String URL_API_DEMOQA = "https://demoqa.com/Account/v1";
	
	public static final String PATH_DATA = "\\src\\main\\resources\\com\\nashtech\\tshape\\data";
	public static final String PATH_DEFAULT_SCREENSHOT = "src\\main\\resources\\com\\nashtech\\tshape\\application\\screenshots\\";
	public static final String PATH_USER = "/User";
	public static final String PATH_AUTHORIZED = "/Authorized";
	public static final String PATH_GENERATE_TOKEN = "/GenerateToken";
	public static final String PATH_BOOK = "/Book";
	public static final String PATH_BOOKS = "/Books";
	
	public static final String EXPECTED_AUTOMATION_PRACTICE_WEB_TITLE = "My Store";
	public static final String EXPECTED_GURU99_BANK_MANAGER_WEB_TITLE = "Guru99 Bank Manager HomePage";
	public static final String EXPECTED_CONTACT_TITLE = "CUSTOMER SERVICE - CONTACT US";
	public static final String EXPECTED_WELCOME_TEXT = "Welcome to your account. Here you can manage all of your personal information and orders.";
	public static final String EXPECTED_PRODUCT_NAME = "Faded Short Sleeve T-shirts";

	public static final String BROWSERSTACK_USERNAME = "tunanhl_tFqYO5";
	public static final String BROWSERSTACK_ACCESSKEY = "nvxDvJefwEagZiAeyiWy";
	public static final String BROWSERSTACK_BUILDNAME = "tshaped_tuananhle_4748";
	
	public static final String RANDOM_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final String DATA_NEW_ACCOUNT_FILENAME = "new_account.xlsx";
	public static final String TIME_PATTERN = "MM-dd-yyyy_HH-mm-ss";
	public static final String TIME_NOW = new SimpleDateFormat(TIME_PATTERN).format(Calendar.getInstance().getTime());
	public static final String SCREENSHOT_FORMAT = ".png";
	public static final String TEST_SUIT_DESCRIPTION = "Le Tuan Anh SD4748_T-Shape Advance Java Assignment";
	public static final String PASSWORD = "@TuananhSD4748";
	public static final String DESTINATION = "Dalat";
	public static final String ROOM_OPTION_BREAKFAST = "Breakfast included";
	
	public static final String KEY_USERID = "userID";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_STATUS = "status";
	public static final String KEY_RESULT = "result";
	public static final String KEY_TOKEN = "token";
}
