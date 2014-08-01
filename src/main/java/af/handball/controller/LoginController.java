package af.handball.controller;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import af.handball.service.LoginService;
import af.handball.service.TeamService;



@Controller
public class LoginController {
	
		
		@Autowired
		private LoginService loginService;
		@Autowired
		private TeamService teamService;
	

		@RequestMapping(value = "/login")
		@ResponseBody
		public String login(@RequestBody String json, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
			System.out.println("@LoginController");
			System.out.println("Json obj = " + json);
			
			JSONObject obtainedJsonObj = new JSONObject(json);
			
			JSONObject jsonObj = new JSONObject();
			String email = obtainedJsonObj.getString("email");
			
			boolean userAuthenticated = loginService.authenticateUser(email, obtainedJsonObj.getString("password"));
			
			if (userAuthenticated) {
				String status = "OK";
				
				jsonObj.put("authenticated", "true");
				session.setAttribute("email", email);
				jsonObj.put("status", status);
				

				// Set the session attributes
				session.setAttribute("teamName", teamService.getTeamName(email));
				
				try {
				session.setAttribute("teamLevel", teamService.getTeamLevel(email));
				} catch(NoResultException nre) {
					System.out.println("No Result Exception for team level");
					nre.printStackTrace();
				}
				
			} else {
				jsonObj.put("status", "false");
				jsonObj.put("authenticated", "false");
			}
			
			
			
			
			return jsonObj.toString();
		}
		
	
	
}
