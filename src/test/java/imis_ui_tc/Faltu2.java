package imis_ui_tc;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.paulhammant.ngwebdriver.NgWebDriver;

import Utilities.ExtentManager;
import Utilities.RetryCountIfFailed;

public class Faltu2 {
WebDriver driver;
JavascriptExecutor jsex;
NgWebDriver ngdriver;
ExtentReports reports;
ExtentTest test;
ExtentManager ob;
List<String> rowList ; 
//String project_time;
//Email email;
	
	@BeforeClass
	public void setup()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver236\\chromedriver_236.exe");	
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\debo\\git\\Dframework\\DFramework\\Drivers\\chromedriver_236.exe");		
		
		//ob = new ExtentManager();
		/*email = new Email();*/
		reports = ExtentManager.GetExtent("Impp_Regression");
		//reports = ob.GetExtent("IMPP_E5_Regression");
	}
	
	@Test
	@RetryCountIfFailed(5)
	public void impp() throws InterruptedException
	{
		//report setup
		rowList= new ArrayList<String>();
		Date d = new java.util.Date();
	    Calendar cal1 = Calendar.getInstance();
	    cal1.setTime(d);
	    cal1.add(Calendar.DAY_OF_YEAR, -1);
	    Date previousDate = cal1.getTime();	    
		test = reports.createTest("Resources with Huge Hours for "+previousDate);
		
		//driver setups
		driver = new ChromeDriver();		
		driver.get("https://www.google.co.in");
		driver.manage().window().maximize();
		jsex= (JavascriptExecutor) driver;
		ngdriver= new NgWebDriver(jsex);
				
		//google login
		driver.findElement(By.xpath("//a[@id='gb_70']")).click();		
		WebDriverWait wait= new WebDriverWait(driver,10);
		
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("debapriyo.halder@imerit.net");
		driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']"))).sendKeys("imerit2359");	
		driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		
		//IMPP starts
		driver.get("https://impp.imerit.net/");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='g-overlay']"))).click();		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(text(),'menu')]"))).click();
		
		WebElement ele=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Rostering')]")));
		jsex.executeScript("arguments[0].click();",ele);
		
		WebElement ele2=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Roster Allo')]")));
		jsex.executeScript("arguments[0].click();",ele2);		
		
		WebElement ele4=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='project-list']")));
		Thread.sleep(2000);
		
		WebElement eleq=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='previous_day']")));
		jsex.executeScript("arguments[0].click();",eleq);
		//jsex.executeScript("arguments[0].click();",eleq);
		Select selproj = new Select(ele4);
		List<WebElement> projects = selproj.getOptions();
		
		for(int k=2;k<projects.size();k++)
		{
			
			projects.get(k).click();
			//selproj.selectByVisibleText("Alegion Car Analysis");
			System.out.println("Project Name: "+projects.get(k).getText());
			
			//Thread.sleep(1000);
	
			/*driver.findElement(By.xpath("//input[@id='picker']")).click();
			driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr[2]/td[4]")).click();*/
			
			
			
			WebElement ele3=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='project-shift']")));
			Select selshift = new Select(ele3);
			//List<WebElement> shift = selshift.getOptions();
			
			for(int z=1;z<=3;z++)
			{
			selshift.selectByValue(Integer.toString(z));
		//	test.info();
			//	shift.get(z).click();
			//	System.out.println("Shift: "+shift.get(k).getText());
				try {
					driver.findElement(By.xpath("//button[contains(text(),'Show')]")).click();
					
					WebElement ele5;
					
						ele5 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='all-branch-list']")));
					
					if(ele5.isDisplayed())
					{
						//
						String xpth="//li[contains(@class,'relative overflow grid ng-scope')]";
						String ul="//li[contains(@class,'relative overflow grid ng-scope')]/div/following-sibling::div[2]/ul[1]";
						String xpathstr="//li[contains(@class,'relative overflow grid ng-scope')]/div/following-sibling::div[2]/ul/li/p/label/following-sibling::label[4]/span/b";
						
						 List <WebElement> branch=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath(xpth))));
						
						for(int i=0;i<branch.size();i++)
						{		
							//BRANCH LEVEL*****************
							try {
							WebElement parentul=branch.get(i).findElement(By.xpath(".//div/following-sibling::div[2]/ul[1]"));
							
							List <WebElement> lis=wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentul, By.tagName("li")));
						//	List <WebElement> lis=parentul.findElements(By.tagName("li"));
							//LINK LEVEL*************************
							//System.out.println("List size"+lis.size());
							for(int j=0;j<lis.size();j++)
							{
								try {
								ngdriver.waitForAngularRequestsToFinish();
								lis.get(j).getText();
								String emp_code=lis.get(j).findElement(By.xpath("//li["+(j+1)+"]/p/label[2]/span[1]")).getAttribute("innerText");			
								String name=lis.get(j).findElement(By.xpath("//li["+(j+1)+"]/p/label[2]/span[2]")).getAttribute("innerText");
								String project_time=lis.get(j).findElement(By.xpath("//li["+(j+1)+"]/p/label[5]/span[1]")).getAttribute("innerText");
								int a = Integer.parseInt(project_time.replaceAll("\\s+","").substring(0, 2));
								System.out.println("PROJECT: "+projects.get(k).getText()+" SHIFT: "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));

								if(a>20)
								{
									
								//	test.info("Project: "+projects.get(k).getText()+" Shift "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));
								//	li.add("Project: "+projects.get(k).getText()+" Shift "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));
									rowList.add(emp_code+" : "+name+" : "+projects.get(k).getText()+" Project Time: "+project_time);
									System.out.println("PROJECT: "+projects.get(k).getText()+" SHIFT: "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));
								}
								}
								catch(Exception e)
								{
									System.out.println("Link Level exception"+e);
									//e.printStackTrace();
								}
								
							}
							}
							catch(Exception e)
							{
								System.out.println("Branch Level exception"+e);
							}
							
							if(i<(branch.size()-1))
							{
								branch.get(i+1).click();			
							}
							Thread.sleep(1000);
						}
					}
					} catch (Exception e) {
						
						System.out.println("Data not found probably"+e);
					}
			}
		}
		
		//Removing duplicates in list and printing to report
		 Set<String> hs = new HashSet<>();
		    hs.addAll(rowList);
		    rowList.clear();
		    rowList.addAll(hs);
		    
		    for (String row : rowList) {
		        System.out.println((row));
		        test.info(row);
		    }
		
		
		
	}
	
	@AfterClass
	public void afterparty()
	{
		
		 reports.flush();
		 ExtentManager.copyFile("C:\\Program Files (x86)\\Jenkins\\workspace\\E5 Regression\\");
//		 email.sendEmail(ob.filePath);
		 
		 
		 driver.quit();
	}

}


