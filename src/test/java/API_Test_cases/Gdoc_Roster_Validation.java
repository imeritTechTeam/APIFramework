package API_Test_cases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utilities.CalenderUtil;
import Utilities.Dbconnection;
import Utilities.ExtentManager;
import Utilities.IOExcel;
import Utilities.PathUtility;
import Utilities.Sheet;
import java.util.GregorianCalendar;

/*Compares Excel with DB
 * 
 * MVN Command 
 * 
mvn clean test -Dmaven.clean.failOnError=false -Dsurefire.suiteXmlFiles=gdoc.xml -DExcelfile=roster20 -DMonth=FEB -DRosterID=20
@Param DExcelfile :Excel File name
@Param Month :Doc tab name is in months
@Param RosterID :Unique ROster Id present in impp_roster_project;

@Author:Debapriyo Haldar
Date 9-Feb-2018
*/
public class Gdoc_Roster_Validation {
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
	Date startdt;
	Date enddt;
	String pattern;
	String rosterID;
	
	@BeforeSuite
	public void startup()
	{
		Dbconnection.dbstartup("local");
	}
	 @BeforeClass
	  public void setBaseUri () {

		  reports = ExtentManager.GetExtent("GDOC Roster Validation");
		  //Get Roster ID present in impp_roster_project;
		   rosterID=System.getProperty("RosterID");		
		  //Open excel test file as per argument
		  String ExcelFile=System.getProperty("Excelfile");
		  IOExcel.excelSetup(".\\TestData\\"+ExcelFile+".xlsx"); 	
		  System.out.println("Excel file argument"+ExcelFile);
		  
		  String month=System.getProperty("Month");		
		  if(month.equalsIgnoreCase("JAN"))
		  {
			  startdt=CalenderUtil.getdate(2017, 11, 26);
			  enddt=CalenderUtil.getdate(2018, 0, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="JAN-18";
		  }	
		  if(month.equalsIgnoreCase("FEB"))
		  {
			  startdt=CalenderUtil.getdate(2018, 0, 26);
			  enddt=CalenderUtil.getdate(2018, 1, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="FEB-18";
		  }
		  
		  if(month.equalsIgnoreCase("MAR"))
		  {
			  startdt=CalenderUtil.getdate(2018, 1, 26);
			  enddt=CalenderUtil.getdate(2018, 2, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="MAR-18";
		  }
		  if(month.equalsIgnoreCase("APR"))
		  {
			  startdt=CalenderUtil.getdate(2018, 2, 26);
			  enddt=CalenderUtil.getdate(2018, 3, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="APR-18";
		  }
		  if(month.equalsIgnoreCase("MAY"))
		  {
			  startdt=CalenderUtil.getdate(2018, 3, 26);
			  enddt=CalenderUtil.getdate(2018, 4, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="MAY-18";
		  }
		  if(month.equalsIgnoreCase("JUN"))
		  {
			  startdt=CalenderUtil.getdate(2018, 4, 26);
			  enddt=CalenderUtil.getdate(2018, 5, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="JUN-18";
		  }
		  if(month.equalsIgnoreCase("JUL"))
		  {
			  startdt=CalenderUtil.getdate(2018, 5, 26);
			  enddt=CalenderUtil.getdate(2018, 6, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="JUL-18";
		  }
		  if(month.equalsIgnoreCase("AUG"))
		  {
			  startdt=CalenderUtil.getdate(2018, 6, 26);
			  enddt=CalenderUtil.getdate(2018, 7, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="AUG-18";
		  }
		  if(month.equalsIgnoreCase("SEP"))
		  {
			  startdt=CalenderUtil.getdate(2018, 7, 26);
			  enddt=CalenderUtil.getdate(2018, 8, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="SEP-18";
		  }
		  if(month.equalsIgnoreCase("OCT"))
		  {
			  startdt=CalenderUtil.getdate(2018, 8, 26);
			  enddt=CalenderUtil.getdate(2018, 9, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="SEP-18";
		  }
		  if(month.equalsIgnoreCase("NOV"))
		  {
			  startdt=CalenderUtil.getdate(2018, 9, 26);
			  enddt=CalenderUtil.getdate(2018, 10, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="NOV-18";
		  }
		  if(month.equalsIgnoreCase("DEC"))
		  {
			  startdt=CalenderUtil.getdate(2018, 10, 26);
			  enddt=CalenderUtil.getdate(2018, 11, 26);
			  System.out.println("start date "+startdt+" "+enddt);
			  Sheet.sheetname="DEC-18";
		  }
	
		  
		 
	  }
	 @SuppressWarnings("unchecked")
	 @Test(dataProvider="testdataProvider2",dataProviderClass=Utilities.impp_testdataProvider.class)
	   public void postString(Hashtable <String,String> TestData) 
	   {
		
		 
		 //Calender calculations ************************
		 pattern = "yyyy-MM-dd";
		 String[] datearr= new String[CalenderUtil.getDatesBetween(startdt, enddt).size()];
		// System.out.println("DATS COUNTED"+CalenderUtil.getDatesBetween(startdt, enddt).size());
		 
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);  
		 for (int i = 0; i < CalenderUtil.getDatesBetween(startdt, enddt).size(); i++) {
				Date dt=CalenderUtil.getDatesBetween(startdt, enddt).get(i);
				//System.out.println(dt);
				String day = simpleDateFormat.format(dt);
			//	System.out.println(day);
				datearr[i]=day;
				
			}
		 
		 
		 //Fetch data from hash map**************************
		 String emp_code=TestData.get("Employee Code");
		 String Branch=TestData.get("Branch");
		 String Employee_Name=TestData.get("Employee Name");
		 String Project_Role=TestData.get("Project Role");
		 String Type=TestData.get("Type");
		 
		 System.out.print(emp_code+" "+Branch+" "+Employee_Name+" "+Project_Role+" "+Type);
		 for(int i=0;i<datearr.length-1;i++)
		 {
			String data1=TestData.get(datearr[i]);
			System.out.print(" "+data1+" ");
		 }	 
		 System.out.println();
		 
		 test = reports.createTest("Emp_code: "+emp_code+" "+Employee_Name);
		 count++;
		 
		
		 
		//DB**********************************************************************
		for(int i=0;i<datearr.length-1;i++)
		 {
			String shift=TestData.get(datearr[i]);
			// System.out.print(" "+data1+" ");
			String query="select rs.emp_code,rs.roster_role,rs.roster_type,rs.roster_day,rs.shift_id,sf.shift_code \r\n" + 
				"from impp_ng_v1.impp_rosters_sheet rs ,impp_ng_v1.impp_shifts sf\r\n" + 
				"where sf.id=rs.shift_id\r\n" + 
				"and emp_code='"+emp_code+"' and rs.roster_day='"+datearr[i]+"'"+"and rostered_project_id="+rosterID;
		  
		  
		 	//System.out.println(query);
			try {
				stmt=Dbconnection.con.createStatement(); 
				rs=stmt.executeQuery(query);
				
				resultsetnotempty =rs.next();
					if(resultsetnotempty)
					{
					System.out.println("emp_code "+rs.getString(1)+" "
										+"roster_role "+rs.getString(2)+" "
										+"roster_type "+rs.getString(3)+" "
										+"roster_day "+rs.getDate(4)+" "
										+"shift_code "+rs.getString(6)
							);
						System.out.println("Sql query executed");
						if(rs.getString(1).equalsIgnoreCase(emp_code)&&(rs.getDate(4).toString().equalsIgnoreCase(datearr[i]))&&(rs.getString(6).equalsIgnoreCase(shift)))
						{
							test.pass("Emp Code:'"+emp_code+"' Roster Date: '"+datearr[i]+"' Shift Code: '"+rs.getString(6)+"' GoogleDoc ROSTER MATCHES WITH DB");
						}
						else
							
							test.fail("Mismatch in Shift between DB and GDOC Roster for employee: "+emp_code+" Date: "+datearr[i]+" In DB: '"+rs.getString(6)+"' in roster: '"+shift+"'");
						/*if(rs.getDate(4).toString().equalsIgnoreCase(datearr[i]))
						{
						test.pass("Roster Date DB"+rs.getDate(4)+" Roster: "+datearr[i]+" MATCHES");
						}
						else
							test.fail("Mismatch between DB and Roster");
						if(rs.getString(6).equalsIgnoreCase(shift))
						{
						test.pass(" Shift Code DB: "+rs.getString(6)+" Roster: "+shift+" MATCHES");
						}*/
						
						
					}
					else
					{
						System.out.println("No row returned by sql query");
					//	test.info("No row returned by sql query");
						test.fail("Roster details for employee: '"+emp_code+"' Date: '"+datearr[i]+"' is not present in DB (No row returned by sql query)");
					}
			
				
			} catch (SQLException e) {
				
				
				e.printStackTrace();
			}  
		
		 }
		 
	   }
	 @AfterClass
	 public void teardown()
	 {
		
		  reports.flush();
		 try {
			Dbconnection.con.close();
		} catch (Exception e) {
			
			System.out.println(e);
		}  
	 }
	 
	
}
