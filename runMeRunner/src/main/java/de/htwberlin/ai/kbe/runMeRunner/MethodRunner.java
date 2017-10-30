package de.htwberlin.ai.kbe.runMeRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

<<<<<<< HEAD
	public static void main(String[] args) throws ClassNotFoundException {
		
		String Anoname = System.getProperty("classWithRunMeAnnos", "de.htwberlin.ai.kbe.runMeRunner.RunMeClass");
		Class<?> clazz = Class.forName(Anoname);
=======
public class MethodRunner {
>>>>>>> 6f82971a6e2de6dc04a21962922ab679b8dca9bc

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
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
				RunMe runmeAnno = method.getAnnotation(RunMe.class);
				method.invoke(runMeClass.newInstance(), runmeAnno.input());
			}
		}
	}

}
//classWithRunMeAnnos=de.htwberlin.ai.kbe.runMeRunner.RunMeClass
