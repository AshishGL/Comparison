import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

public class Compare {
	
	
	private static final String FILENAME = "D:\\OCR_Version.txt";
	private static final String originalFilePath="C:\\Users\\ashish.sharma6\\Desktop\\Test Folder\\Test_ocr.txt";
	public static String text;
	
	
	
	public static void main(String []args) throws FileNotFoundException, org.json.simple.parser.ParseException, InterruptedException{
		
		
		
//===============JSON Parser Start=========================
		JSONParser parser = new JSONParser();  
		  
		  try {  
		  
		   Object obj = parser.parse(new FileReader("C:\\Users\\ashish.sharma6\\Desktop\\Test Folder\\TextFile_OCR_UsingTIKA.txt"));  
		  
		   JSONObject jsonObject = (JSONObject) obj;  
		  
		   text = (String) jsonObject.get("text");  
		   System.out.println("Text is: "+text);  
		  } catch (FileNotFoundException e) {  
			   e.printStackTrace();  
			  } catch (IOException e) {  
			   e.printStackTrace();  
			  }  
		   
//============Writing to a file========================		   
		   BufferedWriter bw = null;
			FileWriter fw = null;
			try {

				fw = new FileWriter(FILENAME);
				bw = new BufferedWriter(fw);
				bw.write(text);

				System.out.println("Done");

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}
				
				
		
		}
		   
		  	   
		  
		   WebDriver driver=new FirefoxDriver();
		   WebDriverWait wait = new WebDriverWait(driver,20);
		   driver.get("https://www.diffnow.com");
		
		  //==============uploading files for comparison===============
		   
		   driver.findElement(By.xpath(".//*[@id='ui-id-2']")).click();
		   driver.findElement(By.id("AsyncFileUploadControl1_ctl02")).sendKeys(FILENAME);
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AsyncFileUploadControl1_ctl02")));
		   driver.findElement(By.id("AsyncFileUploadControl2_ctl02")).sendKeys(originalFilePath);
		  
		   WebElement CompareButton=wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Compare")));
		   CompareButton.click();
		   
		   
		    WebElement frameCompare= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsFrame")));
		    driver.switchTo().frame("resultsFrame"); 
		    System.out.println("Location of the Unmatched text is : "+driver.findElement(By.id("numDiffs")).getText());
		    List<WebElement> keywords=driver.findElements(By.id("colors"));
		    System.out.println("Umnatched Words in Right Doc : "+driver.findElement(By.xpath(".//*[@id='inspectorRightContent']/span[2]")).getText());
		    System.out.println("Umnatched Words in Left Doc : "+driver.findElement(By.xpath(".//*[@id='inspectorLeftContent']/span[2]")).getText());
		 
		    //WebElement rightLine=driver.findElement(By.className("right line"));
		    //WebElement leftLine=driver.findElement(By.className("left line"));
		    
		   // System.out.println("First"+rightLine.findElement(By.className("sa")).getText());
		    //System.out.println("Second"+rightLine.findElement(By.className("content")).findElement(By.className("sa")).getText());
		    
		    for(WebElement w1:keywords){
		    	
		    	System.out.println("Unmatched Keywords are : "+w1.getText());
		    }
		    
		   
		 

		
		
	
	}
	
}
