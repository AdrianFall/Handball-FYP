package af.handball.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.Team;
import af.handball.repository.TeamRepository;

@Component("TeamRepository")
@Repository
public class TeamRepositoryImpl implements TeamRepository {

	@PersistenceContext
	EntityManager emgr;

	@Override
	public boolean newTeam(String email, String teamName) {
		boolean teamCreated = false;
		Team team = new Team();
		team.setEmail(email);
		team.setTeam_name(teamName);
		team.setTeam_level(new Integer(1));
		if (!teamExists(email)) {
			try {
				emgr.persist(team);
				teamCreated = true;
			} catch (Exception e) {
				System.out.println("Exception when persisting team: "
						+ teamName + " of user " + email + " .. Exception = "
						+ e.getLocalizedMessage());
				e.printStackTrace();
			}
			System.out.println("Persisted new team (" + teamName + ").");
		}

		return teamCreated;
	}

	@Override
	public boolean teamExists(String email) {
		boolean hasTeam = false;

		TypedQuery<Team> teamExistsQuery = emgr.createNamedQuery(
				"Team.getTeamByEmail", Team.class);
		teamExistsQuery.setParameter("email", email);
		try {
			Team team = (Team) teamExistsQuery.getSingleResult();

			if (team != null) {
				System.out.println("Team exists for user " + email);
				hasTeam = true;
			}

		} catch (NoResultException nre) {
			// User doesn't have a team yet.
			System.out.println("Team does not exist for user " + email);
		}

		return hasTeam;
	}

	@Override
	public String getTeam(String email) {

		String teamName = "default";

		TypedQuery<Team> teamQuery = emgr.createNamedQuery(
				"Team.getTeamByEmail", Team.class);
		teamQuery.setParameter("email", email);
		try {
			Team team = (Team) teamQuery.getSingleResult();

			if (team != null) {
				System.out.println("Team exists for user " + email);
				teamName = team.getTeam_name();
			}

		} catch (NoResultException nre) {
			// User doesn't have a team yet.
			teamName = "error";
			System.out.println("Team does not exist for user " + email);
		}
		return teamName;
	}

	@Override
	public boolean assignTeam(int teamId, String email, String teamName) {
		boolean assigned = false;
		try {
			Team team = emgr.find(Team.class, teamId);
			team.setEmail(email);
			team.setTeam_name(teamName);
			emgr.persist(team);
			emgr.flush();
			assigned = true;
		} catch (Exception e) {
			System.out.println("Couldn't find/persist a team with id: "
					+ teamId + ". Exception - " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		return assigned;
	}

	@Override
	public int getTeamLevel(String email) {
		TypedQuery<Team> teamQuery = emgr.createNamedQuery(
				"Team.getTeamByEmail", Team.class);
		teamQuery.setParameter("email", email);
		
		Team team = teamQuery.getSingleResult();
		int teamLevel = team.getTeam_level();
		return teamLevel;
	}

}
