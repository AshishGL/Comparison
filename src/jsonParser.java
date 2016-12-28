import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class jsonParser {
	
public static void main(String []args) throws org.json.simple.parser.ParseException, InterruptedException, IOException{
		
		
		
		//Extracting Text from ocr file
		JSONParser parser = new JSONParser();  
		  
		  try {  
		  
			  String ocrConvertedFile="C:\\Users\\ashish.sharma6\\Desktop\\Test Folder\\TextFile_OCR_UsingTIKA.json";
			  Object obj = parser.parse(new FileReader(ocrConvertedFile));  
			  
			  JSONObject jsonObject = (JSONObject) obj;  
			 
			  String text = (String) jsonObject.get("text");  
			  System.out.println("=================================================OCR Extracted File=====================================================");
			  System.out.println(text);   
		  }
		   catch (FileNotFoundException e) {  
			   e.printStackTrace();  
			  } catch (IOException e) {  
			   e.printStackTrace();  
			  }  
		  

}

}
