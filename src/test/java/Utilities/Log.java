/**
 * 
 */
package Utilities;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * @author debo
 *
 */
public class Log {
	
	
	
	public static void startLogForThisCase(String testCaseName)
	{
		BasicConfigurator.configure();
	    
		Log.info("****************************************************************************************");
	    Log.info("$$$$$$$$$$$$$$$$$$$$$      Test Case: "+testCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
	    Log.info("****************************************************************************************");
	    
	}
	
	public static void endLoggForThisCase()
	{
		Log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
	   
	}

	public  static void  info(String string) {
		 Log.info(string);
		
	}
	public static  void debug(String string) {
		 Log.debug(string);		
	}
	
	public static void error(String string) {
		Log.error(string);		
	}
	public void fatal(String string) {
		Log.error(string);		
	}

}
