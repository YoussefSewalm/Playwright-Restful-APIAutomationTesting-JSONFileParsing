package TestBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import PlayWrightFactory.InitializePlaywright;

public class GeneratingToken {
	
	public APIRequestContext requestContext;
	public APIResponse apipostresponse;
	public RequestOptions requestOptions;
    public APIResponse apiputresponse;
    public APIResponse apipatchresponse;
    public APIResponse apigetresponse;
    public APIResponse apigetupdateresponse;
    public APIResponse apideleteresponse;
    public APIResponse apigetresponse_random;
    public String Token;
	 
    


@BeforeClass
public void GenerateToken() throws IOException
{
	 byte [] TokenReqBody = null;  //h3ml initialize ll array bytes
     File file_creatingtoken = new File(System.getProperty("user.dir") + "//src//test//java//resources//TokenReqBody.json");
     TokenReqBody = Files.readAllBytes(file_creatingtoken.toPath());
	
	requestOptions = RequestOptions.create();
	requestContext = InitializePlaywright.InitiatePlayWrightAPI();
	apipostresponse = requestContext.post("https://restful-booker.herokuapp.com/auth",requestOptions
			       .setData(TokenReqBody)
			       .setHeader("Content-Type","application/json"));
	String postresponseText = apipostresponse.text();
	ObjectMapper objectmapper = new ObjectMapper();
	JsonNode JSONMapResponse = objectmapper.readTree( apipostresponse.body() );
	Token = JSONMapResponse.get("token").asText();
	System.out.println(postresponseText);
}


@AfterClass
public void EndSession()
{
	requestContext.dispose();
}

}