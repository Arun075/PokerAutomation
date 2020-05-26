package com.poker.users;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.poker.libraries.Config;
import com.poker.libraries.Generic;

public class UserActivator extends Config {
	public static HashMap<String, String> hm = new HashMap<String, String>();
	@Test
	public void userActivatorTest() throws IOException,
			InterruptedException {

		String moduleControllerPath = "./ModuleController/Test.xlsx";
		String mainModule = Generic.getCellValue(moduleControllerPath, "Main",1, 0);
		String es = Generic.getCellValue(moduleControllerPath, "Main", 1, 1);

		String testCaseName = Generic.getCellValue(moduleControllerPath,mainModule, 2, 0);
		String testcase_es = Generic.getCellValue(moduleControllerPath,mainModule, 2, 1);
		
		hm.put(testCaseName, "flag");
		if (es.equalsIgnoreCase("yes")) {

			if (testcase_es.equalsIgnoreCase("yes")) {

				String testDataPath = "./test_data/User.xlsx";
				APP_LOGS.debug("***********************" + "TestCase " + " \" " + testCaseName + " \" " + " starts executing " + "***********************");
		
				int rc = Generic.getRowCount(testDataPath, testCaseName);

				for (int i = 1; i <= rc; i++) {

					APP_LOGS.debug("***** Data Driving from row no : " + i 	+ " *****");
					String EmailID = Generic.getCellValue(testDataPath,testCaseName, i, 0);
					String Username = Generic.getCellValue(testDataPath,testCaseName, i, 1);
					String Password = Generic.getCellValue(testDataPath,testCaseName, i, 2);
					
					String url = "jdbc:mysql://bezz-db-instance.cwtooze33rto.us-west-2.rds.amazonaws.com/";
					String dbName = "pokerdecentraltest";
					String dbDriver = "com.mysql.jdbc.Driver";
					String userName = "poker";
					String password = "decentral";
					
					try 
					{
						Class.forName(dbDriver).newInstance();
						Connection conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);
						DriverManager.getConnection(url, userName, password);
						APP_LOGS.debug("Connected to Database");
						Statement st = (Statement) conn.createStatement();
						ResultSet rss = st.executeQuery("SELECT playerid,token FROM `playerprofile` WHERE emailid = '"+EmailID+"'");
						if (rss.next()) 
						{
							String token = rss.getString("token");
							System.out.println(token);
							APP_LOGS.debug("Entered the User Token " +token);
							ps.waitFor(1000l);
							String playerID = rss.getString("playerid");
							System.out.println(playerID);
							APP_LOGS.debug("Entered the playerid " +playerID);
							ps.waitFor(1000l);
							ps.activeUser(token, playerID);
						}
						conn.close();
						APP_LOGS.debug("Disconnected from Database");
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
					ps.waitFor(30l);
					ps.webdriverWaitVisibilityOfElementLocated("//input[@name='username']");
					System.out.println("Entering the username and password on Activate your account");
					APP_LOGS.debug("Entering the username and password on Activate your account");
					driver.findElement(By.name("username")).clear();
					driver.findElement(By.name("username")).sendKeys(Username);
					driver.findElement(By.name("password")).clear();
					driver.findElement(By.name("password")).sendKeys(Password);
					driver.findElement(By.name("confirmpassword")).clear();
					driver.findElement(By.name("confirmpassword")).sendKeys(Password);
					driver.findElement(By.id("sendmailBtn")).click();
					ps.waitFor(30l);		
					ps.webdriverWaitVisibilityOfElementLocated("//div[@role='alertdialog']//span");
					String actVal = driver.findElement(By.xpath("//div[@role='alertdialog']//span")).getText();
					String expVal = "The Password has been reset successfully.";
					ps.assertEqualsSigninPage(actVal, expVal,testCaseName, i);
					ps.waitFor(60l);
					
					APP_LOGS.debug("***** Data Driving happened successfully from row no : "+ i + " *****");
					

				}

				String res = hm.get(testCaseName).toString();
				if (!res.equalsIgnoreCase("FAIL"))
					hm.put(testCaseName, "PASS");

				APP_LOGS.debug("***********************" + "TestCase " + " \" "
						+ testCaseName + " \" " + " ends execution "
						+ "***********************");
			} else {

				hm.put(testCaseName, "SKIP");
				APP_LOGS.debug("***********************" + "TestCase " + " \" "
						+ testCaseName + " \" "
						+ " execution status is set to NO "
						+ "***********************");
				throw new SkipException("Skipping " + testCaseName				+ " TestCase");
			}
		} else {
			hm.put(testCaseName, "SKIP");
			APP_LOGS.debug("***********************" + "Main Module " + " \" "
					+ mainModule + " \" " + " execution status is set to NO "
					+ "***********************");
			throw new SkipException("Skipping " + testCaseName + " TestCase");
		}
	}

	@AfterMethod
	public void res() {
		String testcaseName = "UserActivator";
		String res = hm.get(testcaseName).toString();
		if (res.equalsIgnoreCase("PASS")) {
			hm.put(testcaseName, "PASS");
			System.out.println(testcaseName + " TestCase is PASSED");
			APP_LOGS.debug(testcaseName + " TestCase is PASSED");
		} else if (res.equalsIgnoreCase("SKIP")) {
			hm.put(testcaseName, "SKIP");
			System.out.println(testcaseName + " TestCase is SKIPPED");
			APP_LOGS.debug(testcaseName + " TestCase is SKIPPED");
		} else {
			hm.put(testcaseName, "FAIL");
			System.out.println(testcaseName + " TestCase is FAILED");
			APP_LOGS.debug(testcaseName + " TestCase is FAILED");
		}
	}

}
