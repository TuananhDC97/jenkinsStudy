package com.nashtech.tshape.reports;

import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nashtech.tshape.base.APIRequest;
import com.nashtech.tshape.base.APIResponse;

public class HtmlReporter {

	private static ExtentReports _report;
	
	private static final Boolean IS_RELATIVE_SCREENSHOT = true;
	private static ExtentTest testClass;
	private static ExtentTest testCase;
	public static String currentTest;

	public static ExtentReports createReport() throws IOException {

		if (_report == null) {
			_report = createInstance();
		}
		_report.setAnalysisStrategy(AnalysisStrategy.CLASS);
		return _report;
	}

	/**
	 * To create an reporter instance
	 * 
	 * @param fileName
	 *            The report's name
	 * @return
	 * @throws IOException 
	 */
	private static ExtentReports createInstance() throws IOException {
		HtmlReportDirectory.initReportDirectory();
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(HtmlReportDirectory.getReportFilePath());
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(false);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Automation Report");
		
		//adding CSS and Javascript for API Request Step
//		InputStream jsFile = HtmlReporter.class.getClassLoader().getResourceAsStream("config/extent-report/api-step-javascript-code.js");
//		InputStream cssFile = HtmlReporter.class.getClassLoader().getResourceAsStream("config/extent-report/api-step-stylesheet.css");
//		String jsCode = IOUtils.toString(jsFile, StandardCharsets.UTF_8);
//		String cssCode = IOUtils.toString(cssFile, StandardCharsets.UTF_8);
//		htmlReporter.config().setJS(jsCode);
//		htmlReporter.config().setCSS(cssCode);
		//end
		
		htmlReporter.setAppendExisting(true);

		ExtentReports report = new ExtentReports();
		report.attachReporter(htmlReporter);
		report.setSystemInfo("Application", "Automation Test");
		System.out.println("Report path:" + HtmlReportDirectory.getReportFilePath());
		return report;
	}

	/**
	 * Write report
	 */
	public static void flush() {
		if (_report != null) {
			_report.flush();
			_report = null;
		}
	}
	

	/**
	 * To Create an ExtentTest session
	 * 
	 * @param strTestMethodName
	 *            The method name
	 * @param strTestMethodDesc
	 *            The method description
	 * @return ExtentTest session
	 */
	public static ExtentTest createTest(String strTestMethodName, String strTestMethodDesc) {
		testClass = _report.createTest(strTestMethodName, strTestMethodDesc);
		return testClass;
	}

	/**
	 * To Create an ExtentTest session
	 * 
	 * @param strTestMethodName
	 *            The method name
	 * @param strTestMethodDesc
	 *            The method description
	 * @return ExtentTest session
	 */
	public static ExtentTest createTest(String strTestClassName) {

		testClass = _report.createTest(strTestClassName);
		return testClass;
	}

	/**
	 * To Create a node of ExtentTest session
	 * 
	 * @param strTestMethodName
	 *            The method name
	 * @param strTestMethodDesc
	 *            The method description
	 * @param strNodeName
	 *            The node name
	 * @return ExtentTest session
	 */
	public static ExtentTest createNode(String strClassName, String strTestMethodName,
			String strTestMethodDesc) {
		testCase = testClass.createNode(strTestMethodName);
		return testCase;
	}

	/**
	 * To get the ExtentTest's parent session to write report
	 * 
	 * @return ExtentTest session
	 */

	
	/**
	 * To get the ExtentTest session to write report
	 * 
	 * @return ExtentTest session
	 */
	public static ExtentTest getTest() {
		if(testCase == null) {
			return testClass;
		}
		return testCase;
	}
	

	/**
	 * To write a passed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 */
	public static void pass(String strDescription) {
		getTest().pass(strDescription);
	}
	
	/**
	 * To write a passed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 */
	public static void pass(Markup m) {
		getTest().pass(m);
	}
	
	/**
	 * To write a passed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 */
	public static void warning(String strDescription) {
		getTest().warning(strDescription);
	}
	
	/**
	 * To write a passed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 */
	public static void info(String strDescription) {
		getTest().info(strDescription);
	}

	/**
	 * To write a passed step to report with screenshot
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void pass(String strDescription, String strScreenshotPath) throws Exception {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().pass(strDescription);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().pass(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}

	}

	/**
	 * To write a failed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void fail(String strDescription)  {
		getTest().fail(strDescription);
	}
	
	/**
	 * To write a failed step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void fail(Markup m)  {
		getTest().fail(m);
	}
	
	public static void fail(String strDescription,Throwable e)  {
		getTest().fail(strDescription).fail(e);
	}

	/**
	 * To write a failed step to report with screenshot
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void fail(String strDescription, String strScreenshotPath) throws IOException  {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().fail(strDescription);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().fail(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}
	}

	/**
	 * To write a failed step to report with screenshot and throwable stacktrace
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param e
	 *            Throwable object
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void fail(String strDescription, Throwable e, String strScreenshotPath) throws IOException  {

		if (strScreenshotPath.equalsIgnoreCase("")) {
			getTest().fail(strDescription).fail(e);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().fail(strDescription).fail(e).addScreenCaptureFromPath(strScreenshotPath);
		}
	}
	
	/**
	 * To write a skipped step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param e
	 *            Throwable object
	 */
	public static void skip(String strDescription) throws IOException  {
		getTest().skip(strDescription);
	}
	
	/**
	 * To write a skipped step to report
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param e
	 *            Throwable object
	 */
	public static void skip(String strDescription, Throwable e) throws IOException  {
		getTest().skip(strDescription).fail(e);
	}


	/**
	 * To write a skipped step to report with screenshot
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws Exception
	 * @throws IOException
	 *             If the screenshot doesn't exist
	 */
	public static void skip(String strDescription, String strScreenshotPath) throws IOException  {

		if (strDescription.equalsIgnoreCase("")) {
			getTest().skip(strDescription);
		} else {
			strScreenshotPath = getScreenshotPath(strScreenshotPath);
			getTest().skip(strDescription).addScreenCaptureFromPath(strScreenshotPath);
		}
	}

	/**
	 * To write a skipped step to report with screenshot and throwable
	 * stacktrace
	 * 
	 * @param strDescription
	 *            The Step's description
	 * @param e
	 *            Throwable object
	 * @param strScreenshotPath
	 *            The screenshot's path
	 * @throws IOException 
	 * @throws Exception
	 */
	public static void skip(String strDescription, Throwable e, String strScreenshotPath) throws IOException {

			if (strDescription.equalsIgnoreCase("")) {
				getTest().skip(strDescription).skip(e);
			} else {
				strScreenshotPath = getScreenshotPath(strScreenshotPath);
				getTest().skip(strDescription).skip(e).addScreenCaptureFromPath(strScreenshotPath);
			}

	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void label(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.BLUE));

	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void labelFailed(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.RED));

	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void labelSkiped(String strDescription) {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.AMBER));
			
	}

	/**
	 * To label a key step into the report
	 * 
	 * @param strDescription
	 *            The step's description
	 * @throws Exception
	 */
	public static void labelWarning(String strDescription)  {

			getTest().info(MarkupHelper.createLabel(strDescription, ExtentColor.ORANGE));

	}

	/**
	 * To write a failed step to report
	 * 
	 * @throws Exception
	 */
	public static void description(String data) throws Exception {
			getTest().info(MarkupHelper.createCodeBlock(data));
	}
	
	private static String getScreenshotPath(String strAbsolutePath) {
		if(IS_RELATIVE_SCREENSHOT) {
			return new File(HtmlReportDirectory.getReportFolder()).toPath().relativize(new File(strAbsolutePath).toPath()).toString();
		}
		else {
			return "file:///" + strAbsolutePath;
		}
	}

	public static void createAPIRequestBlock(APIRequest request, APIResponse response) {
		getTest().info(MarkupHelperPrime.createAPIRequestBlock(request, response));
	}
}
