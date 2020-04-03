package com.perficient.baseclasses;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.perficient.util.CustomAssertion;
import com.perficient.util.DatabaseUtil;
import com.perficient.util.ExcelReader;
import com.perficient.util.FTPTransfer;
import com.perficient.util.PageManager;
import com.perficient.util.PageObject;

public class AzureDevops extends PageObject

{
	public String TITLE1 = "Welcome to Jenkins";
	public String welcomeMessage = "Welcome to perficient Failed";
	public String InformationText = "GO CORONA GO";
	
	
	@FindBy(xpath = "//h1[text()='GO CORONA GO']")
	public WebElement informationText;
	
	@FindBy(xpath = "//h1[text()='Welcome to perficient']")
	public WebElement welcomeText;

	public AzureDevops(PageManager pm, DatabaseUtil db, ExcelReader xl, FTPTransfer ftp) {
		super(pm, db, xl, ftp);
		// TODO Auto-generated constructor stub
	}
	
	public void open(String url) 
	{
		pageManager.maximizeBrowser();
		pageManager.navigate(url);	
	}
	
	public void verifyWelcomeText ()
	{
		String welcomeMessage = welcomeText.getText();
		System.out.println(welcomeMessage);	
	}
	
	public void verifyInformationText() 
	{				
		String informationMessage = informationText.getText();
		System.out.println(informationMessage);
	}
	
	
}
