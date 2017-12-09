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

public class GetStatusCodeTest {

  @BeforeClass
  public void setBaseUri () {

    RestAssured.baseURI = "https://maps.googleapis.com";
    IOExcel.excelSetup("D:\\testdata");

  }
  
  //@DataProvider(name="DataSource")
  @Test
  public static void exceldatasource()
  {
	  //Object arr[][]=new Object[2][2];
	 // arr[0][0]= IOExcel.getExcelStringData(1,1,"Sheet1");
		// System.out.println(arr[0][0]);
		//System.out.println( IOExcel.getExcelStringData(0,0,"Sheet1"));
	  IOExcel.setExcelStringData(1, 1,"fghjk", "Sheet1");
	  
	/*
	  for(int i=0;i<=1;i++)
	  {
		  for(int j=0;j<=1;j++)
		  { 
			 arr[i][j]= IOExcel.getExcelStringData(i+1, j+1,"Sheet1");
			 System.out.println(arr[i][j]);
		  }
	  }*/
	
	  
	//  return arr;
	
  }

  /*@Test(dataProvider="DataSource")
 
  public void testStatusCode (String SearchText, String Key )
  {
    
    Response res = 
    given ()
    .param("query", SearchText)
    .param ("key", Key)
    .when()
    .get ("/maps/api/place/textsearch/json");
    System.out.println("Status returned is "+res.statusCode ());
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
