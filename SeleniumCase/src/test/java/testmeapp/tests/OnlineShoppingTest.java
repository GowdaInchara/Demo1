package testmeapp.tests;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.text.SimpleDateFormat;
import testmeapp.utility.Drivers;
//===============================================================================================================================
public class OnlineShoppingTest {

	WebDriver driver;
	ExtentHtmlReporter htmlreporter;
	ExtentReports reports;
	ExtentTest logger;
//--------------------------------------------------------------------------------------------------------------------------
	@BeforeTest()
	public void startReportBeforeTest()
	{    
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-ms");
		String path =System.getProperty("user.dir")+"/extent-reports/"+sdf.format(new Date())+".html";
	    htmlreporter=new ExtentHtmlReporter(path);
	    reports=new ExtentReports();
	    reports.attachReporter(htmlreporter);
	    reports.setSystemInfo("username","inchara");
	    reports.setSystemInfo("host", "localhost");
	    reports.setSystemInfo("Environment", "Test Environment");
	    htmlreporter.config().setReportName("TestMe Report");
		driver=Drivers.getDriver("ie");
		driver.get("http://10.232.237.143:443/TestMeApp/fetchcat.htm");
		String url=driver.getCurrentUrl();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	
//===================================================================================================================================
	@Test(priority=1)
    public void testRegistration()
	 {
		
		driver.findElement(By.partialLinkText("SignUp")).click();
		driver.findElement(By.id("userName")).sendKeys("incharagwda");
		driver.findElement(By.id("firstName")).click();
		Assert.assertEquals(driver.findElement(By.id("err")).getText(),"Available");
		driver.findElement(By.id("firstName")).sendKeys("Inchara");
		driver.findElement(By.id("lastName")).sendKeys("Gowda");
		driver.findElement(By.id("password")).sendKeys("Password456");
		driver.findElement(By.id("pass_confirmation")).sendKeys("Password456");
		driver.findElement(By.cssSelector("input[value='Female']")).click();
		driver.findElement(By.id("emailAddress")).sendKeys("askme@email.com");
		driver.findElement(By.id("mobileNumber")).sendKeys("9696589666");
		//----------------------------------------------------------------------------------------------------------------------
		driver.findElement(By.cssSelector("img[class='ui-datepicker-trigger']")).click();
		Select mon=new Select(driver.findElement(By.cssSelector("select[data-handler='selectMonth']")));
		mon.selectByIndex(2);
		Select yr=new Select(driver.findElement(By.cssSelector("select[data-handler='selectYear']")));
		yr.selectByValue("1996");;
		driver.findElement(By.linkText("20")).click();	
		//---------------------------------------------------------------------------------------------------------
		driver.findElement(By.name("address")).sendKeys("dsfggjhhjgjkhmnhygtfghdfgfyhgjnhgjhkjkGHJK");
		Select sa= new Select(driver.findElement(By.name("securityQuestion")));
		sa.selectByIndex(2);
		driver.findElement(By.id("answer")).sendKeys("sadsfdf");
		driver.findElement(By.name("Submit")).click();
		logger=reports.createTest("Registration");
		logger.log(Status.INFO, "Registartion successful");
		String title=driver.getTitle();
		Assert.assertEquals(title, "Login");
      }
//=============================================================================================================================	
	  
	@Test(priority=2)
	public void testLogin() {
		driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
		driver.findElement(By.name("userName")).sendKeys("incharagwda");
		driver.findElement(By.id("password")).sendKeys("Password456");
		driver.findElement(By.name("Login")).click();
		logger=reports.createTest("Login");
		logger.log(Status.INFO, "Login successful");
		String title1=driver.getTitle();
		Assert.assertEquals(title1, "Home");
		Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/div/div/div[2]/div/ul")).getText().contains("inchara"));
	}
//===============================================================================================================================	
	@Test(priority=3)
	public void testCart() throws InterruptedException 
	{
	 
	  Actions mouse=new Actions(driver);
	  mouse.moveToElement(driver.findElement(By.partialLinkText("All Categories"))).perform();
	  mouse.moveToElement(driver.findElement(By.partialLinkText("Electronics"))).click().perform();
	  Thread.sleep(2000);
	  mouse.moveToElement(driver.findElement(By.partialLinkText("Head Phone"))).click().perform();
	  driver.findElement(By.xpath("/html/body/section/div/div/div[2]/div/div/div/div[2]/center/a")).click();
	  logger=reports.createTest("Cart");
	  logger.log(Status.INFO, "Product has been added to cart successfully");
	  driver.findElement(By.xpath("//*[@id=\"header\"]/div[1]/div/div/div[2]/div/a[2]")).click(); 
	  Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"cart\"]/tbody/tr/td[1]/div/div/h4")).getText().contains( "Headphone"));
	  logger=reports.createTest("Cart");
	  logger.log(Status.INFO, "Deliver address shown correctly");
	  driver.findElement(By.xpath("//*[@id=\"cart\"]/tfoot/tr[2]/td[5]/a")).click();
	  
	}
//==============================================================================================================================	
	@Test(priority=4)
	public void testPayment() 
	{
		driver.findElement(By.xpath("/html/body/b/div/div/div[1]/div/div[2]/div[3]/div/form[2]/input")).click();
		driver.findElement(By.xpath("//*[@id=\"swit\"]/div[1]/div/label/i")).click();
		logger=reports.createTest("Payment");
		logger.log(Status.INFO, "Bank selected Successful");
		driver.findElement(By.id("btn")).click();
		driver.findElement(By.name("username")).sendKeys("123456");
		driver.findElement(By.name("password")).sendKeys("Pass@456");
		driver.findElement(By.cssSelector("input[value='LOGIN']")).click();	
		driver.findElement(By.name("transpwd")).sendKeys("Trans@456");
		driver.findElement(By.cssSelector("input[value='PayNow']")).click();
		logger=reports.createTest("Payment");
		logger.log(Status.INFO, "Payment Successful");
		//List<WebElement> dynamicElement = driver.findElements(By.xpath("/html/body/b/section/div/div/div/table[1]/tbody/tr/td[1]"));
		//if(dynamicElement.size() != 0){
		//System.out.println("Element present");
		Assert.assertEquals(driver.findElement(By.xpath("//div/div/div/div[2]/p")).getText(),"Your order has been confirmed");
		logger=reports.createTest("Payment");
		logger.log(Status.INFO, "Order placed Successful");
		driver.findElement(By.partialLinkText("SignOut")).click();
		//}
	}
//=================================================================================================================================
		@AfterMethod
		public void getResultAfterMethod(ITestResult result)  
		{
			if(result.getStatus()==ITestResult.FAILURE)
			{
				logger.log(Status.FAIL,"THE TEST IS FAILED");
				}
			else if(result.getStatus()==ITestResult.SUCCESS)
			{
				logger.log(Status.PASS,"THE TEST IS PASSED");}
			else if (result.getStatus()==ITestResult.SKIP)
				
				{
					logger.log(Status.FAIL,"THE TEST IS FAILED");}
		}
	//-----------------------------------------------------------------------------------------------------------------------	
		@AfterTest
		public void endReportAfterTest()
		{
			reports.flush();
			driver.close();
		
		}	
}
