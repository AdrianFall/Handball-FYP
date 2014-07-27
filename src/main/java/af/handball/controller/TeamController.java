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

import af.handball.entity.Skill;
import af.handball.service.GameService;
import af.handball.service.LeagueService;
import af.handball.service.TeamService;


@Controller
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private LeagueService leagueService;
	
	@RequestMapping(value = "/hasTeam", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces="application/json")
	@ResponseBody
	public String hasTeam(HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jsonObj = new JSONObject();
		
		HttpSession session = request.getSession();
		
		String email = (String) session.getAttribute("email");
		
		if (email == null) {
			jsonObj.put("status", "sessionExpired");
		} else {
			boolean hasTeam = teamService.hasTeam(email);
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
	
	@RequestMapping(value = "/getPlayerSkills", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces="application/json")
	@ResponseBody
	public String getPlayerSkills(@RequestBody String jsonMsg, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		JSONObject jsonObj = new JSONObject();
		String email = (String) session.getAttribute("email");
		
		if (email == null) { // Session expired
			jsonObj.put("status", "sessionExpired");
		} else { // Session active
			
			
			// Obtain the JSON message
			JSONObject obtainedJSONObj = new JSONObject(jsonMsg);
			
			String playerId = (String) obtainedJSONObj.get("playerId");
			System.out.println("Obtained player id = " + playerId);
			if (playerId.trim().equals("") || playerId == null) jsonObj.put("status", "error");
			else {
				try {
					// START Obtain the Skill object and allocate its data in the jsonObj.
					Skill skill = gameService.getPlayerSkills(Integer.parseInt(playerId));
					System.out.println("Skill = " + skill);
					/* Physical Skills */
					jsonObj.put("acceleration", skill.getAcceleration());
					jsonObj.put("sprint_speed", skill.getSprint_speed());
					jsonObj.put("jumping", skill.getJumping());
					jsonObj.put("balance", skill.getBalance());
					jsonObj.put("agility", skill.getAgility());
					jsonObj.put("stamina", skill.getStamina());
					jsonObj.put("strength", skill.getStrength());
					jsonObj.put("reactions", skill.getReactions());
					jsonObj.put("blocking", skill.getBlocking());
					jsonObj.put("fitness", skill.getFitness());
					/* Mental Skills */
					jsonObj.put("aggression", skill.getAggression());
					jsonObj.put("interceptions", skill.getInterceptions());
					jsonObj.put("attack_position", skill.getAttack_position());
					jsonObj.put("vision", skill.getVision());
					jsonObj.put("creativity", skill.getCreativity());
					/* Goal Keeping Skills */
					jsonObj.put("reflexes", skill.getReflexes());
					jsonObj.put("handling", skill.getHandling());
					jsonObj.put("positioning", skill.getPositioning());
					jsonObj.put("leg_saves", skill.getLeg_saves());
					jsonObj.put("penalty_saves", skill.getPenalty_saves());
					jsonObj.put("six_m_saves", skill.getSix_m_saves());
					jsonObj.put("nine_m_saves", skill.getNine_m_saves());
					jsonObj.put("communication", skill.getCommunication());
					jsonObj.put("angles", skill.getAngles());
					jsonObj.put("catching", skill.getCatching());
					/* Technical Skills */
					jsonObj.put("ball_control", skill.getBall_control());
					jsonObj.put("long_shots", skill.getLong_shots());
					jsonObj.put("fk_accuracy", skill.getFk_accuracy());
					jsonObj.put("shot_power", skill.getShot_power());
					jsonObj.put("dribbling", skill.getDribbling());
					jsonObj.put("short_passing", skill.getShort_passing());
					jsonObj.put("long_passing", skill.getLong_passing());
					jsonObj.put("stand_tackles", skill.getStand_tackles());
					jsonObj.put("marking", skill.getMarking());
					jsonObj.put("penalties", skill.getPenalties());
					jsonObj.put("curve", skill.getCurve());
					jsonObj.put("finishing", skill.getFinishing());
					jsonObj.put("six_m_shots", skill.getSix_m_shots());
					jsonObj.put("nine_m_shots", skill.getNine_m_shots());
					jsonObj.put("lob_shots", skill.getLob_shots());
					// END Fetch the data and allocate in jsonObj.
					
					// Add status to the jsonObj
					jsonObj.put("status", "OK");
					
				} catch (Exception e) {
					System.out.println("Exception when parsing player id to obtain his skills. Exception - " + e.getLocalizedMessage());
					jsonObj.put("status", "error");
				}
			}
		}
		
		//TODO
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
				int teamId = teamService.preGenerateTeam(email, teamName);
				
				
				if (teamId != -1) { 
					
					
					/*System.out.println("Calling league service to allocate a team.");*/
					/*leagueService.allocateTeamInLeague(email, teamName, 1);*/
					// Allocate the pre-generated team.
					boolean assigned = teamService.assignTeam(teamId, email, teamName);
					if (assigned) {
						session.setAttribute("teamName", teamName);
						jsonObj.put("teamCreated", "true");
					} else jsonObj.put("teamCreated", "false");
				} else jsonObj.put("teamCreated", "false");
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
