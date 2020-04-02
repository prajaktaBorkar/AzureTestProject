
/**
 * @author niklesh.bahad
 *
 */
package com.perficient.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.reporter.ExtentXReporter;
import com.relevantcodes.extentreports.reporter.configuration.Protocol;



public class ComplexReportFactory {
	public static ExtentReports reporter;
	public static ExtentXReporter extentxReporter;
	public static ExtentHtmlReporter htmlReporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
	static String filePath;
	
	

	private synchronized static ExtentReports getExtentReport() {
		if (reporter == null) {
			String dt = getDatetime();
			filePath = "./test-output/ExtentReport_" + dt + ".html";
			htmlReporter = new ExtentHtmlReporter(filePath);
			htmlReporter.config().setReportName("ExtentReport_" + dt);
			htmlReporter.config().setProtocol(Protocol.HTTPS);
			reporter = new ExtentReports();
			reporter.attachReporter(htmlReporter);
			
		}
		return reporter;
	}

	public synchronized static ExtentTest getTest(String testName, String categoryName, String testDescription) {

		// if this test has already been created return
		if (!nameToTestMap.containsKey(testName)) {
			Long threadID = Thread.currentThread().getId();
			ExtentTest test = getExtentReport().createTest(testName, testDescription);
			test.assignCategory(categoryName);
			nameToTestMap.put(testName, test);
			threadToExtentTestMap.put(threadID, testName);
		}
		return nameToTestMap.get(testName);
	}

	public synchronized static ExtentTest getTest(String testName) {
		return getTest(testName, "", "");
	}

	public synchronized static ExtentTest getTest() {
		Long threadID = Thread.currentThread().getId();

		if (threadToExtentTestMap.containsKey(threadID)) {
			String testName = threadToExtentTestMap.get(threadID);
			return nameToTestMap.get(testName);
		}
		// system log, this shouldnt happen but in this crazy times if it did
		// happen log it.
		return null;
	}

	public synchronized static void closeTest(String testName) {

		if (!testName.isEmpty()) {
			ExtentTest test = getTest(testName);
		}
	}

	public synchronized static void closeTest(ExtentTest test) {
		if (test != null) {
		}
	}

	public synchronized static void closeTest() {
		ExtentTest test = getTest();
		closeTest(test);
	}

	public synchronized static void closeReport() {
		if (reporter != null) {
			reporter.flush();
			reporter.close();
		}
	}

	public static String getDatetime() {
		SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd_hh.mm.ss");
		return date.format(new Date());
	}
	
	}