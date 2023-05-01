package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestng {
	String browser;
	String url;
	WebDriver driver;
	By USER_NAME_FIELD = By.xpath("//input[@id='username']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By LOGIN_FIELD = By.xpath("//button[@name='login']");
	By DASHBORAD_HEADER_FIELD = By.xpath("//h2[contains(text(),' Dashboard ')]");
	By CUSTOMER_MENU_FIELD = By.xpath("//span[contains(text(),'Customers')]");
	By ADD_CUSTOMER_FIELD = By.xpath("//a[contains(text(),'Add Customer')]");
	By Add_CONTACT_FIELD = By.xpath("//h5[contains(text(),'Add Contact')]");
	By CONTACT_ASSERTION = By.xpath("//h2[contains(text(),' Contacts ')]");
	By COMPANY_DROPDOWN_FIELD = By.xpath("//select[@id='cid']");
	By FULL_NAME_FIELD = By.xpath("//*[@id=\"account\"]");
	By EMAIL_INPUt_Field = By.xpath("//*[@id=\"email\"]");
	By COUNTRY_DROPDOWN_FIELD = By.xpath("//select[@id='country']");
//	Test/Mock data:
	String username = "demo@techfios.com";
	String password = "abc123";
	String fullname = "malak ali";
	String company = "Techfios";
	String email = "demo@techfios.com";
	String country = "United States";

	@BeforeClass
	public void readconfig() {

		try {
			InputStream input = new FileInputStream("src\\test\\java\\config\\config.properties");

			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver,chrome,driver", "driver\\ chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Edge")) {
//			if i want to run my driver By EDGE
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver = new EdgeDriver();

		} else {
			System.out.println("Driver Configuration Required");
		}

		driver.manage().deleteAllCookies();
//		this url it will keep changing for example if we running in QA invirmonet it will different id we run if stage inviroment 
		driver.get(url);
//		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

//	@Test
	public void logintest() {
		driver.findElement(USER_NAME_FIELD).sendKeys(username);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(LOGIN_FIELD).click();
		waitForElement(driver, 10, DASHBORAD_HEADER_FIELD);
		Assert.assertEquals(driver.findElement(DASHBORAD_HEADER_FIELD).getText(), "Dashboard", "the page is not found");

	}

	@Test
	public void addcustomer() {

		logintest();
		driver.findElement(CUSTOMER_MENU_FIELD).click();
		driver.findElement(ADD_CUSTOMER_FIELD).click();

//		waitForElement(driver, 10, Add_contact_filed);
		Assert.assertEquals(driver.findElement(Add_CONTACT_FIELD).getText(), "Add Contact", "this page is not found");

		driver.findElement(FULL_NAME_FIELD).sendKeys(fullname + generaterandomnum(999));
//		Select sel = new Select(driver.findElement(COMPANY_DROPDOWN_FIELD));
//		sel.selectByVisibleText(company);
		selectDropDwon(driver.findElement(COMPANY_DROPDOWN_FIELD), company);
		driver.findElement(EMAIL_INPUt_Field).sendKeys(generaterandomnum(999) + email);
//		Select sel1 = new Select(driver.findElement(COUNTRY_DROPDOWN_FIELD));
//		sel1.selectByVisibleText(country);
		selectDropDwon(driver.findElement(COUNTRY_DROPDOWN_FIELD), country);

	}

	// we create this method to avoid the repetition so we don'T have to MAKE
	// EXPLICT WAIT every time
	private void waitForElement(WebDriver driver2, int timeinsencound, By element) {
		WebDriverWait wait = new WebDriverWait(driver, timeinsencound);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));

	}

// we create this method to avoid the repetition so we don'y have to make Select class every time 
	private void selectDropDwon(WebElement element, String visibleText) {
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);

	}

	private int generaterandomnum(int boundrynum) {

		Random rnd = new Random();
		int generatednum = rnd.nextInt(boundrynum);
		return generatednum;

	}

}
