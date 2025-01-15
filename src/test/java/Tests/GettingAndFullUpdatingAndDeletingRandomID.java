package Tests;

import static JsonDataDriven.JsonData.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import TestBase.GeneratingToken;

public class GettingAndFullUpdatingAndDeletingRandomID extends GeneratingToken {
	
	@DataProvider
	public Object[][] UpdateBooking() throws IOException
	{
	    String jsonFilePath_1 = System.getProperty("user.dir") + "//src//test//java//resources//GeneralProperties.json";

	    List<HashMap<String,Object>> data_1 = getJsonDataList(jsonFilePath_1);

	    return new Object[][] {   { data_1.get(0) }  };
	}
	
	@Test(dataProvider="UpdateBooking")
	public void CompleteUpdatingAndDeletingRandomID(HashMap<String,Object> input) throws IOException
	{  
	    System.out.println("My Token is :"+Token);
	    //Getting all IDs
	   apigetresponse = requestContext.get(input.get("Url")+"/booking");
	   ObjectMapper objectmapper = new ObjectMapper();
	   String getresponseText = apigetresponse.text();
	   System.out.println(getresponseText);
	   JsonNode JSONGETResponse = objectmapper.readTree( apigetresponse.body() );
	
       int available_Id = Integer.parseInt(JSONGETResponse.get(0).get("bookingid").asText());
       Assert.assertEquals(apigetresponse.status() , 200);
       
        //Get This Random User
	   apigetresponse_random = requestContext.get(input.get("Url")+"/booking/"+available_Id);
	   String getresponseText_random = apigetresponse_random.text();
	   System.out.println(getresponseText_random);
       Assert.assertEquals(apigetresponse_random.status() , 200);
        //Full Updating this Random User
		 byte [] CompleteUpdatingUserReqBody = null;  //h3ml initialize ll array bytes
         File file_completeupdateuser = new File(System.getProperty("user.dir") + "//src//test//java//resources//CreatingUserReqBody.json");
         CompleteUpdatingUserReqBody = Files.readAllBytes(file_completeupdateuser.toPath());
		apiputresponse = requestContext.put(input.get("Url")+"/booking/"+String.valueOf(available_Id), requestOptions
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .setHeader("Cookie", "token="+Token)
                .setData(CompleteUpdatingUserReqBody)               
				); 
		
		   String PUTresponseText = apiputresponse.text();
		   System.out.println(PUTresponseText);
		   
		   JsonNode JSONPUTResponse = objectmapper.readTree( apiputresponse.body() );
		   JsonNode BookingData_PUT = JSONPUTResponse.get("bookingdates");

		   //Convert Object to Hashmap( 3shan a3rf a get value l Checkin mn l jsonfile
		   //l moshkela n l value deh hwa Nested object 2ly hwa "bookingdates"
		   //f 3shan keda 5adt l awal l bookingdates object w 7wlto l map lwa7do w d5alt gowah
		   Object BookingDates = input.get("bookingdates");
		    Map<String, Object> Actual_BookingDates = objectmapper
		    	      .convertValue(BookingDates, new TypeReference<Map<String, Object>>() {});
		    
		 //VALIDATIONS FROM PUT REPONSE 
         Assert.assertEquals(apiputresponse.status() , 200);
         Assert.assertEquals(JSONPUTResponse.get("firstname").asText(),input.get("firstname"));
         Assert.assertEquals(JSONPUTResponse.get("lastname").asText(),input.get("lastname"));
         Assert.assertEquals(Integer.parseInt(JSONPUTResponse.get("totalprice").asText()),input.get("totalprice"));
         Assert.assertEquals(Boolean.parseBoolean(JSONPUTResponse.get("depositpaid").asText()),input.get("depositpaid"));
         Assert.assertEquals(BookingData_PUT.get("checkin").asText(),Actual_BookingDates.get("checkin"));
         Assert.assertEquals(BookingData_PUT.get("checkout").asText(),Actual_BookingDates.get("checkout"));
         Assert.assertEquals(JSONPUTResponse.get("additionalneeds").asText(),input.get("additionalneeds"));

         
         //Getting Updated User
 		apigetupdateresponse = requestContext.get(input.get("Url")+"/booking/"+String.valueOf(available_Id), requestOptions
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
				); 
		
		   String GETupdateresponseText = apigetupdateresponse.text();
		   System.out.println(GETupdateresponseText);
		   
		   JsonNode JSONGETUpdateResponse = objectmapper.readTree( apigetupdateresponse.body() );
		   JsonNode BookingData_GET = JSONGETUpdateResponse.get("bookingdates");

		 //VALIDATIONS FROM GET REPONSE 
	     Assert.assertEquals(apigetupdateresponse.status() , 200);
         Assert.assertEquals(JSONGETUpdateResponse.get("firstname").asText(),input.get("firstname"));
         Assert.assertEquals(JSONGETUpdateResponse.get("lastname").asText(),input.get("lastname"));
         Assert.assertEquals(Integer.parseInt(JSONGETUpdateResponse.get("totalprice").asText()),input.get("totalprice"));
         Assert.assertEquals(Boolean.parseBoolean(JSONGETUpdateResponse.get("depositpaid").asText()),input.get("depositpaid"));
         Assert.assertEquals(BookingData_GET.get("checkin").asText(),Actual_BookingDates.get("checkin"));
         Assert.assertEquals(BookingData_GET.get("checkout").asText(),Actual_BookingDates.get("checkout"));
         Assert.assertEquals(JSONGETUpdateResponse.get("additionalneeds").asText(),input.get("additionalneeds"));
	   
         
         //Deleting Updated User
  		apideleteresponse = requestContext.delete(input.get("Url")+"/booking/"+String.valueOf(available_Id), requestOptions
                .setHeader("Cookie", "token="+Token)
				); 
		
		   String DELETEresponseText = apideleteresponse.text();
		   System.out.println(DELETEresponseText);
	       Assert.assertEquals(apideleteresponse.status() , 201);
	       Assert.assertEquals(apideleteresponse.statusText(), "Created");
	       
	}
	

}
