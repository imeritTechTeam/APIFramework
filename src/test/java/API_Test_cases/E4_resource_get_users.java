package API_Test_cases;

import static com.jayway.restassured.RestAssured.given;

import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import Utilities.ExtentManager;
import Utilities.IOExcel;
import Utilities.Log;
import Utilities.PathUtility;

public class E4_resource_get_users {
	static int i;
	static int j=0;
	int row =1;int col =0;
	ExtentReports reports;
	ExtentTest test;
	String testCaseName;

  @BeforeClass
  public void setBaseUri () {

	  reports = ExtentManager.GetExtent("http://34.214.158.70:32845/impp/imerit/resource/get/users/0 ");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/resource/get/users/0"; //ITEST
	  Log.startLogForThisCase("API Testing Resource /resource/get/users");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E4_resource_getusers_.xlsx");
	  

	  
  }
  
  @BeforeMethod
 	public void init(Method method)
 	{
 		testCaseName =method.getName();
 		Log.startLogForThisCase(testCaseName);
 		if(reports!=null)
 		{
 		test=reports.createTest(testCaseName);
 		//System.out.println("Report created");
 		}
 		else System.out.println("reports obj is null");
 		
 	}

  @Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
  public void postString (Hashtable<String,String> TestData) 
	{
	  String userCode=TestData.get("userCode");
	  String engagementDetailCode=TestData.get("engagementDetailCode");
	  String nodeId=TestData.get("nodeId"); 
	  
	  
	  test.info("Starting API http://34.214.158.70:32845/impp/imerit/resource/get/users/0");
	//"http://34.214.158.70:32845/impp/imerit/action/access/0"
	 Response res  =
    given().log().all()
   /* .body ("{\"userCode\":\"techteam@imerit.net\","
    +"\"memberCode\":\"animesh@imerit.net\"}"
    )*/
    .body ("{\"userCode\":\""+userCode+"\","
    	    +"\"engagementDetailCode\":\""+engagementDetailCode+"\","  	    
    	    +"\"nodeId\":\""+nodeId+"\"}"
    	   )
    .when ()
    .contentType (ContentType.JSON)
    .post ()
    .then()
    .contentType(ContentType.JSON)
    .extract()
	.response();
	 
	
	 
	 test.info("{\"userCode\":\""+userCode+"\","
	    	    +"\"engagementDetailCode\":\""+engagementDetailCode+"\","  	    
	    	    +"\"nodeId\":\""+nodeId+"\",}");
	 IOExcel.setExcelStringData(row, col, "{\"userCode\":\""+userCode+"\","
	    	    +"\"engagementDetailCode\":\""+engagementDetailCode+"\","  	    
	    	    +"\"nodeId\":\""+nodeId+"\",}", "Sheet2"); 
	 col++;
	 System.out.println ("Status code "+res.statusLine());
	 test.info("Status Code "+res.statusLine());
	 IOExcel.setExcelStringData(row, col, res.statusLine(), "Sheet2");
	 col++;
	/* System.out.println (res.asString());
	 IOExcel.setExcelStringData(row, col, res.asString(), "Sheet2"); 
	 col++;*/
	 test.info("JSON RESPONSE "+res.asString());
	 int time = (int)res.getTimeIn(TimeUnit.MILLISECONDS);
	 test.info("API RESPONSE TIME "+Integer.toString(time)+"ms");
	 IOExcel.setExcelStringData(row,col,Integer.toString(time),"Sheet2");
	 col++;
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
	 int jsoncol=col;
	 
	/* System.out.println("branchCode"+res.body().path("[0].branchCode"));
	 System.out.println("firstName"+res.body().path("[0].firstName"));*/
	 //json detail extraction
	 
	// System.out.println("Json array count "+res.body().path("$.size()"));
	 int jsonarrcount =res.body().path("$.size()");
	 if(jsonarrcount==0)
	 {
		 row++;
	 }
	 else
	 {
		 for(i=0;i<jsonarrcount;i++)
		 {
			
			 
			 String index = Integer.toString(i);
			 
			 String memberCoded=res.body().path("["+index+"].memberCode");
			 IOExcel.setExcelStringData(row, col, memberCoded, "Sheet2");	
			 col++;
			 
			 
			 
			 String data=res.body().path("["+index+"].branchCode").toString();
			 IOExcel.setExcelStringData(row, col, data, "Sheet2"); 
			 col++;
			 
			/* String branchNamed=res.body().path("["+index+"].branchName");
			 IOExcel.setExcelStringData(row, col, branchNamed, "Sheet2");	
			 col++;
			 
			 String data2=res.body().path("["+index+"].firstName");
			 IOExcel.setExcelStringData(row, col, data2, "Sheet2");	
			 col++;
			 
			 String lastNamed=res.body().path("["+index+"].lastName");
			 IOExcel.setExcelStringData(row, col, lastNamed, "Sheet2");	
			 col++;*/
			 
			 Object isAllocated = (res.body().path("["+index+"].isAllocated"));
			 IOExcel.setExcelStringData(row, col, isAllocated.toString(), "Sheet2");	
			 col++;
			 
			 //reset the col back
			 col=jsoncol;
			 row++;
		 }
	 }
	
		 col=0;row++;
		 System.out.println("row "+row);
	
  }


 @AfterClass
 public void teardown()
 {
	  reports.flush();

 }
}
