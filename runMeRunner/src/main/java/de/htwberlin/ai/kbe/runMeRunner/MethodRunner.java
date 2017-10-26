package de.htwberlin.ai.kbe.runMeRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class MethodRunner {

	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Properties properties;
		try {
			properties = PropertyUtility.readConfigFile("runMeConfig.properties");
		} catch (FileNotFoundException e) {
			System.out.println("the given file was not found !");
			return;
		}
		catch (IOException e) {
			System.out.println("A problem occured while reading the config file");
			return;
		}
		String runmeString = properties.getProperty("classWithRunMeAnnos", "de.htwberlin.ai.kbe.runMeRunner.RunMeClass");
		Class<?> runMeClass = Class.forName(runmeString);
		
		Method[] methods = runMeClass.getDeclaredMethods();
		for(Method method : methods){
			if(method.isAnnotationPresent(RunMe.class)){
				RunMe runmeAnno = method.getAnnotation(RunMe.class);
				method.invoke(runMeClass.newInstance(), runmeAnno.input());
			}
		}
	}

}
