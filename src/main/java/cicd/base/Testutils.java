package cicd.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Testutils {

	public static ThreadLocal<RemoteWebDriver> driver=new ThreadLocal<RemoteWebDriver>();
	public static ThreadLocal<ExtentReports> reporter=new ThreadLocal<ExtentReports>();
	public static ThreadLocal<ExtentTest> extentTest=new ThreadLocal<ExtentTest>();
	public static Properties prop;
	public static DesiredCapabilities caps;
	
	public Testutils(){
		String file=System.getProperty("user.dir")+"\\Configurations\\config.properties";
		prop=new Properties();
		try {
			prop.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setDriver() throws URISyntaxException{
		if(prop.getProperty("cross_browser_test").contentEquals("true")){
			crossBrowserSetup();
		}else{
		if(prop.get("execution_platform").equals("local")){
			if(prop.get("execution_browser").toString().equalsIgnoreCase("chrome")){
				//String url=Paths.get(new Testutils().getClass().getClassLoader().getResource("chrome/chromedriver.exe").toURI()).toFile().getAbsolutePath();
				new DesiredCapabilities();
				caps=DesiredCapabilities.chrome();
				ChromeOptions options=new ChromeOptions();
				caps.merge(options);
			}else if(prop.get("execution_browser").toString().equalsIgnoreCase("Firefox")){
				new DesiredCapabilities();
				caps=DesiredCapabilities.firefox();
				FirefoxOptions options=new FirefoxOptions();
				caps.merge(options);
			}
			try {
				driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}else{
			if(prop.get("execution_browser").toString().equalsIgnoreCase("chrome")){
				new DesiredCapabilities();
				caps=DesiredCapabilities.chrome();
				ChromeOptions options=new ChromeOptions();
				caps.merge(options);
			}else if(prop.get("execution_browser").toString().equalsIgnoreCase("Firefox")){
				new DesiredCapabilities();
				caps=DesiredCapabilities.chrome();
				FirefoxOptions options=new FirefoxOptions();
				caps.merge(options);
			}
			try {
				driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		}
	}
	
	public static RemoteWebDriver getDriver(){
		return driver.get();
	}
	
	public static void setReport(){
		reporter.set(new ExtentReports());
	}
	
	public static ExtentReports getReport(){
		return reporter.get();
	}
	
	public static void setExtentTest(ExtentReports report,String testname){
		ExtentTest test=report.createTest(testname);
		extentTest.set(test);
	}
	
	public static ExtentTest getExtentTest(){
		return extentTest.get();
	}
	
	public static void setDriver(String browserName) throws URISyntaxException{
		if(prop.get("execution_platform").equals("local")){
			if(browserName.equalsIgnoreCase("chrome")){
				//String url=Paths.get(new Testutils().getClass().getClassLoader().getResource("chrome/chromedriver.exe").toURI()).toFile().getAbsolutePath();
				new DesiredCapabilities();
				caps=DesiredCapabilities.chrome();
				ChromeOptions options=new ChromeOptions();
				caps.merge(options);
			}else if(browserName.equalsIgnoreCase("Firefox")){
				new DesiredCapabilities();
				caps=DesiredCapabilities.firefox();
				FirefoxOptions options=new FirefoxOptions();
				caps.merge(options);
			}
			try {
				driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}else{
			if(browserName.equalsIgnoreCase("chrome")){
				new DesiredCapabilities();
				caps=DesiredCapabilities.chrome();
				ChromeOptions options=new ChromeOptions();
				caps.merge(options);
			}else if(browserName.equalsIgnoreCase("Firefox")){
				new DesiredCapabilities();
				caps=DesiredCapabilities.chrome();
				FirefoxOptions options=new FirefoxOptions();
				caps.merge(options);
			}
			try {
				driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void crossBrowserSetup(){
		if(prop.getProperty("cross_browser").contains("true")){
			String browserProp=prop.getProperty("cross_browser_test");
			String[] arr=browserProp.split(",");
			for(String browser:arr){
				try {
					setDriver(browser);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
