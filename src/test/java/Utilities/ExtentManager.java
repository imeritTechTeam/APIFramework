package Utilities;

import java.sql.Timestamp;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
 
public class ExtentManager {
	
	private static ExtentReports extent;
	private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;
	private static String filePath ;
	
	
	public static ExtentReports GetExtent(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		 filePath="E:\\testdata\\reports\\"+"Extent_Report_"+timestamp.toString().replace(":", "_").replace("-","_").replace(" ","_").replace(".", "_")+".html";
		// filePath="./src/ExtentReports/Extent_Report"+timestamp.toString().replace(":", "_").replace("-","_").replace(" ","_").replace(".", "_")+".html";
		if (extent != null)
        return extent; //avoid creating new instance of html file
		
        extent = new ExtentReports();		
		extent.attachReporter(getHtmlReporter());
		return extent;
	}
 
	private static ExtentHtmlReporter getHtmlReporter() {
		System.out.println(filePath);
	
        htmlReporter = new ExtentHtmlReporter(filePath);
		
	// make the charts visible on report open
        htmlReporter.config().setChartVisibilityOnOpen(true);
		
        htmlReporter.config().setDocumentTitle("QAV automation report");
        htmlReporter.config().setReportName("Regression cycle");
        return htmlReporter;
	}
	
	/*public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		return test;
	}*/
}
