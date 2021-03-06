package af.handball.repository.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.Captains;
import af.handball.entity.Contract;
import af.handball.entity.Leaderboard;
import af.handball.entity.LeaderboardTeam;
import af.handball.entity.League;
import af.handball.entity.Match;
import af.handball.entity.MatchDay;
import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.entity.Team;
import af.handball.generator.BackGenerator;
import af.handball.generator.GkGenerator;
import af.handball.generator.PivotGenerator;
import af.handball.generator.PlayerNumberGenerator;
import af.handball.generator.QualityGenerator;
import af.handball.generator.RandomTeamNameGenerator;
import af.handball.generator.ScheduleGenerator;
import af.handball.generator.WingGenerator;
import af.handball.repository.LeagueRepository;
import af.handball.scheduler.job.MatchJob;

@Component("LeagueRepository")
@Repository
public class LeagueRepositoryImpl implements LeagueRepository {

	private static final int DEFAULT_MATCH_DURATION_IN_SECONDS = 420;
	private int league_id;
	private int teamId = -1;

	
	
	@PersistenceContext
	EntityManager emgr;

	@Override
	public int allocateTeamInLeague(String email, String teamName,
			int teamLevel) {
		// Query to check for a league with available slots and on the same
		// teamLevel

		TypedQuery<League> leagueQuery = emgr.createNamedQuery(
				"League.getAvailableLeagueByLevel", League.class);
		leagueQuery.setParameter("league_level", teamLevel);

		try {
			League league = leagueQuery.getSingleResult();
			System.out.println("Obtained an available league.. ("
					+ league.getLeague_id() + ")");
			TypedQuery<Team> teamQuery = emgr.createNamedQuery(
					"Team.getAvailableTeams", Team.class);
			teamQuery.setParameter("league_id", league.getLeague_id());
			List<Team> teamList = teamQuery.getResultList();
			Team team = teamList.get(0);
			team.setEmail(email);
			team.setTeam_name(teamName);
			emgr.persist(team);
			emgr.flush();
			teamId = team.getTeam_id();
			
			

			// TODO Concurrency handling
			int leagueAvailableSlots = league.getAvailable_slots() - 1;
			league.setAvailable_slots(leagueAvailableSlots);
			if (leagueAvailableSlots == 0) {
				league.setLocked(true);
			}
			emgr.persist(league);
			emgr.flush();

		} catch (NoResultException nre) {
			System.out
					.println("No League available was found.. Generating new league with level: "
							+ teamLevel);
			// Generate a new league, leaderboard teams and players..
			League league = new League();
			league.setAvailable_slots(12);
			league.setLeague_level(teamLevel);
			league.setLocked(true);
			
			try {
				emgr.persist(league);
				emgr.flush();
				league_id = league.getLeague_id();
				System.out.println("New league persisted with id " + league_id);
				// XXX generate a new leaderboard
				Leaderboard leaderboard = new Leaderboard();
				leaderboard.setLeague_id(league_id);
				emgr.persist(leaderboard);
				emgr.flush();
				System.out.println("CREATED LEADERBOARD WITH ID : " + leaderboard.getLeaderboard_id());
				
				// Create 12 new bot teams
				RandomTeamNameGenerator randomTeamNameGenerator = new RandomTeamNameGenerator(
						teamLevel);
				String[] teamNames = randomTeamNameGenerator.generateTeams();
				int moneyMin = 2000000;
				int moneyMax = 3000000;
				int fansMin = 10000;
				int fansMax = 16000;
				Random randomGenerator = new Random();
				// Loop to create 12 teams
				for (int i = 0; i < 12; i++) {
					Team team = new Team();
					team.setTeam_level(teamLevel);
					team.setTeam_name(teamNames[i]);
					team.setMoney(randomGenerator
							.nextInt((moneyMax - moneyMin) + 1) + moneyMin);
					team.setFans(randomGenerator
							.nextInt((fansMax - fansMin) + 1) + fansMin);
					team.setLeague_id(league_id);

					try {
						emgr.persist(team);
						emgr.flush();
						teamId = team.getTeam_id();
						
						// XXX Persist the default leaderboard team record
						LeaderboardTeam leaderboardTeam = new LeaderboardTeam();
						leaderboardTeam.setLeaderboard_id(leaderboard.getLeaderboard_id());
						leaderboardTeam.setTeam_id(teamId);
						leaderboardTeam.setMatches_played(0);
						leaderboardTeam.setWins(0);
						leaderboardTeam.setDraws(0);
						leaderboardTeam.setLoses(0);
						leaderboardTeam.setGoals_scored(0);
						leaderboardTeam.setGoals_conceded(0);
						leaderboardTeam.setGoals_difference(0);
						leaderboardTeam.setPoints(0);
						leaderboardTeam.setForm("0");
						emgr.persist(leaderboardTeam);
						emgr.flush();
						System.out.println("LEADERBOARD TEAM ID = " + leaderboardTeam.getLeaderboard_team_id());
						
						// Persist the captains roles
						Captains captains = new Captains();
						captains.setTeam_id(teamId);
						captains.setCaptain_id_one(-1);
						captains.setCaptain_id_two(-1);
						captains.setCaptain_id_three(-1);
						captains.setCaptain_id_four(-1);
						emgr.persist(captains);
						emgr.flush();
						
						System.out.println("Persisted team number " + (i + 1)
								+ ". Now generating the players.");
						// generate the players for the team (INCLUDES 3
						// year contract generation for each player
						// and season wage depending on the generated players:
						// age, skills, etc.

						// 2 out of 17 players will be randomly generated with a
						// "very good" quality (i.e. good quality) and the other
						// 12
						// players will be generated with "good" quality
						// P.S. actually it can happen that only 1 player will
						// be "very good" due to the randomness
						int firstVeryGoodPlayer = (randomGenerator.nextInt(17));
						int secondVeryGoodPlayer = (randomGenerator.nextInt(17));
						// 2 out of 17 players will be randomly generated as
						// very young
						int firstYoungPlayer = randomGenerator.nextInt(17);
						int secondYoungPlayer = randomGenerator.nextInt(17);
						// 2 out of 17 players will be randomly generated as
						// "old".
						int firstOldPlayer = randomGenerator.nextInt(17);
						int secondOldPlayer = randomGenerator.nextInt(17);

						int countOfPlayersCreated = 0;

						
						Map<String, Object> playerNumberMap = PlayerNumberGenerator
								.generateRandomNumbers();
						System.out.println("Player Number Map = "
								+ playerNumberMap);
						// GENERATE 2 goal keepers
						GkGenerator gkGenerator = new GkGenerator(teamLevel);
						for (int j = 0; j < 2; j++) {
							boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
							boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
							boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

							int gkNumber = -1;
							if (j == 0)
								gkNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_GK_FIRST);
							else
								gkNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_GK_SECOND);

							Map<String, Object> mappedObjects = new HashMap<String, Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = gkGenerator
											.generateGoalKeeper(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													GkGenerator.AGE_TYPE_YOUNG,
													teamLevel, gkNumber);

								} else if (generateAsOld) {
									mappedObjects = gkGenerator
											.generateGoalKeeper(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													GkGenerator.AGE_TYPE_OLD,
													teamLevel, gkNumber);
								} else { // Generate as "middle" age
									mappedObjects = gkGenerator
											.generateGoalKeeper(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													GkGenerator.AGE_TYPE_MIDDLE,
													teamLevel, gkNumber);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects = gkGenerator
											.generateGoalKeeper(
													QualityGenerator.QUALITY_TYPE_GOOD,
													GkGenerator.AGE_TYPE_YOUNG,
													teamLevel, gkNumber);
								} else if (generateAsOld) {
									mappedObjects = gkGenerator
											.generateGoalKeeper(
													QualityGenerator.QUALITY_TYPE_GOOD,
													GkGenerator.AGE_TYPE_OLD,
													teamLevel, gkNumber);
								} else { // Generate as "middle" age
									mappedObjects = gkGenerator
											.generateGoalKeeper(
													QualityGenerator.QUALITY_TYPE_GOOD,
													GkGenerator.AGE_TYPE_MIDDLE,
													teamLevel, gkNumber);
								}
							}

							Player player = (Player) mappedObjects
									.get(GkGenerator.MAP_PLAYER);
							Skill gkSkills = (Skill) mappedObjects
									.get(GkGenerator.MAP_GK_SKILLS);

							// Persist the first generated goal keeper's
							// formation as first squad
							if (j == 0) {
								player.setFormation(Player.FORMATION_FIRST_SQUAD);
								player.setFirst_squad_play_position("GK");
							} else {
								player.setFormation(Player.FORMATION_BENCH);
								player.setFirst_squad_play_position("");
							}
							try { // Try persist
								emgr.persist(player);
								emgr.flush();
								gkSkills.setPlayer_id(player.getPlayer_id());
								emgr.persist(gkSkills);
								emgr.flush();
								System.out
										.println("Persisted player & gkSkills.");
								// Generate the contract and persist
								Contract contract = new Contract();
								contract.setTeam_id(teamId);
								contract.setExpired(false);
								contract.setPlayer_id(player.getPlayer_id());
								contract.setSeason_wage((int) (player
										.getMarket_value() * 0.22));
								contract.setYears_left(3);
								emgr.persist(contract);
								emgr.flush();
							} catch (Exception e) {
								System.out
										.println("Error when trying to persist Player OR gkSills. Exception = "
												+ e.getLocalizedMessage());
								e.printStackTrace();
							}

							countOfPlayersCreated++;
						} // END of for loop (generating 2 goal keepers)

						// Generate 2 right wing
						WingGenerator rwGenerator = new WingGenerator(
								teamLevel, WingGenerator.POSITION_RW_LABEL);
						for (int rw = 0; rw < 2; rw++) {
							boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
							boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
							boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

							int rwNumber = -1;
							if (rw == 0)
								rwNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_RW_FIRST);
							else
								rwNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_RW_SECOND);
							Map<String, Object> mappedObjects = new HashMap<String, Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = rwGenerator
											.generateWinger(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													WingGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													WingGenerator.POSITION_RW_LABEL,
													rwNumber);

								} else if (generateAsOld) {
									mappedObjects = rwGenerator
											.generateWinger(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													WingGenerator.AGE_TYPE_OLD,
													teamLevel,
													WingGenerator.POSITION_RW_LABEL,
													rwNumber);
								} else { // Generate as "middle" age
									mappedObjects = rwGenerator
											.generateWinger(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													WingGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													WingGenerator.POSITION_RW_LABEL,
													rwNumber);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects = rwGenerator.generateWinger(
											QualityGenerator.QUALITY_TYPE_GOOD,
											WingGenerator.AGE_TYPE_YOUNG,
											teamLevel,
											WingGenerator.POSITION_RW_LABEL,
											rwNumber);
								} else if (generateAsOld) {
									mappedObjects = rwGenerator.generateWinger(
											QualityGenerator.QUALITY_TYPE_GOOD,
											WingGenerator.AGE_TYPE_OLD,
											teamLevel,
											WingGenerator.POSITION_RW_LABEL,
											rwNumber);
								} else { // Generate as "middle" age
									mappedObjects = rwGenerator.generateWinger(
											QualityGenerator.QUALITY_TYPE_GOOD,
											WingGenerator.AGE_TYPE_MIDDLE,
											teamLevel,
											WingGenerator.POSITION_RW_LABEL,
											rwNumber);
								}
							}

							Player player = (Player) mappedObjects
									.get(WingGenerator.MAP_PLAYER);
							Skill fieldPlayerSkills = (Skill) mappedObjects
									.get(WingGenerator.MAP_WINGER_SKILLS);

							// Persist the first generated right winger's
							// formation as first squad
							if (rw == 0) {
								player.setFormation(Player.FORMATION_FIRST_SQUAD);
								player.setFirst_squad_play_position("RW");
							} else {
								player.setFormation(Player.FORMATION_BENCH);
								player.setFirst_squad_play_position("");
							}
						
							try { // Try persist
								emgr.persist(player);
								emgr.flush();
								fieldPlayerSkills.setPlayer_id(player
										.getPlayer_id());
								emgr.persist(fieldPlayerSkills);
								emgr.flush();
								System.out
										.println("Persisted player & fieldPlayerSkills.");
								// Generate the contract and persist
								Contract contract = new Contract();
								contract.setTeam_id(teamId);
								contract.setExpired(false);
								contract.setPlayer_id(player.getPlayer_id());
								contract.setSeason_wage((int) (player
										.getMarket_value() * 0.22));
								contract.setYears_left(3);
								emgr.persist(contract);
								emgr.flush();
							} catch (Exception e) {
								System.out
										.println("Error when trying to persist Player OR fieldPlayerSkills. Exception = "
												+ e.getLocalizedMessage());
								e.printStackTrace();
							}

							countOfPlayersCreated++;
						} // END of for loop (generating 2 right wings)

						// Generate 2 left wing
						WingGenerator lwGenerator = new WingGenerator(
								teamLevel, WingGenerator.POSITION_LW_LABEL);
						for (int lw = 0; lw < 2; lw++) {
							boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
							boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
							boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

							int lwNumber = -1;
							if (lw == 0)
								lwNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_LW_FIRST);
							else
								lwNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_LW_SECOND);

							Map<String, Object> mappedObjects = new HashMap<String, Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = lwGenerator
											.generateWinger(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													WingGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													WingGenerator.POSITION_LW_LABEL,
													lwNumber);

								} else if (generateAsOld) {
									mappedObjects = lwGenerator
											.generateWinger(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													WingGenerator.AGE_TYPE_OLD,
													teamLevel,
													WingGenerator.POSITION_LW_LABEL,
													lwNumber);
								} else { // Generate as "middle" age
									mappedObjects = lwGenerator
											.generateWinger(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													WingGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													WingGenerator.POSITION_LW_LABEL,
													lwNumber);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects = lwGenerator.generateWinger(
											QualityGenerator.QUALITY_TYPE_GOOD,
											WingGenerator.AGE_TYPE_YOUNG,
											teamLevel,
											WingGenerator.POSITION_LW_LABEL,
											lwNumber);
								} else if (generateAsOld) {
									mappedObjects = lwGenerator.generateWinger(
											QualityGenerator.QUALITY_TYPE_GOOD,
											WingGenerator.AGE_TYPE_OLD,
											teamLevel,
											WingGenerator.POSITION_LW_LABEL,
											lwNumber);
								} else { // Generate as "middle" age
									mappedObjects = lwGenerator.generateWinger(
											QualityGenerator.QUALITY_TYPE_GOOD,
											WingGenerator.AGE_TYPE_MIDDLE,
											teamLevel,
											WingGenerator.POSITION_LW_LABEL,
											lwNumber);
								}
							}

							Player player = (Player) mappedObjects
									.get(WingGenerator.MAP_PLAYER);
							Skill fieldPlayerSkills = (Skill) mappedObjects
									.get(WingGenerator.MAP_WINGER_SKILLS);

							// Persist the first generated left winger's
							// formation as first squad
							if (lw == 0) {
								player.setFormation(Player.FORMATION_FIRST_SQUAD);
								player.setFirst_squad_play_position("LW");
							}
							else {
								player.setFormation(Player.FORMATION_BENCH);
								player.setFirst_squad_play_position("");
							}
							try { // Try persist
								emgr.persist(player);
								emgr.flush();
								fieldPlayerSkills.setPlayer_id(player
										.getPlayer_id());
								emgr.persist(fieldPlayerSkills);
								emgr.flush();
								System.out
										.println("Persisted player & fieldPlayerSkills.");
								// Generate the contract and persist
								Contract contract = new Contract();
								contract.setTeam_id(teamId);
								contract.setExpired(false);
								contract.setPlayer_id(player.getPlayer_id());
								contract.setSeason_wage((int) (player
										.getMarket_value() * 0.22));
								contract.setYears_left(3);
								emgr.persist(contract);
								emgr.flush();
							} catch (Exception e) {
								System.out
										.println("Error when trying to persist Player OR fieldPlayerSkills. Exception = "
												+ e.getLocalizedMessage());
								e.printStackTrace();
							}

							countOfPlayersCreated++;
						} // END of for loop (generating 2 left wings)

						// Generate 3 central backs
						BackGenerator centralBackGenerator = new BackGenerator(
								teamLevel, BackGenerator.POSITION_CB_LABEL);
						for (int cb = 0; cb < 3; cb++) {
							boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
							boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
							boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

							
							int cbNumber = -1;
							if (cb == 0)
								cbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_CB_FIRST);
							else if (cb == 1)
								cbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_CB_SECOND);
							else
								cbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_CB_THIRD);

							Map<String, Object> mappedObjects = new HashMap<String, Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = centralBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													BackGenerator.POSITION_CB_LABEL,
													cbNumber);

								} else if (generateAsOld) {
									mappedObjects = centralBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_OLD,
													teamLevel,
													BackGenerator.POSITION_CB_LABEL,
													cbNumber);
								} else { // Generate as "middle" age
									mappedObjects = centralBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													BackGenerator.POSITION_CB_LABEL,
													cbNumber);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects = centralBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													BackGenerator.POSITION_CB_LABEL,
													cbNumber);
								} else if (generateAsOld) {
									mappedObjects = centralBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_OLD,
													teamLevel,
													BackGenerator.POSITION_CB_LABEL,
													cbNumber);
								} else { // Generate as "middle" age
									mappedObjects = centralBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													BackGenerator.POSITION_CB_LABEL,
													cbNumber);
								}
							}

							Player player = (Player) mappedObjects
									.get(BackGenerator.MAP_PLAYER);
							Skill fieldPlayerSkills = (Skill) mappedObjects
									.get(BackGenerator.MAP_BACK_SKILLS);

							// Persist the first generated central back's
							// formation as first squad
							if (cb == 0) {
								player.setFormation(Player.FORMATION_FIRST_SQUAD);
								player.setFirst_squad_play_position("CB");
							}
							else if (cb == 1) {
								player.setFormation(Player.FORMATION_BENCH);
								player.setFirst_squad_play_position("");
							}
							else {
								player.setFormation(Player.FORMATION_RESERVES);
								player.setFirst_squad_play_position("");
							}
							try { // Try persist
								emgr.persist(player);
								emgr.flush();
								fieldPlayerSkills.setPlayer_id(player
										.getPlayer_id());
								emgr.persist(fieldPlayerSkills);
								emgr.flush();
								System.out
										.println("Persisted player & fieldPlayerSkills.");
								// Generate the contract and persist
								Contract contract = new Contract();
								contract.setTeam_id(teamId);
								contract.setExpired(false);
								contract.setPlayer_id(player.getPlayer_id());
								contract.setSeason_wage((int) (player
										.getMarket_value() * 0.22));
								contract.setYears_left(3);
								emgr.persist(contract);
								emgr.flush();
							} catch (Exception e) {
								System.out
										.println("Error when trying to persist Player OR fieldPlayerSkills. Exception = "
												+ e.getLocalizedMessage());
								e.printStackTrace();
							}

							countOfPlayersCreated++;
						} // END of for loop (generating 3 central backs)

						// Generate 3 right backs
						BackGenerator rightBackGenerator = new BackGenerator(
								teamLevel, BackGenerator.POSITION_RB_LABEL);
						for (int rb = 0; rb < 3; rb++) {
							boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
							boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
							boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

							int rbNumber = -1;
							if (rb == 0)
								rbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_RB_FIRST);
							else if (rb == 1)
								rbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_RB_SECOND);
							else
								rbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_RB_THIRD);

							Map<String, Object> mappedObjects = new HashMap<String, Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = rightBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													BackGenerator.POSITION_RB_LABEL,
													rbNumber);

								} else if (generateAsOld) {
									mappedObjects = rightBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_OLD,
													teamLevel,
													BackGenerator.POSITION_RB_LABEL,
													rbNumber);
								} else { // Generate as "middle" age
									mappedObjects = rightBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													BackGenerator.POSITION_RB_LABEL,
													rbNumber);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects = rightBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													BackGenerator.POSITION_RB_LABEL,
													rbNumber);
								} else if (generateAsOld) {
									mappedObjects = rightBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_OLD,
													teamLevel,
													BackGenerator.POSITION_RB_LABEL,
													rbNumber);
								} else { // Generate as "middle" age
									mappedObjects = rightBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													BackGenerator.POSITION_RB_LABEL,
													rbNumber);
								}
							}

							Player player = (Player) mappedObjects
									.get(BackGenerator.MAP_PLAYER);
							Skill fieldPlayerSkills = (Skill) mappedObjects
									.get(BackGenerator.MAP_BACK_SKILLS);

							// Persist the first generated right back's
							// formation as first squad
							if (rb == 0) {
								player.setFormation(Player.FORMATION_FIRST_SQUAD);
								player.setFirst_squad_play_position("RB");
							}
							else if (rb == 1) {
								player.setFormation(Player.FORMATION_BENCH);
								player.setFirst_squad_play_position("");
							} else {
								player.setFormation(Player.FORMATION_RESERVES);
								player.setFirst_squad_play_position("");
							}
							try { // Try persist
								emgr.persist(player);
								emgr.flush();
								fieldPlayerSkills.setPlayer_id(player
										.getPlayer_id());
								emgr.persist(fieldPlayerSkills);
								emgr.flush();
								System.out
										.println("Persisted player & fieldPlayerSkills.");
								// Generate the contract and persist
								Contract contract = new Contract();
								contract.setTeam_id(teamId);
								contract.setExpired(false);
								contract.setPlayer_id(player.getPlayer_id());
								contract.setSeason_wage((int) (player
										.getMarket_value() * 0.22));
								contract.setYears_left(3);
								emgr.persist(contract);
								emgr.flush();
							} catch (Exception e) {
								System.out
										.println("Error when trying to persist Player OR fieldPlayerSkills. Exception = "
												+ e.getLocalizedMessage());
								e.printStackTrace();
							}

							countOfPlayersCreated++;
						} // END of for loop (generating 3 right backs)

						// Generate 3 left backs
						BackGenerator leftBackGenerator = new BackGenerator(
								teamLevel, BackGenerator.POSITION_LB_LABEL);
						for (int lb = 0; lb < 3; lb++) {
							boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
							boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
							boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

							int lbNumber = -1;
							if (lb == 0)
								lbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_LB_FIRST);
							else if (lb == 1)
								lbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_LB_SECOND);
							else
								lbNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_LB_THIRD);

							Map<String, Object> mappedObjects = new HashMap<String, Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = leftBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													BackGenerator.POSITION_LB_LABEL,
													lbNumber);

								} else if (generateAsOld) {
									mappedObjects = leftBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_OLD,
													teamLevel,
													BackGenerator.POSITION_LB_LABEL,
													lbNumber);
								} else { // Generate as "middle" age
									mappedObjects = leftBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													BackGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													BackGenerator.POSITION_LB_LABEL,
													lbNumber);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects = leftBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_YOUNG,
													teamLevel,
													BackGenerator.POSITION_LB_LABEL,
													lbNumber);
								} else if (generateAsOld) {
									mappedObjects = leftBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_OLD,
													teamLevel,
													BackGenerator.POSITION_LB_LABEL,
													lbNumber);
								} else { // Generate as "middle" age
									mappedObjects = leftBackGenerator
											.generateBack(
													QualityGenerator.QUALITY_TYPE_GOOD,
													BackGenerator.AGE_TYPE_MIDDLE,
													teamLevel,
													BackGenerator.POSITION_LB_LABEL,
													lbNumber);
								}
							}

							Player player = (Player) mappedObjects
									.get(BackGenerator.MAP_PLAYER);
							Skill fieldPlayerSkills = (Skill) mappedObjects
									.get(BackGenerator.MAP_BACK_SKILLS);

							// Persist the first generated left back's formation
							// as first squad
							if (lb == 0) {
								player.setFormation(Player.FORMATION_FIRST_SQUAD);
								player.setFirst_squad_play_position("LB");
							}
							else if (lb == 1) {
								player.setFormation(Player.FORMATION_BENCH);
								player.setFirst_squad_play_position("");
							} else {
								player.setFormation(Player.FORMATION_RESERVES);
								player.setFirst_squad_play_position("");
							}
							try { // Try persist
								emgr.persist(player);
								emgr.flush();
								fieldPlayerSkills.setPlayer_id(player
										.getPlayer_id());
								emgr.persist(fieldPlayerSkills);
								emgr.flush();
								System.out
										.println("Persisted player & fieldPlayerSkills.");
								// Generate the contract and persist
								Contract contract = new Contract();
								contract.setTeam_id(teamId);
								contract.setExpired(false);
								contract.setPlayer_id(player.getPlayer_id());
								contract.setSeason_wage((int) (player
										.getMarket_value() * 0.22));
								contract.setYears_left(3);
								emgr.persist(contract);
								emgr.flush();
							} catch (Exception e) {
								System.out
										.println("Error when trying to persist Player OR fieldPlayerSkills. Exception = "
												+ e.getLocalizedMessage());
								e.printStackTrace();
							}

							countOfPlayersCreated++;
						} // END of for loop (generating 3 left backs)

						// Generate 2 pivot
						PivotGenerator pivotGenerator = new PivotGenerator(
								teamLevel);
						for (int pv = 0; pv < 2; pv++) {
							boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
							boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
							boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

							int pvNumber = -1;
							if (pv == 0)
								pvNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_PV_FIRST);
							else
								pvNumber = (Integer) playerNumberMap
										.get(PlayerNumberGenerator.MAP_PV_SECOND);

							Map<String, Object> mappedObjects = new HashMap<String, Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = pivotGenerator
											.generatePivot(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													PivotGenerator.AGE_TYPE_YOUNG,
													teamLevel, pvNumber);

								} else if (generateAsOld) {
									mappedObjects = pivotGenerator
											.generatePivot(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													PivotGenerator.AGE_TYPE_OLD,
													teamLevel, pvNumber);
								} else { // Generate as "middle" age
									mappedObjects = pivotGenerator
											.generatePivot(
													QualityGenerator.QUALITY_TYPE_VERY_GOOD,
													PivotGenerator.AGE_TYPE_MIDDLE,
													teamLevel, pvNumber);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects = pivotGenerator
											.generatePivot(
													QualityGenerator.QUALITY_TYPE_GOOD,
													PivotGenerator.AGE_TYPE_YOUNG,
													teamLevel, pvNumber);
								} else if (generateAsOld) {
									mappedObjects = pivotGenerator
											.generatePivot(
													QualityGenerator.QUALITY_TYPE_GOOD,
													PivotGenerator.AGE_TYPE_OLD,
													teamLevel, pvNumber);
								} else { // Generate as "middle" age
									mappedObjects = pivotGenerator
											.generatePivot(
													QualityGenerator.QUALITY_TYPE_GOOD,
													PivotGenerator.AGE_TYPE_MIDDLE,
													teamLevel, pvNumber);
								}
							}

							Player player = (Player) mappedObjects
									.get(PivotGenerator.MAP_PLAYER);
							Skill fieldPlayerSkills = (Skill) mappedObjects
									.get(PivotGenerator.MAP_PIVOT_SKILLS);

							// Persist the first generated pivot's formation as
							// first squad
							if (pv == 0) {
								player.setFormation(Player.FORMATION_FIRST_SQUAD);
								player.setFirst_squad_play_position("PV");
							}
							else {
								player.setFormation(Player.FORMATION_BENCH);
								player.setFirst_squad_play_position("");
							}

							try { // Try persist
								emgr.persist(player);
								emgr.flush();
								fieldPlayerSkills.setPlayer_id(player
										.getPlayer_id());
								emgr.persist(fieldPlayerSkills);
								emgr.flush();
								System.out
										.println("Persisted player & fieldPlayerSkills.");
								// Generate the contract and persist
								Contract contract = new Contract();
								contract.setTeam_id(teamId);
								contract.setExpired(false);
								contract.setPlayer_id(player.getPlayer_id());
								contract.setSeason_wage((int) (player
										.getMarket_value() * 0.22));
								contract.setYears_left(3);
								emgr.persist(contract);
								emgr.flush();
							} catch (Exception e) {
								System.out
										.println("Error when trying to persist Player OR fieldPlayerSkills. Exception = "
												+ e.getLocalizedMessage());
								e.printStackTrace();
							}

							countOfPlayersCreated++;
						} // END of for loop (generating 2 pivots)

					} catch (Exception e) {
						System.out
								.println("Error when persisting one of the 12 teams. Exception = "
										+ e.getLocalizedMessage());
						e.printStackTrace();

					}
				} // END of loop (for 12 teams)

				try {
					// Set the league as not locked
					league.setLocked(false);
					// Decrement the league available slots
					league.setAvailable_slots(11);
					emgr.persist(league);
					System.out.println("League persisted.");
					emgr.flush();
					try {
						// TODO Schedule the matches
						TypedQuery<Team> userPlayersQuery = emgr.createNamedQuery(
								"Team.getLeagueTeams", Team.class);
						userPlayersQuery.setParameter("league_id", league.getLeague_id());
						
						List<Team> teamList = userPlayersQuery.getResultList();
						System.out.println("TEAM LIST = " + teamList);
						ArrayList<String> teamListId = new ArrayList<String>(); 
						System.out.println("Returning team id = " + teamId);
						int userTeamId = 0;
						
						for (int n = 0; n < teamList.size(); n++) {
							teamListId.add(Integer.toString(teamList.get(n).getTeam_id()));
							if (teamList.get(n).getTeam_id()== teamId) {
								System.out.println("Team id match.");
								userTeamId = teamList.get(n).getTeam_id();
							} else {
								
							}
						}
						ScheduleGenerator sg = new ScheduleGenerator();
						Map<String, Map<String, Map<String, String>>> mappedMatchSchedule = sg.generateAllMatchesCombinationList(teamListId, userTeamId);
						
						for (int i = 0; i < mappedMatchSchedule.size(); i++) { // each day
							MatchDay matchDay = new MatchDay();
							matchDay.setDay_number(i+1);
							matchDay.setLeague_id(league.getLeague_id());
							emgr.persist(matchDay);
							emgr.flush();
							
							ArrayList<Integer> listOfAlreadyPersistedTeams = new ArrayList<Integer>(); // List to avoid the repetition caused by the schedule generator (i.e. it's architectural model) 
							
							// key = "day1"
							for (int j = 0; j < mappedMatchSchedule.get("day" + Integer.toString(i+1)).size(); j++) { // each team
								// Map of the match in the particular (currently iterated) day
								Map<String, String> matchMap = mappedMatchSchedule.get("day" + Integer.toString(i+1)).get(teamListId.get(j));
								int homeId = Integer.parseInt(matchMap.get("home"));
								int awayId = Integer.parseInt(matchMap.get("away"));
								
								if (!listOfAlreadyPersistedTeams.contains(homeId) || !listOfAlreadyPersistedTeams.contains(awayId)) {
									if ((listOfAlreadyPersistedTeams.contains(homeId) && !listOfAlreadyPersistedTeams.contains(awayId)) || (!listOfAlreadyPersistedTeams.contains(homeId) && listOfAlreadyPersistedTeams.contains(awayId))) {
										System.err.println("Houston we got a problem");
									}
									listOfAlreadyPersistedTeams.add(homeId);
									listOfAlreadyPersistedTeams.add(awayId);
									Match match = new Match();
									match.setMatch_day_id(matchDay.getMatch_day_id());
									match.setAway_team(awayId);
									match.setHome_team(homeId);
									match.setMatch_duration_in_seconds(DEFAULT_MATCH_DURATION_IN_SECONDS);
									match.setUpdates_per_minute(MatchRepositoryImpl.UPDATES_PER_MINUTE);
									
									for (int n = 0; n < teamList.size(); n++) {
										if (teamList.get(n).getTeam_id() == homeId)
											match.setHome_team_name(teamList.get(n).getTeam_name());
										else if (teamList.get(n).getTeam_id() == awayId)
											match.setAway_team_name(teamList.get(n).getTeam_name());
									}
									
									// Set the date
									GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
									gc.clear();
									gc.set(Calendar.YEAR, Integer.parseInt(matchMap.get("year")));
									gc.set(Calendar.MONTH, Integer.parseInt(matchMap.get("month")));
									gc.set(Calendar.DAY_OF_MONTH, Integer.parseInt(matchMap.get("day")));
									gc.set(Calendar.HOUR_OF_DAY, Integer.parseInt(matchMap.get("hour")));
									gc.set(Calendar.MINUTE, Integer.parseInt(matchMap.get("minute")));
									
									match.setMatch_date(new java.sql.Timestamp(gc.getTimeInMillis()));
									//java.sql.Date date = new java.sql.Date(gc.getTimeInMillis());
									
									// Persist the match
									emgr.persist(match);
									emgr.flush();
									
									// Schedule the jobs
							
									 try {
										 	Scheduler scheduler=new StdSchedulerFactory().getScheduler("MatchScheduler");
										 	System.out.println("Obtained scheduler: " + scheduler.getSchedulerName());
										    
										    final String jobName="SolrWatcher";
										    final String jobGroup="Solr";
										  /*  JobDetail existingJob=scheduler.getJobDetail(jobName);
										    if (existingJob != null) {
										      scheduler.deleteJob(jobName,jobGroup);
										    }
										    JobDetail job=new JobDetail(jobName,jobGroup,PrintJob.class);
										    JobDataMap jobDataMap=new JobDataMap();
										    jobDataMap.put("SOLR_TRACKER",this);
										    job.setJobDataMap(jobDataMap);
										    trigger=new CronTrigger("SolrWatcherTrigger",jobGroup,solrPingCronExpression);*/
										  
										    System.out.println("Match time: " + match.getMatch_date().toString());
										    
										    JobKey jobKey = new JobKey("job:" + Integer.toString(homeId) + ":" + Integer.toString(awayId), "schedule:league:" + Integer.toString(league_id));
										    JobDetail jobA = JobBuilder.newJob(MatchJob.class)
												.withIdentity(jobKey)
												.requestRecovery(true)
												.usingJobData("matchTime", match.getMatch_date().getTime())
												.usingJobData("matchId", match.getMatch_id())
												.build();
										    
										    Trigger trigger1 = TriggerBuilder
										    		.newTrigger()
										    		.withIdentity("trigger:" + Integer.toString(homeId) + ":" + Integer.toString(awayId), "group1")
										    		.startAt(gc.getTime())
										    		.forJob(jobA)
										    		.build();
										    
										    scheduler.scheduleJob(jobA,trigger1);
										  }
									 catch (  Exception e) {
										    System.err.println("Couldn't schedule a job. Exception: " + e.getLocalizedMessage());
									 }
									
								}
							}
							
							
						}
						
						
					} catch (Exception e) {
						System.out.println("Error when scheduling matches " + e.getLocalizedMessage());
					}
					
				} catch (Exception e) {
					System.out
							.println("Error when persisting (updating) league lock. Exception - "
									+ e.getLocalizedMessage());
					e.printStackTrace();
				}

			} catch (Exception e) {
				System.out.println("Error creating a new league.. "
						+ e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		
		return teamId;
	}

}
