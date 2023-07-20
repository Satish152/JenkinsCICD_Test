package cicd.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelRead {
public XSSFWorkbook workbook;
public XSSFSheet sheet;
	public XSSFSheet loadTestSuiteFile() throws IOException{
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\TestData\\TestData.xlsx");
		workbook=new XSSFWorkbook(fis);
		sheet=workbook.getSheetAt(0);
		return sheet;
	}
	
	public XSSFSheet loadTestSuiteFile(String sheetName) throws IOException{
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\TestData\\TestData.xlsx");
		workbook=new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		return sheet;
	}
	
	
	
	public String getCellData(int row,int col){
		return sheet.getRow(row).getCell(col).getStringCellValue();
	}
	
	public String getCellData(int row,int col,String sheetname){
		sheet=workbook.getSheet(sheetname);
		return sheet.getRow(row).getCell(col).getStringCellValue();
	}
	
	public String getCellData(int row,int col,int sheetIndex){
		sheet=workbook.getSheetAt(sheetIndex);
		return sheet.getRow(row).getCell(col).getStringCellValue();
	}
}
