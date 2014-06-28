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
	
	public PasswordValidator() {
		pattern = Pattern.compile(PASS_PATTERN);
	}
	
	public boolean validate(final String pass) {
		
		matcher = pattern.matcher(pass);
		return matcher.matches();
	}
}
