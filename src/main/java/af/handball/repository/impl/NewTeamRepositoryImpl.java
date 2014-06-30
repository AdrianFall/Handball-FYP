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
		Team team = new Team();
		team.setEmail(email);
		team.setTeam_name(teamName);
		emgr.persist(team);
		System.out.println("Persisted new team (" + teamName + ").");
		return false;
	}

	@Override
	public boolean teamExists(String email) {
		boolean hasTeam = false;
		
		TypedQuery teamExistsQuery = emgr.createNamedQuery("Team.userTeamExists", Team.class);
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
