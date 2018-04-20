package imis_ui_tc;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utilities.ExtentManager;

public class TestCls {
	ExtentReports reports;
	ExtentTest test;

	
	@BeforeClass
	public void setup()
	{
		
		reports = ExtentManager.GetExtent("TestEmail");
	}
	@Test
	public void fun() 
	{
		 test = reports.createTest("E5 DEtail Regression");
		 System.out.println("Hello jenkins");
		 test.info("Hello jenkins");
		 test.info("Hello jenkins hgkhg");
		 test.info("Hello jenkins hgkhhghkgkglg");
		 test.info("Hello jenkins hgkhhghkgkglg");
		 
		
	}
	
	@AfterClass
	public void afterparty()
	{
		
		 reports.flush();
		 ExtentManager.copyFile("C:\\Program Files (x86)\\Jenkins\\workspace\\Email test\\");
		
	}

}
