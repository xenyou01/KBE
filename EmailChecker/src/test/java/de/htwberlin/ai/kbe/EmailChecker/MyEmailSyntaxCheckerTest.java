package de.htwberlin.ai.kbe.EmailChecker;

import org.junit.*;
import org.junit.Test;

public class MyEmailSyntaxCheckerTest {
	
	ExternalEmailSyntaxChecker checker;
	
	@Before
	public void initialize()
	{
		checker = new ExternalEmailSyntaxChecker();
	}
	
	@Test
	public void nullEmailShouldReturnFalse() {
		Assert.assertFalse(checker.isValidEmailAddress(null));
	}
	
	@Test
	public void emptyEmailShouldReturnFalse() {
		Assert.assertFalse(checker.isValidEmailAddress(""));
	}
	
	@Test
	public void noAtSignsShouldReturnFalse() {
		Assert.assertFalse(checker.isValidEmailAddress("gireshtw.de"));
	}
	
	@Test
	public void noDomainShouldReturnFalse() {
		Assert.assertFalse(checker.isValidEmailAddress("gires@"));
	}
	
	@Test
	public void whitespaceInEmailShouldReturnFalse() {
		Assert.assertFalse(checker.isValidEmailAddress("gires @htw-berlin.de"));
	}
	
	@Test
	public void validEmailShouldReturnTrue() {
		Assert.assertTrue(checker.isValidEmailAddress("gires@htw-berlin.de"));
	}

}
