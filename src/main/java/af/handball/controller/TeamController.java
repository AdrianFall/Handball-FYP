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
import af.handball.generator.QualityGenerator;
import af.handball.helper.MathRoundHelper;
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
				session.setAttribute("teamLevel", teamService.getTeamLevel(email));
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
					
					int teamLevel = (Integer) session.getAttribute("teamLevel");
					System.out.println("Team levle = " + teamLevel);
					System.out.println("acceleration qt  = " + QualityGenerator.getQualityType(skill.getAcceleration(), teamLevel));
					System.out.println("acceleration as perc = " + QualityGenerator.getQualityAsPercentage(skill.getAcceleration(), teamLevel));
					
					/* Physical Skills */
					jsonObj.put("acceleration", MathRoundHelper.round(skill.getAcceleration(), 1));
					jsonObj.put("acceleration_quality_type", QualityGenerator.getQualityType(skill.getAcceleration(), teamLevel));
					jsonObj.put("acceleration_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getAcceleration(), teamLevel));
					
					jsonObj.put("sprint_speed",  MathRoundHelper.round(skill.getSprint_speed(), 1));
					jsonObj.put("sprint_speed_quality_type", QualityGenerator.getQualityType(skill.getSprint_speed(), teamLevel));
					jsonObj.put("sprint_speed_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getSprint_speed(), teamLevel));
					
					jsonObj.put("jumping",  MathRoundHelper.round(skill.getJumping(), 1));
					jsonObj.put("jumping_quality_type", QualityGenerator.getQualityType(skill.getJumping(), teamLevel));
					jsonObj.put("jumping_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getJumping(), teamLevel));
					
					jsonObj.put("balance", MathRoundHelper.round(skill.getBalance(), 1));
					jsonObj.put("balance_quality_type", QualityGenerator.getQualityType(skill.getBalance(), teamLevel));
					jsonObj.put("balance_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getBalance(), teamLevel));
					
					jsonObj.put("agility", MathRoundHelper.round(skill.getAgility(), 1));
					jsonObj.put("agility_quality_type", QualityGenerator.getQualityType(skill.getAgility(), teamLevel));
					jsonObj.put("agility_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getAgility(), teamLevel));
					
					jsonObj.put("stamina", MathRoundHelper.round(skill.getStamina(), 1));
					jsonObj.put("stamina_quality_type", QualityGenerator.getQualityType(skill.getStamina(), teamLevel));
					jsonObj.put("stamina_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getStamina(), teamLevel));
					
					jsonObj.put("strength", MathRoundHelper.round(skill.getStrength(), 1));
					jsonObj.put("strength_quality_type", QualityGenerator.getQualityType(skill.getStrength(), teamLevel));
					jsonObj.put("strength_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getStrength(), teamLevel));
					
					jsonObj.put("reactions", MathRoundHelper.round(skill.getReactions(), 1));
					jsonObj.put("reactions_quality_type", QualityGenerator.getQualityType(skill.getReactions(), teamLevel));
					jsonObj.put("reactions_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getReactions(), teamLevel));
					
					jsonObj.put("blocking", MathRoundHelper.round(skill.getBlocking(), 1));
					jsonObj.put("blocking_quality_type", QualityGenerator.getQualityType(skill.getBlocking(), teamLevel));
					jsonObj.put("blocking_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getBlocking(), teamLevel));
					
					jsonObj.put("fitness", MathRoundHelper.round(skill.getFitness(), 1));
					jsonObj.put("fitness_quality_type", QualityGenerator.getQualityType(skill.getFitness(), teamLevel));
					jsonObj.put("fitness_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getFitness(), teamLevel));
					/* Mental Skills */
					jsonObj.put("aggression", MathRoundHelper.round(skill.getAggression(), 1));
					jsonObj.put("aggression_quality_type", QualityGenerator.getQualityType(skill.getAggression(), teamLevel));
					jsonObj.put("aggression_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getAggression(), teamLevel));
					
					jsonObj.put("interceptions", MathRoundHelper.round(skill.getInterceptions(), 1));
					jsonObj.put("interceptions_quality_type", QualityGenerator.getQualityType(skill.getInterceptions(), teamLevel));
					jsonObj.put("interceptions_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getInterceptions(), teamLevel));
					
					jsonObj.put("attack_position", MathRoundHelper.round(skill.getAttack_position(), 1));
					jsonObj.put("attack_position_quality_type", QualityGenerator.getQualityType(skill.getAttack_position(), teamLevel));
					jsonObj.put("attack_position_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getAttack_position(), teamLevel));
					
					jsonObj.put("vision", MathRoundHelper.round(skill.getVision(), 1));
					jsonObj.put("vision_quality_type", QualityGenerator.getQualityType(skill.getVision(), teamLevel));
					jsonObj.put("vision_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getVision(), teamLevel));
					
					jsonObj.put("creativity", MathRoundHelper.round(skill.getCreativity(), 1));
					jsonObj.put("creativity_quality_type", QualityGenerator.getQualityType(skill.getCreativity(), teamLevel));
					jsonObj.put("creativity_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getCreativity(), teamLevel));
					/* Goal Keeping Skills */
					jsonObj.put("reflexes", MathRoundHelper.round(skill.getReflexes(), 1));
					jsonObj.put("reflexes_quality_type", QualityGenerator.getQualityType(skill.getReflexes(), teamLevel));
					jsonObj.put("reflexes_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getReflexes(), teamLevel));
					
					jsonObj.put("handling", MathRoundHelper.round(skill.getHandling(), 1));
					jsonObj.put("handling_quality_type", QualityGenerator.getQualityType(skill.getHandling(), teamLevel));
					jsonObj.put("handling_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getHandling(), teamLevel));
					
					jsonObj.put("positioning", MathRoundHelper.round(skill.getPositioning(), 1));
					jsonObj.put("positioning_quality_type", QualityGenerator.getQualityType(skill.getPositioning(), teamLevel));
					jsonObj.put("positioning_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getPositioning(), teamLevel));
					
					jsonObj.put("leg_saves", MathRoundHelper.round(skill.getLeg_saves(), 1));
					jsonObj.put("leg_saves_quality_type", QualityGenerator.getQualityType(skill.getLeg_saves(), teamLevel));
					jsonObj.put("leg_saves_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getLeg_saves(), teamLevel));
					
					jsonObj.put("penalty_saves", MathRoundHelper.round(skill.getPenalty_saves(), 1));
					jsonObj.put("penalty_saves_quality_type", QualityGenerator.getQualityType(skill.getPenalty_saves(), teamLevel));
					jsonObj.put("penalty_saves_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getPenalty_saves(), teamLevel));
					
					jsonObj.put("six_m_saves", MathRoundHelper.round(skill.getSix_m_saves(), 1));
					jsonObj.put("six_m_saves_quality_type", QualityGenerator.getQualityType(skill.getSix_m_saves(), teamLevel));
					jsonObj.put("six_m_saves_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getSix_m_saves(), teamLevel));
					
					jsonObj.put("nine_m_saves", MathRoundHelper.round(skill.getNine_m_saves(), 1));
					jsonObj.put("nine_m_saves_quality_type", QualityGenerator.getQualityType(skill.getNine_m_saves(), teamLevel));
					jsonObj.put("nine_m_saves_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getNine_m_saves(), teamLevel));
					
					jsonObj.put("communication", MathRoundHelper.round(skill.getCommunication(), 1));
					jsonObj.put("communication_quality_type", QualityGenerator.getQualityType(skill.getCommunication(), teamLevel));
					jsonObj.put("communication_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getCommunication(), teamLevel));
					
					jsonObj.put("angles", MathRoundHelper.round(skill.getAngles(), 1));
					jsonObj.put("angles_quality_type", QualityGenerator.getQualityType(skill.getAngles(), teamLevel));
					jsonObj.put("angles_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getAngles(), teamLevel));
					
					jsonObj.put("catching", MathRoundHelper.round(skill.getCatching(), 1));
					jsonObj.put("catching_quality_type", QualityGenerator.getQualityType(skill.getCatching(), teamLevel));
					jsonObj.put("catching_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getCatching(), teamLevel));
					
					/* Technical Skills */
					jsonObj.put("ball_control", MathRoundHelper.round(skill.getBall_control(), 1));
					jsonObj.put("ball_control_quality_type", QualityGenerator.getQualityType(skill.getBall_control(), teamLevel));
					jsonObj.put("ball_control_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getBall_control(), teamLevel));
					
					jsonObj.put("long_shots", MathRoundHelper.round(skill.getLong_shots(), 1));
					jsonObj.put("long_shots_quality_type", QualityGenerator.getQualityType(skill.getLong_shots(), teamLevel));
					jsonObj.put("long_shots_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getLong_shots(), teamLevel));
					
					jsonObj.put("fk_accuracy", MathRoundHelper.round(skill.getFk_accuracy(), 1));
					jsonObj.put("fk_accuracy_quality_type", QualityGenerator.getQualityType(skill.getFk_accuracy(), teamLevel));
					jsonObj.put("fk_accuracy_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getFk_accuracy(), teamLevel));
					
					jsonObj.put("shot_power", MathRoundHelper.round(skill.getShot_power(), 1));
					jsonObj.put("shot_power_quality_type", QualityGenerator.getQualityType(skill.getShot_power(), teamLevel));
					jsonObj.put("shot_power_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getShot_power(), teamLevel));
					
					jsonObj.put("dribbling", MathRoundHelper.round(skill.getDribbling(), 1));
					jsonObj.put("dribbling_quality_type", QualityGenerator.getQualityType(skill.getDribbling(), teamLevel));
					jsonObj.put("dribbling_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getDribbling(), teamLevel));
					
					jsonObj.put("short_passing", MathRoundHelper.round(skill.getShort_passing(), 1));
					jsonObj.put("short_passing_quality_type", QualityGenerator.getQualityType(skill.getShort_passing(), teamLevel));
					jsonObj.put("short_passing_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getShort_passing(), teamLevel));
					
					jsonObj.put("long_passing", MathRoundHelper.round(skill.getLong_passing(), 1));
					jsonObj.put("long_passing_quality_type", QualityGenerator.getQualityType(skill.getLong_passing(), teamLevel));
					jsonObj.put("long_passing_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getLong_passing(), teamLevel));
					
					jsonObj.put("stand_tackles", MathRoundHelper.round(skill.getStand_tackles(), 1));
					jsonObj.put("stand_tackles_quality_type", QualityGenerator.getQualityType(skill.getStand_tackles(), teamLevel));
					jsonObj.put("stand_tackles_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getStand_tackles(), teamLevel));
					
					jsonObj.put("marking", MathRoundHelper.round(skill.getMarking(), 1));
					jsonObj.put("marking_quality_type", QualityGenerator.getQualityType(skill.getMarking(), teamLevel));
					jsonObj.put("marking_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getMarking(), teamLevel));
					
					jsonObj.put("penalties", MathRoundHelper.round(skill.getPenalties(), 1));
					jsonObj.put("penalties_quality_type", QualityGenerator.getQualityType(skill.getPenalties(), teamLevel));
					jsonObj.put("penalties_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getPenalties(), teamLevel));
					
					jsonObj.put("curve", MathRoundHelper.round(skill.getCurve(), 1));
					jsonObj.put("curve_quality_type", QualityGenerator.getQualityType(skill.getCurve(), teamLevel));
					jsonObj.put("curve_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getCurve(), teamLevel));
					
					jsonObj.put("finishing", MathRoundHelper.round(skill.getFinishing(), 1));
					jsonObj.put("finishing_quality_type", QualityGenerator.getQualityType(skill.getFinishing(), teamLevel));
					jsonObj.put("finishing_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getFinishing(), teamLevel));
					
					jsonObj.put("six_m_shots", MathRoundHelper.round(skill.getSix_m_shots(), 1));
					jsonObj.put("six_m_shots_quality_type", QualityGenerator.getQualityType(skill.getSix_m_shots(), teamLevel));
					jsonObj.put("six_m_shots_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getSix_m_shots(), teamLevel));
					
					jsonObj.put("nine_m_shots", MathRoundHelper.round(skill.getNine_m_shots(), 1));
					jsonObj.put("nine_m_shots_quality_type", QualityGenerator.getQualityType(skill.getNine_m_shots(), teamLevel));
					jsonObj.put("nine_m_shots_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getNine_m_shots(), teamLevel));
					
					jsonObj.put("lob_shots", MathRoundHelper.round(skill.getLob_shots(), 1));
					jsonObj.put("lob_shots_quality_type", QualityGenerator.getQualityType(skill.getLob_shots(), teamLevel));
					jsonObj.put("lob_shots_quality_perc", QualityGenerator.getQualityAsPercentage(skill.getLob_shots(), teamLevel));
					
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
						session.setAttribute("teamLevel", teamService.getTeamLevel(email));
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
