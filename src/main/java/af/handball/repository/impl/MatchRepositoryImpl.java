package af.handball.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import af.handball.entity.Leaderboard;
import af.handball.entity.LeaderboardTeam;
import af.handball.entity.Match;
import af.handball.entity.MatchHighlight;
import af.handball.entity.MatchOutcome;
import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.entity.Team;
import af.handball.generator.QualityGenerator;
import af.handball.helper.MatchHelper;
import af.handball.repository.GameRepository;
import af.handball.repository.MatchRepository;


@Component("MatchRepository")
@Transactional
@Scope("prototype")
@Repository
public class MatchRepositoryImpl implements MatchRepository {
	@PersistenceContext
	EntityManager emgr;
	@Autowired
	GameRepository gameRepository;
	
	private static final int REAL_MATCH_TIME_IN_MINUTES = 60;
	
	private static final int AVERAGE_NUMBER_OF_GOALS_PER_MATCH = 50;
	private static final int AVERAGE_NUMBER_OF_ACTIONS_PER_MATCH = 70;
	
	private static final int MINIMUM_NUMBER_OF_ACTIONS_PER_MATCH = 45;
	private static final int MAXIMUM_NUMBER_OF_ACTIONS_PER_MATCH = 60;
	
	private static final int MINIMUM_NUMBER_OF_PENALTIES_PER_MATCH = 2;
	private static final int MAXIMUM_NUMBER_OF_PENALTIES_PER_MATCH = 5;
	
	public static final int UPDATES_PER_MINUTE = 2;
	
	private int numberOfPenalties = 0;
	private int penaltiesSoFar = 0;
	private int homeScore = 0;
	private int awayScore = 0;
	private int matchDurationInSeconds;
	private int matchGenerationSpeed = 0; 
	private int numberOfUpdates = 0;
	private int countUpdates = 0;
	private Team homeTeam;
	private Team awayTeam;
	private List<Skill> homePlayerSkillsList;
	private List<Skill> awayPlayerSkillsList;
	private int homeTeamQualityPercentage = 0;
	private int awayTeamQualityPercentage = 0;
	// Assessed upon players playing on the right positions (Penalties apply), stamina, sprint speed
	private int homeTeamGoingForwardSuccessionPercentage = 100;
	private int awayTeamGoingForwardSuccessionPercentage = 100;
	// Assessed upon players playing on right positions(Penalties apply), stamina, blocking
	private int homeTeamDefendingSuccessionPercentage = 100;
	private int awayTeamDefendingSucessionPercentage = 100;
	
	private int numberOfActionsPerUpdate = 0;
	
	// By default away team starts the match
	private String teamInPosessionOfBall = "away";
	private int actionsPerMatch = 0;
	private int countNumberOfActions = 0;
	
	private int possessionHomePercentage = 50;
	private int possessionAwayPercentage = 50;
	
	
	
	private Match match;
	
	@Override
	public Map<String, Object> initializeMatch(int matchId) {
		Map<String, Object> settingsMap = new HashMap<String, Object>();
		settingsMap.put("initialized", false);
		try {
			// Obtain the Match entity
			Match match = emgr.find(Match.class, matchId);
			// Obtain the match duration from the match entity
			final int MATCH_DURATION_IN_MINUTES = match.getMatch_duration_in_seconds() / 60;
			
			
			matchDurationInSeconds = MATCH_DURATION_IN_MINUTES * 60;
			
			matchGenerationSpeed = REAL_MATCH_TIME_IN_MINUTES / MATCH_DURATION_IN_MINUTES;
			
			numberOfUpdates = MATCH_DURATION_IN_MINUTES * UPDATES_PER_MINUTE;
			
			Random random = new Random();
			
			// Generate random number of actions per match
			actionsPerMatch = random.nextInt((MAXIMUM_NUMBER_OF_ACTIONS_PER_MATCH - MINIMUM_NUMBER_OF_ACTIONS_PER_MATCH) + 1) + MINIMUM_NUMBER_OF_ACTIONS_PER_MATCH;
			System.out.println("Actions per match = " + actionsPerMatch);
			
			numberOfActionsPerUpdate = actionsPerMatch / numberOfUpdates;
			System.out.println("numberOfActionsPerUpdate = " + numberOfActionsPerUpdate);
			
			// roll for number of penalties
			numberOfPenalties = random.nextInt((MAXIMUM_NUMBER_OF_PENALTIES_PER_MATCH - MINIMUM_NUMBER_OF_PENALTIES_PER_MATCH) + 1) + MINIMUM_NUMBER_OF_PENALTIES_PER_MATCH;
			
			
			if (match != null) {
				this.match = match;
				
				homeTeam = emgr.find(Team.class, match.getHome_team());
				awayTeam = emgr.find(Team.class, match.getAway_team());
				
				
				
				match.setMatch_started(true);
				// Persist match to apply changes
				emgr.persist(match);
				emgr.flush();
		
				
				
				
				settingsMap.put("matchDurationInSeconds", matchDurationInSeconds);
				settingsMap.put("numberOfUpdates", numberOfUpdates);
				settingsMap.put("initialized", true);
				System.out.println("Initialized Match (" + matchId + ").");
			}
			
		} catch (Exception e) {
			System.err.println("Initializing match id: " + matchId + ". Exception: " + e.getLocalizedMessage());
		}
		return settingsMap;
	}
	
	@Override
	public boolean updateMatch() {
		boolean matchUpdated = false;
		
		countUpdates++;
			 
		// Obtain list of players for home and away team
		List<Player> homeTeamPlayers = gameRepository.getTeamPlayers(homeTeam.getTeam_id());
		List<Player> awayTeamPlayers = gameRepository.getTeamPlayers(awayTeam.getTeam_id());
		System.out.println("Update Match - obtained homeTeamPlayers size (" + homeTeamPlayers.size() + ") and awayTeamPlayers size (" + awayTeamPlayers.size() + ").");
		
		
		homePlayerSkillsList = gameRepository.getAllPlayersSkills(homeTeamPlayers);
		awayPlayerSkillsList = gameRepository.getAllPlayersSkills(awayTeamPlayers);
		
		int homeTeamPenaltyGoingForward = 0;
		int awayTeamPenaltyGoingForward = 0;
		int homeTeamPenaltyDefense = 0;
		int awayTeamPenaltyDefense = 0;
		
		// Obtain the first squad (7 players) quality
		int homeTeamAverage = 0;
		int awayTeamAverage = 0;
		for (int i = 0; i < 7; i++) {
	
			// The first 7 players in the array must be the first squad
			// players
			// where the first is GK, second LW, third RW,
			// fourth is CB, fifth is RB, sixth is LB
			// and seventh is PV
			homeTeamAverage += QualityGenerator.getQualityAsPercentage(homeTeamPlayers.get(i).getPlayer_quality(), homeTeam.getTeam_level());
			awayTeamAverage += QualityGenerator.getQualityAsPercentage(awayTeamPlayers.get(i).getPlayer_quality(), awayTeam.getTeam_level());
			
			// CHECK IF PLAYER IS PLAYING ON APPROPRIATE POSITION
			homeTeamPenaltyGoingForward += MatchHelper.assessPenaltyGoingForward(homeTeamPlayers.get(i).getPlay_position(), homeTeamPlayers.get(i).getFirst_squad_play_position());
			awayTeamPenaltyGoingForward += MatchHelper.assessPenaltyGoingForward(awayTeamPlayers.get(i).getPlay_position(), awayTeamPlayers.get(i).getFirst_squad_play_position());
			homeTeamPenaltyDefense += MatchHelper.assessPenaltyOfDefense(homeTeamPlayers.get(i).getPlay_position(), homeTeamPlayers.get(i).getFirst_squad_play_position());
			awayTeamPenaltyDefense += MatchHelper.assessPenaltyOfDefense(awayTeamPlayers.get(i).getPlay_position(), awayTeamPlayers.get(i).getFirst_squad_play_position());
		}
		
		homeTeamGoingForwardSuccessionPercentage = 100 - homeTeamPenaltyGoingForward / 2;
		awayTeamGoingForwardSuccessionPercentage = 100 - awayTeamPenaltyGoingForward / 2;
		homeTeamDefendingSuccessionPercentage = 100 - homeTeamPenaltyDefense / 2;
		awayTeamDefendingSucessionPercentage = 100 - awayTeamPenaltyDefense / 2;
		
		homeTeamQualityPercentage = homeTeamAverage / 7;
		awayTeamQualityPercentage = awayTeamAverage / 7;
		
		double homeTeamGoingForwardSkillAsPercentage = MatchHelper.compareTeamsGoingForwardSkills(homeTeamPlayers, awayTeamPlayers, homePlayerSkillsList, awayPlayerSkillsList);
		double homeTeamDefensiveSkillAsPercentage = MatchHelper.compareTeamsDefensiveSkills(homeTeamPlayers, awayTeamPlayers, homePlayerSkillsList, awayPlayerSkillsList);
		System.out.println("Home team going forward skills: " + homeTeamGoingForwardSkillAsPercentage + " home team defensive skills: " + homeTeamDefensiveSkillAsPercentage);
		System.out.println("Match id: " + match.getMatch_id() + ". home team quality: " + homeTeamQualityPercentage + " away team quality " + awayTeamQualityPercentage + ". Penalties going forward: home (penalty going forward = " + homeTeamPenaltyGoingForward + ", sucession going forward " + homeTeamGoingForwardSuccessionPercentage + ", penalty defense: " + homeTeamPenaltyDefense + ", sucession defense: " + homeTeamDefendingSuccessionPercentage + ").  AWAY: (penalty going forward " + awayTeamPenaltyGoingForward + ", sucession going fowrad: " + awayTeamGoingForwardSuccessionPercentage + ", defense penalty: " + awayTeamPenaltyDefense + ", sucession defense: " + awayTeamDefendingSucessionPercentage + ").");
		homeTeamGoingForwardSuccessionPercentage *= homeTeamGoingForwardSkillAsPercentage * 2;
		awayTeamGoingForwardSuccessionPercentage *= (1 - homeTeamGoingForwardSkillAsPercentage) * 2;
		homeTeamDefendingSuccessionPercentage *= homeTeamDefensiveSkillAsPercentage * 2;
		awayTeamDefendingSucessionPercentage *= (1- homeTeamDefensiveSkillAsPercentage) * 2;
		
		if (homeTeamGoingForwardSuccessionPercentage > 100) 
			homeTeamGoingForwardSuccessionPercentage = 100;
		
		if (awayTeamGoingForwardSuccessionPercentage > 100) 
			awayTeamGoingForwardSuccessionPercentage = 100;
		
		if (homeTeamDefendingSuccessionPercentage > 100)
			homeTeamDefendingSuccessionPercentage = 100;
		if (awayTeamDefendingSucessionPercentage > 100)
			awayTeamDefendingSucessionPercentage = 100;
		
		
		System.out.println("Percentage going forward: home (" + homeTeamGoingForwardSuccessionPercentage + ") away: (" + awayTeamGoingForwardSuccessionPercentage + ").");
		System.out.println("Percentage defensive: home (" + homeTeamDefendingSuccessionPercentage + ") away: " + awayTeamDefendingSucessionPercentage + ").");
		
		String highlight = "";
		// Example of highlight text
		// "possession=away:minute=1;
		//  goal=away:minute=2:scorer=J.Lozano:scorerId=104283;
		//  possession=home:minute=2;
		//  penalty=home:minute=4:causedBy=R.Cavalehro:causedById=102345:taker=C.Rodrigo:takerId=10321:isGoal=false;
		//  suspension=away:minute=4:suspensionFor=R.Cavalehro:suspensionForId=102345:causedFoulOn=M.Miyaichi:causedFoulOnId=15435;
		//  possession=away:minute=5;
		//  free-kick=away:minute=6:causedBy=R.Cavalehro:causedById=102345:taker=A.Fall:takerId=1337:isGoal=true;
		//  possession=home:minute=7;"
		
		// Main generation of match
		for (int i = 0; i < numberOfActionsPerUpdate; i++) {
			countNumberOfActions++;
			double actionCalc = ((double) countNumberOfActions / ((double) numberOfActionsPerUpdate * (double) numberOfUpdates));
			double actionTime = (actionCalc * (double) REAL_MATCH_TIME_IN_MINUTES); 
			int actionTimeInt = (int) actionTime;
			Random random = new Random();
			if (teamInPosessionOfBall.equals("away")) {
				// TODO add possession percentage
				highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=away;");
				if (random.nextInt(100) < awayTeamGoingForwardSuccessionPercentage) { // action
					// Assess the home team defending - 100% means they have 10% chance to make a block
					if (random.nextInt(100) <= homeTeamDefendingSuccessionPercentage / 10) { // block types
						
						//TODO change possession percentage (for all types of blocks).. decreasing away's and increasing home's by 1
						
						/* between penalty and others, such that penalty chance is numberOfPenaltiesLeftr*20 (i.e. 5 left = 100% chance, 4 left = 80% chance)*/
						int penaltyChance = 100-(penaltiesSoFar*20);
						
						
						
						
						int rollRandom = random.nextInt(100);
						
						if (rollRandom < penaltyChance) {
							
							// Penalty
							System.out.println("PENALTY!!!!!!!!!");
							// penalty 8% chance -> follows that there is  40% chance of 2 minute suspension (i.e. 1 action defense and going forward goes down drastically) from opposing team player
							Player penaltyTakerPlayer = checkTheBestPenaltyTakerInTeam(awayTeamPlayers, awayPlayerSkillsList);
							Player causedByPlayer = randomlySelectCauserOfPenalty(homeTeamPlayers);
									
							boolean isGoal = false;
							if (random.nextInt(100) < 60) {
								isGoal = true;
								awayScore++;
							}
							
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "penalty=away:causedBy="
																							+causedByPlayer.getName()
																							+":causedById="
																							+causedByPlayer.getPlayer_id()
																							+":taker="
																							+penaltyTakerPlayer.getName()
																							+":takerId="
																							+penaltyTakerPlayer.getPlayer_id()
																							+":isGoal=" 
																							+isGoal
																							+";");
							penaltiesSoFar++;
							// TODO Chance of suspension && injury
							
						} else {
							// Another types
							rollRandom = random.nextInt(100);
							if (rollRandom < 16) {
							
								// 8% free kick chance -> follows that there is 5% chance of 2 minute suspension.
								System.out.println("FREE-KICK!!!!!!");
								Player freeKickTaker = awayTeamPlayers.get(6);
								Player causedByPlayer = randomlySelectCauserOfPenalty(homeTeamPlayers);
								
								boolean isGoal = false;
								if (random.nextInt(100) < 33) {
									isGoal = true;
									awayScore++;
								}
								
								highlight = appendMatchHighlightText(highlight, actionTimeInt, "free-kick=away:causedBy="
																								+causedByPlayer.getName()
																								+":causedById="
																								+causedByPlayer.getPlayer_id()
																								+":taker="+freeKickTaker.getName()
																								+":takerId="+freeKickTaker.getPlayer_id()
																								+":isGoal="
																								+isGoal
																								+";");
								// TODO Chance of suspension && injury
								
							} else if (rollRandom >= 16 && rollRandom < 46) {
								// 30% save by defence
								highlight = appendMatchHighlightText(highlight, actionTimeInt, "save=away:from=defense;");
							} else {
								//TODO obtain gk name
								// 54 % save by gk
								highlight = appendMatchHighlightText(highlight, actionTimeInt, "save=away:from=gk;");
							}
						}
						
						
						teamInPosessionOfBall = "home"; 
						//TODO add percentage
						highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=home;");
					} else { // goal
						
						//TODO change possession percentage. increasing away's and decreasing home's by 1
						int randomRoll = random.nextInt(100);
						
						if (randomRoll < 40) {
							// 40% pivot
							Player player = awayTeamPlayers.get(6);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=away:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 40 && randomRoll < 62) {
							// 22% left wing
							Player player = awayTeamPlayers.get(1);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=away:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 62 && randomRoll < 84) {
							// 22% right wing
							Player player = awayTeamPlayers.get(2);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=away:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 84 && randomRoll < 90) {
							// 6% center back
							Player player = awayTeamPlayers.get(3);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=away:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 90 && randomRoll < 95) {
							// 5% left back
							Player player = awayTeamPlayers.get(5);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=away:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else {
							// 5% right back
							Player player = awayTeamPlayers.get(4);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=away:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						}
						
						awayScore++;
						System.out.println("AWAY TEAM SCORE time: " + actionTime + " minute.");
						teamInPosessionOfBall = "home";
						// TODO add percentage
						highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=home;");
					}
					
				} else { // No action
					//TODO change possession percentage.. decreasing away's and increasing home's by 1
					teamInPosessionOfBall = "home";
					// TODO add percentage
					highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=home;");
				}
			} else if (teamInPosessionOfBall.equals("home")) {
				// TODO add percentage
				highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=home;");
				if (random.nextInt(100) < homeTeamGoingForwardSuccessionPercentage) { // action
					// Assess the away team defending - 100% means they have 10% chance to make a block
					if (random.nextInt(100) <= awayTeamDefendingSucessionPercentage / 10) { // block types
						
						//TODO change possession percentage (for all types of blocks).. decreasing home's and increasing away's by 1
						
						/* between penalty and others, such that penalty chance is numberOfPenaltiesLeftr*20 (i.e. 5 left = 100% chance, 4 left = 80% chance)*/
						int penaltyChance = 100-(penaltiesSoFar*20);
					
						
						int rollRandom = random.nextInt(100);
						
						if (rollRandom < penaltyChance) {
							System.out.println("PENALTY!!!!!!!!!");
							// penalty 8% chance -> follows that there is  40% chance of 2 minute suspension (i.e. 1 action defense and going forward goes down drastically) from opposing team player
							Player penaltyTakerPlayer = checkTheBestPenaltyTakerInTeam(homeTeamPlayers, homePlayerSkillsList);
							Player causedByPlayer = randomlySelectCauserOfPenalty(awayTeamPlayers);
									
							boolean isGoal = false;
							if (random.nextInt(100) < 60) {
								isGoal = true;
								homeScore++;
							}
							
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "penalty=home:causedBy="
																							+causedByPlayer.getName()
																							+":causedById="
																							+causedByPlayer.getPlayer_id()
																							+":taker="
																							+penaltyTakerPlayer.getName()
																							+":takerId="
																							+penaltyTakerPlayer.getPlayer_id()
																							+":isGoal=" 
																							+isGoal
																							+";");
							penaltiesSoFar++;
							// TODO Chance of suspension && injury
							
						} else { 
							rollRandom = random.nextInt(100);
							if (rollRandom < 16) {
							// 16% free kick chance -> follows that there is 5% chance of 2 minute suspension.
							System.out.println("FREE-KICK!!!!!!");
							// Get pivot as the free kick taker
							Player freeKickTaker = homeTeamPlayers.get(6);
							Player causedByPlayer = randomlySelectCauserOfPenalty(awayTeamPlayers);
							
							boolean isGoal = false;
							if (random.nextInt(100) < 33) {
								isGoal = true;
								homeScore++;
							}
							
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "free-kick=home:causedBy"
																							+causedByPlayer.getName()
																							+":causedById="
																							+causedByPlayer.getPlayer_id()
																							+":taker="+freeKickTaker.getName()
																							+":takerId="+freeKickTaker.getPlayer_id()
																							+":isGoal="
																							+isGoal
																							+";");
							// TODO Chance of suspension && injury
							
						} else if (rollRandom >= 16 && rollRandom < 46) {
							// 30% save by defence
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "save=home:from=defense;");
						} else {
							//TODO obtain gk name
							// 54 % save by gk
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "save=home:from=gk;");
						}
					}
						
						teamInPosessionOfBall = "away";
						// TODO add percentage
						highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=away;");
					} else { // goal
						
						//TODO change possession percentage.. decreasing away's and increasing home's by 1
						
						int randomRoll = random.nextInt(100);
						
						if (randomRoll < 40) {
							// 40% pivot
							Player player = homeTeamPlayers.get(6);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=home:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 40 && randomRoll < 62) {
							// 22% left wing
							Player player = homeTeamPlayers.get(1);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=home:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 62 && randomRoll < 84) {
							// 22% right wing
							Player player = homeTeamPlayers.get(2);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=home:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 84 && randomRoll < 90) {
							// 6% center back
							Player player = homeTeamPlayers.get(3);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=home:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else if (randomRoll >= 90 && randomRoll < 95) {
							// 5% left back
							Player player = homeTeamPlayers.get(5);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=home:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						} else {
							// 5% right back
							Player player = homeTeamPlayers.get(4);
							highlight = appendMatchHighlightText(highlight, actionTimeInt, "goal=home:scorer="+player.getName()+":scorerId="+player.getPlayer_id()+";");
						}
						homeScore++;
						System.out.println("HOME TEAM SCORE time: " + actionTime + " minute.");
						teamInPosessionOfBall = "away";
						// TODO add percentage
						highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=away;");
					}
				} else { // no action
					//TODO change possession percentage.. decreasing home's and increasing away's by 1
					teamInPosessionOfBall = "away";
					// TODO add percentage
					highlight = appendMatchHighlightText(highlight, actionTimeInt, "possession=away;");
				}
			}
			
		} // END for (number of actions per update)
		// END Main generation of match
		
		/*homeScore++;
		awayScore++;*/
		
		// Generate Match Highlight
		MatchHighlight matchHighlight = new MatchHighlight();
		matchHighlight.setHighlight_text(highlight);
		matchHighlight.setMatch_id(match.getMatch_id());
		matchHighlight.setUpdate_number(countUpdates);
				
		emgr.persist(matchHighlight);
		emgr.flush();
		
		/*System.out.println("updateMatch() homeScore: " + homeScore + " awayScore: " + awayScore);*/
		System.out.println("Score so far: " + homeScore + ":" + awayScore);
		matchUpdated = true;
		System.out.println("Updated match (" + match.getMatch_id() + ")");
		return matchUpdated;
		
	}
	

	private Player randomlySelectCauserOfPenalty(List<Player> teamPlayers) {
		Random random = new Random();
		int randomValue = random.nextInt(100);
		Player player = new Player();
		
		if (randomValue < 21) {
			// 21% CB
			player = teamPlayers.get(3);
		} else if (randomValue >= 21 && randomValue < 42) {
			// 21% RB
			player = teamPlayers.get(4);
		} else if (randomValue >= 42 && randomValue < 63) {
			// 21% LB
			player = teamPlayers.get(5);
		} else if (randomValue >= 63 && randomValue < 78) {
			// 15% PV
			player = teamPlayers.get(6);
		} else if (randomValue >= 78 && randomValue < 89) {
			// 11% LW
			player = teamPlayers.get(1);
		} else {
			// 11% RW
			player = teamPlayers.get(2);
		}
		
		
		
		
		
		return player;
	}

	private Player checkTheBestPenaltyTakerInTeam(List<Player> teamPlayers,
			List<Skill> playerSkillsList) {
		Player bestPlayer = new Player();
		for (int i = 0; i < 7; i++) {
			double currentHighestSkill = 0;
			if (i == 0) {
				bestPlayer = teamPlayers.get(i);
				// Obtain the skill
				for (int j = 0; j < playerSkillsList.size(); j++) {
					if (playerSkillsList.get(j).getPlayer_id() == teamPlayers.get(i).getPlayer_id()) {
						currentHighestSkill = playerSkillsList.get(j).getPenalties();
					}
				}
			} else {
				// Compare
				for (int j = 0; j < playerSkillsList.size(); j++) {
					if (playerSkillsList.get(j).getPlayer_id() == teamPlayers.get(i).getPlayer_id()) {
						double tempSkill = playerSkillsList.get(j).getPenalties();
						if (tempSkill > currentHighestSkill) {
							bestPlayer = teamPlayers.get(i);
							currentHighestSkill = tempSkill;
						}
					}
				}
			}
		}
		return bestPlayer;
	}
	
	// Example of highlight text
	// "possession=away:minute=1;
	//  goal=away:minute=2:scorer=J.Lozano:scorerId=104283;
	//  possession=home:minute=2;
	//  penalty=home:minute=4:causedBy=R.Cavalehro:causedById=102345:taker=C.Rodrigo:takerId=10321:isGoal=false;
	//  suspension=away:minute=4:suspensionFor=R.Cavalehro:suspensionForId=102345:causedFoulOn=M.Miyaichi:causedFoulOnId=15435;
	//  possession=away:minute=5;
	//  free-kick=away:minute=6:causedBy=R.Cavalehro:causedById=102345:taker=A.Fall:takerId=1337:isGoal=true;
	//  possession=home:minute=7;"

	

	private String appendMatchHighlightText(String highlightSoFar, int minute, String newHighlight) {
		/*if (highlightSoFar.isEmpty() || highlightSoFar.trim().isEmpty() || highlightSoFar.equals("")) {
			highlightSoFar = Integer.toString(minute) + ":" + newHighlight;
		} else {
			highlightSoFar += Integer.toString(minute) + ":" + newHighlight +";";
		}*/
		
		highlightSoFar += "minute=" + minute + ":" + newHighlight; 
		
		return highlightSoFar;
		
	}

	@Override
	public boolean generateMatchOutcome() {
		boolean matchGenerated = false;
		
		// Generate new match outcome
		MatchOutcome matchOutcome = new MatchOutcome();
		matchOutcome.setMatch_id(match.getMatch_id());
		matchOutcome.setMatch_score(homeScore + ":" + awayScore);
		System.out.println("Finishing match (" + match.getMatch_id() + ") final score = " + matchOutcome.getMatch_score());
		try {
			
			// Finish the match
			match.setMatch_finished(true);
			emgr.merge(match);
			emgr.flush();
			// Persist the match outcome
			emgr.persist(matchOutcome);
			emgr.flush();
			if (generateLeaderboardRecord(homeTeam, awayTeam)) {
				matchGenerated = true;
			}
		} catch (Exception e) {
			System.err.println("Couldn't generate match outcome for matchId: " + match.getMatch_id() + ". Exception: " + e.getLocalizedMessage());
		}
		
		
		return matchGenerated;
	}

	private boolean generateLeaderboardRecord(Team homeTeam, Team awayTeam) {
		boolean generated = false;
		
		// Obtain the leaderboard entity
		int leagueId = homeTeam.getLeague_id();
		
		
		TypedQuery<Leaderboard> leaderboardQuery = emgr.createNamedQuery("Leaderboard.getByLeagueId", Leaderboard.class);
		leaderboardQuery.setParameter("league_id", leagueId);

		try {
			Leaderboard leaderboard = leaderboardQuery.getSingleResult();
			int leaderboardId = leaderboard.getLeaderboard_id();
			// Obtain the leaderboard team entity
			TypedQuery<LeaderboardTeam> leaderboardHomeTeamQuery = emgr.createNamedQuery("LeaderboardTeam.getByLeaderboardIdAndTeamId", LeaderboardTeam.class);
			leaderboardHomeTeamQuery.setParameter("leaderboard_id", leaderboardId);
			leaderboardHomeTeamQuery.setParameter("team_id", homeTeam.getTeam_id());
			TypedQuery<LeaderboardTeam> leaderboardAwayTeamQuery = emgr.createNamedQuery("LeaderboardTeam.getByLeaderboardIdAndTeamId", LeaderboardTeam.class);
			leaderboardAwayTeamQuery.setParameter("leaderboard_id", leaderboardId);
			leaderboardAwayTeamQuery.setParameter("team_id", awayTeam.getTeam_id());
			try {
				LeaderboardTeam leaderboardHomeTeam = leaderboardHomeTeamQuery.getSingleResult();
				LeaderboardTeam leaderboardAwayTeam = leaderboardAwayTeamQuery.getSingleResult();
				// Modify the leaderboardTeam record
				leaderboardHomeTeam.setMatches_played(leaderboardHomeTeam.getMatches_played()+1);
				leaderboardAwayTeam.setMatches_played(leaderboardAwayTeam.getMatches_played()+1);
				
				// Determine the outcome of match for both teams
				if (homeScore == awayScore) {
					leaderboardHomeTeam.setDraws(leaderboardHomeTeam.getDraws()+1);
					leaderboardAwayTeam.setDraws(leaderboardAwayTeam.getDraws()+1);
					// Add 1 point for each team
					leaderboardHomeTeam.setPoints(leaderboardHomeTeam.getPoints()+1);
					leaderboardAwayTeam.setPoints(leaderboardAwayTeam.getPoints()+1);
					// form
					String currentHomeForm = leaderboardHomeTeam.getForm();
					String currentAwayForm = leaderboardAwayTeam.getForm();
					String newHomeForm = generateNewForm(currentHomeForm, "D");
					String newAwayForm = generateNewForm(currentAwayForm, "D");
					
					leaderboardHomeTeam.setForm(newHomeForm);
					leaderboardAwayTeam.setForm(newAwayForm);
				} else if (homeScore > awayScore) {
					leaderboardHomeTeam.setWins(leaderboardHomeTeam.getWins()+1);
					leaderboardAwayTeam.setLoses(leaderboardAwayTeam.getLoses()+1);
					// Add 3 points to home and none to away team
					leaderboardHomeTeam.setPoints(leaderboardHomeTeam.getPoints()+3);
					// form
					String currentHomeForm = leaderboardHomeTeam.getForm();
					String currentAwayForm = leaderboardAwayTeam.getForm();
					String newHomeForm = generateNewForm(currentHomeForm, "W");
					String newAwayForm = generateNewForm(currentAwayForm, "L");
					
					leaderboardHomeTeam.setForm(newHomeForm);
					leaderboardAwayTeam.setForm(newAwayForm);
					
				} else if (awayScore > homeScore) {
					leaderboardHomeTeam.setLoses(leaderboardHomeTeam.getLoses()+1);
					leaderboardAwayTeam.setWins(leaderboardAwayTeam.getWins()+1);
					// Add 3 points to away team and none to home
					leaderboardAwayTeam.setPoints(leaderboardAwayTeam.getPoints()+3);
					// form
					String currentHomeForm = leaderboardHomeTeam.getForm();
					String currentAwayForm = leaderboardAwayTeam.getForm();
					String newHomeForm = generateNewForm(currentHomeForm, "L");
					String newAwayForm = generateNewForm(currentAwayForm, "W");
					
					leaderboardHomeTeam.setForm(newHomeForm);
					leaderboardAwayTeam.setForm(newAwayForm);
					
				} else {
					System.err.println("ERROR: When generating leaderboard for home & away team. The match was neither drawn or lost (?!)");
				}
				// END Determine the outcome of match for both teams
				
				// goals_scored
				leaderboardHomeTeam.setGoals_scored(leaderboardHomeTeam.getGoals_scored() + homeScore);
				leaderboardAwayTeam.setGoals_scored(leaderboardAwayTeam.getGoals_scored() + awayScore);
				// goals_conceded
				leaderboardHomeTeam.setGoals_conceded(leaderboardHomeTeam.getGoals_conceded() + awayScore);
				leaderboardAwayTeam.setGoals_conceded(leaderboardAwayTeam.getGoals_conceded() + homeScore);
				// goal_difference
				leaderboardHomeTeam.setGoals_difference(leaderboardHomeTeam.getGoals_scored() - leaderboardHomeTeam.getGoals_conceded());
				leaderboardAwayTeam.setGoals_difference(leaderboardAwayTeam.getGoals_scored() - leaderboardAwayTeam.getGoals_conceded());
				
				
				// END Modify the leaderboardTeam record
				
				// Merge the leaderboardTeam record
				emgr.merge(leaderboardHomeTeam);
				emgr.flush();
				emgr.merge(leaderboardAwayTeam);
				emgr.flush();
				generated = true;
			} catch (NoResultException nre) {
				System.err.println("ERROR: Couldn't obtain the leaderboard team object for leaderboard id: " + leaderboardId + " and leagueId: " + leagueId);
			}
		} catch (NoResultException nre) {
			System.err.println("ERROR: Couldn't obtain the leaderboard object for league id: " + leagueId);
		}
		return generated;
	}

	private String generateNewForm(String currentForm, String outcome) {
		String newForm = "";
		if (currentForm.equals("0")) {
			// Overwrite the form, and start without '-' at the beginning 
			newForm = outcome;
		} else if (currentForm.contains("-")) { // Contains at least 2 records
			// split the current form to see how many records there are
			String[] splitCurrentForm = currentForm.split("-");
			int length = splitCurrentForm.length;
			if (length == 5) {
				// Swap the oldest record with the newest one
				newForm = outcome + "-" + splitCurrentForm[0] + splitCurrentForm[1] + splitCurrentForm[2] + splitCurrentForm[3];
			} else {
				// Keep appending
				newForm = outcome + "-" + currentForm;
			}
			
			
		} else {
			// Cases where there is only 1 record
			newForm = outcome + "-"  + currentForm;
			
		}
		return newForm;
	}
	

	
	
	
}
