package com.poker.libraries;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Config 
{
	public static WebDriver driver = null;
	public static ProjectSpecific ps = null;
	public static Logger APP_LOGS = null;
	public static Properties CONFIG = null;
	public static Properties OR = null;
	public static HashMap<String, String> hm = new HashMap<String, String>();

	@BeforeSuite
	public void preCondition() throws IOException {
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Loading Property files");
		CONFIG = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir") 	+ "//src//main//java//com//poker//config//config.properties");
		CONFIG.load(ip);

		OR = new Properties();
		ip = new FileInputStream(System.getProperty("user.dir") 	+ "//src//main//java//com//poker//config//OR.properties");
		OR.load(ip);
		APP_LOGS.debug("Loaded Property files successfully");
		String browserType = CONFIG.getProperty("browserType");

		if (browserType.equalsIgnoreCase("Mozilla"))
		{
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			System.out.println("Firefox Launching");
			APP_LOGS.debug("Launching Browser");
			Reporter.log("Opening Firefox", true);
		}
		else if(browserType.equalsIgnoreCase("Chrome"))
		{
			String exePath = "F:\\MavenSelenium\\Poker\\Drivers\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", exePath);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			System.out.println("Chrome Launching");
			APP_LOGS.debug("Chrome Browser");
			Reporter.log("Opening Chrome", true);
		}

		ps = new ProjectSpecific(driver, APP_LOGS, OR, CONFIG, hm);
	}

	@AfterSuite
	public void afterSuiteMethod() throws IOException
	{
		driver.quit();
		APP_LOGS.debug("Quits the session");
		Generic.writeRes("./ModuleController/Test.xlsx", hm);
	}

}
