package af.handball.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import af.handball.entity.LeaderboardTeam;
import af.handball.entity.Match;
import af.handball.entity.MatchHighlight;
import af.handball.entity.MatchOutcome;
import af.handball.entity.Player;
import af.handball.entity.Team;
import af.handball.service.GameService;


@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;

	
	@RequestMapping("/getUpdate")
	@ResponseBody
	public String getUpdate(@RequestBody String json, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		JSONObject requestJson = new JSONObject(json);
		int matchId = (Integer) requestJson.get("matchId");
		int updateNumber = (Integer) requestJson.get("updateNumber");
		System.out.println("Get update for match id = " + matchId + ". Update number: " + updateNumber);
		 
		JSONObject returnJson = new JSONObject();
		MatchHighlight matchHighlight = gameService.getMatchHighlightByUpdateNumber(matchId, updateNumber);
		if (matchHighlight != null) {
			returnJson.put("status", "OK");
			returnJson.put("highlight", matchHighlight.getHighlight_text());
		} else {
			returnJson.put("status", "ERROR");
		}
		
		
		return returnJson.toString();
	}

	@RequestMapping("/matchPanel")
	public String matchPanel(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return "matchPanel";
	}
	
	@RequestMapping("/modalMatch")
	public String modalMatch(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String returnPage = "modalMatch";
		try {
			String matchIdParamter = (String) request.getParameter("matchId");
			System.out.println("Match id para: " + matchIdParamter);
			
			// Obtain the match
			Match match = gameService.getMatchById(Integer.parseInt(matchIdParamter));
			
			// Update the names of team in the match
			List<Match> matchList = (List<Match>) session.getAttribute("matchList");
			for (Match m : matchList) {
				System.out.println("Current m id = " + m.getMatch_id() + ". Match id = " + match.getMatch_id());
	
				if (m.getMatch_id().intValue() == match.getMatch_id().intValue()) {
									System.out.println("Found match with the same id");
					match.setHome_team_name(m.getHome_team_name());
					match.setAway_team_name(m.getAway_team_name());
					
					break;
				} else System.out.println("Doesn't match");
			}
			
			List<MatchHighlight> matchHighlightList = gameService.getMatchHighlights(match.getMatch_id());
			if (!matchHighlightList.isEmpty())
				session.setAttribute("matchHighlightList", matchHighlightList);
			/*Map<String, Object> nextMatchMap = (Map<String, Object>) session.getAttribute("nextMatchMap");
			int matchId = (Integer) nextMatchMap.get("matchId");
			Match match = gameService.getMatchById(matchId);*/
			Date date = new Date(match.getMatch_date().getTime());
			System.out.println("Match date: " + date.toString());
			// Set the time left to match
			long timeLeftInMs = match.getMatch_date().getTime() - Calendar.getInstance().getTime().getTime();
			if (timeLeftInMs < 0)
				timeLeftInMs = 0;
			System.out.println(timeLeftInMs);
			session.setAttribute("timeLeftInMs", timeLeftInMs);
			
			session.setAttribute("currentMatch", match);
			System.out.println("/modalMatch isMatchStarted = " + match.isMatch_started());
		} catch (Exception e) {
			returnPage = "sessionExpired";
		}
		return returnPage;
	}
	
	@RequestMapping("/sessionExpired") 
	public String sessionExpired(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return "sessionExpired";
	}
	
	@RequestMapping("/modalLiveMatch")
	public String modalLiveMatch(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String returnPage = "modalLiveMatch";
		try {
			String matchIdParamter = (String) request.getParameter("matchId");
			System.out.println("Match id para: " + matchIdParamter);
			
			// Obtain the match
			Match match = gameService.getMatchById(Integer.parseInt(matchIdParamter));
			
			// Update the names of team in the match
			List<Match> matchList = (List<Match>) session.getAttribute("matchList");
			for (Match m : matchList) {
				System.out.println("Current m id = " + m.getMatch_id() + ". Match id = " + match.getMatch_id());
	
				if (m.getMatch_id().intValue() == match.getMatch_id().intValue()) {
									System.out.println("Found match with the same id");
					match.setHome_team_name(m.getHome_team_name());
					match.setAway_team_name(m.getAway_team_name());
					
					break;
				} else System.out.println("Doesn't match");
			}
			
			List<MatchHighlight> matchHighlightList = gameService.getMatchHighlights(match.getMatch_id());
			if (!matchHighlightList.isEmpty())
				session.setAttribute("matchHighlightList", matchHighlightList);
			/*Map<String, Object> nextMatchMap = (Map<String, Object>) session.getAttribute("nextMatchMap");
			int matchId = (Integer) nextMatchMap.get("matchId");
			Match match = gameService.getMatchById(matchId);*/
			Date date = new Date(match.getMatch_date().getTime());
			System.out.println("Match date: " + date.toString());
			// Set the time left to match
			long timeLeftInMs = match.getMatch_date().getTime() - Calendar.getInstance().getTime().getTime();
			if (timeLeftInMs < 0)
				timeLeftInMs = 0;
			System.out.println(timeLeftInMs);
			session.setAttribute("timeLeftInMs", timeLeftInMs);
			
			session.setAttribute("currentMatch", match);
			System.out.println("/modalLiveMatch isMatchStarted = " + match.isMatch_started());
				
		} catch (Exception e) {
			returnPage = "sessionExpired";
		}
		
		return returnPage;
	}
	
	@RequestMapping("/leaderboard")
	public String leaderboard(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String returnPage = "leaderboard";
		try {
			int leagueId = (Integer) session.getAttribute("leagueId");
			
			// Obtain the leaderboard list
			Map<String,Object> leaderboardMap = gameService.getLeaderboardTeamMap(leagueId);
			// get the leaderboardTeamList
			List<LeaderboardTeam> leaderboardTeamList = (List<LeaderboardTeam>) leaderboardMap.get("leaderboardTeamList");
			List<String> teamNameList = (List<String>) leaderboardMap.get("teamNameList");
			for (int i = 0; i < leaderboardTeamList.size(); i++) 
				System.out.println("Position (index): " + (i+1) + ". Points: " + leaderboardTeamList.get(i).getPoints() + " Goal Diff: " + leaderboardTeamList.get(i).getGoals_difference());
			
			request.setAttribute("leaderboardTeamList", leaderboardTeamList);
			request.setAttribute("teamNameList", teamNameList);
		} catch (Exception e) {
			returnPage = "sessionExpired";
		}
		return returnPage;
	}
	
	@RequestMapping("/squad")
	public String squad(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String returnPage = "squad";
		try {
			String email = (String) session.getAttribute("email");
			List<Player> playerList = gameService.getUsersPlayers(email);
			Map<String, String> captainsMap = gameService.getCaptainsMap(email);
			request.setAttribute("captainsMap", captainsMap);
			request.setAttribute("playerList", playerList);
			/*Map<String, Skill> allPlayersSkills = gameService.getPlayersSkills(playerList);
			request.setAttribute("allPlayersSkills", allPlayersSkills);*/
		} catch (Exception e) {
			returnPage = "sessionExpired";
		}
		return returnPage;
	}
	
	@RequestMapping("/schedule")
	public String schedule(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String returnPage = "schedule";
		try {
			String email = (String) session.getAttribute("email");
			int teamId = (Integer) session.getAttribute("teamId");
			int leagueId = (Integer) session.getAttribute("leagueId");
			
			// home team, away team, year, month, day, hour, minutes, seconds + LATER ON; for matches that happend the score
			
			Map<String, Object> scheduleMap = gameService.getUsersSchedule(teamId, leagueId);
			
			List<Match> matchList = (List<Match>) scheduleMap.get("matchList");
			List<Team> teamList = (List<Team>) scheduleMap.get("teamList");
			List<MatchOutcome> matchOutcomeList = (List<MatchOutcome>) scheduleMap.get("matchOutcomeList");
			Map<String, Object> nextMatchMap = new HashMap<String, Object>();
			boolean nextMatchMapped = false;
			// update the names of teams in matchList
			for (int i = 0; i < matchList.size(); i++) {
				int homeTeamId = matchList.get(i).getHome_team();
				int awayTeamId = matchList.get(i).getAway_team();
				
				
				
				for (int j = 0; j < teamList.size(); j++) {
					boolean homeTeamDone = false;
					boolean awayTeamDone = false;
					
					if (homeTeamId == teamList.get(j).getTeam_id()) {
						matchList.get(i).setHome_team_name(teamList.get(j).getTeam_name());
						homeTeamDone = true;
					} else if (awayTeamId == teamList.get(j).getTeam_id()) {
						matchList.get(i).setAway_team_name(teamList.get(j).getTeam_name());
						awayTeamDone = true;
					} else if (homeTeamDone && awayTeamDone)
						break;
				}
				
				if (!nextMatchMapped && !matchList.get(i).isMatch_finished()) {
					nextMatchMap.put("matchId", matchList.get(i).getMatch_id());
					nextMatchMap.put("homeTeamId", matchList.get(i).getHome_team());
					nextMatchMap.put("awayTeamId", matchList.get(i).getAway_team());
					nextMatchMap.put("homeTeamName", matchList.get(i).getHome_team_name());
					nextMatchMap.put("awayTeamName", matchList.get(i).getAway_team_name());
					long timeLeftInMs = matchList.get(i).getMatch_date().getTime() - Calendar.getInstance().getTime().getTime();
					if (timeLeftInMs < 0)
						timeLeftInMs = 0;
					nextMatchMap.put("timeLeftInMs",timeLeftInMs);
					
					nextMatchMapped = true;
				}
				
			}
			
			
			session.setAttribute("matchList", matchList);
			session.setAttribute("matchOutcomeList", matchOutcomeList);
			System.out.println("setting nextMatchMap as session attribute");
			session.setAttribute("nextMatchMap", nextMatchMap);
			
			
			/*List<Match> matchList = gameService.getUsersSchedule(teamId, leagueId);
			session.setAttribute("matchList", matchList);
			for (int i = 0; i < matchList.size(); i++) {
				String home_team = matchList.get(i).getHome_team_name();
				String away_team = matchList.get(i).getAway_team_name();
				
				System.out.println("i = " + i + " home: " + home_team + " vs away: " + away_team);
			}*/
			
		} catch (Exception e) {
			returnPage = "sessionExpired";
		}
		return returnPage;
	} 

	

}
