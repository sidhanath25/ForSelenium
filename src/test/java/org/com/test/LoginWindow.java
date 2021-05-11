package org.com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginWindow {
	private WebDriver driver;
	private static String baseUrl = "https://www.facebook.com/";
	WebDriverWait wait;

	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "E:\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(baseUrl);
		wait = new WebDriverWait(driver, 120);

	}

	@Test(priority = 0)
	public void verifyValidationMessage() {
		WebElement loginButton = driver.findElement(By.name("login"));
		loginButton.click();
		String getValidationMessage = driver.findElement(By.xpath("//div[@class='_9ay7']")).getText();
		System.out.println(getValidationMessage);
		String expected = "The email address or mobile number you entered isn't connected to an account. Find your account and log in.";
		Assert.assertEquals(getValidationMessage, expected);
	}

	@Test(priority = 1)
	public void verifyValidationMessageWhenPasswordMissing() {
		WebElement emailTextField = driver.findElement(By.id("email"));
		emailTextField.sendKeys("sidhanath.more@gmail.com");
		WebElement loginButton = driver.findElement(By.name("login"));
		loginButton.click();
		WebElement element = driver.findElement(By.xpath("//div[@class='_9ay7']"));
		WebElement ele = wait.until(ExpectedConditions.visibilityOf(element));
		String getValidationMessage = ele.getText();
		System.out.println(getValidationMessage);
		String expected = "The password that you've entered is incorrect. Forgotten password?";
		Assert.assertEquals(getValidationMessage, expected);
	}

	@Test(priority = 3)
	public void verifyValidationMessageWhenInvalidEmail() {
		WebElement emailTextField = driver.findElement(By.id("email"));
		emailTextField.clear();
		emailTextField.sendKeys("1234");

		WebElement login = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("login"))));
		login.click();

		WebElement alertBox = wait.until(
				ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='fsl fwb fcb']"))));
		Assert.assertNotNull(alertBox);

	}

	@AfterTest
	public void tearDown() {
		driver.close();

	}
}
