package de.htwberlin.ai.kbe.runMeRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertyUtility {
	
	public static Properties readConfigFile(String filename) throws FileNotFoundException, IOException{
		Properties result = new Properties();
		File configFile = new File(filename);
		FileInputStream fileInputStream = new FileInputStream(configFile);
		BufferedReader buReader = new BufferedReader(new InputStreamReader(fileInputStream));
		String propertyKeyValue;
		while((propertyKeyValue = buReader.readLine()) != null){
			String key = propertyKeyValue.substring(0, propertyKeyValue.indexOf('='));
			String value = propertyKeyValue.substring(propertyKeyValue.indexOf('=') + 1, propertyKeyValue.length());
			result.setProperty(key, value);
		}
		return result;
	}

}
