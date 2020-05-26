package com.poker.libraries;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ProjectSpecific {

	public static WebDriver driver = null;
	public static Logger APP_LOGS = null;
	public static Properties OR = null;
	public static Properties CONFIG = null;
	public static HashMap<String, String> hm = null;

	ProjectSpecific(WebDriver driver, Logger APP_LOGS, Properties OR, Properties CONFIG, HashMap<String, String> hm) 
	{
		ProjectSpecific.driver = driver;
		ProjectSpecific.APP_LOGS = APP_LOGS;
		ProjectSpecific.OR = OR;
		ProjectSpecific.CONFIG = CONFIG;
		ProjectSpecific.hm = hm;
	}
	
	// to activate user
		public void activeUser(String token, String playerID) throws InterruptedException
		{
			      String testSiteName = CONFIG.getProperty("testSiteName");
					String url = testSiteName+"/pokerdecentral/emailverification?key="+token+"&playerId="+playerID;
					driver.get(url);
					APP_LOGS.debug("Opening the URL:" + url);
					String waitTime = CONFIG.getProperty("default_implicitWait");
					driver.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
					 waitFor(2000l);
		}
	
			
		// navigate to test-url
		public void navigateTestURL() throws InterruptedException 
		{
			String url = CONFIG.getProperty("testSiteName");
			driver.get(url);
			APP_LOGS.debug("Opening the URL:" + url);
			String waitTime = CONFIG.getProperty("default_implicitWait");
			driver.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
			 waitFor(2000l);
		}

	// to sign-in
	public void signIN(String un, String pwd, String testCaseName) throws IOException, InterruptedException 
	{
		waitFor(1000l);
		navigateTestURL();
		 webdriverWaitVisibilityOfElementLocated("//p[text()='Sign-In']");
		 driver.findElement(By.xpath("//p[text()='Sign-In']")).click();
		 
		 webdriverWaitVisibilityOfElementLocated("//p[text()='Sign-In']");
		 driver.findElement(By.name("emailid")).clear();
		driver.findElement(By.name("emailid")).sendKeys(un);
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys(pwd);
		driver.findElement(By.id("signinBtn")).click();
		waitFor(1000l);
	}

	// to sign-out
	public void signOUT()
	{
		driver.findElement(By.xpath("//i[@class='material-icons']"))	.click();
		webdriverWaitVisibilityOfElementLocated("//span[text()='Logout']");
		driver.findElement(By.xpath("//span[text()='Logout']")).click();
		String waitTime = CONFIG.getProperty("default_implicitWait");
		driver.manage().timeouts().implicitlyWait(Long.parseLong(waitTime), TimeUnit.SECONDS);
		System.out.println("Signed out");
		APP_LOGS.debug("Signed out");
		webdriverWaitVisibilityOfElementLocated("//*");
	}
	

		
	// to click by ID
	public void clickByID(String ID) 
	{
		driver.findElement(By.id(ID)).click();
	}

	// to click by XPATH
	public void clickByXPATH(String XPATH) 
	{
		driver.findElement(By.xpath(XPATH)).click();
	}

	// to click by NAME
	public void clickByName(String NAME) 
	{
		driver.findElement(By.name(NAME)).click();
	}

	// to click by ClassName
	public void clickByClassName(String ClassName)
	{
		driver.findElement(By.className(ClassName)).click();
	}

	// switch to frame or iframes by id or name
	public void switchToFrame(String Id_Name) 
	{
		driver.switchTo().frame(Id_Name);
	}

	// switch to frame or iframes by index number
	public void switchToFrameByIndex(int indexNO) 
	{
		driver.switchTo().frame(indexNO);
	}

	// switch back to default content
	public void switchToDefault() 
	{
		driver.switchTo().defaultContent();
	}

	// to check whether an element is present or not
	public boolean isElementPresent(String xpath) 
	{
		boolean flag = false;
		try 
		{
			driver.findElement(By.xpath(xpath));
			flag = true;
			return flag;
		} 
		catch (Exception e) 
		{
			return flag;
		}
	}

	// REUSABLE
			

	// wait till visibility of element located
	public void webdriverWaitVisibilityOfElementLocated(String xpath) 
	{
		WebDriverWait wait = new WebDriverWait(driver, 30l);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	// wait till invisibility of element located
	public void webdriverWaitInVisibilityOfElementLocated(String xpath) 
	{
		WebDriverWait wait = new WebDriverWait(driver, 20l);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	}

	public boolean existsElement(String xPath) 
	{
		try {
			driver.findElement(By.xpath(xPath));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	// wait till invisibility of All element located
	public void webdriverWaitpresenceOfAllElementsLocated(String xpath) 
	{
		WebDriverWait wait = new WebDriverWait(driver, 20l);		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
	}

	// wait
	public void waitFor(long milliSEC) throws InterruptedException {
		Thread.sleep(milliSEC);
	}

	// select the value by visible text
	public void selectByVisibleText(WebElement we, String visible_text) 
	{
		Select sel = new Select(we);
		sel.selectByVisibleText(visible_text);
	}

	// select the value by the value
	public void selectByValue(WebElement we, String val) 
	{
		Select sel = new Select(we);
		sel.selectByValue(val);
	}

	// switch to alert
	public void swithToAlert$Accept() 
	{
		Alert art = driver.switchTo().alert();
		art.accept();
	}

	// to clear the text field
	public void clear(String ID)
	{
		driver.findElement(By.id(ID)).clear();
	}

	public void clearByName(String NAME) 
	{
		driver.findElement(By.name(NAME)).clear();
	}

	public void clearTextField(String xPath) 
	{
		driver.findElement(By.xpath(xPath)).clear();
	}

	// ASSERTION AND VERIFICATION CODE'S

	// asserting the pop-up
	public void assertPopUP(String xpath, String testCaseName, String popupInfo) throws IOException 
	{
		try {
			System.out.println("Asserting " + popupInfo + " POPUP");
			APP_LOGS.debug("Asserting " + popupInfo + " POPUP");
			Assert.assertTrue(driver.findElement(By.xpath(xpath)).isDisplayed());
			System.out.println(popupInfo + " POPUP is displayed");
			APP_LOGS.debug(popupInfo + " POPUP is displayed");
		} catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver,
					testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("Assertion failed on " + popupInfo + " POPUP");
			APP_LOGS.debug("*********************** TestCase " + " \" "
					+ testCaseName + " \" "
					+ " is Failed ***********************");
			driver.findElement(By.className("close")).click();
			signOUT();
			Assert.assertTrue(driver.findElement(By.xpath(xpath)).isDisplayed());
		}
	}

	// assert the page
	public void assertPage(String xpath, String testCaseName, String info) throws IOException {
		try {
			System.out.println("Asserting " + info + " page");
			APP_LOGS.debug("Asserting " + info + " page");
			Assert.assertTrue(driver.findElement(By.xpath(xpath)).isDisplayed());
			System.out.println("Control is on a " + info + " page");
			APP_LOGS.debug("Control is on a " + info + " page");
		} catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver,
					testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("Assertion failed on " + info + " page");
			APP_LOGS.debug("*********************** TestCase " + " \" "
					+ testCaseName + " \" "
					+ " is Failed ***********************");
			signOUT();
			Assert.assertTrue(driver.findElement(By.xpath(xpath)).isDisplayed());
		}
	}

		// assert the value
	public void assertEquals(String act_val, String exp_val,	String testCaseName, int iteration) throws IOException 
	{
		try 
		{
			System.out.println("Asserting the Actual Value and the Expected Value");
			APP_LOGS.debug("Actual Value is: " + act_val + "---" 	+ "and the Expected Value is: " + exp_val);
			Assert.assertEquals(act_val, exp_val);
			System.out.println("Asserted the actual and expected value");
			APP_LOGS.debug("Asserted the actual and expected value");
		}
		catch (Error e)
		{
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver, testCaseName + " " + iteration + " " + Generic.curDate());
			APP_LOGS.debug("*********************** TestCase " 	+ " \" " + testCaseName + " \" " 
					+ " is Failed while asserting the expected and actual value. Data derived from row number: " 	+ iteration + " ***********************");
			signOUT();

			// stops the current test script execution. if you comment the below line then continuous the current test script execution
			Assert.assertEquals(act_val, exp_val);
		}
	}

	
	// asserting the actual and expected arrays -- consider iterations
	public void assertArrayEquals(String[] act_val, String[] exp_val,
			String testCaseName, int iteration) throws IOException {

		try {
			System.out.println("Asserting the Actual and the Expected Array");
			APP_LOGS.debug("Asserting the Actual and the Expected Array");
			Assert.assertEquals(act_val, exp_val);
			for (int i = 0; i < act_val.length; i++) 
			{
				APP_LOGS.debug("Actual Value : " + act_val[i]  + " and Expected Value : " + exp_val[i]);
			}

			System.out.println("Asserted the Actual and the Expected Array");
			APP_LOGS.debug("Asserted the Actual and the Expected Array");
		} catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver, testCaseName + " " + iteration + " "
					+ Generic.curDate());
			APP_LOGS.debug("*********************** TestCase "
					+ " \" "
					+ testCaseName
					+ " \" "
					+ " is Failed while asserting the expected and actual array. Data derived from row number: "
					+ iteration + "***********************");
			signOUT();

			Assert.assertEquals(act_val, exp_val);
		}
	}

	// assert the value on pop-up without iterations
	public void assertEquals(String act_val, String exp_val, String testCaseName)
			throws IOException {

		try {
			System.out
					.println("Asserting the Actual Value and the Expected Value");
			APP_LOGS.debug("Actual Value is: " + act_val + "---"
					+ "and the Expected Value is: " + exp_val);
			Assert.assertEquals(act_val, exp_val);
			System.out.println("Asserted the actual and expected value");
			APP_LOGS.debug("Asserted the actual and expected value");
		} catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver,
					testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("*********************** TestCase "
					+ " \" "
					+ testCaseName
					+ " \" "
					+ " is Failed while asserting the expected and actual value. ***********************");
			driver.findElement(By.className("close")).click();
			signOUT();

			// stops the current test script execution. if you comment the below
			// line then continuous the current test script execution
			Assert.assertEquals(act_val, exp_val);
		}
	}

	// asserting the actual and expected value
	public void assertEqual(String act_val, String exp_val, String testCaseName)
			throws IOException {

		try {
			System.out
					.println("Asserting the Actual Value and the Expected Value");
			APP_LOGS.debug("Actual Value is: " + act_val + "---"
					+ "and the Expected Value is: " + exp_val);
			Assert.assertEquals(act_val, exp_val);
			System.out.println("Asserted the actual and expected value");
			APP_LOGS.debug("Asserted the actual and expected value");
		} catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver,
					testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("*********************** TestCase "
					+ " \" "
					+ testCaseName
					+ " \" "
					+ " is Failed while asserting the expected and actual value. ***********************");
			signOUT();

			// stops the current test script execution. if you comment the below
			// line then continuous the current test script execution
			Assert.assertEquals(act_val, exp_val);
		}
	}

	// asserting the actual and expected value
	public void assert_Equal(String act_val, String exp_val, String testCaseName)
			throws IOException {

		try {
			System.out	.println("Asserting the Actual Value and the Expected Value");
			APP_LOGS.debug("Actual Value is: " + act_val + "---"	+ "and the Expected Value is: " + exp_val);
			Assert.assertEquals(act_val, exp_val);
			System.out.println("Asserted the actual and expected value");
			APP_LOGS.debug("Asserted the actual and expected value");
		} catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver,
					testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("*********************** TestCase "
					+ " \" "
					+ testCaseName
					+ " \" "
					+ " is Failed while asserting the expected and actual value. ***********************");
			// stops the current test script execution. if you comment the below
			// line then continuous the current test script execution
			Assert.assertEquals(act_val, exp_val);
		}
	}

	// asserting the actual and expected arrays
	public void assertArrayEquals(String[] act_val, String[] exp_val,
			String testCaseName) throws IOException {

		try {
			System.out.println("Asserting the Actual and the Expected Array");
			APP_LOGS.debug("Asserting the Actual and the Expected Array");
			Assert.assertEquals(act_val, exp_val);
			System.out.println("Asserted the Actual and the Expected Array");
			APP_LOGS.debug("Asserted the Actual and the Expected Array");
		} catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver,
					testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("*********************** TestCase "
					+ " \" "
					+ testCaseName
					+ " \" "
					+ " is Failed while asserting the expected and actual array. ***********************");
			signOUT();

			// stops the current test script execution. if you comment the below
			// line then continuous the current test script execution
			Assert.assertEquals(act_val, exp_val);
		}
	}

	// assert the value
		public void assertEqualsSigninPage(String act_val, String exp_val, String testCaseName, int iteration) throws IOException
		{
			try 
			{
				System.out.println("Asserting the Actual Value and the Expected Value");
				APP_LOGS.debug("Actual Value is: " + act_val + "---" 	+ "and the Expected Value is: " + exp_val);
				Assert.assertEquals(act_val, exp_val);
				APP_LOGS.debug("Asserted the actual and expected value");
			} 
			catch (Error e) 
			{
				hm.put(testCaseName, "FAIL");
				Generic.takeScreenShot(driver, testCaseName + " " + iteration + " " 	+ Generic.curDate());
				APP_LOGS.debug("*********************** TestCase " 	+ " \" " + testCaseName + " \" "
						+ " is Failed while asserting the expected and actual value. Data derived from row number: "
						+ iteration + " ***********************");
				// stops the current test script execution. if you comment the below  line then continuous the current test script execution
				Assert.assertEquals(act_val, exp_val);
			}
		}
		
	// assert the value
	public void assertTrue(String act_val, String exp_val, String testCaseName) throws IOException 
	{
		try 
		{
			System.out.println("Asserting... The Actual Value should contain the Expected Value");
			APP_LOGS.debug("Actual Value is: " + act_val + "---"	+ "and the Expected Value is: " + exp_val);
			Assert.assertTrue(act_val.contains(exp_val));
			System.out.println("Asserted! The Actual Value contains the Expected Value");
			APP_LOGS.debug("Asserted! The Actual Value contains the Expected Value");
			}
		catch (Error e) {
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver, testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("*********************** TestCase "	+ " \" "	+ testCaseName	+ " \" "	+ " is Failed while asserting. The Actual Value doesn't contains the Expected Value");
			signOUT(); 	// stops the current test script execution. if you comment the below  line then continues the current test script execution
			Assert.assertEquals(act_val, exp_val);
		}
	}

	// assert the value
	public void assertTrue(List<String> act, List<String> exp, String testCaseName) throws IOException 
	{
		try 
		{
			System.out.println("Asserting the Actual List and the Expected List");
			APP_LOGS.debug("Asserting the Actual List and the Expected List");
			Assert.assertTrue(act.containsAll(exp));
			System.out.println("Asserted the Actual List and the Expected List");
			APP_LOGS.debug("Asserted the Actual List and the Expected List");
		}
		catch (Error e) 
		{
			hm.put(testCaseName, "FAIL");
			Generic.takeScreenShot(driver, testCaseName + " " + Generic.curDate());
			APP_LOGS.debug("*********************** TestCase " 	+ " \" " + testCaseName + " \" "
					+ " is Failed while asserting. The Actual Value doesn't contains the Expected Value");
			signOUT(); 	// stops the current test script execution. if you comment the below line then continues the current test script execution
			Assert.assertTrue(act.containsAll(exp));
		}
	}

	

}
