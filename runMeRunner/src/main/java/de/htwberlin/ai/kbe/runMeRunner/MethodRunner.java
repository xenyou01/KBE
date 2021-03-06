package de.htwberlin.ai.kbe.runMeRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class MethodRunner {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InstantiationException {
		String runmeString;
		Class<?> runMeClass;
		if(args.length == 0){
			System.out.println("Please give a config file as parameter.");
			return;
		}
		Properties properties;
		try {
			properties = PropertyUtility.readConfigFile(args[0]);
			runmeString = properties.getProperty("classWithRunMeAnnos");
			if(runmeString == null){
				System.out.println("The expected property was not found!");
				return;
			}
			try{
				runMeClass = Class.forName(runmeString);
			} catch (ClassNotFoundException e) {
				System.out.println("The given class was not found!");
				return;
			}
		} catch (FileNotFoundException e) {
			System.out.println("the given file was not found !");
			return;
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Problem occured while reading the file, make sure it only contains properties!");
			return;
		}
		catch (IOException e) {
			System.out.println("A problem occured while reading the config file");
			return;
		}
		
		Method[] methods = runMeClass.getDeclaredMethods();
		for(Method method : methods){
			if(method.isAnnotationPresent(RunMe.class)){
				try {
					RunMe runmeAnno = method.getAnnotation(RunMe.class);
					method.invoke(runMeClass.newInstance(), runmeAnno.input());
				} catch (InvocationTargetException e) {
					System.out.println(">>>>>>> Invocation Exception");
					continue;
				} catch (Exception e) {
					continue;
				}
			}
		}
	}

}
//classWithRunMeAnnos=de.htwberlin.ai.kbe.runMeRunner.RunMeClass
