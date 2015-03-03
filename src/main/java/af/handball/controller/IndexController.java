package af.handball.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {
	

	

	@RequestMapping("/main")
	public String index() {

		return "main";
	}
	
	/*@RequestMapping(value = "/postRegistration",
					method = RequestMethod.POST,
					headers = {"Content-type=application/json"})
	@ResponseBody
	public JSONObject postRegistration(@RequestBody PostRegistration postReg) {
		System.out.println("Hello from postRegistration()");
		System.out.println("Forwarded from = " + postReg.getForwardedFrom());
		
		
		
		return new JSONObject();
	}*/
	
	
	@RequestMapping("/game")
	public String game() {
		System.out.println("@IndexController... Forwarding to game");
		return "game";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		System.out.println("logout()");
		session.invalidate();
		return "main";
	}

	

}
