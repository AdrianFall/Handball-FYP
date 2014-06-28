package af.handball.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
	
	public EmailValidator() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	public boolean validate(final String email) {
		 
		matcher = pattern.matcher(email);
		return matcher.matches();
 
	}
}
