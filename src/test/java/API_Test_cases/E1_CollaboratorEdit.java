package API_Test_cases;

import static com.jayway.restassured.RestAssured.given;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

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

public class E1_CollaboratorEdit 
{
	
	static int i;
	static int j=0;
	int row =1;int col =0;
	
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
		Dbconnection.dbstartup();
	}

  @BeforeClass
  public void setBaseUri () 
  {

	  reports = ExtentManager.GetExtent("http://34.214.158.70:32855/impp/imerit/collaborator/edit/0");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/collaborator/edit/0"; //ITEST API uri will start from 'imerit'
	  Log.startLogForThisCase("API Testing Resource collaborator/edit");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E1_CollaboratorEdit.xlsx");
	  System.out.println(RestAssured.baseURI);
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
  public void postString(Hashtable <String,String> TestData) 
  {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
 Date date = new Date();    
  String userCode=TestData.get("userCode");
  String memberCode=TestData.get("memberCode");
  String engagementDetailCode=TestData.get("engagementDetailCode");
  String roleParameter=TestData.get("roleParameter");

 
  String jsonreq;
	  
	  jsonreq="{\"userCode\":\""+userCode+"\","
			  +"\"memberCode\":\""+memberCode+"\","
			  +"\"engagementDetailCode\":\""+engagementDetailCode+"\","
	    	    +"\"roleParameter\":\""+roleParameter+"\"}";
	    	  
	  
	 test.info("Starting API 34.214.158.70:32855/impp/imerit/collaborator/edit/0");
	 
	Response res  =
    given()//.log().all()
    .body(jsonreq)
    .when()
    .contentType (ContentType.JSON)
    .post();
   
	String message = res.then()
    .contentType(ContentType.JSON)
    .extract().asString();
	
	System.out.println(message);
	
	 
	
	test.info(jsonreq);

	 System.out.println(jsonreq);
	 
	
	IOExcel.setExcelStringData(row, col, jsonreq, "Sheet2"); 
	 col++;
	 System.out.println ("Status code "+res.statusLine());
	 IOExcel.setExcelStringData(row, col, res.statusLine(), "Sheet2");
	 test.info("Status code "+res.statusLine());
	 col++;
	 System.out.println (res.asString());
	 IOExcel.setExcelStringData(row, col, res.asString(), "Sheet2"); 
	 test.info("JSON RESPONSE "+res.asString());
	 col++;
	 int time = (int)res.getTimeIn(TimeUnit.MILLISECONDS);
	 test.info("API Response time : "+Integer.toString(time)+" ms");
	 IOExcel.setExcelStringData(row,col,Integer.toString(time),"Sheet2");
	 col++;
	 
	 
	 col=0;
	 row++;
	 
	 
	 if(res.statusCode()!=200)
	 {
		  test.fail("Test failed due to error code "+res.statusCode());
	 }
	
  
	
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
	  
	  String query="select c.user_code,a.is_active,\r\n" + 
	  		"\r\n" + 
	  		"json_extract(a.role_parameter, '$.write'),\r\n" + 
	  		"b.engagement_detail_code ,\r\n" + 
	  		"a.start_date,\r\n" + 
	  		"a.end_date\r\n" + 
	  		"from live_2912.impp_project_engagement_role_tr a,\r\n" + 
	  		"live_2912.impp_project_engagement_detail b,\r\n" + 
	  		"live_2912.impp_user_auth c,\r\n" + 
	  		"live_2912.impp_member d\r\n" + 
	  		"where a.user_id=c.id\r\n" + 
	  		"and b.id=a.engagement_detail_id\r\n" + 
	  		"and c.member_id=d.id\r\n" + 
	  		 "and b.engagement_detail_code in ('"+engagementDetailCode+"')\r\n" + 
	  		 "and c.user_code=('"+memberCode+"')\r\n" + 
	  		"and a.is_active=1" ;
	   
	  
	  /*String query="select c.user_code,a.role_parameter,b.engagement_detail_code from live_2912.impp_project_engagement_role_tr a,\r\n" + 
	  "live_2912.impp_project_engagement_detail b,\r\n" + 
	  "live_2912.impp_user_auth c,\r\n" + 
	  "live_2912.impp_member d\r\n" + 
	  "where a.user_id=c.id\r\n" + 
	  "and b.id=a.engagement_detail_id\r\n" + 
	  "and c.member_id=d.id\r\n" + 
	  "and b.engagement_detail_code in ('"+engagementDetailCode+"')\r\n" + 
	  "and c.user_code=('"+memberCode+"')\r\n"; */ 
	  int is_active=0;
	  String role_parameter = null;
	  	 System.out.println(query);
		try {
			stmt=Dbconnection.con.createStatement(); 
			rs=stmt.executeQuery(query);
			
			resultsetnotempty =rs.next();
				if(resultsetnotempty)
				{
					is_active=rs.getInt(2);
					role_parameter=rs.getString(3);
				System.out.println("user_code "+rs.getString(1)+" "
									+"is_active "+rs.getInt(2)+" "
									+"role_parameter "+rs.getString(3)+" "
									+"engagement_detail_code "+rs.getString(4)	
									
						          );
				System.out.println("Query Executed");
				}
				else System.out.println("No row returned by sql query");
		
			
		} catch (SQLException e) {
			
			
			e.printStackTrace();
		}  
		 
	  //DB ENDS*******************************************
		//Assertion
		try {
			if(resultsetnotempty)
			{
			if((is_active==1)&&(role_parameter.equalsIgnoreCase("true")&&roleParameter.equalsIgnoreCase("write"))) //if allocation call
				{
					
					test.pass("Collaborator Write permission granted . In db \"role_parameter\" is changed to "+role_parameter+" and \"is_active\"="+is_active);
				}
			else if((is_active==1)&&(role_parameter.equalsIgnoreCase("false")&&roleParameter.equalsIgnoreCase("read")))
				{
				
				test.pass("Collaborator Read permission granted. In db \"role_parameter\" is changed to "+role_parameter+" and \"is_active\"="+is_active);
				}
			}
			else 
			{
				test.fail("Test Failed...");
				
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	 
	}

 @AfterClass
 public void teardown()
 {
	  reports.flush();
	 try {
		Dbconnection.con.close();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println(e);
	}  
 }


}
