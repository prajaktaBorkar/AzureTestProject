package com.azureproject.test;




import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



public class AzureTest {
	
	WebDriver driver;
 
	
	@BeforeClass
	public void openBrowser() throws Exception {
		
		driver = new ChromeDriver();

		
//		 Change in URL
		 
		driver.get("http://3.22.169.214:8080/");
		
		driver.manage().window().maximize();

		driver.manage().deleteAllCookies();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

//	Verification
	
	@Test
	public void verifyTitle()
	{
		
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Welcome to Jenkins");

	}
	
	@Test
	public void verifyWelcomeText ()
	{
		/*
		 * CHange in h1 tag text
		 */
		String text = driver.findElement(By.xpath("//h1[text()='Welcome to perficient']")).getText();
					 


		if (text.equalsIgnoreCase("Welcome to perficient")) {
			System.out.println(text);
		} else {
			System.out.println("Welcome Text is different");
		}
	}
	
	@Test
	public void verifyInformationText() {
		/*
		 * CHange in h1 tag text
		 */
		String text = driver.findElement(By.xpath("//h1[text()='GO CORONA GO']")).getText();
		

		if (text.equalsIgnoreCase("GO CORONA GO")) {
			System.out.println(text);
		} else {
			System.out.println("Information Text is different");
		}

	}

	
//	Close Browser
	
	@AfterClass
	public void tearDown() {
	
		driver.quit();
	
	}

}
