package com.poker.users;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.HashMap;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.poker.libraries.Config;
import com.poker.libraries.Generic;

public class UpdateUserBalance extends Config {
	public static HashMap<String, String> hm = new HashMap<String, String>();
	@Test
	public void updateUserBalanceTest() throws IOException,
			InterruptedException {

		String moduleControllerPath = "./ModuleController/Test.xlsx";
		String mainModule = Generic.getCellValue(moduleControllerPath, "Main",1, 0);
		String es = Generic.getCellValue(moduleControllerPath, "Main", 1, 1);

		String testCaseName = Generic.getCellValue(moduleControllerPath,mainModule, 6, 0);
		String testcase_es = Generic.getCellValue(moduleControllerPath,mainModule, 6, 1);
		
		hm.put(testCaseName, "flag");
		if (es.equalsIgnoreCase("yes")) {

			if (testcase_es.equalsIgnoreCase("yes")) {

				String testDataPath = "./test_data/User.xlsx";
				APP_LOGS.debug("***********************" + "TestCase " + " \" " + testCaseName + " \" " + " starts executing " + "***********************");
		
				int rc = Generic.getRowCount(testDataPath, testCaseName);

				for (int i = 1; i <= rc; i++) {

					APP_LOGS.debug("***** Data Driving from row no : " + i 	+ " *****");
					String EmailID = Generic.getCellValue(testDataPath,testCaseName, i, 0);
					String Balance = Generic.getCellValue(testDataPath,testCaseName, i, 1);
					String sql ="UPDATE `playerprofile` SET `balance` = '"+Balance+"' WHERE emailid ='"+EmailID+"'";
					
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
						APP_LOGS.debug(sql);
						st.executeUpdate(sql);
						APP_LOGS.debug("Update Player Balance as "+Balance+" for the EmailID "+EmailID);
						APP_LOGS.debug("Database updated successfully ");
						conn.close();
						APP_LOGS.debug("Disconnected from Database");
					} 
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
					ps.waitFor(30l);
										
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
		String testcaseName = "UpdateUserBalance";
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
