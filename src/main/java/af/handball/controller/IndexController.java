package af.handball.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import af.handball.entity.User;
import af.handball.helper.EmailValidator;
import af.handball.helper.PasswordValidator;
import af.handball.service.RegistrationService;

@Controller
public class IndexController {
	
	PrintWriter out;

	@Autowired
	private RegistrationService registrationService;

	@PersistenceContext(name = "handball-game")
	private EntityManager emgr;

	@RequestMapping("/main")
	public String index() {

		return "main";
		// return "/WEB-INF/jsp/index.jsp";
	}

	@RequestMapping("/dbtest")
	public String dbtest() {
		System.out.println("Hello from dbtest");

		User user = emgr.find(User.class, new BigDecimal(2));

		System.out.println("User's email = " + user.getEmail());

		return "main";
	}

	@RequestMapping("/userRegistration.html")
	public void registration(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Index Controller: registration() method");
		
		
		JSONObject jsonObj = new JSONObject();
		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		/*Registration validation*/
		EmailValidator emailValidator = new EmailValidator();
		boolean emailValidated = emailValidator.validate(email);
		PasswordValidator passwordValidator = new PasswordValidator();
		boolean passwordValidated = passwordValidator.validate(password);
		System.out.println(email + " validated = " + emailValidated);
		if (emailValidated && passwordValidated) {
			jsonObj.put("success", "succeed");
		} else if (!emailValidated) {
			jsonObj.put("success", "fail");
			jsonObj.put("failReason", "emailRegex");
		} else if (!passwordValidated) {
			jsonObj.put("success", "fail");
			jsonObj.put("failReason", "passwordRegex");
		}
		
		
		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			out.print(jsonObj);
			/*out.print("{\"success\": true, \"text\":\"Test\"}");*/
			out.close();
		} catch (IOException e) {
			System.out.println("PrintWriter exception... " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		
		
		/*
		 * System.out.println("REQUEST METHOD = " + request.getMethod()); String
		 * email = request.getParameter("email");
		 * System.out.println("Obtained email from form = " + email);
		 */


	}

}
