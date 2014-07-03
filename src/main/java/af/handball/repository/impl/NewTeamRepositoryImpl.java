package af.handball.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.Team;
import af.handball.repository.NewTeamRepository;

@Component("NewTeamRepository")
@Repository
public class NewTeamRepositoryImpl implements NewTeamRepository {

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
				System.out.println("Exception when persisting team: " + teamName
						+ " of user " + email + " .. Exception = "
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

		TypedQuery teamExistsQuery = emgr.createNamedQuery(
				"Team.userTeamExists", Team.class);
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

}
