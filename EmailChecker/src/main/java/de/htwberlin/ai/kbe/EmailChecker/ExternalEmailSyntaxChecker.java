package de.htwberlin.ai.kbe.EmailChecker;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class ExternalEmailSyntaxChecker {
	
	public static boolean isValidEmailAddress(String email) {
		if (email == null) {
			return false;
		}
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}

}
