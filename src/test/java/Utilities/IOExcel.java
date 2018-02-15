/**
 * EcommerceAuto 2017
 * 
 */
package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author debo
 * Aug 20, 2017
 *  
 */
public class IOExcel {
	
	private static XSSFWorkbook wbook,wbookop;
	private static XSSFSheet sheet;
	private static File source;
	private static FileInputStream input;
	private static FileOutputStream output;
	private static XSSFCell cell;
	private static XSSFRow crow;
	private static String excelfilepath;
	static Hashtable<String,String> table;
	

	
	static int rowcount=0;
	public static int Getrowcount(String sheetname)
	{
		try {
			sheet=wbook.getSheet(sheetname);
			rowcount=sheet.getLastRowNum();
			
		} catch (Exception e) {						
			Log.error("Excel file data problem: "+e);
			e.printStackTrace();
		}
		return rowcount;
	}
	public static void  excelSetup(String filePath)
	{
		 try {
			 excelfilepath=filePath;
			 input = new FileInputStream(filePath);			 		
			 wbook=new XSSFWorkbook(input);			
			// sheet=wbook.getSheet(sheetname);
			
		} catch (Exception e) {
			Log.error("File Initialization constructor failed due to : "+e);
		}
		 
	}
	
	 //*****************Get Data from Excel sheet *************//
	public static Object[][] getDataArray(String SheetName)
		
	{
			Object[][] data=null;
			
		try
			{
				//FileInputStream Scriptfis = new FileInputStream(FilePath);
				
				//wbook=new XSSFWorkbook(Scriptfis);
				sheet = wbook.getSheet(SheetName);
				
				int Startrow=1;   
				int StartCol=0;   
				crow=sheet.getRow(0);
			    
							
								
							int TotalRow=sheet.getLastRowNum();
							int TotalCol=crow.getLastCellNum();
							
							 System.out.println(TotalRow+"\t\t"+TotalCol);
							 
				                 data=new Object[TotalRow][1];
				 		    
			                for(int j=1; j<=TotalRow;j++)
			                    
			                {           
			                       table = new Hashtable<String,String>();
			   
			                         for(int k=0 ; k<TotalCol; k++)
			                          {
			                    
			                         table.put(getCellData(0, k), getCellData(j, k));
			                    
			                          }
			   
			                          data[j-1][0] = table;
			                
			                 }
				              
						
			             
					
					
					}
		catch (Exception e)
				    
		{
				     
			System.out.println("Could not read the Excelsheet"+e.getMessage());
		    e.printStackTrace();
	    }
				
		  return data;
	
		  
	}
	
	public static Object[][] getDataArray(String SheetName, int startroww,int totalcoll) //specific for roster sheet proj
	
	{
			Object[][] data=null;
			
		try
			{
				//FileInputStream Scriptfis = new FileInputStream(FilePath);
				
				//wbook=new XSSFWorkbook(Scriptfis);
				sheet = wbook.getSheet(SheetName);
				System.out.println("Sheet name"+sheet.getSheetName());
				/*int Startrow=startroww;   
				int StartCol=0;   */
				crow=sheet.getRow(0);
			    
							
								
							int TotalRow=sheet.getLastRowNum();
						//	int TotalCol=crow.getLastCellNum();
							int TotalCol=totalcoll;
							 System.out.println("Total Row"+TotalRow+"\tcol \t"+TotalCol);
							 
				                 data=new Object[TotalRow][1];
				 		    
			                for(int j=startroww; j<=TotalRow;j++)
			                    
			                {           
			                       table = new Hashtable<String,String>();
			   
			                         for(int k=0 ; k<TotalCol; k++)
			                          {
			                        	 
			                        	 String key;
			                        	 if(k<5)
			                        	 {
			                        		 key=getCellData(1, k);
			                        	 }
			                        	 else
			                        	 {
			                        		 key=getDateData(1, k);
			                        	 }
			                        	 String value=getCellData(j, k);
			                        	 
			                        	 table.put(key,value );
			                    
			                          }
			   
			                          data[j-1][0] = table;
			                
			                 }
				              
						
			             
					
					
					}
		catch (Exception e)
				    
		{
				     
			System.out.println("Could not read the Excelsheet"+e.getMessage());
		    e.printStackTrace();
	    }
				
		  return data;
	
		  
	}
	  //*****************Get Data from Cell of Excel sheet *************//
			public static String getCellData(int RowNum, int ColNum) throws Exception
			{

			   try
			   {
				     cell = sheet.getRow(RowNum).getCell(ColNum);
				     
				     String CellData = (String)cell.getStringCellValue();
				   //  System.out.println("row:"+RowNum+"col:"+ColNum+"celldata :"+CellData);
				    
				    return CellData;
			   }
			   catch (Exception e)
			   {
				   System.out.println("Row:"+RowNum+"col"+ColNum+"getcelldata"+e);
			   return "";
			   }
	 
			}
			public static String getDateData(int RowNum, int ColNum) throws Exception //for roster
			{

			   try
			   {
				     cell = sheet.getRow(RowNum).getCell(ColNum);
				     String data;
				     String pattern = "yyyy-MM-dd";
				     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				     
				     Date CellData = cell.getDateCellValue();			       
				     
				     data = simpleDateFormat.format(CellData);
				       
				    //   data = DateFormat.getDateInstance().format(CellData);  
				     //  data =CellData.toString() ;
				   //  System.out.println("row:"+RowNum+"col:"+ColNum+"celldata :"+data);
				    
				    return data;
			   }
			   catch (Exception e)
			   {
				   System.out.println("Row:"+RowNum+"col"+ColNum+"getcelldata"+e);
			   return "";
			   }
	 
			}
	public static String getExcelStringData(int row,int col,String sheetname)
	{
		String cellvalue=null;
		System.out.println("inside getExcelStringData(int row,int col,String sheetname) ");
		try {
			sheet=wbook.getSheet(sheetname);
			rowcount=sheet.getLastRowNum();
			cellvalue=sheet.getRow(row).getCell(col).getStringCellValue();
		} catch (Exception e) {						
			Log.error("Excel file data problem: "+e);
			System.out.println("Excel getExcelStringData problem: "+e);
			//e.printStackTrace();
		}
		return cellvalue;
	}
	public static Integer getExcelIntData(int row,int col,String sheetname)
	{
		Integer cellvalue=0;
		
		try {
			sheet=wbook.getSheet(sheetname);
			rowcount=sheet.getLastRowNum();
			cellvalue=(int) sheet.getRow(row).getCell(col).getNumericCellValue();
		} catch (Exception e) {						
			Log.error("Excel file data problem: "+e);
			System.out.println("IOExcel getExcelIntData problem: "+e);
			//e.printStackTrace();
		}
		return cellvalue;
	}
	
	

	
	public static void setExcelStringData( int row,int columnIndex,String stringValue,String sheetname)
	{
			
		try {
			sheet=wbook.getSheet(sheetname);
			crow = sheet.getRow(row);
			if(crow==null)
			{
				Log.info("row is not Created");
				try {
					crow=sheet.createRow(row);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			cell = crow.getCell(columnIndex, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			if(cell==null)
			{
				crow.createCell(columnIndex).setCellValue(stringValue);
			}
			else {
				cell.setCellValue(stringValue);
			}
			
			output=new FileOutputStream(excelfilepath);
			wbook.write(output);
			output.flush();
			output.close();
		
		} 
		catch (Exception e) {
			Log.error("Excel write problem : "+e);
			e.printStackTrace();
		}
		
	}
	
	public static void setExcelIntData( int rowparam,int columnIndex,int Value,String sheetname)
	{
			
		try {
			sheet=wbook.getSheet(sheetname);
			crow = sheet.getRow(rowparam);
			if(crow==null)
				{
					Log.info("row is not Created");
					try {
						crow=sheet.createRow(rowparam);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			
			cell = crow.getCell(columnIndex, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			if(cell==null)
			{
				crow.createCell(columnIndex).setCellValue(Value);
			}
			else {
				cell.setCellValue(Value);
			}
			
			output=new FileOutputStream(excelfilepath);
			wbook.write(output);
			output.flush();
			output.close();
		
		} 
		catch (Exception e) {
			Log.error("Excel write problem : "+e);
			e.printStackTrace();
		}
		
	}

}
