package com.nashtech.tshape.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Method;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.nashtech.tshape.reports.HtmlReporter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseSetup {
	CommonFunctions commonFunctions;
	public WebDriver Driver;
	public int statusCode;
	public WebDriver getDriver() {
		return Driver;
	}
	
	@BeforeClass
	public WebDriver initializeTestBaseSetup() throws FileNotFoundException, IOException  {	
		System.out.println("Launching " + ConfigLoader.getEnv("browser") + " browser...");
		
		switch(ConfigLoader.getEnv("isRemote")) {
		case Constants.REMOTE_LOCAL:
			DesiredCapabilities capabilitiesLocal = new DesiredCapabilities();
			capabilitiesLocal.setCapability("browserName", ConfigLoader.getEnv("browser"));
			capabilitiesLocal.setCapability("platform", "windows");
			WebDriver driverLocal = new RemoteWebDriver(new URL(ConfigLoader.getEnv("url")), capabilitiesLocal);
			driverLocal.manage().window().maximize();
			driverLocal.get(ConfigLoader.getEnv("url"));
			this.Driver = driverLocal;
			break;
		case Constants.REMOTE_BROWSERSTACK:
			DesiredCapabilities capabilitiesBrowserStack = new DesiredCapabilities();
			capabilitiesBrowserStack.setCapability("browserName", ConfigLoader.getEnv("browser"));
			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
			browserstackOptions.put("os", "Windows");
			browserstackOptions.put("osVersion", "10");
			browserstackOptions.put("sessionName", "BStack Build Name: " + Constants.BROWSERSTACK_BUILDNAME);
			browserstackOptions.put("seleniumVersion", "4.0.0");
			capabilitiesBrowserStack.setCapability("bstack:options", browserstackOptions);

			WebDriver driverBrowserStack = new RemoteWebDriver(new URL("https://" + Constants.BROWSERSTACK_USERNAME + ":" + Constants.BROWSERSTACK_ACCESSKEY + "@hub.browserstack.com/wd/hub"), capabilitiesBrowserStack);
			driverBrowserStack.manage().window().maximize();
			driverBrowserStack.get(ConfigLoader.getEnv("url"));
			this.Driver = driverBrowserStack;
			break;
		case Constants.LOCAL_BROWSER:
			if(ConfigLoader.getEnv("browser").equals(Constants.BROWSER_CHROME)) {
				WebDriverManager.chromedriver().setup();
				ChromeDriver driver = new ChromeDriver();
				
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.get(ConfigLoader.getEnv("url"));
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				this.Driver = driver;
			}
			else {
				WebDriverManager.firefoxdriver().setup();
				FirefoxDriver driver = new FirefoxDriver();
				
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.get(ConfigLoader.getEnv("url"));
				driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				this.Driver = driver;
			}
		}
		
		return this.Driver;
	}
	
	@BeforeSuite
	public void beforeSuite() throws IOException {
		HtmlReporter.createReport();
	}
	
	@BeforeClass
	public void createReports(String testSuitName, String testSuitDescription, String testCaseName) throws IOException {
		HtmlReporter.createTest(testSuitName, testSuitDescription);
	}
	
	public void initializeTest(String testSuitName, String testSuitDescription, String testCaseName) throws IOException {
		createReports(testSuitName, testSuitDescription, testCaseName);
		initializeTestBaseSetup();
	}
	
	@BeforeMethod
	public void beforeMethod(Method method, Object[] listParameter) throws MalformedURLException, Exception {
		String testParameters = "";
		for(Object param : listParameter) {
			testParameters += " - [" + param.toString() + "]";
		}
		String methodName = method.getName() + testParameters;
		HtmlReporter.createNode(this.getClass().getSimpleName(), methodName, "");
	}
	
	@AfterClass
	public void tearDown(){
		Driver.quit();
		HtmlReporter.flush();
	}
}
