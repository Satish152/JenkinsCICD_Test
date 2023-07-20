package cicd.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cicd.utilities.PropertyReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class Reports {
public ExtentReports report;
public ExtentTest extentTest;
public ExtentSparkReporter sparkReport;
public PropertyReader propertyReader;

public Reports(ExtentReports report){
	this.report=report;
	Calendar cal=Calendar.getInstance();
	Date d=cal.getTime();
	propertyReader=new PropertyReader();
	String date=new SimpleDateFormat("MM_dd_yyyy-HHmmss").format(d);
	sparkReport=new ExtentSparkReporter(new File(System.getProperty("user.dir")+"\\Reports\\Report_"+date+"\\executionReport_"+date+".html"));
	report.attachReporter(sparkReport);
	sparkReport.config().setDocumentTitle("Automation Test Execution Report - "+propertyReader.getPropertyValue("execution_browser")+"_"+date);
	report.setSystemInfo("OS", "Windows");
	report.setSystemInfo("Browser", "chrome");
	sparkReport.config().setReportName("Test Report");
	sparkReport.config().setTheme(Theme.STANDARD);
	sparkReport.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
}

public ExtentTest setExtentTest(ExtentTest test){
	this.extentTest=test;
	return extentTest;
}

public void reportStepStatus(String status,String message){
	if(status.equalsIgnoreCase("pass"))
		extentTest.log(Status.PASS, message);
	else if(status.equalsIgnoreCase("fail")){
		extentTest.log(Status.FAIL,message);
	}
}

public void flushReport(){
	report.flush();
}
	
}
