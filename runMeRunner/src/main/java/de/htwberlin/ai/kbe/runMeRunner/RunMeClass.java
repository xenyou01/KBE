package de.htwberlin.ai.kbe.runMeRunner;

import de.htwberlin.ai.kbe.EmailChecker.ExternalEmailSyntaxChecker;

public class RunMeClass {
	
	@RunMe (input="something@domain.com")
	public boolean method22 (String input) {
		System.out.println("In method22");
		return ExternalEmailSyntaxChecker.isValidEmailAddress(input);
	}
	
	@RunMe (input="something@domain.com")
	public void method23 (String input) {
		System.out.print("In method23, ");
		System.out.println("Thank you for running me :)");
	}

	@SuppressWarnings("Yo!")
	public boolean method24 (String input) {
		System.out.println("In method24");
		return true;
	}


	public String method25 (String input) {
		System.out.println("IN method25");
		return input;
	}


}
