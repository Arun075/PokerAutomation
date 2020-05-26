package com.poker.users;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.poker.libraries.Config;
import com.poker.libraries.Generic;

public class CheckInitialBalance extends Config {
	public static HashMap<String, String> hm = new HashMap<String, String>();
	@Test
	public void checkInitialBalanceTest() throws IOException,
			InterruptedException {

		String moduleControllerPath = "./ModuleController/Test.xlsx";
		String mainModule = Generic.getCellValue(moduleControllerPath, "Main",1, 0);
		String es = Generic.getCellValue(moduleControllerPath, "Main", 1, 1);

		String testCaseName = Generic.getCellValue(moduleControllerPath,mainModule, 4, 0);
		String testcase_es = Generic.getCellValue(moduleControllerPath,mainModule, 4, 1);
		
		hm.put(testCaseName, "flag");
		if (es.equalsIgnoreCase("yes")) {

			if (testcase_es.equalsIgnoreCase("yes")) {

				String testDataPath = "./test_data/User.xlsx";
				APP_LOGS.debug("***********************" + "TestCase " + " \" " + testCaseName + " \" " + " starts executing " + "***********************");
				ps.waitFor(30l);
				ps.navigateTestURL();
				ps.webdriverWaitVisibilityOfElementLocated("//span[@class='MuiButton-label' and text()='POKERdeCentral']");
				
				int rc = Generic.getRowCount(testDataPath, testCaseName);
				
				for (int i = 1; i <= rc; i++) {

					APP_LOGS.debug("***** Data Driving from row no : " + i 	+ " *****");
					String EmailID = Generic.getCellValue(testDataPath,testCaseName, i, 0);
					String Password = Generic.getCellValue(testDataPath,testCaseName, i, 1);
					String Balance = Generic.getCellValue(testDataPath, testCaseName, i, 2);
					ps.webdriverWaitVisibilityOfElementLocated("//header[@class='MuiPaper-root MuiPaper-elevation4 MuiAppBar-root MuiAppBar-positionFixed jss1 jss16 jss3 MuiAppBar-colorPrimary mui-fixed']//p[text()='Sign-In']");
					driver.findElement(By.xpath("//header[@class='MuiPaper-root MuiPaper-elevation4 MuiAppBar-root MuiAppBar-positionFixed jss1 jss16 jss3 MuiAppBar-colorPrimary mui-fixed']//p[text()='Sign-In']")).click();
					ps.webdriverWaitVisibilityOfElementLocated("//input[@name='emailid']");
					driver.findElement(By.name("emailid")).clear();
					driver.findElement(By.name("emailid")).sendKeys(EmailID);
					APP_LOGS.debug("Entering the EmailID : " + EmailID);
					driver.findElement(By.name("password")).clear();
					driver.findElement(By.name("password")).sendKeys(Password);
					APP_LOGS.debug("Entering the Password : " + Password);
					ps.waitFor(1000l);
					driver.findElement(By.xpath("//span[text()='Sign-In']")).click();
					System.out.println("Sign-In Button Clicked");
					APP_LOGS.debug("Sign-In Button Clicked");
					ps.waitFor(1000l);
					ps.webdriverWaitVisibilityOfElementLocated("//p[text()='Take me to Lobby']");
					driver.findElement(By.xpath("//p[text()='Take me to Lobby']")).click();
					System.out.println("Take me to Lobby Link Clicked");
					APP_LOGS.debug("Take me to Lobby Link Clicked");
					ps.waitFor(1000l);
					String actVal = driver.findElement(By.xpath("//p[@id='etherDivBorder']")).getText();
					String expVal = " "+Balance;
					ps.assert_Equal(actVal, expVal, testCaseName);
					ps.navigateTestURL();
					ps.webdriverWaitVisibilityOfElementLocated("//header[@class='MuiPaper-root MuiPaper-elevation4 MuiAppBar-root MuiAppBar-positionFixed jss1 jss16 jss3 MuiAppBar-colorPrimary mui-fixed']//p[ text()='Sign out']");
					driver.findElement(By.xpath("//header[@class='MuiPaper-root MuiPaper-elevation4 MuiAppBar-root MuiAppBar-positionFixed jss1 jss16 jss3 MuiAppBar-colorPrimary mui-fixed']//p[ text()='Sign out']")).click();
					APP_LOGS.debug("Sign-out Link Clicked");
					
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
		String testcaseName = "CheckInitialBalance";
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