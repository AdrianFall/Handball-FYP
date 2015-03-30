package af.handball.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import af.handball.entity.Captains;
import af.handball.entity.Contract;
import af.handball.entity.LeaderboardTeam;
import af.handball.entity.Match;
import af.handball.entity.MatchHighlight;
import af.handball.entity.MatchOutcome;
import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.entity.Team;
import af.handball.helper.MatchHelper;
import af.handball.repository.GameRepository;

@Component("GameRepository")
@Transactional
@Repository
public class GameRepositoryImpl implements GameRepository {

	@PersistenceContext
	EntityManager emgr;
	
	@Override
	public void deleteMatchHighlights(int matchId) {
		// TODO Auto-generated method stub
		TypedQuery<MatchHighlight> deleteHighlightsQuery = emgr.createNamedQuery("MatchHighlight.deleteHighlightsOfMatch", MatchHighlight.class);
		deleteHighlightsQuery.setParameter("match_id", matchId);
		deleteHighlightsQuery.executeUpdate();
	}
	
	@Override
	public MatchHighlight getMatchHighlightByUpdateNumber(int matchId, int updateNumber) {
		MatchHighlight matchHighlight = null;
		
		TypedQuery<MatchHighlight> matchHighlightByUpdateNumberQuery = emgr.createNamedQuery("MatchHighlight.getHighlightByUpdateNumber", MatchHighlight.class);
		matchHighlightByUpdateNumberQuery.setParameter("update_number", updateNumber);
		matchHighlightByUpdateNumberQuery.setParameter("match_id", matchId);
		try {
			matchHighlight = matchHighlightByUpdateNumberQuery.getSingleResult();
		} catch (NoResultException nre) {
			
		}
		
		
		return matchHighlight;
	}
	
	@Override
	public List<MatchHighlight> getMatchHighlights(int matchId) {
		List<MatchHighlight> matchHighlightList = new ArrayList<MatchHighlight>();
		TypedQuery<MatchHighlight> matchScheduleQuery = emgr.createNamedQuery("MatchHighlight.getHighlightsOfMatch", MatchHighlight.class);
		matchScheduleQuery.setParameter("match_id", matchId);
		matchHighlightList = matchScheduleQuery.getResultList();
		return matchHighlightList;
	}
	
	@Override
	public Match getMatchById(int matchId) {
		Match match = new Match();
		
		match = emgr.find(Match.class, matchId);
		
		return match;
	}

	@Override
	public Map<String, Object> getUserSchedule(int teamId, int leagueId) {
		
		Map<String,Object> scheduleMap = new HashMap<String,Object>();
		List<Match> matchList = new ArrayList<Match>();
		List<Team> teamList = new ArrayList<Team>();
		List<MatchOutcome> matchOutcomeList = new ArrayList<MatchOutcome>();
		
		
		// Create query for match schedle
		TypedQuery<Match> matchScheduleQuery = emgr.createNamedQuery("Match.getSchedule", Match.class);
		matchScheduleQuery.setParameter("teamId", teamId);
		System.out.println("Passing league id = " + leagueId);
		matchScheduleQuery.setParameter("league_id", leagueId);
		
		// Create query for team list in league
		TypedQuery<Team> teamListQuery = emgr.createNamedQuery("Team.getLeagueTeams", Team.class);
		teamListQuery.setParameter("league_id", leagueId);
		
		try {
			matchList = matchScheduleQuery.getResultList();
			scheduleMap.put("matchList", matchList);
			teamList = teamListQuery.getResultList();
			scheduleMap.put("teamList", teamList);
			
				// Obtain the match outcomes
				for (int i = 0; i < matchList.size(); i++) {
					int matchId = matchList.get(i).getMatch_id();
					try {
						MatchOutcome matchOutcome = emgr.createNamedQuery("MatchOutcome.getByMatch", MatchOutcome.class)
							.setParameter("match_id", matchId).getSingleResult();
						matchOutcomeList.add(matchOutcome);
						System.out.println("Obtained MatchOutcome for matchId: " + matchId);
					}  catch (NoResultException nre) {
						System.err.println("Couldn't obtain MatchOutcome for matchId: " + matchId + ". NoResultException: " + nre.getLocalizedMessage());
					} catch (Exception e) {
						System.err.println("Couldn't obtain MatchOutcome for matchId: " + matchId + ". Exception: " + e.getLocalizedMessage());
					}
				}
			scheduleMap.put("matchOutcomeList", matchOutcomeList);
								
				
			
			
		} catch (NoResultException nre) {
			System.out.println("Couldn't obtain schedule for teamId: " + teamId + " Exception: " + nre.getLocalizedMessage());
		}
		
		
		
		return scheduleMap;
	}
	
	@Override
	public List<Player> getTeamPlayers(int teamId) {
		
		System.out.println("Getting team ("+ teamId + ") players");
		List<Player> playerList = new ArrayList<Player>();
		ArrayList<Player> sortedPlayerList = new ArrayList<Player>();
		
		TypedQuery<Player> teamPlayersQuery = emgr.createNamedQuery("Player.getTeamPlayers", Player.class);
		teamPlayersQuery.setParameter("team_id", teamId);
		
		try {
			playerList = teamPlayersQuery.getResultList();
			sortedPlayerList = MatchHelper.sortPlayerList(playerList);
		} catch (NoResultException nre) {
			System.out.println("Couldn't obtain a list of players for team id: " + teamId + ". NoResultException: " + nre.getLocalizedMessage());
		}
		
		return sortedPlayerList;
	}
	
	@Override
	public List<Player> getUserTeam(String email) {
		System.out.println("GET USER TEAM");
		List<Player> playerList = new ArrayList<Player>();
		TypedQuery<Player> userPlayersQuery = emgr.createNamedQuery(
				"Player.getUsersPlayers", Player.class);
		userPlayersQuery.setParameter("email", email);
		
		ArrayList<Player> sortedPlayerList = new ArrayList<Player>();
		try {
			playerList = userPlayersQuery.getResultList();
			System.out.println("PLAYER LIST = " + playerList);

			sortedPlayerList = MatchHelper.sortPlayerList(playerList);

			System.out.println("player list size = " + playerList.size());
		} catch (Exception e) {
			System.out.println("Error when obtaining player list. Exception - "
					+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		System.out.println("Sorted player list = " + sortedPlayerList);
		return sortedPlayerList;
	}
	
	

	@Override
	public List<Skill> getAllPlayersSkills(List<Player> listOfPlayers) {
		List<Skill> playersSkills = new ArrayList<Skill>();

		for (int i = 0; i < listOfPlayers.size(); i++) {
			int tempPlayerId = listOfPlayers.get(i).getPlayer_id();
			TypedQuery<Skill> playerSkillsQuery = emgr.createNamedQuery(
					"Skill.getPlayerSkills", Skill.class);
			playerSkillsQuery.setParameter("player_id", tempPlayerId);
			Skill skill = playerSkillsQuery.getSingleResult();
			
			playersSkills.add(skill);
			
		}
	
		return playersSkills;
	}

	
	@Override
	public boolean setMatchIsPlayed(boolean isPlayed, int matchId) {
		
		boolean changeApplied = false;
		
		try {
			Match match = emgr.find(Match.class, matchId);
			if (match != null) {
				if (match.isMatch_started() != isPlayed) {
					match.setMatch_started(isPlayed);
					emgr.persist(match);
					emgr.flush();
				}
				changeApplied = true;
			}
		} catch (Exception e) {
			System.err.println("setMatchIsPlayed() Exception: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return changeApplied;
	}
	
	@Override
	public Skill getPlayerSkills(int playerId) {
		return emgr.createNamedQuery("Skill.getPlayerSkills", Skill.class)
				.setParameter("player_id", playerId).getSingleResult();
	}

	@Override
	public Player getPlayer(int playerId) {

		return emgr.find(Player.class, playerId);
	}

	@Override
	public Contract getPlayerContract(int playerId) {
		TypedQuery<Contract> contractQuery = emgr.createNamedQuery(
				"Contract.getContractByPlayerId", Contract.class);
		contractQuery.setParameter("player_id", playerId);

		return contractQuery.getSingleResult();
	}

	@Override
	public Map<String, String> getCaptainsMap(String email) {
		Map<String, String> captainsMap = new HashMap<String, String>();

		TypedQuery<Team> teamQuery = emgr.createNamedQuery(
				"Team.getTeamByEmail", Team.class);
		teamQuery.setParameter("email", email);
		Team team = teamQuery.getSingleResult();
		int teamId = team.getTeam_id();

		Captains captain = emgr.find(Captains.class, teamId);

		captainsMap.put("captain_id_one",
				Integer.toString(captain.getCaptain_id_one()));
		captainsMap.put("captain_id_two",
				Integer.toString(captain.getCaptain_id_two()));
		captainsMap.put("captain_id_three",
				Integer.toString(captain.getCaptain_id_three()));
		captainsMap.put("captain_id_four",
				Integer.toString(captain.getCaptain_id_four()));

		if (captain.getCaptain_id_one() != -1) {
			// Obtain the first player name and number
			Player firstPlayer = emgr.find(Player.class,
					captain.getCaptain_id_one());
			captainsMap.put("captain_name_one", firstPlayer.getName());
			captainsMap.put("captain_number_one",
					Integer.toString(firstPlayer.getNumber()));

		}

		if (captain.getCaptain_id_two() != -1) {
			// Obtain the second player name and number
			Player secondPlayer = emgr.find(Player.class,
					captain.getCaptain_id_two());
			captainsMap.put("captain_name_two", secondPlayer.getName());
			captainsMap.put("captain_number_two",
					Integer.toString(secondPlayer.getNumber()));
		}

		if (captain.getCaptain_id_three() != -1) {
			// Obtain the third player name and number
			Player thirdPlayer = emgr.find(Player.class,
					captain.getCaptain_id_three());
			captainsMap.put("captain_name_three", thirdPlayer.getName());
			captainsMap.put("captain_number_three",
					Integer.toString(thirdPlayer.getNumber()));
		}

		if (captain.getCaptain_id_four() != -1) {
			// Obtain the fourth player name and number
			Player fourthPlayer = emgr.find(Player.class,
					captain.getCaptain_id_four());
			captainsMap.put("captain_name_four", fourthPlayer.getName());
			captainsMap.put("captain_number_four",
					Integer.toString(fourthPlayer.getNumber()));
		}

		return captainsMap;
	}

	@Override
	public Map<String, Object> getLeaderboardTeamMap(int leagueId) {
		
		Map<String,Object> leaderboardMap = new HashMap<String,Object>();
		List<LeaderboardTeam> leaderboardTeamList = new ArrayList<LeaderboardTeam>();
		
		
		TypedQuery<LeaderboardTeam> leaderboardTeamQuery = emgr.createNamedQuery("LeaderboardTeam.getByLeagueId", LeaderboardTeam.class);
		leaderboardTeamQuery.setParameter("league_id", leagueId);
		try {
			// Obtain all leaderboardTeam records
			leaderboardTeamList = leaderboardTeamQuery.getResultList();
			System.out.println("Pre-sorting.....................");
			for (int i = 0; i < leaderboardTeamList.size(); i++)
				System.out.println("Position (index): " + (i+1) + ". Points: " + leaderboardTeamList.get(i).getPoints() + " Goal Diff: " + leaderboardTeamList.get(i).getGoals_difference());
			System.out.println("END Pre-sorting.................");
			// Sorts the leaderboard by position
			leaderboardTeamList = (List<LeaderboardTeam>) sortLeaderboardList(leaderboardTeamList).get("leaderboardList");
			// Create team name list
			List<String> teamNameList = new ArrayList<String>();
			for (int j = 0; j < leaderboardTeamList.size(); j++) {
				Team team = emgr.find(Team.class, leaderboardTeamList.get(j).getTeam_id());
				teamNameList.add(team.getTeam_name());
			}
			leaderboardMap.put("teamNameList", teamNameList);
		} catch (NoResultException nre) {
			System.err.println("Couldn't obtain leaderboard for league id: " + leagueId);
		} finally {
			leaderboardMap.put("leaderboardTeamList", leaderboardTeamList);
		}
		
		
		
		return leaderboardMap;
	}

	private Map<String,Object> sortLeaderboardList(
			List<LeaderboardTeam> leaderboardTeamList) {
		Map<String,Object> leaderboardMap = new HashMap<String,Object>();
		
		List<LeaderboardTeam> sortedList = new ArrayList<LeaderboardTeam>();
		List<LeaderboardTeam> positionList = new ArrayList<LeaderboardTeam>(leaderboardTeamList);
		
		
		if (!isLeaderboardTeamListEmpty(leaderboardTeamList)) {
			int lastPoint = -1;
			for (int i=0; i<leaderboardTeamList.size(); i++) {
				int countOccurencesOfSamePointAmount = 0;
				while (i+countOccurencesOfSamePointAmount < leaderboardTeamList.size() && leaderboardTeamList.get(i).getPoints() == leaderboardTeamList.get(i+countOccurencesOfSamePointAmount).getPoints())  // current iteration ltl has the same points as the next + count record
					countOccurencesOfSamePointAmount++;
				if (countOccurencesOfSamePointAmount > 1) {
					System.out.println(countOccurencesOfSamePointAmount);
					// compare the goal difference and sort the selection
					// Obtain the selection
					List<LeaderboardTeam> selectionList = new ArrayList<LeaderboardTeam>();
					for (int j = 0; j < countOccurencesOfSamePointAmount; j++) {
						selectionList.add(leaderboardTeamList.get(i+j));
					}
					
					// END Obtain the selection
					List<LeaderboardTeam> sortedSelectionList = sortLeaderboardTeamSelection(selectionList);
					System.out.println("Sorted selection list : " + sortedSelectionList);
					for (int n = 0;n<sortedSelectionList.size(); n++)
						sortedList.add(sortedSelectionList.get(n));
					// increment i (the selection should be added to the sortedList by now)
					i += countOccurencesOfSamePointAmount-1;
				} else {
					sortedList.add(leaderboardTeamList.get(i));
				}
				
					/*if (leaderboardTeamList.get(i).getPoints() == )*/ // next record contains the same point
			}
		} else {
			sortedList = leaderboardTeamList;
		}
		leaderboardMap.put("leaderboardList", sortedList);
		return leaderboardMap;
	}
	
	private List<LeaderboardTeam> sortLeaderboardTeamSelection(List<LeaderboardTeam> selectionList) {
		List<LeaderboardTeam> newList = new ArrayList<LeaderboardTeam>();
		int i = 0;
		do {
			LeaderboardTeam lt = selectionList.get(i);
			int index = i;
			int goalDiff = selectionList.get(i).getGoals_difference();
			// compare the elements ahead
			for (int j = i+1; j < selectionList.size(); j++) {
				if (selectionList.get(j).getGoals_difference() > goalDiff) {
					lt = selectionList.get(j);
					index = j;
					goalDiff = selectionList.get(j).getGoals_difference();
				}
			}
			
			// remove
			newList.add(lt);
			selectionList.remove(index);
			
			
		} while (!selectionList.isEmpty());
		
		return newList;
	}

	

	// Empty - meaning all records contain no entry yet
	private boolean isLeaderboardTeamListEmpty(List<LeaderboardTeam> leaderboardTeamList) {
		boolean isEmpty = true;
		
		for (LeaderboardTeam lt : leaderboardTeamList) {
			if (lt.getPoints() != 0) 
				isEmpty = false;
		}
		
		return isEmpty;
	}
	
	
}
