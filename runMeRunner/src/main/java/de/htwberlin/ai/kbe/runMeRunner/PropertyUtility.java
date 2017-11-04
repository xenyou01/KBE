package de.htwberlin.ai.kbe.runMeRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyUtility {
	
//<<<<<<< HEAD
//	
//	
//=======
	public static Properties readConfigFile(String filename) throws FileNotFoundException, IOException{
		Properties result = new Properties();
		File configFile = new File(filename);
		FileInputStream fileInputStream = null;
		BufferedReader buReader = null;
		String propertyKeyValue;
		try{
			fileInputStream = new FileInputStream(configFile);
			buReader = new BufferedReader(new InputStreamReader(fileInputStream));
			
			while((propertyKeyValue = buReader.readLine()) != null){
				String key = propertyKeyValue.substring(0, propertyKeyValue.indexOf('='));
				String value = propertyKeyValue.substring(propertyKeyValue.indexOf('=') + 1, propertyKeyValue.length());
				result.setProperty(key, value);
			}
		}finally {
			if(fileInputStream != null){
				fileInputStream.close();
			}
		}
		return result;
	}
//>>>>>>> 6f82971a6e2de6dc04a21962922ab679b8dca9bc	
	
}
