package API_Test_cases;

import static com.jayway.restassured.RestAssured.given;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import Utilities.Dbconnection;
import Utilities.ExtentManager;
import Utilities.IOExcel;
import Utilities.Log;
import Utilities.PathUtility;

public class IMIS_login_logout {
	
	static int i;
	static int j=0;
	int row =1;int col =0;
	int count=1;
	
	Statement stmt;
	ResultSet rs;
	int r=1; int c=0;
	ExtentReports reports;
	ExtentTest test;
	String testCaseName;
	Boolean resultsetnotempty;
	
	@BeforeSuite
	public void startup()
	{
		Dbconnection.dbstartup("amazon");
	}

  @BeforeClass
  public void setBaseUri () {

	  reports = ExtentManager.GetExtent(PathUtility.BaseUrl+"impp/imerit/public/project/signin/");
	  RestAssured.baseURI="http://34.214.158.70:32865/impp/imerit/public/project/signin/0"; //ITEST
	//  IOExcel.excelSetup(".\\TestData\\imis.xlsx");
	  
	  
	  String ExcelFile=System.getProperty("Excelfile");
	  System.out.println("Excel file argument"+ExcelFile);
	  IOExcel.excelSetup(".\\TestData\\"+ExcelFile+".xlsx"); 
	  
	 // String event=TestData.get("event");
	 /* if(ExcelFile.contains("login1"))
	  {
		  IOExcel.excelSetup(".\\TestData\\loginscenario1.xlsx"); 
	  }
	  else if(ExcelFile.contains("login2"))
	  {
	  IOExcel.excelSetup(".\\TestData\\loginscenario2.xlsx");
	  }
	  else if(ExcelFile.contains("login3"))
	  {
	  IOExcel.excelSetup(".\\TestData\\loginscenario3.xlsx");
	  }
	  else if(ExcelFile.contains("login4"))
	  {
	  IOExcel.excelSetup(".\\TestData\\loginscenario4.xlsx");
	  }
	  else
	  {
		  System.out.println("Test data excel cant be opened due to incorrect/missing Maven argument");
	  }*/
  }
 


  @SuppressWarnings("unchecked")
@Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
  public void postString(Hashtable <String,String> TestData) 
  {
	  
	 /* int randomNum = ThreadLocalRandom.current().nextInt(30000, 200000 );
	  System.out.println("Sleeping for : "+randomNum);
	  try {
		Thread.sleep(randomNum);
	} catch (InterruptedException e) {
		System.out.println("Sleep issues"+e);
	}
	  */
	  
  JSONObject jsonreq= new JSONObject();  
  //JSONArray allocateMember= new JSONArray(); 
  //JSONArray unAllocateMembers= new JSONArray(); 
  
  String subProjectCode=TestData.get("subProjectCode");
  String empId=TestData.get("empId");
  String projectCode=TestData.get("projectCode");
  
  String event=System.getProperty("event");
  String ip=System.getProperty("ip");
  System.out.println(event);
  

  test = reports.createTest("API IMIS "+event+" "+count);
  count++;
  //JSON Creation
  
  jsonreq.put("subProjectCode",subProjectCode);
  jsonreq.put("empId",empId);
  jsonreq.put("projectCode",projectCode);
  jsonreq.put("event",event);
  jsonreq.put("ip",ip);
  

	 
 test.info("Starting http://34.214.158.70:32865/impp/imerit/public/project/signin/0");
	
	  //API Execution...
	  
	Response res  =
    given()
    .body(jsonreq)
    .when ()
    .contentType (ContentType.JSON)
    .post ()
    .then()
    .contentType(ContentType.JSON)
    .extract()
	.response();
	 
	//Adding Created JSON in Report 
	
	 test.info(jsonreq.toJSONString());
	 System.out.println(jsonreq);
	 
	//Writing Response to report and Console
	 
	
	// System.out.println ("Status code "+res.statusLine());
	 test.info("Status code "+res.statusLine());

	 System.out.println ("JSON Response "+res.asString());
	 test.info("JSON RESPONSE "+res.asString());
	
	 int time = (int)res.getTimeIn(TimeUnit.MILLISECONDS);
	 test.info("API Response time : "+Integer.toString(time)+" ms");

//	 System.out.println("message:"+res.body().path("message"));
//	 System.out.println("appcode:"+res.body().path("appcode"));
	 
	 if((res.statusCode()==200)&&(res.body().path("message").toString().contains("Success")))
	 {
		 test.pass(event+" Success");
	 }
	 else
	 {
		 test.fail(event+" Failure");
	 }
	 
	
	 
	 

	  
	  
	  //DB************************************************
	  
	
	  
	/*  String query="select distinct\r\n" + 
	  		"d.email,\r\n" + 
	  		"a.engagement_detail_code,\r\n" + 
	  		"c.*,\r\n" + 
	  		"b.id as flowid\r\n" + 
	  		"from live_2912.impp_project_engagement_detail a ,live_2912.impp_project_engagement_flow b,\r\n" + 
	  		"live_2912.impp_engagement_allocation c,live_2912.impp_member d\r\n" + 
	  		"where a.engagement_detail_code='"+engagementDetailCode+"'\r\n" + 
	  		"and b.engagement_detail_id=a.id and c.engagement_flow_id =b.id\r\n" + 
	  		"and c.member_id=d.id\r\n" + 
	  		"and c.is_active in (1)\r\n" + 
	  		"and d.email in ('"+usermail+"') \r\n" + 
	  		"and c.node_cid in ('"+nodeId+"')";
	  
	  
	  //	System.out.println(query);
		try {
			stmt=Dbconnection.con.createStatement(); 
			rs=stmt.executeQuery(query);
			//System.out.println("Query Executed");
			resultsetnotempty =rs.next();
				if(resultsetnotempty)
				{
				System.out.println("email "+rs.getString(1)+" "
									+"engagement_code "+rs.getString(2)+" "
									+"node_cid "+rs.getString(5)+" "
									+"start_date "+rs.getDate(7)+" "
									+"end_date "+rs.getDate(8)+" "
									+"is_active "+rs.getInt(9)
						);
					System.out.println("Sql query executed");
				}
				else System.out.println("No row returned by sql query");
		
			
		} catch (SQLException e) {
			
			
			e.printStackTrace();
		}  */
		 
	  //DB ENDS*******************************************
		//Assertion
	/*	try {
			
			
			if(res.statusCode()==200)
			{
				if((unAllocateMembers.isEmpty())&&(rs.getInt(9)==1)&&(rs.getDate(8)==null)) //if allocation call
					{
						test.pass("Resource assigned succesfull . In db \"is_active\" is changed to "+rs.getInt(9)+" and \"end_date\"="+rs.getDate(8));
					}
				else if((allocateMem.isEmpty())&&(resultsetnotempty==false)&&(res.statusCode()==200)) //if unallocate call
					{
						test.pass("Resource Unassign succesfull ");
					}
				else 
					{
						test.fail("Test Failed");
						
					}
			}
			else
			{
				
					test.fail("Test Failed due to status code"+res.statusCode());
				
			}
			
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}*/
	 
	}

 @AfterClass
 public void teardown()
 {
	// reports.removeTest(test);
	  reports.flush();
	 try {
		Dbconnection.con.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println(e);
	}  
 }


}
