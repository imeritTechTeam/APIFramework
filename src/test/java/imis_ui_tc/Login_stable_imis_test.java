package imis_ui_tc;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utilities.DriverFactory;
import Utilities.DriverManager;
import Utilities.ExtentManager;
import Utilities.IOExcel;
import Utilities.PathUtility;

public class Login_stable_imis_test {
	ExtentReports reports;
	WebDriver driver;
	ExtentTest test;
	int count;
	int row =1;int col =0;
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
	long millis=0;

	 @BeforeMethod
	    public void handleTestMethodName(Method method)
	    {  
	    	String methodName = method.getName();
	      //  logger.info("Ready to run test case: " + methodName);
/*
	    	if (DriverManager.getDriver()==null) {
	            DriverManager.setWebDriver(DriverFactory.createInstance());      
	    	}*/
	    }
	
	@BeforeClass
	public void setup()
	{

		System.setProperty("webdriver.chrome.driver", "D:\\selenium jars\\chromedriver.exe");
		reports = ExtentManager.GetExtent("LOGIN");
		IOExcel.excelSetup(".\\TestData\\imistest.xlsx"); 		

	}
	
	
	@Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class )
	public void loginToProj2(Hashtable <String,String> TestData) throws InterruptedException
	{
		//Thread currentThread = Thread.currentThread();
		// System.out.println("id of the thread is " + currentThread.getId());   
	
		if (DriverManager.getDriver()==null) {
            DriverManager.setWebDriver(DriverFactory.createInstance());      
    	}
		
		
		driver = DriverManager.getDriver();
		//LOGIN PAGE
		String empid=TestData.get("empId");
		String password=TestData.get("password");
		System.out.println("Proj2 "+empid+" "+password+" Thread "+Thread.currentThread().getId());
		
		
		try {
			driver.get("https://35.190.145.182");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//input[@name='username']")).sendKeys(empid);
			driver.findElement(By.xpath("//input[@name='userpass']")).sendKeys(password);
			System.out.println("eXECUTED "+empid+" "+password+" Thread "+Thread.currentThread().getId());
			Thread.sleep(4000);
			driver.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception"+e);
		}
		driver.close();
	}
	
/*	@Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
	public void loginToProj1(Hashtable <String,String> TestData) throws InterruptedException
	{
		 
		

		driver = DriverManager.getDriver();
		//LOGIN PAGE
		String empid=TestData.get("empId");
		String password=TestData.get("password");
		System.out.println("Proj2 "+empid+" "+password);
		
		
		driver.get("https://35.190.145.182");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(empid);
		driver.findElement(By.xpath("//input[@name='userpass']")).sendKeys(password);
		driver.close();
		
	}*/
	
	
	/*@SuppressWarnings("unchecked")
	@Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
	public void loginToProj(Hashtable <String,String> TestData) throws InterruptedException
	{
		 
		
		//driver = new ChromeDriver();
		driver = DriverManager.getDriver();
		//LOGIN PAGE
		String empid=TestData.get("empId");
		String password=TestData.get("password");
		
		test = reports.createTest("Emp code: "+empid);
		count++;
		
		driver.get("https://35.190.145.182");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(empid);
		driver.findElement(By.xpath("//input[@name='userpass']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@value='Login']")).click();
		
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
	
		//Project Selection
		Select sel = new Select (driver.findElement(By.xpath("//select[@id='emp_center']")));
		sel.selectByVisibleText("Saltlake");
		
		Select sel2 = new Select (driver.findElement(By.xpath("//select[@id='domain_id']")));
		sel2.selectByVisibleText("General");
		
		Thread.sleep(3000);
		Select sel3 = new Select(driver.findElement( By.xpath("//select[@id='project_id']")));
		sel3.selectByVisibleText("SGA [ITS_003]");
		
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		WebElement button= wait.until(ExpectedConditions.presenceOfElementLocated(By.id("project_login_btn")))	;
		button.submit();
		
		IOExcel.setExcelStringData(row, col,empid , "Sheet2"); 
        col++;
		//get time
		LocalDateTime now = LocalDateTime.now();
        System.out.println("Click time "+dtf.format(now));
        IOExcel.setExcelStringData(row, col, dtf.format(now), "Sheet2"); 
        col++;
        
        
        //login success check
        boolean loginstatus2=false;
		//Thread.sleep(1000);
		try {
			WebDriverWait wait2 = new WebDriverWait(driver, 10);
			
			
			
		//	loginstatus2=driver.findElement(By.xpath("//div[contains(text(),'SUCCESSFULLY LOGIN')]")).isDisplayed();
			WebElement ele2= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'SUCCESSFULLY LOGIN')]")))	;
			loginstatus2=ele2.isDisplayed();
			
			System.out.println("Project login status"+loginstatus2);
			LocalDateTime now2 = LocalDateTime.now();
	        System.out.println("Login time "+dtf.format(now2));
	        IOExcel.setExcelStringData(row, col, dtf.format(now2), "Sheet2"); 
	        col++;
	        
	        long millis2 = now.until(now2, ChronoUnit.MILLIS);
	        System.out.println("Time taken to login: "+millis2+" ms");
	        test.info("Time taken to login: "+millis2+" ms");
	        
	        IOExcel.setExcelStringData(row, col, String.valueOf(millis2), "Sheet2"); 
	        col++;
	        
	        IOExcel.setExcelStringData(row, col, "Passed", "Sheet2"); 
	        col++;
	       
	        col=0;
	        row++;
	        
	        millis=millis+millis2;
			
			if(loginstatus==true)
			{
				test.info(driver.findElement(By.xpath("//div[contains(text(),'SUCCESSFULLY LOGIN')]")).getText());
				driver.close();
			}
		} catch (Exception e) {
			
			System.out.println(e);
			LocalDateTime now3 = LocalDateTime.now();
	        System.out.println("Login time "+dtf.format(now3));
	        IOExcel.setExcelStringData(row, col, dtf.format(now3), "Sheet2"); 
	        col++;
	        
	        IOExcel.setExcelStringData(row, 4, "Failed", "Sheet2"); 
	        col++;
	        
	        col=0;
	        row++;
		}
        
       
      
		test.pass("Login Success");
		//Click logout
		driver.findElement(By.xpath("//i[@class='fa fa-gears']")).click();
		driver.findElement(By.xpath("//button[@id='logout_btn']"));
		
		Thread.sleep(3000);
	//	System.out.println("Log in operation successfull for : "+username);
		driver.close();
	}
	
	
	*/
	
	
	@AfterClass
	public void teardown() throws InterruptedException
	{
		reports.flush();
		/*System.out.println("closing now....");
		System.out.println("Total login time taken is: "+millis+" ms");
		IOExcel.setExcelStringData(row, col, "Total login time taken is: "+millis+" ms", "Sheet2"); 
        col++;*/
		//driver.quit();
	}


}
