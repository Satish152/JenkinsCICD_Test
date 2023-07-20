package cicd.testSet;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import cicd.base.BrowserBase;
import cicd.base.Reports;
import cicd.utilities.CommonFunctions;
import cicd.utilities.ExcelRead;
import cicd.utilities.Logging;
import cicd.utilities.PropertyReader;

public class Login {

	public RemoteWebDriver driver;
	public Reports report;
	public ExcelRead excelRead;
	public PropertyReader propertyReader;
	public Logger logger;
	public CommonFunctions commonFunctions;
	
	public Login(RemoteWebDriver driver,Reports report){
		this.driver=driver;
		this.report=report;
		logger=Logging.logManager(BrowserBase.testCaseDesc);
	    commonFunctions=new CommonFunctions(driver, report);
	    excelRead=new ExcelRead();
	}
	
	public void login_field_verification(){
		driver.get("https://google.com");
		logger.info("URL Loaded in browser https://google.com");
		if(driver.getTitle().contains("Google"))
			report.reportStepStatus("Pass", "URL loaded successfully");
		else
			report.reportStepStatus("Fail", "URL not loaded successfully");
	}
	
	public void verify_forgot_password(){
		driver.get("https://google.com");
		logger.info("Executed Test2");
		if(driver.getTitle().contains("Google"))
			report.reportStepStatus("Pass", "Executed Test2");
		else
			report.reportStepStatus("Fail", "Executed Test2");
	}
}
