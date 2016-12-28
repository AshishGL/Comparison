import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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

import org.apache.commons.io.IOUtils;
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

public class CompareFiles {
	
	
	private static final String FILENAME = "D:\\filename.txt";
	public static String text;
	//static WebDriver driver;
	static String username="ashish.sharma6@globallogic.com";
	static String password="Test@1234";
	static String originalFile;
	static String originalFilePath="C:\\Users\\ashish.sharma6\\Desktop\\Test Folder\\Test_ocr.txt";
	static String ocrConvertedFile="C:\\Users\\ashish.sharma6\\Desktop\\Test Folder\\TextFile_OCR_UsingTIKA.txt";
	
	@BeforeSuite
	public void login()
	{
		WebDriver driver=new FirefoxDriver();
		//driver= new FirefoxDriver();
		driver.navigate().to("http://www.diffnow.com");
		driver.findElement(By.id("login_username")).sendKeys(username);
		driver.findElement(By.id("login_username")).sendKeys(password);
		driver.findElement(By.xpath(".//*[@id='loginButton']/span")).click();
	}
	
	
	public static void main(String []args) throws org.json.simple.parser.ParseException, InterruptedException, IOException{
		
		  //===========================Reading File===============================
		  
		  FileInputStream inputStream = new FileInputStream(originalFilePath);
		  try {
		      String originalFile = IOUtils.toString(inputStream);
		      System.out.println("============================================Original File======================"+originalFile);
		  } finally {
		      inputStream.close();
		  }
		
		
		
		
//===============JSON Parser Start=========================
		JSONParser parser = new JSONParser();  
		  
		  try {  
		  
		   Object obj = parser.parse(new FileReader(ocrConvertedFile));  
		  
		   JSONObject jsonObject = (JSONObject) obj;  
		  
		   text = (String) jsonObject.get("text");  
		   System.out.println("=================================================OCR Extracted File=====================================================");
		   System.out.println(text);  	
		   
		   
//==============JSON Parser End===========================		   
		  
		   WebDriver driver=new FirefoxDriver();
		   driver.get("https://www.diffnow.com");
		   
		   WebDriverWait wait = new WebDriverWait(driver,20);
		   
		   
		   WebElement textbox1=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clip1")));
		   textbox1.sendKeys(text);
		   
		   WebElement textbox2=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clip2")));
		   textbox2.sendKeys("Ashish");
		    //driver.findElement(By.xpath(".//*[@id='compareButtonText']/span[2]")).click(); 
		    WebElement compareButton= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='compareButtonText']/span[2]")));
		    compareButton.click();
		    WebElement frameCompare= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultsFrame")));
		    driver.switchTo().frame("resultsFrame"); 
		    System.out.println("Location of he Unmatched text is : "+driver.findElement(By.id("numDiffs")).getText());
		    List<WebElement> keywords=driver.findElements(By.id("colors"));
		    System.out.println("Umnatched Words in OCR Tika File : "+driver.findElement(By.xpath(".//*[@id='inspectorRightContent']/span[2]")).getText());
		    System.out.println("Umnatched Words in Original File : "+driver.findElement(By.xpath(".//*[@id='inspectorLeftContent']/span[2]")).getText());
		 
		    driver.close();
		    //WebElement rightLine=driver.findElement(By.className("right line"));
		    //WebElement leftLine=driver.findElement(By.className("left line"));
		    
		   // System.out.println("First"+rightLine.findElement(By.className("sa")).getText());
		    //System.out.println("Second"+rightLine.findElement(By.className("content")).findElement(By.className("sa")).getText());
		    
		    for(WebElement w1:keywords){
		    	
		    	System.out.println("Unmatched Keywords are : "+w1.getText());
		    }
		    
		   
		  } catch (FileNotFoundException e) {  
		   e.printStackTrace();  
		  } catch (IOException e) {  
		   e.printStackTrace();  
		  }  

		
		  
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
		
	
	}
	
}
