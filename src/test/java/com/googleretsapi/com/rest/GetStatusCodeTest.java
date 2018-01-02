package com.googleretsapi.com.rest;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import Utilities.IOExcel;
import Utilities.Log;

public class GetStatusCodeTest {
	static int i;
	static int j=0;
	int row =1;int col =0;

  @BeforeClass
  public void setBaseUri () {

    //RestAssured.baseURI = "https://maps.googleapis.com";
	 // RestAssured.baseURI ="http://34.214.158.70:32845/impp/imerit/action/access/0";
	  RestAssured.baseURI="http://34.214.158.70:32845/impp/imerit/resource/user/details/0";
    Log.startLogForThisCase("ffff");
    IOExcel.excelSetup("D:\\testdata\\API_resource_user_details.xlsx");

  }
  
/*@Test(dataProvider="DataSource")

  public void postString (String userCode,String accessValue ,String accessKey) 
	{
	//"http://34.214.158.70:32845/impp/imerit/action/access/0"
	 Response res  =
    given()//.log().all()
    .body ("{\"userCode\":\"arjun@imerit.net\","
    +"\"accessValue\":\"EC-By8t5bKbG\","
    +"\"accessKey\":\"engagementedit\"}")
    .body ("{\"userCode\":\""+userCode+"\","
    	    +"\"accessValue\":\""+accessValue+"\","
    	    +"\"accessKey\":\""+accessKey+"\"}")
    .when ()
    .contentType (ContentType.JSON)
    .post ()
    .then()
    .contentType(ContentType.JSON)
    .extract()
	.response();
	 System.out.println ("Status code "+res.statusCode());
	 IOExcel.setExcelIntData(row, col, res.statusCode(), "Sheet2");
	 col++;
	 System.out.println (res.asString());
	 IOExcel.setExcelStringData(row, col, res.asString(), "Sheet2"); 
	 if(col>=1)
	 {
		 col=0;
		 row++;
	 }
  }*/

  @Test(dataProvider="DataSource")

  public void postString (String userCode,String memberCode ) 
	{
	//"http://34.214.158.70:32845/impp/imerit/action/access/0"
	 Response res  =
    given()//.log().all()
   /* .body ("{\"userCode\":\"techteam@imerit.net\","
    +"\"memberCode\":\"animesh@imerit.net\"}"
    )*/
    .body ("{\"userCode\":\""+userCode+"\","
    	    +"\"memberCode\":\""+memberCode+"\"}"
    	   )
    .when ()
    .contentType (ContentType.JSON)
    .post ()
    .then()
    .contentType(ContentType.JSON)
    .extract()
	.response();
	 System.out.println ("Status code "+res.statusCode());
	 IOExcel.setExcelIntData(row, col, res.statusCode(), "Sheet2");
	 col++;
	 System.out.println (res.asString());
	 IOExcel.setExcelStringData(row, col, res.asString(), "Sheet2"); 
	 if(col>=1)
	 {
		 col=0;
		 row++;
	 }
  }

 @DataProvider(name="DataSource")
 
  public static Object[][] exceldatasource()
  {
	 int count=IOExcel.Getrowcount("Sheet1");
	 System.out.println("row count"+count);
	 
	  Object arr[][]=new Object[count][2];
	 
	  int n=0;int k=0;
	
	  for( i=1;i<=count;i++)
	  {
		  k=0;
		  for( j=0;j<=1;j++)
		  { 
			 arr[n][k]= IOExcel.getExcelStringData(i, j,"Sheet1");
			// System.out.println("i "+i+" j "+j+" arr[n][k]"+arr[n][k]+" n "+n+" k "+k);
			// System.out.println("i "+i+" j "+j);
			 k++;
			//System.out.println(arr[n][k]);
		  }
		  n++;
	  }
	
	  
	  return arr;
	
  }
/*
  @Test(dataProvider="DataSource")
 
  public void testStatusCode (String SearchText, String Key )
  {
    
    Response res = 
    given ()
    .param("query", SearchText)
    .param ("key", Key)
    .when()
    .contentType (ContentType.JSON)
    .post("/maps/api/place/textsearch/json");
    //.get ("/maps/api/place/textsearch/json");
    System.out.println("Status returned is "+res.statusCode ());
    System.out.println (res.asString());
    
   // String op = res.path("results[0].formatted_address");
   // IOExcel.setExcelStringData(i, j,op, "Sheet2");
   // System.out.println(op); 
    Assert.assertEquals (res.statusCode (), 200);
  }*/

  
/*@Test
public void testStatusCodeRestAssured () {

given ().param ("query", "restaurants in mumbai")
        .param ("key", "Xyz")
        .when()
        .get ("/maps/api/place/textsearch/json")
        .then ()
        .assertThat ().statusCode (200);


}
*/
/*@Test
public void test01()  {
  Response res  =
		  given ().param ("query", "Maharshi Karve Road, Churchgate")
		  .param ("key", "AIzaSyBrhdZP1wWpMXVEvzpY4-3W-FKieCYhVXg")
		  .when()
		  .get ("/maps/api/place/textsearch/json")
		  .then()
		  .contentType(ContentType.JSON)
		  .extract()
		//  .path("results[0].formatted_address") ;
		.response();  //gives whole response

	//	  Assert.assertEquals(res," Maharshi Karve Rd, Churchgate, Mumbai, Maharashtra 400020");
  System.out.println (res.asString());

}*/

/*@Test
public void test02()  {
  Response res  =
		  given ().param ("query", "Maharshi Karve Road, Churchgate")
		  .param ("key", "AIzaSyBrhdZP1wWpMXVEvzpY4-3W-FKieCYhVXg")
		  .when()
		  .get ("/maps/api/place/textsearch/json")
		  .then()
		  .contentType(ContentType.JSON)
		  .extract()
		  .response();
		String op = res.path("results[0].formatted_address");

		 Assert.assertEquals(op,"Maharshi Karve Rd, Churchgate, Mumbai, Maharashtra, India");
		 System.out.println(op);

}*/
}
