package Utilities;
import org.testng.annotations.DataProvider;

	public class impp_testdataProvider
	{
		static String Testcaserow;
	    static String TestDataRow;
		 

				@DataProvider(name="testdataProvider")
					public  static Object[][] testdataProvider()
					     {
						 Object[][] TesttabArray=null;
							try
								{
								 
								TesttabArray=IOExcel.getDataArray("Sheet1");
									 
								}
							catch(Exception e)
							{
							System.out.println("Error in Dataprovider code"+e.getMessage());
						    }
							return TesttabArray;
					
					} 
				}


