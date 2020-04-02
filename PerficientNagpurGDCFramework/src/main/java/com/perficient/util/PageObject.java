package com.perficient.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.support.PageFactory;

public class PageObject {

	protected Log log = LogFactory.getLog(this.getClass());
	protected PageManager pageManager;
	public DatabaseUtil database;
	public ExcelReader excelReader;
	public FTPTransfer ftpTransfer ;
	public SoapApiUtil soapApi;
	


	public PageObject(PageManager pm, DatabaseUtil db, ExcelReader xl ,FTPTransfer ftp) {
		pageManager = pm;
		database = db;
		excelReader=xl;
		ftpTransfer = ftp;
		soapApi = new SoapApiUtil();
		PageFactory.initElements(pageManager.getDriver(), this);
	}
	
}



