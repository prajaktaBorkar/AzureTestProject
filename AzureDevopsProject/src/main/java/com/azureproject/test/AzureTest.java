package com.azureproject.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



public class AzureTest {
	
	WebDriver driver;
 
	
	@BeforeClass
	public void openBrowser() throws Exception {
		
		driver = new ChromeDriver();

		
//		 Change in URL
		 
		driver.get("https://my-web-app1.azurewebsites.net/mvn-hello-world/");
		
		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

//	Verification
	
	@Test
	public void webelement() {
		/*
		 * CHange in h1 tag text
		 */
		String text = driver.findElement(By.xpath("//h1[text()='GO CORONA GO']")).getText();

		if (text.equalsIgnoreCase("GO CORONA GO")) {
			System.out.println(text);
		} else {
			System.out.println("h1 Tag Text is different");
		}

	}

	
//	Close Browser
	
	@AfterClass
	public void tearDown() {
	
		driver.quit();
	
	}

}
