  package API_Test_cases;

import static com.jayway.restassured.RestAssured.given;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import Utilities.IOExcel;
import Utilities.Log;
import Utilities.Dbconnection;
import Utilities.ExtentManager;
import Utilities.PathUtility;

public class E4_resource_allocate 
{
	
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

	  reports = ExtentManager.GetExtent(PathUtility.BaseUrl+"imerit/resource/allocate/new/0");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/resource/allocate/new/0"; //ITEST
	  Log.startLogForThisCase("API Testing Resource /resource/allocate/");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E4_resoure_allocate.xlsx");
	  
  }
  @BeforeMethod
	public void init(Method method)
	{
	/*	testCaseName =method.getName();*/
		Log.startLogForThisCase(testCaseName);
	/*	if(reports!=null)
		{
		test=reports.createTest(testCaseName);
		//System.out.println("Report created");
		}
		else System.out.println("reports obj is null");
		*/
	}


  @SuppressWarnings("unchecked")
@Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
  public void postString(Hashtable <String,String> TestData) 
  {
	  test = reports.createTest("API resource/allocate/0 Test Case: "+count);
	  count++;
	  
  JSONObject jsonreq= new JSONObject();  
  JSONArray allocateMember= new JSONArray(); 
  JSONArray unAllocateMembers= new JSONArray(); 
  
  String userCode=TestData.get("userCode");
  String allocateMem=TestData.get("allocateMember");
  String unAllocateMem=TestData.get("unAllocateMembers");
  String nodeId=TestData.get("nodeId");
  String engagementDetailCode=TestData.get("engagementDetailCode");
  
  //JSON Creation
  
  jsonreq.put("userCode",userCode);
  jsonreq.put("nodeId",nodeId);
  jsonreq.put("engagementDetailCode",engagementDetailCode);
  
	  if(unAllocateMem.isEmpty())
	  {
		  allocateMember.add(allocateMem);
		  unAllocateMembers.add(unAllocateMem);
	  
	  }
	  else
	  {
		  
		  unAllocateMembers.add(unAllocateMem);
	  }
	 
	  //Adding JSONArray 
	  
	  jsonreq.put("allocateMember",allocateMember);
	  jsonreq.put("unAllocateMembers",unAllocateMembers);
	 
	  test.info("Starting API 34.214.158.70:32845/impp/imerit/resource/allocate/0");
	
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
	 
	//Writing Response to Excel 
	 
	IOExcel.setExcelStringData(row, col, jsonreq.toJSONString(), "Sheet2"); 
	 col++;
	 System.out.println ("Status code "+res.statusLine());
	 IOExcel.setExcelStringData(row, col, res.statusLine(), "Sheet2");
	 test.info("Status code "+res.statusLine());
	 col++;
	 System.out.println ("JSON Response "+res.asString());
	 IOExcel.setExcelStringData(row, col, res.asString(), "Sheet2"); 
	 test.info("JSON RESPONSE "+res.asString());
	 col++;
	 int time = (int)res.getTimeIn(TimeUnit.MILLISECONDS);
	 test.info("API Response time : "+Integer.toString(time)+" ms");
	 IOExcel.setExcelStringData(row,col,Integer.toString(time),"Sheet2");
	 col++;
	 
	 
	 col=0;
	 row++;
	 
	 
	 /*if(res.statusCode()!=200)
	 {
		  test.fail("Test failed due to error code "+res.statusCode());
	 }*/
	
  
	
	 int jsoncol=col;
	 
	// System.out.println("nodeId"+res.body().path("[0].nodeId"));
	// System.out.println("nodeName"+res.body().path("[0].nodeName"));
	 //json detail extraction
	 
	// System.out.println("Json array count "+res.body().path("$.size()"));
	 int jsonarrcount;
	try {
		jsonarrcount = res.body().path("$.size()");
	
	 if(jsonarrcount==0)
	 {
		 row++;
	 }
	 else
	 {
		 for(i=0;i<jsonarrcount;i++)
		 {
					 
			 String index = Integer.toString(i);
			 String data=res.body().path("["+index+"].nodeId").toString();
			 IOExcel.setExcelStringData(row, col, data, "Sheet2"); 
			 col++;
			 
			 String data2=res.body().path("["+index+"].nodeName");
			 IOExcel.setExcelStringData(row, col, data2, "Sheet2");	
			 col++;
			 
			 try {
				IOExcel.setExcelStringData(row, col, rs.getString(1).toString(), "Sheet2"); 
				col++;
				IOExcel.setExcelStringData(row, col, rs.getString(2).toString(), "Sheet2"); 
				col++;
			} catch (SQLException e) {
				System.out.println(e);
			}
			 
			 //reset the col back
			 col=jsoncol;
			 row++;
		 }
	 }
	 
	 
	
		 col=0;
		 System.out.println("row "+row);
	} catch (Exception e1) {
		
		System.out.println(e1);
	}
	  
	  
	  //DB************************************************
	  
	  String usermail;
	  
	  if(allocateMem.isEmpty()) //For situations where we only allocate and deallocate mails are null. or vice versa
	  {
		  usermail=unAllocateMembers.toString() ;
	  }
	  else
	  {
		  usermail=allocateMem.toString();
	  }
	  
	  
	  String query="select distinct\r\n" + 
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
				/*System.out.println("email "+rs.getString(1)+" "
									+"engagement_code "+rs.getString(2)+" "
									+"node_cid "+rs.getString(5)+" "
									+"start_date "+rs.getDate(7)+" "
									+"end_date "+rs.getDate(8)+" "
									+"is_active "+rs.getInt(9)
						);*/
					System.out.println("Sql query executed");
				}
				else System.out.println("No row returned by sql query");
		
			
		} catch (SQLException e) {
			
			
			e.printStackTrace();
		}  
		 
	  //DB ENDS*******************************************
		//Assertion
		try {
			
			
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
		}
	 
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
