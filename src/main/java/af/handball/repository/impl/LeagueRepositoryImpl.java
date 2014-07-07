package af.handball.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.FieldPlayerSkills;
import af.handball.entity.GkSkills;
import af.handball.entity.League;
import af.handball.entity.Player;
import af.handball.entity.Team;
import af.handball.generator.QualityGenerator;
import af.handball.generator.GkGenerator;
import af.handball.generator.RWGenerator;
import af.handball.generator.RandomTeamNameGenerator;
import af.handball.repository.LeagueRepository;

@Component("LeagueRepository")
@Repository
public class LeagueRepositoryImpl implements LeagueRepository {

	private int league_id;

	@PersistenceContext
	EntityManager emgr;

	@Override
	public boolean allocateTeamInLeague(String email, String teamName,
			int teamLevel) {
		boolean allocated = false;

		// Query to check for a league with available slots and on the same
		// teamLevel

		TypedQuery<League> leagueQuery = emgr.createNamedQuery(
				"League.getAvailableLeagueByLevel", League.class);
		leagueQuery.setParameter("league_level", teamLevel);

		try {
			League league = leagueQuery.getSingleResult();
			System.out.println("Obtained an available league.. ");
		} catch (NoResultException nre) {
			System.out
					.println("No League available was found.. Generating new league with level: "
							+ teamLevel);
			// TODO generate a new league, teams and players..
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
						System.out.println("Persisted team number " + (i + 1)
								+ ". Now generating the players.");
						// TODO generate the players for the team (INCLUDES 3
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

						// GENERATE 2 goal keepers
						GkGenerator gkGenerator = new GkGenerator(
								teamLevel);
						for (int j = 0; j < 2; j++) {
							Map<String,Object> mappedObjects = new HashMap<String,Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = gkGenerator.generateGoalKeeper(
											QualityGenerator.QUALITY_TYPE_VERY_GOOD, GkGenerator.AGE_TYPE_YOUNG, teamLevel);
									
									
								} else if (generateAsOld) {
									mappedObjects = gkGenerator.generateGoalKeeper(
											QualityGenerator.QUALITY_TYPE_VERY_GOOD, GkGenerator.AGE_TYPE_OLD, teamLevel);
								} else { // Generate as "middle" age
									mappedObjects =  gkGenerator.generateGoalKeeper(
											QualityGenerator.QUALITY_TYPE_VERY_GOOD, GkGenerator.AGE_TYPE_MIDDLE, teamLevel);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects =  gkGenerator.generateGoalKeeper(
											QualityGenerator.QUALITY_TYPE_GOOD, GkGenerator.AGE_TYPE_YOUNG, teamLevel);
								} else if (generateAsOld) {
									mappedObjects =  gkGenerator.generateGoalKeeper(
											QualityGenerator.QUALITY_TYPE_GOOD, GkGenerator.AGE_TYPE_OLD, teamLevel);
								} else { // Generate as "middle" age
									mappedObjects =  gkGenerator.generateGoalKeeper(
											QualityGenerator.QUALITY_TYPE_GOOD, GkGenerator.AGE_TYPE_MIDDLE, teamLevel);
								}
							}
							
							Player player = (Player) mappedObjects.get(GkGenerator.MAP_PLAYER);
							GkSkills gkSkills = (GkSkills) mappedObjects.get(GkGenerator.MAP_GK_SKILLS);
							
							try { // Try persist 
								emgr.persist(player);
								emgr.flush();
								gkSkills.setPlayer_id(player.getPlayer_id());
								emgr.persist(gkSkills);
								emgr.flush();
								System.out.println("Persisted player & gkSkills.");
							} catch (Exception e) {
								System.out.println("Error when trying to persist Player OR gkSills. Exception = " + e.getLocalizedMessage());
								e.printStackTrace();
							}
							
							countOfPlayersCreated++;
						} // END of for loop (generating 2 goal keepers)

						// Generate 2 right wing
						RWGenerator rwGenerator = new RWGenerator(teamLevel);
						for (int rw = 0; rw < 2; rw++) {
							Map<String,Object> mappedObjects = new HashMap<String,Object>();
							if (generateAsVeryGood) {
								if (generateAsYoung) {
									mappedObjects = rwGenerator.generateRightWing(
											QualityGenerator.QUALITY_TYPE_VERY_GOOD, GkGenerator.AGE_TYPE_YOUNG, teamLevel);
									
									
								} else if (generateAsOld) {
									mappedObjects = rwGenerator.generateRightWing(
											QualityGenerator.QUALITY_TYPE_VERY_GOOD, GkGenerator.AGE_TYPE_OLD, teamLevel);
								} else { // Generate as "middle" age
									mappedObjects =  rwGenerator.generateRightWing(
											QualityGenerator.QUALITY_TYPE_VERY_GOOD, GkGenerator.AGE_TYPE_MIDDLE, teamLevel);
								}
							} else { // Generate as "good"
								if (generateAsYoung) {
									mappedObjects =  rwGenerator.generateRightWing(
											QualityGenerator.QUALITY_TYPE_GOOD, GkGenerator.AGE_TYPE_YOUNG, teamLevel);
								} else if (generateAsOld) {
									mappedObjects =  rwGenerator.generateRightWing(
											QualityGenerator.QUALITY_TYPE_GOOD, GkGenerator.AGE_TYPE_OLD, teamLevel);
								} else { // Generate as "middle" age
									mappedObjects =  rwGenerator.generateRightWing(
											QualityGenerator.QUALITY_TYPE_GOOD, GkGenerator.AGE_TYPE_MIDDLE, teamLevel);
								}
							}
							
							Player player = (Player) mappedObjects.get(RWGenerator.MAP_PLAYER);
							FieldPlayerSkills fieldPlayerSkills = (FieldPlayerSkills) mappedObjects.get(RWGenerator.MAP_RW_SKILLS);
							
							try { // Try persist 
								emgr.persist(player);
								emgr.flush();
								fieldPlayerSkills.setPlayer_id(player.getPlayer_id());
								emgr.persist(fieldPlayerSkills);
								emgr.flush();
								System.out.println("Persisted player & fieldPlayerSkills.");
							} catch (Exception e) {
								System.out.println("Error when trying to persist Player OR fieldPlayerSkills. Exception = " + e.getLocalizedMessage());
								e.printStackTrace();
							}
							
							countOfPlayersCreated++;
						} // END of for loop (generating 2 right wings)
						
						// Generate 2 left wing

						// Generate 2 playmakers (AKA. CB (Central Back) or PV
						// (Pivot))

						// Generate 3 right backs

						// Generate 3 left backs

					} catch (Exception e) {
						System.out
								.println("Error when persisting one of the 12 teams.");
					}
				}

			} catch (Exception e) {
				System.out.println("Error creating a new league.. "
						+ e.getLocalizedMessage());
				e.printStackTrace();
			}
		}

		return allocated;
	}

}
