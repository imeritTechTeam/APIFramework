package imis_ui_tc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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

public class Bakwas {
	WebDriver driver;
	JavascriptExecutor jsex;
	NgWebDriver ngdriver;
	ExtentReports reports;
	ExtentTest test;
	String [][] data;
	String login;
	String logout;
	List<String> rowList ; 
		
		@BeforeClass
		public void setup()
		{
			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver236\\chromedriver_236.exe");		
			//System.setProperty("webdriver.gecko.driver", "D:\\chromedriver236\\geckodriver.exe");
			reports = ExtentManager.GetExtent("Impp_Detailed_Regression");
		}
		
		@Test
		@RetryCountIfFailed(5)
		public void impp() throws InterruptedException
		{
			rowList= new ArrayList<String>();
			test = reports.createTest("E5_Detail_Regression");
			//System.out.println("Window Size "+driver.manage().window().getSize());
			/* ChromeOptions options = new ChromeOptions(); 
			 options.addArguments("window-size=1036,744");
			driver = new ChromeDriver(options); */
			 driver = new ChromeDriver();
			//driver= new FirefoxDriver();
			driver.get("https://www.google.co.in");
			
			
			/*Dimension d = new Dimension(1382,744); 
			driver.manage().window().setSize(d);*/
			driver.manage().window().maximize();
			System.out.println("Window Size "+driver.manage().window().getSize());
			jsex= (JavascriptExecutor) driver;
			ngdriver= new NgWebDriver(jsex);
					
			driver.findElement(By.xpath("//a[@id='gb_70']")).click();
			
			WebDriverWait wait= new WebDriverWait(driver,17);
			
			driver.findElement(By.xpath("//input[@type='email']")).sendKeys("debapriyo.halder@imerit.net");
			driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']"))).sendKeys("imerit2359");
		
			driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
			
			driver.get("https://impp.imerit.net/");
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='g-overlay']"))).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[contains(text(),'menu')]"))).click();
			
			
			
			WebElement ele=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Rostering')]")));
			jsex.executeScript("arguments[0].click();",ele);
			
			WebElement ele2=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'Roster Allo')]")));
			jsex.executeScript("arguments[0].click();",ele2);
			
			//wait for page to load
			//jsex.executeScript("return document.readyState").equals("complete");
			
			WebElement ele4=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='project-list']")));
			Thread.sleep(2000);
			
			WebElement eleq=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='previous_day']")));
			jsex.executeScript("arguments[0].click();",eleq);
		//	jsex.executeScript("arguments[0].click();",eleq);
			
			Select selproj = new Select(ele4);
			List<WebElement> projects = selproj.getOptions();
			
			for(int k=2;k<projects.size();k++)
			{
				
				projects.get(k).click();
			//	selproj.selectByVisibleText("Alfa");
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
						WebElement el77=driver.findElement(By.xpath("//button[contains(text(),'Show')]"));
						jsex.executeScript("arguments[0].click();",el77);
						
						WebElement ele5;
						//Check if any branches are displayed
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
								//BRANCH LEVEL
								WebElement parentul=branch.get(i).findElement(By.xpath(".//div/following-sibling::div[2]/ul[1]"));
								
								List <WebElement> lis=wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parentul, By.tagName("li")));
								//Link level
								for(int j=0;j<lis.size();j++)
								{
									ngdriver.waitForAngularRequestsToFinish();
									String emp_code=lis.get(j).findElement(By.xpath("//li["+(j+1)+"]/p/label[2]/span[1]")).getAttribute("innerText");			
									String name=lis.get(j).findElement(By.xpath("//li["+(j+1)+"]/p/label[2]/span[2]")).getAttribute("innerText");
									String project_time=lis.get(j).findElement(By.xpath("//li["+(j+1)+"]/p/label[5]/span[1]")).getAttribute("innerText");
									
									//int a = Integer.parseInt(project_time.substring(0, 2));
									/*if(a>20)
									{*/
										
									//	test.info("Project: "+projects.get(k).getText()+" Shift "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));

										System.out.println("PROJECT: "+projects.get(k).getText()+" SHIFT: "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));
									//}
									
										
									//lis.get(j).findElement(By.xpath("//p/label[2]/span[2]")).click();
									
									 List<WebElement> ell = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(lis.get(j),By.xpath("//li["+(j+1)+"]/p/label[2]/span[2]")));	
									 ell.get(0).click();
									 //lis.get(j).findElement(By.xpath("//li["+(j+1)+"]/p/label[2]/span[2]")).click();
									 
									//Thread.sleep(1000);
									List<WebElement> dtlinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath("//li[@ng-repeat='log in loginDetails']"))));
									//System.out.println("details links size"+dtlinks.size());
									data= new String[dtlinks.size()][2];
									for(int x=0;x<dtlinks.size();x++)
									{
										try {
											
											//System.out.println(dtlinks.get(x).getText());
											login=dtlinks.get(x).findElement(By.xpath(".//b[1]")).getAttribute("innerText");
											data[x][0]=login;
											logout=dtlinks.get(x).findElement(By.xpath(".//b[2]")).getAttribute("innerText");
											data[x][1]=logout;
										//	System.out.println(login+" "+logout);
										} catch (Exception e) {
											
											System.out.println("array issue"+e);
										}
									}
								
									for(int w=0;w<data.length;w++)
									{
										for(int y=0;y<data.length;y++)
										{
											if(data[w][0].equals(data[y][0])&(w!=y))
											{
												/*test.info("Project: "+projects.get(k).getText()+" Shift "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));
												test.info("Duplicate Login Values found: "+data[w][0]);*/
												
												rowList.add(emp_code+" : "+name+" : "+projects.get(k).getText()+": Duplicate Login found: "+data[w][0]);
												System.out.println("Duplicate Login Values found: "+data[w][0]);
											}
											if(data[w][1].equals(data[y][1])&(w!=y))
											{
												/*test.info("Project: "+projects.get(k).getText()+" Shift "+selshift.getFirstSelectedOption().getText()+emp_code.replaceAll("\\s+","")+" "+name.replaceAll("\\s+","")+" Project Time: "+project_time.replaceAll("\\s+",""));
												test.info("Duplicate Logout Values found: "+data[w][1]);*/
												
												rowList.add(emp_code+" : "+name+" : "+projects.get(k).getText()+": Duplicate Logout found: "+data[w][1]);
												System.out.println("Duplicate Logout Values found: "+data[w][1]);
											}
											
										}
									}
								
								}
								
								if(i<(branch.size()-1))
								{
									branch.get(i+1).click();			
								}
								Thread.sleep(1000);
							}
						}
						} catch (Exception e) {
							
						//	System.out.println("Data not found probably"+e);
							e.printStackTrace();
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
			// ExtentManager.copyFile();
			 driver.quit();
		}

}
