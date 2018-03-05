package imis_ui_tc;

import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utilities.ExtentManager;
import Utilities.IOExcel;

public class Logout_Stable_imis_test {
	ExtentReports reports;	
	WebDriver driver;
	ExtentTest test;
	int count;
	
	@BeforeClass
	public void setup()
	{

		System.setProperty("webdriver.chrome.driver", "D:\\selenium jars\\chromedriver.exe");

		reports = ExtentManager.GetExtent("LOGOUT");
		IOExcel.excelSetup(".\\TestData\\imistest.xlsx"); 
		

	}
	
	@SuppressWarnings("unchecked")
	@Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
	public void logoutToProj(Hashtable <String,String> TestData) throws InterruptedException
	{
		/*int randomNum = ThreadLocalRandom.current().nextInt(30000, 200000 );
		System.out.println("Sleeping for : "+randomNum);
		Thread.sleep(randomNum);*/
		
		String empid=TestData.get("empId");
		String password=TestData.get("password");
		
		test = reports.createTest("Emp code: "+empid);
		count++;
		
		driver = new ChromeDriver();
		driver.get("https://35.190.145.182");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(empid);
		driver.findElement(By.xpath("//input[@name='userpass']")).sendKeys(password);
	//	Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@value='Login']")).click();
		
		Timeouts wait2 = driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		boolean loginstatus=false;
		Thread.sleep(1000);
		try {
			loginstatus=driver.findElement(By.xpath("//p[@id='login_msg_p']")).isDisplayed();
			System.out.println("loginstatus"+loginstatus);
			
			if(loginstatus==true)
			{
				test.fail("Login Failed: "+driver.findElement(By.xpath("//p[@id='login_msg_p']")).getText());
				driver.close();
			}
		} catch (Exception e) {
			
			System.out.println(e);
		}
		
		Thread.sleep(1000);
		 WebDriverWait wait = new WebDriverWait(driver, 2);
	/*	 wait.ignoring(NoSuchSessionException.class);*/
		 wait.ignoring(NoSuchElementException.class);
		 
		 
		// List<WebElement> radio = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//input[@name='login_option']"))));
		WebElement ele;
		
			try {
				ele = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@value='restore_session']"))));

				if(ele.isDisplayed())
				{
					ele.click();
					Thread.sleep(1000);
					//2nd alert box
					Alert promptAlert  = driver.switchTo().alert();
					promptAlert.accept();
					//promptAlert.dismiss();
					/*WebElement ele2 = driver.findElement(By.xpath("//input[@value=\"destroy_session\"]"));
					ele.click();*/
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		
		
		
		
		
		
		//Click logout
		driver.findElement(By.xpath("//i[@class='fa fa-gears']")).click();
	//	Thread.sleep(2000);
		//WebElement logoutbutton = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@id='logout_btn']"))));
		//logoutbutton.submit();
	//	Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@id='logout_btn']")).click();
		test.pass("Logout Success");
		driver.close();
	//	Thread.sleep(2000);
	

	}
	
	@AfterClass
	public void teardown() throws InterruptedException
	{
		reports.flush();
		System.out.println("closing now....");
		driver.quit();
	}
/*
	  @DataProvider(name="DataSource")
	  public static Object [][] exceldatasource()
	  {
		  int count=IOExcel.Getrowcount("Sheet1");
		  System.out.println("row count"+count);
		  
		  
		  Object arr[][]=new Object[count][2];
		  
		  int n=0;int k=0;
		  for(int i=1;i<=count;i++)
		  {
			  k=0;
			  for(int j=0;j<=1;j++)
			  { 
				 arr[n][k]= IOExcel.getExcelStringData(i, j,"Sheet1");
				 System.out.println(arr[n][k]);
				 k++;
				
			  }
			  n++;
		  }
		
		  
		  return arr;
		
	  }*/
	

}
