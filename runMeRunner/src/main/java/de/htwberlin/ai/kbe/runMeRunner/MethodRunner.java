package de.htwberlin.ai.kbe.runMeRunner;

public class MethodRunner {

	public static void main(String[] args) throws ClassNotFoundException {
		
		String Anoname = System.getProperty("classWithRunMeAnnos", "de.htwberlin.ai.kbe.runMeRunner.RunMeClass");
		Class<?> clazz = Class.forName(Anoname);

	}

}
