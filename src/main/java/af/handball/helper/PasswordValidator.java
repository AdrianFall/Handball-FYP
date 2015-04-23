package af.handball.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
	
	/*The password policy is:

		At least 6 chars

		Contains at least one digit

		Contains at least one lower alpha char

		Does not contain space, tab, etc.*/

	public static final String PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$";
	
	private Pattern pattern;
	private Matcher matcher;

	private boolean isAtLeastSixChars;
	private boolean containsAtLeastOneDigit = false;
	private boolean containsAtLeastOneLowerAlphaChar = false;
	private boolean containsBlankSpace = false;
	
	
	public PasswordValidator() {
		pattern = Pattern.compile(PASS_PATTERN);
	}
	
	public boolean validate(final String pass) {
		
		matcher = pattern.matcher(pass);
		boolean matches = matcher.matches();
		
		// Upon invalid password, call a method to figure out which specific parts of the regex have set the password to invalid.
		if (!matches)
			processValidationError(pass);
		
		return matches;
	}

	/**
	 * a method to figure out which specific parts of the regex have set the password to invalid.
	 * @param pass - the password
	 */
	private void processValidationError(String pass) {
		// Is at least 6 chars
		if (pass.length() >= 6)
			isAtLeastSixChars = true;
		// At least one digit
		if (Pattern.compile(("[0-9]")).matcher(pass).find())
			containsAtLeastOneDigit = true;
		// At least one lower alpha char
		if (Pattern.compile("[a-z]").matcher(pass).find())
			containsAtLeastOneLowerAlphaChar = true;
		// Does not contain a blank space
		if (pass.contains(" "))
			containsBlankSpace = true;
	}

	public boolean isAtLeastSixChars() {
		return isAtLeastSixChars;
	}

	public boolean isContainsAtLeastOneDigit() {
		return containsAtLeastOneDigit;
	}

	public boolean isContainsAtLeastOneLowerAlphaChar() {
		return containsAtLeastOneLowerAlphaChar;
	}

	public boolean isContainsBlankSpace() {
		return containsBlankSpace;
	}
}
