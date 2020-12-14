package com.qa.tdla.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

class GoogleTEST {
	
	private static String URL = "google.com";
	private static WebDriver driver;
	
	@BeforeAll
	public static void setup() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		ChromeOptions cOptions = new ChromeOptions();
		cOptions.setHeadless(false);
		
		driver = new ChromeDriver(cOptions);
		driver.manage().window().maximize();
	}
	
	@AfterAll
	public static void tearDown() {
		driver.quit();
	}

	@Test
	void searchGoogle() {
		driver.get(URL);
		
	}

}
