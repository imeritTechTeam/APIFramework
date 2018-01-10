package API_Test_cases;

import static com.jayway.restassured.RestAssured.given;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
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

import Utilities.Dbconnection;
import Utilities.ExtentManager;
import Utilities.IOExcel;
import Utilities.Log;
import Utilities.PathUtility;

public class E4_resource_get_users {
	static int i;
	static int j=0;
	int row =1;int col =0;
	int count=1;
	ExtentReports reports;
	ExtentTest test;
	String testCaseName;
	Statement stmt;
	ResultSet rs ;
	int counter=0;


	
	//Starts the database connection
	@BeforeSuite
	public void startup()
	{
		Dbconnection.dbstartup();
	}
	
  @BeforeClass
  public void setBaseUri () {

	  reports = ExtentManager.GetExtent("http://34.214.158.70:32845/impp/imerit/resource/get/users/0");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/resource/get/users/0"; //ITEST
	  Log.startLogForThisCase("API Testing Resource /resource/get/users");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E4_resource_getusers_.xlsx");
	  

	  
  }
  
  @BeforeMethod
 	public void init(Method method)
 	{
 	/*	testCaseName =method.getName();*/
 		Log.startLogForThisCase(testCaseName);
 		/*if(reports!=null)
 		{
 		test=reports.createTest(testCaseName);
 		//System.out.println("Report created");
 		}
 		else System.out.println("reports obj is null");*/
 		
 	}

  @Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
  public void postString (Hashtable<String,String> TestData) 
	{
	  test = reports.createTest("API /resource/get/users/0 TC"+count);
	  count++;
	  String userCode=TestData.get("userCode");
	  String engagementDetailCode=TestData.get("engagementDetailCode");
	  String nodeId=TestData.get("nodeId"); 
	  
	  
	  test.info("Starting API http://34.214.158.70:32845/impp/imerit/resource/get/users/0");
	//"http://34.214.158.70:32845/impp/imerit/action/access/0"
	 Response res  =
    given()//.log().all()
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
	
	Object[][] apiarr = new Object[jsonarrcount][8];
	
	
	
	System.out.println("JSON Array count"+jsonarrcount);
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
			 apiarr[i][0]=memberCoded;
			// IOExcel.setExcelStringData(row, col, memberCoded, "Sheet2");	
			 col++;
			 
			 String data2=res.body().path("["+index+"].firstName");
			 apiarr[i][1]=data2;
			// IOExcel.setExcelStringData(row, col, data2, "Sheet2");	
			 col++;
			 
			 String data3=res.body().path("["+index+"].middleName");
			 apiarr[i][2]=data3;
			// IOExcel.setExcelStringData(row, col, data3, "Sheet2");	
			col++;
				 
			 String lastNamed=res.body().path("["+index+"].lastName");
			 apiarr[i][3]=lastNamed;
			// IOExcel.setExcelStringData(row, col, lastNamed, "Sheet2");	
			 col++;
			 
			 String empCode=res.body().path("["+index+"].empCode");
			 apiarr[i][4]=empCode;
			// IOExcel.setExcelStringData(row, col, empCode, "Sheet2");	
			col++;
				 
			 String data=res.body().path("["+index+"].branchCode").toString();
			 apiarr[i][5]=data;
			// IOExcel.setExcelStringData(row, col, data, "Sheet2"); 
			 col++;
			 
			 String branchNamed=res.body().path("["+index+"].branchName");
			 apiarr[i][6]=branchNamed;
			// IOExcel.setExcelStringData(row, col, branchNamed, "Sheet2");	
			 col++;
			 
			
			 
			 Object isAllocated = (res.body().path("["+index+"].isAllocated"));
			 apiarr[i][7]=(int)isAllocated;
			// IOExcel.setExcelStringData(row, col, isAllocated.toString(), "Sheet2");	
			 col++;
			 
			 //reset the col back
			 col=jsoncol;
			 row++;
		 }
	 }
	
	// Arrays.sort(apiarr);
	 //print apiarr
	 	/*for(int i=0;i<jsonarrcount;i++)
		{
			for(int z=0;z<8;z++)
			{
				System.out.print(apiarr[i][z]);
				
			}
			System.out.println("");
		}*/
	 
		 col=0;row++;
		// System.out.println("row "+row);
		 
		 
		 //**************DB************************************************************
		 String query ="select\r\n" + 
		 		"m.email,\r\n" + 
		 		"m.first_name,\r\n" + 
		 		"m.middle_name,\r\n" + 
		 		"m.last_name ,\r\n" + 
		 		"a.emp_code,\r\n" + 
		 		"brm.branch_code,\r\n" + 
		 		"brm.branch_name,\r\n" + 
		 		"\r\n" + 
		 		"\r\n" + 
		 		"(\r\n" + 
		 		"select distinct \r\n" + 
		 		"c.is_active\r\n" + 
		 		"from live_2912.impp_project_engagement_detail a ,live_2912.impp_project_engagement_flow b,\r\n" + 
		 		"live_2912.impp_engagement_allocation c,live_2912.impp_member d\r\n" + 
		 		"where a.engagement_detail_code='"+engagementDetailCode+"'\r\n" + 
		 		"and b.engagement_detail_id=a.id and c.engagement_flow_id =b.id\r\n" + 
		 		"and c.member_id=d.id\r\n" + 
		 		"and d.email in (m.email) \r\n" + 
		 		"and c.node_cid in ('"+nodeId+"') \r\n" + 
		 		")as is_allocated\r\n" + 
		 		"\r\n" + 
		 		"\r\n" + 
		 		"from live_2912.impp_employee a ,\r\n" + 
		 		"live_2912.impp_member m,\r\n" + 
		 		"live_2912.impp_member_branch_tr br ,\r\n" + 
		 		"live_2912.impp_branch brm \r\n" + 
		 		"where \r\n" + 
		 		"m.id=a.member_id \r\n" + 
		 		"and br.member_id=a.member_id \r\n" + 
		 		"and br.branch_id=brm.id ";
		 
		 		int x=0;
		 		int y=0;
		 		Object[][] dbarr = new Object[jsonarrcount][8];
			
		
				try {
					stmt = Dbconnection.con.createStatement(); 
					rs = stmt.executeQuery(query);
					System.out.println("Query Executed");
					counter=0;
					while(rs.next())
					{
						dbarr[x][0]=rs.getString(1);
						//System.out.print("email "+rs.getString(1));
						//System.out.print(" first_name "+rs.getString(2));
						dbarr[x][1]=rs.getString(2);
						//System.out.print(" middle_name "+rs.getString(3));
						dbarr[x][2]=rs.getString(3);
						//System.out.print(" last_name "+rs.getString(4));
						dbarr[x][3]=rs.getString(4);
						//System.out.print(" emp_code "+rs.getString(5));
						dbarr[x][4]=rs.getString(5);
						//System.out.print(" branch_code "+rs.getString(6));
						dbarr[x][5]=rs.getString(6);
						//System.out.print(" branch_name "+rs.getString(7));
						dbarr[x][6]=rs.getString(7);
						//System.out.print(" is_allocated "+rs.getInt(8));
						dbarr[x][7]=rs.getInt(8);
						//System.out.println("");
						counter++;
						x++;
					}
					System.out.println("Total Rows "+counter);
				
					//*******sorting array***************
					/* Arrays.sort(dbarr, new Comparator<String[]>() {
				            @Override
				            public int compare(final String[] entry1, final String[] entry2) {
				                final String time1 = entry1[0];
				                final String time2 = entry2[0];
				                return time1.compareTo(time2);
				            }
				        });*/
					 
					 
					
					/*for(int i=0;i<counter;i++) //print dbarray 
					{
						for(int z=0;z<8;z++)
						{
							System.out.print(dbarr[i][z]);
							
						}
						System.out.println("");
					}*/
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				boolean status=true;
//*******************Matching both arrays now**************************************************
				if(jsonarrcount==counter) //check if record count of both api and db is same
				{
					for(int i=0;i<counter;i++)
					{
						for(int z=0;z<8;z++)
						{
							
							if(z==7)//for int is_active
							{
								int dbval=(int) dbarr[i][z];
								int apival=(int)apiarr[i][z];
								if(dbval!=apival)
								{
									test.warning("Mismatch db "+dbarr[i][z]+" API "+apiarr[i][z]);
									status=false;
								}
								else
								{
									System.out.println("matched");
									
								}
							}
							else
							{
								String dbvalue=(String) dbarr[i][z];
								String apivalue=(String) apiarr[i][z];
								if(dbvalue.equalsIgnoreCase(apivalue))
								{
									System.out.println("matched");	
								}
								else
								{
									test.warning("Mismatch db "+dbarr[i][z]+" API "+apiarr[i][z]);
									status=false;
									
									
								}
							}
							
							
							
						}
						
					}				
				}
				else
				{
					test.fail("Total user count mismatch between api and db");
				}
				
				if(status==false)
				{
					test.fail("Test failed due to data mismatch between API and DB");
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
