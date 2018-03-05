package Utilities;
import org.testng.annotations.DataProvider;

	public class impp_testdataProvider
	{
		static String Testcaserow;
	    static String TestDataRow;
		 

					@DataProvider(name="testdataProvider" ,parallel=true )
					public  static Object[][] testdataProvider()
					{
						 Object[][] TesttabArray=null;
							try
								{
								 
								TesttabArray=IOExcel.getDataArray("Sheet1");
									 
								}
							catch(Exception e)
							{
							System.out.println("Error in testdataProvider"+e.getMessage());
						    }
							return TesttabArray;
					
					} 
					@DataProvider(name="testdataProvider2" )
					public  static Object[][] testdataProvider2()
					{
						 Object[][] TesttabArray=null;
							try
								{
								System.out.println("@impp_testdataprovider.class sheetname is : "+Sheet.sheetname);
								TesttabArray=IOExcel.getDataArray(Sheet.sheetname,2,36);
									 
								}
							catch(Exception e)
							{
							System.out.println("Error in testdataProvider2"+e.getMessage());
						    }
							return TesttabArray;
					
					} 
	}


