
package de.htwberlin.ai.kbe.runMeRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import de.htwberlin.ai.kbe.EmailChecker.ExternalEmailSyntaxChecker;

public class PropertyUtilityTest {
	
	PropertyUtility pUtility;
	
	@Before
	public void initialize() {
		pUtility = new PropertyUtility();
	}
	
	@Test
	public void correctFileParameterShouldReturnProperty() {
		String filename = "runMeConfig.properties";
		String stringProperties = "{classWithRunMeAnnos=de.htwberlin.ai.kbe.runMeRunner.RunMeClass}";
		try {
			Assert.assertTrue(stringProperties.equals(pUtility.readConfigFile(filename).toString()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(expected = FileNotFoundException.class) 
	public void methodCallWithWrongFilenameShouldThrowFileNotFoundException() throws FileNotFoundException, IOException {
		pUtility.readConfigFile("fakeName.properties");
	}
	
	@Test(expected = IndexOutOfBoundsException.class) 
	public void nonExistingPropertyInPropertyFileShouldThrowIndexOutOfBoundsException() throws FileNotFoundException, IOException {
		String filename = "runMeConfigWithoutProperties.properties";
		pUtility.readConfigFile(filename);
	}	
	
	@Test(expected= NullPointerException.class)
	public void nullFilenameshouldThrowNullPointerException() throws FileNotFoundException, IOException{
		PropertyUtility.readConfigFile(null);
	}
}