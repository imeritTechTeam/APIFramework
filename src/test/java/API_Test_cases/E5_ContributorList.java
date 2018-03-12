package API_Test_cases;

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

public class E5_ContributorList {
	static int i;
	static int j=0;
	int row =1;int col =0;
	ExtentReports reports;
	ExtentTest test;
	String testCaseName;
	int count=1;
	JSONObject jsonreq= new JSONObject(); 

  @BeforeClass
  public void setBaseUri () {

	  reports = ExtentManager.GetExtent("API Test Results of http://34.214.158.70:32845/impp/imerit/get/contributor/roster/0");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/get/contributor/roster/0"; //ITEST
	  Log.startLogForThisCase("Resource resource/contributor/roster/0");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E5_contributor_rosterr.xlsx");

	  
  }
  
  @BeforeMethod
	public void init(Method method)
	{
		/*testCaseName =method.getName();*/
		Log.startLogForThisCase(testCaseName);
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
	  
	  test = reports.createTest("API resource/user/details/0 TC"+count);
	  count++;
	  
	  String userCode=TestData.get("userCode");
	  String memberCode=TestData.get("memberCode");
	  
	  test.info("Starting API http://34.214.158.70:32845/impp/imerit/get/contributor/roster/0");
	  
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
	 
	 //Writing Data to Excel
/*	 
	 IOExcel.setExcelStringData(row, col,jsonreq.toJSONString(), "Sheet2"); 
	 col++;
	 System.out.println ("Status code "+res.statusLine()); //STATUS CODE
	 test.info("Status Code "+res.statusLine());
	 IOExcel.setExcelStringData(row, col, res.statusLine(), "Sheet2");
	 col++;
	 test.info("JSON RESPONSE "+res.asString());
	 IOExcel.setExcelStringData(row, col, res.asString(), "Sheet2"); 
	 col++;
	 int time = (int)res.getTimeIn(TimeUnit.MILLISECONDS); //RESPONSE TIME
	 test.info("API RESPONSE TIME "+Integer.toString(time)+"ms");
	 IOExcel.setExcelStringData(row,col,Integer.toString(time),"Sheet2");
	 col++;	 
	 
	 System.out.println (res.asString()); //PRINT JSON RESPONSE
	 */
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
	}
 
 @AfterClass
 public void teardown()
 {
	 reports.removeTest(test);
	 reports.flush();

 }

}
