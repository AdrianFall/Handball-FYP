package af.handball.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.Contract;
import af.handball.entity.League;
import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.entity.Team;
import af.handball.generator.BackGenerator;
import af.handball.generator.GkGenerator;
import af.handball.generator.PivotGenerator;
import af.handball.generator.PlayerNumberGenerator;
import af.handball.generator.QualityGenerator;
import af.handball.generator.RandomTeamNameGenerator;
import af.handball.generator.WingGenerator;
import af.handball.repository.LeagueRepository;

@Component("LeagueRepository")
@Repository
public class LeagueRepositoryImpl implements LeagueRepository {

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

		} catch (NoResultException nre) {
			System.out
					.println("No League available was found.. Generating new league with level: "
							+ teamLevel);
			// generate a new league, teams and players..
			League league = new League();
			league.setAvailable_slots(12);
			league.setLeague_level(teamLevel);
			league.setLocked(true);

			try {
				emgr.persist(league);
				emgr.flush();
				league_id = league.getLeague_id();
				System.out.println("New league persisted with id " + league_id);
				// Create 12 new bot teams
				RandomTeamNameGenerator randomTeamNameGenerator = new RandomTeamNameGenerator(
						teamLevel);
				String[] teamNames = randomTeamNameGenerator.generateTeams();
				int moneyMin = 2000000;
				int moneyMax = 3000000;
				int fansMin = 100;
				int fansMax = 160;
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
						System.out.println("Persisted team number " + (i + 1)
								+ ". Now generating the players.");
						// generate the players for the team (INCLUDES 3
						// year contract generation for each player
						// and season wage depending on the generated players:
						// age, skills, etc.

						// 2 out of 14 players will be randomly generated with a
						// "very good" quality (i.e. good quality) and the other
						// 12
						// players will be generated with "good" quality
						// P.S. actually it can happen that only 1 player will
						// be "very good" due to the randomness of Random class
						int firstVeryGoodPlayer = (randomGenerator.nextInt(12));
						int secondVeryGoodPlayer = (randomGenerator.nextInt(12));
						// 2 out of 14 players will be randomly generated as
						// very young
						int firstYoungPlayer = randomGenerator.nextInt(12);
						int secondYoungPlayer = randomGenerator.nextInt(12);
						// 2 out of 14 players will be randomly generated as
						// "old".
						int firstOldPlayer = randomGenerator.nextInt(12);
						int secondOldPlayer = randomGenerator.nextInt(12);

						int countOfPlayersCreated = 0;

						boolean generateAsVeryGood = (firstVeryGoodPlayer == countOfPlayersCreated || secondVeryGoodPlayer == countOfPlayersCreated);
						boolean generateAsYoung = (countOfPlayersCreated == firstYoungPlayer || countOfPlayersCreated == secondYoungPlayer);
						boolean generateAsOld = (countOfPlayersCreated == firstOldPlayer || countOfPlayersCreated == secondOldPlayer);

						Map<String, Object> playerNumberMap = PlayerNumberGenerator
								.generateRandomNumbers();
						System.out.println("Player Number Map = "
								+ playerNumberMap);
						// GENERATE 2 goal keepers
						GkGenerator gkGenerator = new GkGenerator(teamLevel);
						for (int j = 0; j < 2; j++) {
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
