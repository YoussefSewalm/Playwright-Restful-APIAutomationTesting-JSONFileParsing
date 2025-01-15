package Tests;

import static JsonDataDriven.JsonData.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import TestBase.GeneratingToken;


public class CreatingUserThenPartialUpdatingThenDeleting extends GeneratingToken{
	
			                               

    
	@DataProvider
	public Object[][] UpdateBooking() throws IOException
	{
	    String jsonFilePath_1 = System.getProperty("user.dir") + "//src//test//java//resources//GeneralProperties.json";


	    List<HashMap<String,Object>> data_1 = getJsonDataList(jsonFilePath_1);


	    return new Object[][] {   { data_1.get(0) }   };
	}
	
	@Test(dataProvider="UpdateBooking")
	public void CreatingAndPartialUpdateThenDeleteUser(HashMap<String,Object> input) throws IOException
	{
		 byte [] CreatingUserReqBody = null;  
         File file_creatinguser = new File(System.getProperty("user.dir") + "//src//test//java//resources//CreatingUserReqBody.json");
         CreatingUserReqBody = Files.readAllBytes(file_creatinguser.toPath());

	    System.out.println("My Token is :"+Token);
	    System.out.println(CreatingUserReqBody);

	    //Creating User
		apipostresponse = requestContext.post(input.get("Url")+"/booking", requestOptions
				                        .setHeader("Content-Type", "application/json")
				                        .setHeader("Accept", "application/json")
				                        .setData(CreatingUserReqBody)	                        
				                    );
		
	   String postresponseText = apipostresponse.text();
	   System.out.println(postresponseText);

	   ObjectMapper objectmapper = new ObjectMapper();
	   JsonNode JSONPOSTResponse = objectmapper.readTree( apipostresponse.body() );
       int BookingID = Integer.parseInt(JSONPOSTResponse.get("bookingid").asText());
 //H3ml Node gdeda ll booking 3shan ll booking 2ly tl3 fl response l valuebta3to 3obara 3an Map (Keys,values)
       //ya3ny l booking dh Object lwa7do gowa l Object l asasy (Response)
       JsonNode booking = JSONPOSTResponse.get("booking");	   
       Assert.assertEquals(apipostresponse.status() , 200);
	   Assert.assertEquals(booking.get("firstname").asText(), input.get("firstname"));
	   
	   //Partial Updating User
		 byte [] PartialUpdatingUserReqBody = null; 
         File file_partialupdateuser = new File(System.getProperty("user.dir") + "//src//test//java//resources//PartialUpdateReqBody.json");
         PartialUpdatingUserReqBody = Files.readAllBytes(file_partialupdateuser.toPath());
		apipatchresponse = requestContext.patch(input.get("Url")+"/booking/"+String.valueOf(BookingID), requestOptions
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setHeader("Cookie", "token="+Token)
                .setData(PartialUpdatingUserReqBody)               
				); 
		
		   String PATCHresponseText = apipatchresponse.text();
		   System.out.println(PATCHresponseText);
		   
		   JsonNode JSONPATCHResponse = objectmapper.readTree( apipatchresponse.body() );
	  
         Assert.assertEquals(apipatchresponse.status() , 200);
         Assert.assertEquals(JSONPATCHResponse.get("firstname").asText(), input.get("firstnameupdate"));
         Assert.assertEquals(JSONPATCHResponse.get("lastname").asText(), input.get("lastnameupdate"));
         
         
     	//Get Updated User
		apigetresponse = requestContext.get(input.get("Url")+"/booking/"+String.valueOf(BookingID), requestOptions
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
				); 
		
		   String GETresponseText = apigetresponse.text();
		   System.out.println(GETresponseText);
		   
		   JsonNode JSONGETResponse = objectmapper.readTree( apigetresponse.body() );
	  
         Assert.assertEquals(apigetresponse.status() , 200);
         Assert.assertEquals(JSONGETResponse.get("firstname").asText(), JSONPATCHResponse.get("firstname").asText());
         Assert.assertEquals(JSONGETResponse.get("lastname").asText(), JSONPATCHResponse.get("lastname").asText());
         
         
      	//Delete User
 		apideleteresponse = requestContext.delete(input.get("Url")+"/booking/"+String.valueOf(BookingID), requestOptions
                 .setHeader("Cookie", "token="+Token)
 				); 
 		
 		   String DELETEresponseText = apideleteresponse.text();
 		   System.out.println(DELETEresponseText);
 	       Assert.assertEquals(apideleteresponse.status() , 201);
 	       Assert.assertEquals(apideleteresponse.statusText(), "Created");
 	       
	}
	

}
