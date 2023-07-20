package cicd.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import cicd.base.Reports;

public class CommonFunctions {

	public RemoteWebDriver driver;
	public Reports report;
	public JavascriptExecutor js;
	public CommonFunctions(RemoteWebDriver driver,Reports report){
		this.driver=driver;
		this.report=report;
	}
	
	public CommonFunctions(RemoteWebDriver driver){
		this.driver=driver;
	}
	
	
	
	public File captureScreenShot() throws IOException{
		TakesScreenshot ts= ((TakesScreenshot)driver);
		File f=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File(""));
		return f;
	}
}
