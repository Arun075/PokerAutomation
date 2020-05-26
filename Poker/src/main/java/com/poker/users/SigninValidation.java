package com.poker.users;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import java.util.HashMap;
import com.poker.libraries.Config;
import com.poker.libraries.Generic;

public class SigninValidation extends Config {
	public static HashMap<String, String> hm = new HashMap<String, String>();
	@Test
	public void signinFormValidationTest() throws IOException,
			InterruptedException {

		String moduleControllerPath = "./ModuleController/Test.xlsx";
		String mainModule = Generic.getCellValue(moduleControllerPath, "Main",1, 0);
		String es = Generic.getCellValue(moduleControllerPath, "Main", 1, 1);

		String testCaseName = Generic.getCellValue(moduleControllerPath,mainModule, 5, 0);
		String testcase_es = Generic.getCellValue(moduleControllerPath,mainModule, 5, 1);
		
		hm.put(testCaseName, "flag");
		if (es.equalsIgnoreCase("yes")) {

			if (testcase_es.equalsIgnoreCase("yes")) {

				String testDataPath = "./test_data/User.xlsx";
				APP_LOGS.debug("***********************" + "TestCase " + " \" " + testCaseName + " \" " + " starts executing " + "***********************");

				ps.navigateTestURL();
				APP_LOGS.debug("Clicking on Sign IN Link");
				ps.webdriverWaitVisibilityOfElementLocated("//header[@class='MuiPaper-root MuiPaper-elevation4 MuiAppBar-root MuiAppBar-positionFixed jss1 jss16 jss3 MuiAppBar-colorPrimary mui-fixed']//p[text()='Sign-In']");
				driver.findElement(By.xpath("//header[@class='MuiPaper-root MuiPaper-elevation4 MuiAppBar-root MuiAppBar-positionFixed jss1 jss16 jss3 MuiAppBar-colorPrimary mui-fixed']//p[text()='Sign-In']")).click();

				int rc = Generic.getRowCount(testDataPath, testCaseName);

				for (int i = 1; i <= rc; i++) {

					APP_LOGS.debug("***** Data Driving from row no : " + i 	+ " *****");
					String EmailID = Generic.getCellValue(testDataPath,testCaseName, i, 0);
					String Password = Generic.getCellValue(testDataPath,testCaseName, i, 1);
					String Controller = Generic.getCellValue(testDataPath,testCaseName, i, 2);

					if (Controller.equalsIgnoreCase("flag"+i)) {
						APP_LOGS.debug("Entering the EmailID : " + EmailID);
						ps.webdriverWaitVisibilityOfElementLocated("//input[@name='emailid']");
						
						driver.findElement(By.name("emailid")).clear();
						driver.findElement(By.name("emailid")).sendKeys(EmailID);
						ps.webdriverWaitVisibilityOfElementLocated("//input[@name='password']");
						APP_LOGS.debug("Entering the Password : " + Password);
						driver.findElement(By.name("password")).clear();
						driver.findElement(By.name("password")).sendKeys(Password);
						APP_LOGS.debug("Clicking on Submit Button");
						ps.webdriverWaitVisibilityOfElementLocated("//button[@id='signinBtn']");
						driver.findElement(By.id("signinBtn")).click();
						ps.waitFor(40l);
						ps.webdriverWaitVisibilityOfElementLocated("//div[@role='alertdialog']//span");
						String actVal = driver.findElement(By.xpath("//div[@role='alertdialog']//span")).getText();
						String expVal = "Email address and password do not match. Please try again.";
						ps.assertEqualsSigninPage(actVal, expVal,testCaseName, i);
						ps.waitFor(60l);
					} 

					APP_LOGS.debug("***** Data Driving happened successfully from row no : " + i + " *****");
					

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
		String testcaseName = "SigninValidation";
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
