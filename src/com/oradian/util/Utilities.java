package com.oradian.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.net.HttpURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.util.Reporter;



public class Utilities {
		
		

       public static String readProp(String key)
       {
              Properties prop = new Properties();
              InputStream input = null;

              try {

                     input = new FileInputStream("./resources/application.properties");

                     // load a properties file
                     prop.load(input);
                     
                     String value = prop.getProperty(key);
                     System.out.println(value);
                     return value;



              } catch (IOException ex) {
                     ex.printStackTrace();
                     return "fail";
       }

}
       
       public static List<WebElement> clickableLinks(QAFExtendedWebDriver driver) {
   		List<WebElement> linksToClick = new ArrayList<WebElement>();
   		List<WebElement> elements = driver.findElements(By.tagName("a"));
   		elements.addAll(driver.findElements(By.tagName("img")));
   		
   		for (WebElement e : elements) {
   			if (e.getAttribute("href") != null) {
   				linksToClick.add(e);
   			}
   		}
   		return linksToClick;
   	}
   	
   	public static String linkStatus(URL url) {
   		try {
   			HttpURLConnection http = (HttpURLConnection) url.openConnection();
   			http.connect();
   			String responseMessage = http.getResponseMessage();
   			http.disconnect();
   			return responseMessage;
   		}
   		catch (Exception e) {
   			return e.getMessage();
   		}
   	}
   	
   	public static String randomString(String type,int limit) {
   		if(type.equalsIgnoreCase("text"))
   		{
        String charsOradian = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder oradUser = new StringBuilder();
        Random rnd = new Random();
        while (oradUser.length() < limit) { // length of the random string.
            int index = (int) (rnd.nextFloat() * charsOradian.length());
            oradUser.append(charsOradian.charAt(index));
        }
        String randomizedString = oradUser.toString();
        return randomizedString;
   		}
   		else if(type.equalsIgnoreCase("digit"))
   		{
   			String charsOradian = "0123456789";
   	        StringBuilder oradUser = new StringBuilder();
   	        Random rnd = new Random();
   	        while (oradUser.length() < limit) { // length of the random string.
   	            int index = (int) (rnd.nextFloat() * charsOradian.length());
   	            oradUser.append(charsOradian.charAt(index));
   	        }
   	        String randomizedString = oradUser.toString();
   	        return randomizedString;
   		}
   		else {
   			Reporter.log("Improper inout passed to randomizer function");
   			return "bad Input";
   			
   		}

    }

      
}

