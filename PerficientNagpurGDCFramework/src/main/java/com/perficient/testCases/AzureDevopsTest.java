package com.perficient.testCases;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.perficient.baseclasses.AzureDevops;

import com.perficient.util.TestCaseBase;
import com.perficient.util.TestData;

public class AzureDevopsTest extends TestCaseBase 
{
	
	
	@Test
	public void Azure() throws Exception
	{
		AzureDevops AzDevOPs = new AzureDevops(pageManager, database, excelReader, ftpTransfer);
		
		String url = TestData.get("url");
		
		AzDevOPs.open(url);
		customAssertion.assertTrue(pageManager.getTitle().contains(AzDevOPs.TITLE1));
				
	}
//	
//	@Test
//	public void Azure01() throws Exception
//	{	
//		AzureDevops AzDevOPs = new AzureDevops(pageManager, database, excelReader, ftpTransfer);
//		AzDevOPs.verifyWelcomeText();
//		customAssertion.assertTrue(pageManager.getText(driver.findElement(By.xpath("//h1[text()='Welcome to perficient']"))).contains(AzDevOPs.welcomeMessage));
//		
//	}
//	
//	@Test
//	public void Azure02() throws Exception
//	{
//		AzureDevops AzDevOPs = new AzureDevops(pageManager, database, excelReader, ftpTransfer);
//		AzDevOPs.verifyInformationText();
//		customAssertion.assertTrue(pageManager.getText(driver.findElement(By.xpath("//h1[text()='GO CORONA GO']"))).contains(AzDevOPs.InformationText));
//		
//	}
	
	
	

}
