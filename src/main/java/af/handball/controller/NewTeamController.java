package af.handball.controller;

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

import af.handball.service.NewTeamService;

@Controller
public class NewTeamController {
	
	@Autowired
	private NewTeamService newTeamService;
	
	@RequestMapping(value = "/hasTeam", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces="application/json")
	@ResponseBody
	public String hasTeam(HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jsonObj = new JSONObject();
		
		HttpSession session = request.getSession();
		
		String email = (String) session.getAttribute("email");
		
		if (email == null) {
			jsonObj.put("status", "sessionExpired");
		} else {
			boolean hasTeam = newTeamService.hasTeam(email);
			if (!hasTeam) {
				jsonObj.put("status", "OK");
				jsonObj.put("hasTeam", "false");
			} else {
				jsonObj.put("status", "OK");
				jsonObj.put("hasTeam", "true");
			}
		}
		
		return jsonObj.toString();
	}
	
	@RequestMapping(value = "/createNewTeam", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces="application/json")
	@ResponseBody
	public String createNewTeam(@RequestBody String jsonMsg, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		JSONObject jsonObj = new JSONObject();
		String email = (String) session.getAttribute("email");
		
		if (email == null) {
			jsonObj.put("status", "sessionExpired");
		} else {
			
			
			// Obtain the JSON message
			JSONObject obtainedJSONObj = new JSONObject(jsonMsg);
			String teamName = (String) obtainedJSONObj.get("teamName");
			System.out.println("Obtained team name: " + teamName + " from " + email);
			
			// Validate the team name (no empty data & more than 3 characters)
			if (teamName.trim().equals("")) {
				jsonObj.put("teamNameError", "The team name can't be empty");
				jsonObj.put("status", "error");
			} else if (teamName.trim().length() <= 3) {
				jsonObj.put("status", "error");
				jsonObj.put("teamNameError", "The team name must be longer than 3 characters.");
			} else {
				jsonObj.put("status", "OK");
				// Create the new team
				boolean teamCreated = newTeamService.newTeam(email, teamName);
				
				if (teamCreated) { 
					session.setAttribute("teamName", teamName);
					jsonObj.put("teamCreated", "true");
				}
				else jsonObj.put("teamCreated", "false");
			}
			
			
		}
		
		
		
		/*RequestDispatcher reqDisp = request.getRequestDispatcher(forwardURL);
		
		try {
			System.out.println("NewTeamController forwarding to " + forwardURL);
			reqDisp.forward(request, response);
		} catch (ServletException e) {
			System.out.println("ServletException when forwarding to " + forwardURL + e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException when forwarding to " + forwardURL + e.getLocalizedMessage());
			e.printStackTrace();
		}*/
		
		
		return jsonObj.toString();
	}
}
