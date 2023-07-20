package cicd.base;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import cicd.utilities.ExcelRead;
import cicd.utilities.PropertyReader;

public class BrowserBase {
	public WebDriver driver;
	public Reports reports;
	public ExtentTest extentTest;
	public ExtentReports extentreport;
    
	public ExcelRead excelRead;
	public ExcelRead excelRead1;
	public PropertyReader propertyReader;
	public static String testCaseDesc;
	public Logger logger;
	public Testutils testUtils;

	@Test
	public void TestCaseExecutor() throws Exception {
		propertyReader = new PropertyReader();
		excelRead = new ExcelRead();
		excelRead1 = new ExcelRead();
		testUtils = new Testutils();
		logger=LogManager.getLogger("logname");
		int testRows = excelRead.loadTestSuiteFile().getLastRowNum();
		setupReport();
		for (int flag = 1; flag < testRows + 1; flag++) {
			int rowNo = flag;
			int colNo = 2;
			testCaseDesc = excelRead.sheet.getRow(rowNo).getCell(1).getStringCellValue();

			if (excelRead.getCellData(rowNo, colNo).contentEquals("No")
					|| excelRead.getCellData(rowNo, colNo).isEmpty()) {
				continue;
			} else {
				/*
				 * for(int Tests=flag;Tests<testRows+1;Tests++)
				 * { //This for loop when same test case calling multiple
				 * methods
				 */
				logger.log(Level.INFO,"*******Testcase execution Started : "+testCaseDesc+" *******");
				Testutils.setDriver();
				setUpTestResult();
				driver = Testutils.getDriver();
				excelRead1.loadTestSuiteFile("TestSuite");
				Class<?> classname = Class.forName(
						"cicd.testSet." + excelRead1.sheet.getRow(flag).getCell(0).getStringCellValue().trim());
				String MethodName = excelRead1.sheet.getRow(flag).getCell(2).getStringCellValue().trim(); // Method
																											// Name
																											// from
																											// Excel
				try {
					Object parameter = null;
					try {
						parameter = excelRead1.sheet.getRow(flag).getCell(3).getStringCellValue();
					} catch (Exception e) {
						parameter = excelRead1.sheet.getRow(flag).getCell(3).getRawValue();
					}
					int parameterCount = 0;
					if (parameter instanceof String)
						parameterCount = Integer.valueOf((String) parameter);
					else if (parameter instanceof Double || parameter instanceof Integer)
						parameterCount = (int) parameter;
					for (Method m : classname.getMethods()) // reading Method
															// names from
															// Functional
															// library
					{
						if (m.getName().equals(MethodName)) // Compapring Method
															// Name with Excel
															// method name
						{
							if (m.getParameterCount() == parameterCount) // Comparing
																			// paraameter
																			// Count
																			// from
																			// both
																			// FL
																			// and
																			// Excel
							{
								Class<?> param[] = {};
								if (parameterCount != 0) {
									param = new Class[parameterCount]; // Creating
																		// an
																		// array
																		// with
																		// class

									for (int i = 0; i < m.getParameterCount(); i++) {
										param[i] = m.getParameterTypes()[i]; // assigning
																				// values
																				// in
																				// to
																				// array
																				// with
																				// parameter
																				// type
									}
								}
								Method method = classname.getMethod(MethodName, param);
								Constructor<?> con = classname
										.getConstructor(new Class[] { RemoteWebDriver.class, Reports.class });
								method.invoke(con.newInstance(new Object[] { driver, reports }),
										ParameterData(parameterCount, flag));
							}
						}
					}
				} catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException
						| NullPointerException e) {
					logger.log(Level.INFO,"********Testcase execution Failed : "+testCaseDesc+" *******");
					e.printStackTrace();
					driver.quit();
				} catch (IllegalArgumentException e) {
					driver.quit();
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
				// } test name for loop close
			}
		}
	}

	public String[] ParameterData(int ParameterCount, int RowNum) {
		String[] data = {};
		if (ParameterCount != 0) {
			data = new String[ParameterCount];
			int cell = 4;
			for (int i = 0; i < ParameterCount; i++) {
				String parameter = excelRead1.sheet.getRow(RowNum).getCell(cell).getStringCellValue();
				if (parameter.equals("") | parameter.equals(null)) {
					parameter = excelRead1.sheet.getRow(RowNum).getCell(cell).getStringCellValue();
					data[i] = parameter;
				} else {
					data[i] = parameter;
				}
				cell = cell + 1;
			}
		}
		return data;
	}

	public void setupReport() {
		if (propertyReader.getPropertyValue("parallel").contentEquals("true")
				&& propertyReader.getPropertyValue("cross_browser").contentEquals("true")) {
			Testutils.setReport();
			extentreport = Testutils.getReport();
			reports = new Reports(extentreport);
		} else if (propertyReader.getPropertyValue("parallel").contentEquals("true")
				&& !propertyReader.getPropertyValue("cross_browser").contentEquals("true")) {
			extentreport = new ExtentReports();
			reports = new Reports(extentreport);
		} else if (!propertyReader.getPropertyValue("parallel").contentEquals("true")
				&& propertyReader.getPropertyValue("cross_browser").contentEquals("true")) {
			Testutils.setReport();
			extentreport = Testutils.getReport();
			reports = new Reports(extentreport);
		} else {
			extentreport = new ExtentReports();
			reports = new Reports(extentreport);
		}
	}

	public void setUpTestResult() {
		if (propertyReader.getPropertyValue("parallel").contentEquals("true")
				&& propertyReader.getPropertyValue("cross_browser").contentEquals("true")) {
			Testutils.setExtentTest(extentreport, testCaseDesc);
			extentTest = Testutils.getExtentTest();
			reports.setExtentTest(extentTest);
		} else if (propertyReader.getPropertyValue("parallel").contentEquals("true")
				&& !propertyReader.getPropertyValue("cross_browser").contentEquals("true")) {
			Testutils.setExtentTest(extentreport, testCaseDesc);
			extentTest = Testutils.getExtentTest();
			reports.setExtentTest(extentTest);
		} else if (!propertyReader.getPropertyValue("parallel").contentEquals("true")
				&& propertyReader.getPropertyValue("cross_browser").contentEquals("true")) {
			Testutils.setExtentTest(extentreport, testCaseDesc);
			extentTest = Testutils.getExtentTest();
			reports.setExtentTest(extentTest);
		} else {
			extentTest = extentreport.createTest(testCaseDesc);
			reports.setExtentTest(extentTest);
		}
	}

	

	@AfterSuite
	public void tearDown() {
		driver.quit();
		reports.flushReport();
	}
}
