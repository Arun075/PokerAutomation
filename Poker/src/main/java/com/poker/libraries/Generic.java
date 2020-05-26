package com.poker.libraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Generic 
{
	public static int getRowCount(String xlPath, String sheetName) 
	{
		try 
		{
			FileInputStream fis = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			return wb.getSheet(sheetName).getLastRowNum();
		}
		catch (Exception e) 
		{
			return -1;
		}
   }

	public static String getCellValue(String xlPath, String sheetName, int rowNum, int cellNum) 
	{
		FileInputStream fis;
		try 
		{
			fis = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			return wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();
		} 
		catch (Exception e) 
		{
			return "";
		}
	}

	public static int getColumnCount(String xlPath, String sheetName, int rowNum) 
	{
		FileInputStream fis;
		try 
		{
			fis = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			return wb.getSheet(sheetName).getRow(rowNum).getLastCellNum();
		} 
		catch (Exception e) 
		{
			return -1;
		}
	}

	public static void setCellValue(String xlPath, String sheetName, 	int rowNum, int cellNum, String cellVal)
	{
		FileInputStream fis;
		FileOutputStream fos;
		try 
		{
			fis = new FileInputStream(xlPath);
			Workbook wb = WorkbookFactory.create(fis);
			Row r = wb.getSheet(sheetName).getRow(rowNum);
			if (r == null)
				r = wb.getSheet(sheetName).createRow(rowNum);
			Cell c = r.getCell(cellNum);
			if (c == null)
				c = r.createCell(cellNum);
			c.setCellValue(cellVal);
			fis.close();
			fos = new FileOutputStream(xlPath);
			wb.write(fos);
			fos.close();
		} 
		catch (Exception e) 
		{

		}
	}

	public static void writeRes(String fisPath, HashMap<String, String> hm) 
	{
		FileInputStream fis;
		FileOutputStream fos;
		try 
		{
			fis = new FileInputStream(fisPath);
			Workbook wb = WorkbookFactory.create(fis);
			wb.createCellStyle();
			Sheet mainSheet = wb.getSheet("Main");
			int rc = mainSheet.getLastRowNum();
			for (int i = 1; i <= rc; i++) 
			{
				String module = mainSheet.getRow(i).getCell(0).getStringCellValue();
				Sheet testCaseSheet = wb.getSheet(module);
				int rowCnt = testCaseSheet.getLastRowNum();
				for (int j = 1; j <=rowCnt; j++) 
				{
					String testcase = testCaseSheet.getRow(j).getCell(0).getStringCellValue();
					Set<String> key = hm.keySet();
					for (String k : key) 
					{
						if (testcase.equalsIgnoreCase(k))
						{
							Cell c = testCaseSheet.getRow(j).getCell(2);
							if (c == null)
								c = testCaseSheet.getRow(j).createCell(2);
							String test1 = (String) hm.get(k);
							System.out.println("test1 = -------------"+ test1);
							String value = hm.get(k).toString();
							if (value.equalsIgnoreCase("PASS")) 
							{
								c.setCellValue(hm.get(k).toString());
								Font headerFont = wb.createFont();
								headerFont.setBold(true);
								//headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
								CellStyle headerStyle = wb.createCellStyle();
								headerStyle.setFont(headerFont);
								headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
								headerFont.setColor(IndexedColors.BLACK.getIndex());
								headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								c.setCellStyle(headerStyle);
							} 
							else if (value.equalsIgnoreCase("SKIP")) 
							{
								c.setCellValue(hm.get(k).toString());
								Font headerFont2 = wb.createFont();
								headerFont2.setBold(true);
								CellStyle headerStyle2 = wb.createCellStyle();
								headerStyle2.setFont(headerFont2);
								headerStyle2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
								headerFont2.setColor(IndexedColors.BLACK.getIndex());
								headerStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								c.setCellStyle(headerStyle2);
							} 
							else if (value.equalsIgnoreCase("FAIL")) 
							{
								c.setCellValue(hm.get(k).toString());
								Font headerFont1 = wb.createFont();
								headerFont1.setBold(true);
								CellStyle headerStyle1 = wb.createCellStyle();
								headerStyle1.setFont(headerFont1);
								headerStyle1.setFillForegroundColor(IndexedColors.RED.getIndex());
								headerFont1.setColor(IndexedColors.BLACK.getIndex());
								headerStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								c.setCellStyle(headerStyle1);
							}

						}
					}
				}
			}

			fis.close();
			fos = new FileOutputStream("./Results/Results " + curDate()+ ".xlsx");
			wb.write(fos);
			fos.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// returns current date with time
	public static String curDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// returns current date
	public static String date() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	// returns past date
	public static String past_furure_Date(int i) 
	{
		SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.DAY_OF_MONTH, -i);
		String pastDate = date.format(gc.getTime());
		return pastDate;
	}

	public static void takeScreenShot(WebDriver driver, String name) throws IOException
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(".\\Screenshot\\" + name + ".png"));

	}

	// to delete .png files in screenshot folder
	public static void deleteRecursive(File path)
	{
		File[] c = path.listFiles();
		System.out.println("Cleaning out folder:" + path.toString());
		for (File file : c)
		{
			if (file.isDirectory()) 
			{
				System.out.println("Deleting file:" + file.toString());
				deleteRecursive(file);
				file.delete();
			} 
			else 
			{
				file.delete();
			}
		}
	}

}
