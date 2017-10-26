package de.htwberlin.ai.kbe.runMeRunner;

import de.htwberlin.ai.kbe.EmailChecker.ExternalEmailSyntaxChecker;

public class RunMeClass {
	
	@RunMe (input="something@domain.com")
	public boolean method22 (String input) {
		System.out.println("IN method22");
		return ExternalEmailSyntaxChecker.isValidEmailAddress(input);
	}

}
