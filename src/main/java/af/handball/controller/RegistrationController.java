package af.handball.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import af.handball.helper.EmailValidator;
import af.handball.helper.PasswordValidator;
import af.handball.service.RegistrationService;

@Controller
public class RegistrationController {

	
	private JSONObject jsonObj;
	private String success = "";
	private String failReason;
	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping("/userRegistration.html")
	public void registration(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		System.out.println("Index Controller: registration() method");
		
		
		jsonObj = new JSONObject();
		
		
		final String email = request.getParameter("email");
		final String password = request.getParameter("password");
		
		/*Registration validation*/
		EmailValidator emailValidator = new EmailValidator();
		boolean emailValidated = emailValidator.validate(email);
		PasswordValidator passwordValidator = new PasswordValidator();
		boolean passwordValidated = passwordValidator.validate(password);
		System.out.println(email + " validated = " + emailValidated);
		if (emailValidated && passwordValidated) {
			postValidationRegistration(email, password, session);
			
		} else if (!emailValidated) {
			success = "fail";
			failReason = "emailRegex";
		} else if (!passwordValidated) {
			success = "fail";
			failReason = "passwordRegex";
			// obtain the first specific error in the validation of password
			if (!passwordValidator.isAtLeastSixChars())
				jsonObj.put("passwordRegexFailReason", "doesNotContainSixChars");
			else if (!passwordValidator.isContainsAtLeastOneDigit())
				jsonObj.put("passwordRegexFailReason", "doesNotContainAtLeastOneDigit");
			else if (!passwordValidator.isContainsAtLeastOneLowerAlphaChar())
				jsonObj.put("passwordRegexFailReason", "doesNotContainAtLeastOneLowerAlphaChar");
			else if (passwordValidator.isContainsBlankSpace())
				jsonObj.put("passwordRegexFailReason", "doesContainBlankSpace");
		}
		
		
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			jsonObj.put("success", success);
			
			request.setAttribute("forwardedFrom", "registration");
			
			if (failReason != null) jsonObj.put("failReason", failReason);
			out.print(jsonObj);
			/*out.print("{\"success\": true, \"text\":\"Test\"}");*/
			out.close();
		} catch (IOException e) {
			System.out.println("PrintWriter exception... " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
	} // End of registration
	
	

	private void postValidationRegistration(final String email, final String password, HttpSession session) {
		boolean emailExists = registrationService.emailExists(email);
		if (!emailExists) {
			// Create a new user
			boolean userCreated = registrationService.newUser(email, password);
			if (userCreated) {
				System.out.println("NEW USER CREATED!!!!!!");
				session.setAttribute("email", email);
				success = "success";
				
			} else {
				System.out.println("USER WASNT CREATED :((((((((");
				/*jsonObj.put("success", "fail");
				jsonObj.put("failReason", "emailTaken");*/
				success = "fail";
				failReason = "dbError";
			}
		} else { // Email exists
			/*jsonObj.put("success", "fail");
			jsonObj.put("failReason", "emailTaken");*/
			success = "fail";
			failReason = "emailExists";
		}
	}
	
}
