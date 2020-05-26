package com.test;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoggingDemo 
{
	public static Logger APP_LOGS = null;

	public static void main(String[] args) 
	{
		String exePath = "F:\\Office\\MavenProject\\Poker\\Drivers\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		APP_LOGS = Logger.getLogger("devpinoyLogger");
		APP_LOGS.debug("Loading Property files");
        driver.get("http://healthunify.com/bmicalculator/");
        APP_LOGS.debug("opening webiste");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        APP_LOGS.debug("entring weight");
		 driver.findElement(By.name("wg")).sendKeys("87");
		 APP_LOGS.debug("selecting kilograms");
        driver.findElement(By.name("opt1")).sendKeys("kilograms");
        APP_LOGS.debug("selecting height in feet");
        driver.findElement(By.name("opt2")).sendKeys("5");
        APP_LOGS.debug("selecting height in inchs");
        driver.findElement(By.name("opt3")).sendKeys("10");
        APP_LOGS.debug("Clicking on calculate");
        driver.findElement(By.name("cc")).click();

        APP_LOGS.debug("Getting SIUnit value");
        String SIUnit = driver.findElement(By.name("si")).getAttribute("value");
        APP_LOGS.debug("Getting USUnit value");
        String USUnit = driver.findElement(By.name("us")).getAttribute("value");
        APP_LOGS.debug("Getting UKUnit value");
        String UKUnit = driver.findElement(By.name("uk")).getAttribute("value");
        APP_LOGS.debug("Getting overall description");
        String note = driver.findElement(By.name("desc")).getAttribute("value");
     
        System.out.println("SIUnit = " + SIUnit);
        System.out.println("USUnit = " + USUnit);
        System.out.println("UKUnit = " + UKUnit);
        System.out.println("note = " + note); 
		driver.quit();
	}
}