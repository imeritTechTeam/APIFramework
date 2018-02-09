package API_Test_cases;

import static com.jayway.restassured.RestAssured.given;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
import Utilities.PathUtility;

public class IMIS_login_logout_db {
	
	static int i;
	static int j=0;
	int row =1;int col =0;
	int count=1;
	
	Statement stmt,stmt2;
	ResultSet rs;int rs2;
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

	  reports = ExtentManager.GetExtent(PathUtility.BaseUrl+"IMIS LOGIN LOGOUT WITH DB UPDATE");
	  RestAssured.baseURI="http://34.214.158.70:32865/impp/imerit/public/project/signin/0"; //ITEST
	//  IOExcel.excelSetup(".\\TestData\\imis.xlsx");
	  
	  
	  String ExcelFile=System.getProperty("Excelfile");
	  System.out.println("Excel file argument"+ExcelFile);
	 // String event=TestData.get("event");
	  if(ExcelFile.contains("login1"))
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
	  }
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
	}*/
	  
	  
  JSONObject jsonreq= new JSONObject();  
  //JSONArray allocateMember= new JSONArray(); 
  //JSONArray unAllocateMembers= new JSONArray(); 
  
  String subProjectCode=TestData.get("subProjectCode");
  String empId=TestData.get("empId");
  String projectCode=TestData.get("projectCode");
  String eventtime =TestData.get("time");
  
  String event=System.getProperty("event");
  System.out.println("event from sheet "+eventtime);
  

  test = reports.createTest("API IMIS "+event+" "+count);
  count++;
  //JSON Creation
  
  jsonreq.put("subProjectCode",subProjectCode);
  jsonreq.put("empId",empId);
  jsonreq.put("projectCode",projectCode);
  jsonreq.put("event",event);
  

	 
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
	  
	
	  
	  String query="select * from impp_ng_v1.impp_imis_user_project_log order by id desc limit 1";
	  
	  	int id=0;
	  	String empcode=null;
	  	System.out.println(query);
		try {
			stmt=Dbconnection.con.createStatement(); 
			rs=stmt.executeQuery(query);
			//System.out.println("Query Executed");
			resultsetnotempty =rs.next();
				if(resultsetnotempty)
				{
					id=rs.getInt(1);
					empcode=rs.getString(2);
				System.out.println("id "+rs.getInt(1)+" "
									+"emp_code "+rs.getString(2)+" "
									+"imis_project_code "+rs.getString(4)+" "
									+"imis_subproject_code "+rs.getString(5)+" "
									+"event "+rs.getString(6)+" "
									+"event_time "+rs.getTimestamp(7)
						);
					System.out.println("Sql query executed");
				}
				else System.out.println("No row returned by sql query");
		
			
		} catch (SQLException e) {
			
			
			e.printStackTrace();
		}  
		
		//Update query *********************************
		 
		  String query2="update impp_ng_v1.impp_imis_user_project_log set event_time='"+eventtime+"' \r\n" + 
		  		"where id="+id+" and emp_code='"+empcode+"'";
		  
		  System.out.println(query2);
		 	
			try {
				stmt2=Dbconnection.con.createStatement(); 
				rs2=stmt.executeUpdate(query2);
				//System.out.println("Query Executed");
				//resultsetnotempty =rs2.next();
					if(rs2==-1)
					{
						System.out.println("Update query failed");
						
					}
					else 
						System.out.println("Update query executed");
			
				
			} catch (SQLException e) {
				
				
				e.printStackTrace();
			}  
		
		 
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
