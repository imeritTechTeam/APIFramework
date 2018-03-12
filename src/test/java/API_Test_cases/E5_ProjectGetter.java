package API_Test_cases;

import org.apache.log4j.Logger;

/*
 * /impp/imerit/resource/user/details/0
This api is used to get details of a resource

*/

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.*;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import Utilities.ExtentManager;
import Utilities.IOExcel;
import Utilities.Log;
import Utilities.PathUtility;

public class E5_ProjectGetter {
	static int i;
	static int j=0;
	int row =1;int col =0;
	ExtentReports reports;
	ExtentTest test;
	String testCaseName;
	int count=1;
	Log logger=new Log();
	JSONObject jsonreq= new JSONObject(); 

  @BeforeClass
  public void setBaseUri () {

	  reports = ExtentManager.GetExtent("API Test Results of http://34.214.158.70:32855/impp/imerit/imis/project/getter/0");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/imis/project/getter/0"; //ITEST
	 // logger  = Logger.getLogger(Log.class.getName());
	  logger.info("Start Project Getter");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E5_ProjectGetter.xlsx");

	  
  }
  
  @BeforeMethod
	public void init(Method method)
	{
		/*testCaseName =method.getName();*/
	  logger.startLogForThisCase(testCaseName);
		/*if(reports!=null)
		{
		test=reports.createTest(testCaseName);
		//System.out.println("Report created");
		}
		else System.out.println("reports obj is null");*/
		
	}


  @SuppressWarnings("unchecked")
  @Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
  public void postString (Hashtable<String,String> TestData) 
	{
	  
	try
	{
	  test = reports.createTest("API resource/user/details/0 TC"+count);
	  count++;
	  
	  String userCode=TestData.get("userCode");
	  
	  
	  test.info("Starting API http://34.214.158.70:32855/impp/imerit/imis/project/getter/0");
	  
	//JSON Creation
	  jsonreq.put("userCode", userCode); 
	
	  System.out.println(jsonreq);
	  
	Response res  =
    given()
    .body(jsonreq)
    .when()
    .contentType(ContentType.JSON)
    .post()
    .then()
    .contentType(ContentType.JSON)
    .extract()
	.response();
	 
	 test.info("JSON REQUEST"+jsonreq.toJSONString());
	 
	 
	 System.out.println (res.asString()); //PRINT JSON RESPONSE
	 
	 if(res.statusCode()==200)
	 {
		 test.pass("Test Case Passed  ");
	 }
	 else
	 {
		 test.fail("Test failed due to error code "+res.statusCode());
		 Assert.assertEquals(res.statusCode(), 200);
	 }
	 Assert.assertTrue(res.statusCode()==200);
 
	test.info(res.asString());
	}
	catch(Exception e)
	{
		System.out.println(e);
		logger.error(e.toString());
	}
  }
 
 @AfterClass
 public void teardown()
 {
	 reports.removeTest(test);
	 reports.flush();

 }

}
