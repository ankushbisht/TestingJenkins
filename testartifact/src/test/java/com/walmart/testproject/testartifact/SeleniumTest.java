package com.walmart.testproject.testartifact;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.simple.JSONObject;
import org.junit.*;

import com.gurock.testrail.APIClient;

public class SeleniumTest {

	private static WebDriver driver;
	private static APIClient client;
	
	@BeforeClass
	public static void initializeWebDriver()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		driver = new  ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		client = new APIClient("https://ankushbisht.testrail.net/");
		client.setUser("ank.bst@msn.com");
		client.setPassword("P@ssw0rd");

	}
	
	@Test
	public void TestGoogle()
	{
		System.out.println("Starting test " + new Object(){}.getClass().getEnclosingMethod().getName());
		driver.get("https://www.google.co.in");
		driver.findElement(By.xpath("//*[@id='lst-ib']")).sendKeys("Testrail and Selenium Integration");
		driver.findElement(By.xpath("//*[@id='sblsbb']/button")).click();
		driver.findElement(By.xpath("//*[@id='rso']/div[2]/li[1]/div/h3/a")).click();
		String actualText = driver.findElement(By.xpath("//*[@id='topic-title']/div/div/h1/a")).getText();
		System.out.println("Actual Text on Page " + actualText);
		if(actualText.equals("Support for Selenium"))
		{
			try{
				JSONObject case1 = (JSONObject) client.sendGet("get_case/3");
				System.out.println(case1.get("title"));
				
				Map result1 = new HashMap();
				result1.put("status_id", new Integer(1));
				result1.put("comment", "This test is successful!");
				JSONObject post1 = (JSONObject) client.sendPost("add_result_for_case/3/3", result1);
				
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
		}
		
		else
		{
			try{
				JSONObject case1 = (JSONObject) client.sendGet("get_case/3");
				System.out.println(case1.get("title"));
				
				Map result1 = new HashMap();
				result1.put("status_id", new Integer(5));
				result1.put("comment", "This test got failed!");
				JSONObject post1 = (JSONObject) client.sendPost("add_result_for_case/3/1", result1);
				
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
		}
		
		System.out.println("Finished test " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@AfterClass
	public static void closeBrowser()
	{
		System.out.println("Closing browser");
		driver.quit();
	}
}
