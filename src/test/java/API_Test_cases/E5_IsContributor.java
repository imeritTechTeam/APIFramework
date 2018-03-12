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

public class E5_IsContributor {
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

	  reports = ExtentManager.GetExtent("API Test Results of http://34.214.158.70:32845/impp/imerit/set/contributor/0");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/impp/imerit/set/contributor/0"; //ITEST
	  Log.startLogForThisCase("Start Cotributor setter");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E5_contributorSetter.xlsx");

	  
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
	 
	 //System.out.println("memberCode"+res.body().path("userDetails.memberCode"));
	 String memberCoded=res.body().path("userDetails.memberCode").toString();	 
	 IOExcel.setExcelStringData(row, col,memberCode , "Sheet2");
	 col++;
	 
	 //System.out.println("firstName"+res.body().path("userDetails.firstName"));
	 String firstNamed=res.body().path("userDetails.firstName").toString();	
	 IOExcel.setExcelStringData(row, col,firstNamed , "Sheet2");
	 col++;
	 
	 
	 //System.out.println("lastName"+res.body().path("userDetails.lastName"));
	 String lastNamed=res.body().path("userDetails.lastName").toString();	
	 IOExcel.setExcelStringData(row, col,lastNamed , "Sheet2");
	 col++;
	 
//	 System.out.println("departmentName"+res.body().path("userDetails.departmentName"));
	 String departmentNamed=res.body().path("userDetails.departmentName").toString();	
	 IOExcel.setExcelStringData(row, col,departmentNamed , "Sheet2");
	 col++;
	 
	// System.out.println("branchName"+res.body().path("userDetails.branchName"));
	 String branchNamed=res.body().path("userDetails.branchName").toString();	
	 IOExcel.setExcelStringData(row, col,branchNamed , "Sheet2");
	 col++;
	 
	// System.out.println("branchCode"+res.body().path("userDetails.branchCode"));
	 String branchCoded=res.body().path("userDetails.branchCode").toString();	
	 IOExcel.setExcelStringData(row, col,branchCoded , "Sheet2");
	 col++;
	 
	 int jsoncol=col;
	 //***************json detail extraction*******************
	 
	// System.out.println("Json array count "+res.body().path("assignedEngagement.size()"));
	/* System.out.println("engagementDetailCode "+res.body().path("assignedEngagement[0].engagementDetailCode"));
	 System.out.println("projectCode "+res.body().path("assignedEngagement[0].projectCode"));
	 System.out.println("projectTitle "+res.body().path("assignedEngagement[0].projectTitle"));
	 System.out.println("engagementTitle "+res.body().path("assignedEngagement[0].engagementTitle"));
	 System.out.println("name "+res.body().path("assignedEngagement[0].name"));
	 System.out.println("noOfRunningJob "+res.body().path("assignedEngagement[0].noOfRunningJob")); */
	// System.out.println("noOfRunningJob "+res.body().path("assignedEngagement[0].allocation"));
	
	 //***********Writing Data to Excel************
	 int jsonarrcount =res.body().path("assignedEngagement.size()");
	 if(jsonarrcount==0)
	 {
		 row++;
	 }
	 else
	 {
		 for(i=0;i<jsonarrcount;i++)
		 	{
					 
			 String index = Integer.toString(i);
			 
			 String engagementDetailCoded=res.body().path("assignedEngagement["+index+"].engagementDetailCode").toString();
			 test.info(engagementDetailCoded);
			 
			 String projectCoded=res.body().path("assignedEngagement["+index+"].projectCode");
			 test.info(projectCoded);
			 
			 String projectTitled=res.body().path("assignedEngagement["+index+"].projectTitle");
			 test.info(projectTitled);
			 
			 String engagementTitled=res.body().path("assignedEngagement["+index+"].engagementTitle");
			 test.info(projectTitled);
			 
			 String named=res.body().path("assignedEngagement["+index+"].name");
			 test.info(projectTitled);
			
			 int allocationcount =res.body().path("assignedEngagement["+index+"].allocation.size()");
			// System.out.println("inner array count "+allocationcount);
			 int inncol=col;
			 if(allocationcount>0)
			 {
				 for(int m=0;m<allocationcount;m++)
				 {
					 
				 String index2 = Integer.toString(m);	 
				// System.out.println("nodeName "+res.body().path("assignedEngagement["+index+"].allocation["+index2+"].nodeName"));
				// System.out.println("nodeId "+res.body().path("assignedEngagement["+index+"].allocation["+index2+"].nodeId"));
				String allocationd=res.body().path("assignedEngagement["+index+"].allocation["+index2+"].nodeName").toString();
				 IOExcel.setExcelStringData(row, col, allocationd, "Sheet2");	
				 col++;
				
				String nodeIdD=res.body().path("assignedEngagement["+index+"].allocation["+index2+"].nodeId").toString();
				 IOExcel.setExcelStringData(row, col, nodeIdD, "Sheet2");	
				 col++;
				 col=inncol;
				 row++;
				 }
			 }
			 
			/* String noOfRunningJobd=res.body().path("assignedEngagement["+index+"].noOfRunningJob");
			 IOExcel.setExcelStringData(row, col, noOfRunningJobd, "Sheet2");	
			 col++;
			 		 */
			 //reset the col back
			 col=jsoncol;
			// row++;
		 	}
	 }
	 
	
		 col=0;
		
		// System.out.println("row "+row);
	
  }
 
 @AfterClass
 public void teardown()
 {
	 reports.removeTest(test);
	 reports.flush();

 }

}
