package JsonDataDriven;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonData {
	
	//Function TO Drive JsonData (Array/List Of Maps)
	public static List<HashMap<String,Object>> getJsonDataList(String jsonFilePath) throws IOException
	{
        //First Step: Parsing Jsonfile Into String
        String JsonContent=FileUtils.readFileToString(new File(jsonFilePath),StandardCharsets.UTF_8);
        
        //Second Step: Map The String File to List of HashMaps
        ObjectMapper objectmapper = new ObjectMapper();
        List<HashMap<String,Object>> data = objectmapper.readValue(JsonContent ,
        new TypeReference<List<HashMap<String,Object>>>(){} );
        
        //Return data
        return data;	
	}
	//Function TO Drive JsonData (Maps)
	public static HashMap<String,Object> getJsonDataMap(String jsonFilePath) throws IOException
	{
        //First Step: Parsing Jsonfile Into String
        String JsonContent=FileUtils.readFileToString(new File(jsonFilePath),StandardCharsets.UTF_8);
        
        //Second Step: Map The String File to List of HashMaps
        ObjectMapper objectmapper = new ObjectMapper();
        HashMap<String,Object> data = objectmapper.readValue(JsonContent ,
        new TypeReference<HashMap<String,Object>>(){} );
        
        //Return data
        return data;	
	}

}
