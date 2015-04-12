package af.handball.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import af.handball.entity.Contract;
import af.handball.entity.Player;
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

	@RequestMapping(value = "/updateSquad", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces = "application/json")
	@ResponseBody
	public String updateSquad(@RequestBody String jsonMsg,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		JSONObject jsonObj = new JSONObject();

		String email = (String) session.getAttribute("email");

		// Obtain the JSON message
		JSONObject obtainedJSONObj = new JSONObject(jsonMsg);

		// Obtain the array with player id list
		JSONArray jsonArrayPlayerId = (JSONArray) obtainedJSONObj
				.get("playerIdList");
		
		// Obtain the array with captains list
		JSONArray jsonArrayCaptainsList = (JSONArray) obtainedJSONObj.get("captainsList");
		System.out.println("jsonarray captains list = " + jsonArrayCaptainsList);
		
		
		// Create an array list to hold the player ids as Integer data type
		ArrayList<Integer> playerIdList = new ArrayList<Integer>();
		
		// Create an array list to hold the captain roles 
		ArrayList<Integer> captainsIdList = new ArrayList<Integer>();

		// The first 7 players in the array are first squad players
		// where the first is GK, second LW, third RW,
		// fourth is CB, fifth is RB, sixth is LB
		// and seventh is PV
		try {
			for (int i = 0; i < jsonArrayPlayerId.length(); i++) {
				playerIdList.add(Integer.parseInt((String) jsonArrayPlayerId.get(i)));
			}
			
			for (int j = 0; j < 4; j++) {
				if (jsonArrayCaptainsList.get(j).toString().equals("")) {
					captainsIdList.add(-1);
				} else {
					captainsIdList.add(Integer.parseInt((String) jsonArrayCaptainsList.get(j)));
				}
			}
			
			System.out.println("captains id list =  " + captainsIdList);
			
			boolean changed = teamService.changeSquad(playerIdList, captainsIdList, email);
			if (changed) jsonObj.put("status", "OK");
			else jsonObj.put("status", "error");

		} catch (NumberFormatException nfe) {
			jsonObj.put("status", "error");
			System.out.println("One of the obtained ids in jsonArrayPlayerId/captainsIdList wasn't a number");
		} catch (Exception e) {
			jsonObj.put("status", "error#1");
		}
		// The next 7 players are bench players.
		// Any other is reserves

		if (email == null) {
			jsonObj.put("status", "sessionExpired");
		} else {

		}

		return jsonObj.toString();
	}

	@RequestMapping(value = "/hasTeam", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces = "application/json")
	@ResponseBody
	public String hasTeam(HttpServletRequest request,
			HttpServletResponse response) {

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
				session.setAttribute("teamLevel",
						teamService.getTeamLevel(email));
			}
		}

		return jsonObj.toString();
	}

	@RequestMapping(value = "/getPlayerDetails", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces = "application/json")
	@ResponseBody
	public String getPlayerSkills(@RequestBody String jsonMsg,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		JSONObject jsonObj = new JSONObject();
		String email = (String) session.getAttribute("email");
		String teamName = (String) session.getAttribute("teamName");

		if (email == null) { // Session expired
			jsonObj.put("status", "sessionExpired");
		} else { // Session active

			// Obtain the JSON message
			JSONObject obtainedJSONObj = new JSONObject(jsonMsg);

			String playerId = (String) obtainedJSONObj.get("playerId");
			System.out.println("Obtained player id = " + playerId);
			if (playerId.trim().equals("") || playerId == null)
				jsonObj.put("status", "error");
			else {
				try {
					// START Obtain the Skill object and allocate its data in
					// the jsonObj.
					Skill skill = gameService.getPlayerSkills(Integer
							.parseInt(playerId));
					System.out.println("Skill = " + skill);

					int teamLevel = (Integer) session.getAttribute("teamLevel");

					/* Physical Skills */

					jsonObj.put("sprint_speed",
							MathRoundHelper.round(skill.getSprint_speed(), 1));
					jsonObj.put(
							"sprint_speed_quality_type",
							QualityGenerator.getQualityType(
									skill.getSprint_speed(), teamLevel));
					jsonObj.put(
							"sprint_speed_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getSprint_speed(), teamLevel));

					jsonObj.put("jumping",
							MathRoundHelper.round(skill.getJumping(), 1));
					jsonObj.put("jumping_quality_type", QualityGenerator
							.getQualityType(skill.getJumping(), teamLevel));
					jsonObj.put(
							"jumping_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getJumping(), teamLevel));

					jsonObj.put("balance",
							MathRoundHelper.round(skill.getBalance(), 1));
					jsonObj.put("balance_quality_type", QualityGenerator
							.getQualityType(skill.getBalance(), teamLevel));
					jsonObj.put(
							"balance_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getBalance(), teamLevel));

					jsonObj.put("stamina",
							MathRoundHelper.round(skill.getStamina(), 1));
					jsonObj.put("stamina_quality_type", QualityGenerator
							.getQualityType(skill.getStamina(), teamLevel));
					jsonObj.put(
							"stamina_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getStamina(), teamLevel));

					jsonObj.put("strength",
							MathRoundHelper.round(skill.getStrength(), 1));
					jsonObj.put("strength_quality_type", QualityGenerator
							.getQualityType(skill.getStrength(), teamLevel));
					jsonObj.put(
							"strength_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getStrength(), teamLevel));

					jsonObj.put("blocking",
							MathRoundHelper.round(skill.getBlocking(), 1));
					jsonObj.put("blocking_quality_type", QualityGenerator
							.getQualityType(skill.getBlocking(), teamLevel));
					jsonObj.put(
							"blocking_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getBlocking(), teamLevel));

					/* Mental Skills */
					jsonObj.put("aggression",
							MathRoundHelper.round(skill.getAggression(), 1));
					jsonObj.put(
							"aggression_quality_type",
							QualityGenerator.getQualityType(
									skill.getAggression(), teamLevel));
					jsonObj.put(
							"aggression_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getAggression(), teamLevel));

					jsonObj.put("attack_position", MathRoundHelper.round(
							skill.getAttack_position(), 1));
					jsonObj.put(
							"attack_position_quality_type",
							QualityGenerator.getQualityType(
									skill.getAttack_position(), teamLevel));
					jsonObj.put(
							"attack_position_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getAttack_position(), teamLevel));

					jsonObj.put("vision",
							MathRoundHelper.round(skill.getVision(), 1));
					jsonObj.put("vision_quality_type", QualityGenerator
							.getQualityType(skill.getVision(), teamLevel));
					jsonObj.put(
							"vision_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getVision(), teamLevel));

					jsonObj.put("creativity",
							MathRoundHelper.round(skill.getCreativity(), 1));
					jsonObj.put(
							"creativity_quality_type",
							QualityGenerator.getQualityType(
									skill.getCreativity(), teamLevel));
					jsonObj.put(
							"creativity_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getCreativity(), teamLevel));
					/* Goal Keeping Skills */
					jsonObj.put("reflexes",
							MathRoundHelper.round(skill.getReflexes(), 1));
					jsonObj.put("reflexes_quality_type", QualityGenerator
							.getQualityType(skill.getReflexes(), teamLevel));
					jsonObj.put(
							"reflexes_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getReflexes(), teamLevel));

					jsonObj.put("positioning",
							MathRoundHelper.round(skill.getPositioning(), 1));
					jsonObj.put(
							"positioning_quality_type",
							QualityGenerator.getQualityType(
									skill.getPositioning(), teamLevel));
					jsonObj.put(
							"positioning_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getPositioning(), teamLevel));

					jsonObj.put("leg_saves",
							MathRoundHelper.round(skill.getLeg_saves(), 1));
					jsonObj.put(
							"leg_saves_quality_type",
							QualityGenerator.getQualityType(
									skill.getLeg_saves(), teamLevel));
					jsonObj.put(
							"leg_saves_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getLeg_saves(), teamLevel));

					jsonObj.put("penalty_saves",
							MathRoundHelper.round(skill.getPenalty_saves(), 1));
					jsonObj.put(
							"penalty_saves_quality_type",
							QualityGenerator.getQualityType(
									skill.getPenalty_saves(), teamLevel));
					jsonObj.put(
							"penalty_saves_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getPenalty_saves(), teamLevel));

					jsonObj.put("six_m_saves",
							MathRoundHelper.round(skill.getSix_m_saves(), 1));
					jsonObj.put(
							"six_m_saves_quality_type",
							QualityGenerator.getQualityType(
									skill.getSix_m_saves(), teamLevel));
					jsonObj.put(
							"six_m_saves_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getSix_m_saves(), teamLevel));

					jsonObj.put("nine_m_saves",
							MathRoundHelper.round(skill.getNine_m_saves(), 1));
					jsonObj.put(
							"nine_m_saves_quality_type",
							QualityGenerator.getQualityType(
									skill.getNine_m_saves(), teamLevel));
					jsonObj.put(
							"nine_m_saves_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getNine_m_saves(), teamLevel));

					/* Technical Skills */
					jsonObj.put("catching",
							MathRoundHelper.round(skill.getCatching(), 1));
					jsonObj.put("catching_quality_type", QualityGenerator
							.getQualityType(skill.getCatching(), teamLevel));
					jsonObj.put(
							"catching_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getCatching(), teamLevel));

					jsonObj.put("shot_power",
							MathRoundHelper.round(skill.getShot_power(), 1));
					jsonObj.put(
							"shot_power_quality_type",
							QualityGenerator.getQualityType(
									skill.getShot_power(), teamLevel));
					jsonObj.put(
							"shot_power_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getShot_power(), teamLevel));

					jsonObj.put("dribbling",
							MathRoundHelper.round(skill.getDribbling(), 1));
					jsonObj.put(
							"dribbling_quality_type",
							QualityGenerator.getQualityType(
									skill.getDribbling(), teamLevel));
					jsonObj.put(
							"dribbling_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getDribbling(), teamLevel));

					jsonObj.put("passing",
							MathRoundHelper.round(skill.getPassing(), 1));
					jsonObj.put("passing_quality_type", QualityGenerator
							.getQualityType(skill.getPassing(), teamLevel));
					jsonObj.put(
							"passing_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getPassing(), teamLevel));

					jsonObj.put("penalties",
							MathRoundHelper.round(skill.getPenalties(), 1));
					jsonObj.put(
							"penalties_quality_type",
							QualityGenerator.getQualityType(
									skill.getPenalties(), teamLevel));
					jsonObj.put(
							"penalties_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getPenalties(), teamLevel));

					jsonObj.put("curve",
							MathRoundHelper.round(skill.getCurve(), 1));
					jsonObj.put("curve_quality_type", QualityGenerator
							.getQualityType(skill.getCurve(), teamLevel));
					jsonObj.put(
							"curve_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getCurve(), teamLevel));

					jsonObj.put("finishing",
							MathRoundHelper.round(skill.getFinishing(), 1));
					jsonObj.put(
							"finishing_quality_type",
							QualityGenerator.getQualityType(
									skill.getFinishing(), teamLevel));
					jsonObj.put(
							"finishing_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getFinishing(), teamLevel));

					jsonObj.put("six_m_shots",
							MathRoundHelper.round(skill.getSix_m_shots(), 1));
					jsonObj.put(
							"six_m_shots_quality_type",
							QualityGenerator.getQualityType(
									skill.getSix_m_shots(), teamLevel));
					jsonObj.put(
							"six_m_shots_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getSix_m_shots(), teamLevel));

					jsonObj.put("nine_m_shots",
							MathRoundHelper.round(skill.getNine_m_shots(), 1));
					jsonObj.put(
							"nine_m_shots_quality_type",
							QualityGenerator.getQualityType(
									skill.getNine_m_shots(), teamLevel));
					jsonObj.put(
							"nine_m_shots_quality_perc",
							QualityGenerator.getQualityAsPercentage(
									skill.getNine_m_shots(), teamLevel));
					
					// Obtain the player entity
					Player player = gameService.getPlayer(Integer
							.parseInt(playerId));
					jsonObj.put("height", player.getHeight());
					jsonObj.put("weight", player.getWeight());
					
					
					int playerHand = player.getHanded();
					String playerHandText = "";
					// Determine which handed the player is
					if (playerHand == player.PLAYER_HAND_LEFT)
						playerHandText = "left";
					else if (playerHand == player.PLAYER_HAND_RIGHT)
						playerHandText = "right";
					else if (playerHand == player.PLAYER_HAND_BOTH)
						playerHandText = "both";
					
					jsonObj.put("handed", player.getHanded());
					jsonObj.put("special_ability", player.getSpecial_ability());
					// Obtain the Contract of a player
					Contract contract = gameService.getPlayerContract(Integer
							.parseInt(playerId));
					jsonObj.put("season_wage", contract.getSeason_wage());
					jsonObj.put("years_left", contract.getYears_left());
					
					// Obtain the player entity
					Player playerEntity = gameService.getPlayer(Integer.parseInt(playerId));
					jsonObj.put("market_value", playerEntity.getMarket_value());
					jsonObj.put("condition", player.getCondition());
					jsonObj.put("morale", player.getMorale());
					jsonObj.put("injury", player.getInjury_cause());
					jsonObj.put("injury_days", player.getInjury_days());
					
					jsonObj.put("team_name", teamName);
					

					// END Fetch the data and allocate in jsonObj.

					// Add status to the jsonObj
					jsonObj.put("status", "OK");

				} catch (Exception e) {
					System.out
							.println("Exception when parsing player id to obtain his skills. Exception - "
									+ e.getLocalizedMessage());
					jsonObj.put("status", "error");
				}
			}
		}

		return jsonObj.toString();
	}

	@RequestMapping(value = "/createNewTeam", method = RequestMethod.POST, headers = { "Content-type=application/json" }, produces = "application/json")
	@ResponseBody
	public String createNewTeam(@RequestBody String jsonMsg,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		JSONObject jsonObj = new JSONObject();
		String email = (String) session.getAttribute("email");

		if (email == null) {
			jsonObj.put("status", "sessionExpired");
		} else {

			// Obtain the JSON message
			JSONObject obtainedJSONObj = new JSONObject(jsonMsg);
			String teamName = (String) obtainedJSONObj.get("teamName");
			System.out.println("Obtained team name: " + teamName + " from "
					+ email);

			// Validate the team name (no empty data & more than 3 characters)
			if (teamName.trim().equals("")) {
				jsonObj.put("teamNameError", "The team name can't be empty");
				jsonObj.put("status", "error");
			} else if (teamName.trim().length() <= 3) {
				jsonObj.put("status", "error");
				jsonObj.put("teamNameError",
						"The team name must be longer than 3 characters.");
			} else {
				jsonObj.put("status", "OK");
				// Create the new team
				int teamId = teamService.preGenerateTeam(email, teamName);

				if (teamId != -1) {

					/*
					 * System.out.println(
					 * "Calling league service to allocate a team.");
					 */
					/* leagueService.allocateTeamInLeague(email, teamName, 1); */
					// Allocate the pre-generated team.
					boolean assigned = teamService.assignTeam(teamId, email,
							teamName);
					if (assigned) {
						session.setAttribute("team", teamService.getTeam(email));
						session.setAttribute("teamName", teamName);
						session.setAttribute("teamLevel",
								teamService.getTeamLevel(email));
						
						teamService.getTeam(email);
						jsonObj.put("teamCreated", "true");
						session.setAttribute("teamId", teamId);
						session.setAttribute("leagueId", teamService.getTeamLeagueId(email));
					} else
						jsonObj.put("teamCreated", "false");
				} else
					jsonObj.put("teamCreated", "false");
			}

		}

		/*
		 * RequestDispatcher reqDisp = request.getRequestDispatcher(forwardURL);
		 * 
		 * try { System.out.println("NewTeamController forwarding to " +
		 * forwardURL); reqDisp.forward(request, response); } catch
		 * (ServletException e) {
		 * System.out.println("ServletException when forwarding to " +
		 * forwardURL + e.getLocalizedMessage()); e.printStackTrace(); } catch
		 * (IOException e) {
		 * System.out.println("IOException when forwarding to " + forwardURL +
		 * e.getLocalizedMessage()); e.printStackTrace(); }
		 */

		return jsonObj.toString();
	}
}
