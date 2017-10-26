package de.htwberlin.ai.kbe.runMeRunner;

import de.htwberlin.ai.kbe.EmailChecker.ExternalEmailSyntaxChecker;

public class RunMeClass {
	
	@RunMe (input="something@domain.com")
	public boolean method22 (String input) {
		System.out.println("In method22");
		return ExternalEmailSyntaxChecker.isValidEmailAddress(input);
	}
	
	@RunMe (input="something@domain.com")
	public boolean method23 (String input) {
		System.out.println("In method23");
		return true;
	}


	public boolean method24 (String input) {
		System.out.println("In method24");
		return true;
	}


	public boolean method25 (String input) {
		System.out.println("IN method25");
		return true;
	}


}
