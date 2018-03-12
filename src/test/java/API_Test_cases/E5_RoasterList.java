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

import Utilities.Dbconnection;
import Utilities.ExtentManager;
import Utilities.IOExcel;
import Utilities.Log;
import Utilities.PathUtility;

public class E5_RoasterList {
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
	JSONObject jsonreq= new JSONObject();

	
	//Starts the database connection
	@BeforeSuite
	public void startup()
	{
		Dbconnection.dbstartup("amazon");
	}
	
  @BeforeClass
  public void setBaseUri () {

	  reports = ExtentManager.GetExtent("http://34.214.158.70:32845/impp/imerit/roaster/list/0");
	  RestAssured.baseURI=PathUtility.BaseUrl+"imerit/roaster/list/0"; //ITEST
	  Log.startLogForThisCase("API Testing Resource roaster/list/0");
	  IOExcel.excelSetup(PathUtility.BaseFilepath+"E5_Roster_list.xlsx");
	  

	  
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

  @SuppressWarnings("unchecked")
@Test(dataProvider="testdataProvider",dataProviderClass=Utilities.impp_testdataProvider.class)
  public void postString (Hashtable<String,String> TestData) 
	{
	  //Reading data from Excel 
	  String userCode=TestData.get("userCode");
	  String memberCode=TestData.get("memberCode");
	  String engagementDetailCode=TestData.get("engagementDetailCode");
	  String nodeId=TestData.get("nodeId");
	  
	  //Create Report
	  test = reports.createTest("API roaster/list/0 TC"+count);
	  count++;
	  
	  //Add info
	  test.info("Starting API http://34.214.158.70:32845/impp/imerit/roaster/list/0");
	  
	  //Json Create...
	  
	  jsonreq.put("userCode",userCode);
	 
	  
	 //API Execution...
	  
	 Response res  =
     given()
    .body (jsonreq)
    .when ()
    .contentType (ContentType.JSON)
    .post ()
    .then()
    .contentType(ContentType.JSON)
    .extract()
	.response();
	 
	//Adding Created JSON in Report 
	 test.info(jsonreq.toJSONString());
	 
	//Writing data to Excel 
	 IOExcel.setExcelStringData(row, col, "jsonreq.toJSONString()", "Sheet2"); 
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
	
	 Assert.assertTrue(res.statusCode()==200);
	 int jsoncol=col;
	 
	/* System.out.println("branchCode"+res.body().path("[0].branchCode"));
	 System.out.println("firstName"+res.body().path("[0].firstName"));*/
	 //json detail extraction
	 
	// System.out.println("Json array count "+res.body().path("$.size()"));
	
	 int jsonarrcount =res.body().path("$.size()");
	
	Object[][] apiarr = new Object[jsonarrcount][2];
	
	
	
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
			 
						 
			 String data2=res.body().path("["+index+"].roasterCode");
			 apiarr[i][1]=data2;
			// IOExcel.setExcelStringData(row, col, data2, "Sheet2");	
			 col++;
			 
			 String data3=res.body().path("["+index+"].roasterTitle");
			 apiarr[i][2]=data3;
			// IOExcel.setExcelStringData(row, col, data3, "Sheet2");	
			col++;
			
			 //reset the col back
			 col=jsoncol;
			 row++;
			 System.out.println("inside json extraction loop "+row);
		 }
	 }
	
	// Arrays.sort(apiarr);
	// Arrays.sort(apiarr, (a, b) -> String.compare(a[0], b[0]));
	/* Arrays.sort(apiarr, new Comparator<Object[]>() {
	       			@Override
			public int compare(Object[] o1, Object[] o2) {
	       				String a1= o1.toString();
	       				String a2=o2.toString();
				
	       		return a1.compareTo(a2);
	       				
			}
	    });*/
	 
	 Arrays.sort(apiarr, new Comparator<Object[]>() {
			@Override
                     //arguments to this method represent the arrays to be sorted   
			public int compare(Object[] o1, Object[] o2) {
                             //get the item ids which are at index 0 of the array
				Object itemIdOne = o1[0];
				Object itemIdTwo = o2[0];
				// sort on item id
				return ((String) itemIdOne).compareTo((String) itemIdTwo);
			}
		});
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
		 
		 String query= "select "
		 + "roster_project_code,\r\n"
		 + "roster_project_title\r\n" 
		 +"from impp_ng_v1.impp_roster_project\r\n"
		 + "where \r\n"
		 + "is_active=1;";
		 		
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
						/*dbarr[x][2]=rs.getString(3);
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
						//System.out.println("");*/
						counter++;
						x++;
					}
					System.out.println("Total Rows "+counter);
				
					//*******sorting array***************
					Arrays.sort(dbarr, new Comparator<Object[]>() {
						@Override
			                     //arguments to this method represent the arrays to be sorted   
						public int compare(Object[] o1, Object[] o2) {
			                             //get the item ids which are at index 0 of the array
							Object itemIdOne = o1[0];
							Object itemIdTwo = o2[0];
							// sort on item id
							return ((String) itemIdOne).compareTo((String) itemIdTwo);
						}
					});
					
					 
					 
					
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
						for(int z=0;z<1;z++)
						{
							
							if(z==1)//for int is_active
							{
								int dbval=(int) dbarr[i][z];
								int apival=(int)apiarr[i][z];
								if(dbval!=apival)
								{
									test.warning("Mismatch db "+dbarr[i][z]+" API "+apiarr[i][z]+"in Record number"+counter);
									status=false;
								}
								else
								{
									//System.out.println("matched");
									
								}
							}
							else
							{
								String dbvalue=(String) dbarr[i][z];
								String apivalue=(String) apiarr[i][z];
								if(dbvalue.equalsIgnoreCase(apivalue))
								{
									//System.out.println("matched");	
								}
								else
								{
									test.warning("Mismatch db "+dbarr[i][z]+" API "+apiarr[i][z]+"in Record number"+counter);
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
