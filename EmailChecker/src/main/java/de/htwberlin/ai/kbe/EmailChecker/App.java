package de.htwberlin.ai.kbe.EmailChecker;

/**
 * Check the Email Syntax with the external Email Checker
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if(args.length == 0)
        {
        	System.out.println("No Email adress has been given to test");
        	return;
        }
        ExternalEmailSyntaxChecker externalEmailSyntaxChecker = new ExternalEmailSyntaxChecker();
        boolean check = externalEmailSyntaxChecker.isValidEmailAddress(args[0]);
        if(check)
        	System.out.println("The Email Syntax is correct!");
        else
        	System.out.println("The Email Syntax is not correct!");
    }
}
