package af.handball.service;

import java.util.ArrayList;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af.handball.repository.LeagueRepository;
import af.handball.repository.TeamRepository;

@Service
@Transactional
public class TeamService {
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired LeagueRepository leagueRepository;
	
	/*public List<User> findAll() {
		return userRepository.findAll();
	}*/
	
	
	
	public int preGenerateTeam(final String email, final String team_name) {
		/*boolean teamCreated = teamRepository.newTeam(email, team_name);*/
		
		int teamId = -1;
		teamId = leagueRepository.allocateTeamInLeague(email, team_name, 1);
		
		return teamId;
	}
	
	public boolean assignTeam(final int teamId, final String email, final String team_name) {
		boolean assigned = false;
		
		assigned = teamRepository.assignTeam(teamId, email, team_name);
		
		return assigned;
	}
	
	public boolean hasTeam(final String email) {
		
		boolean hasTeam = teamRepository.teamExists(email);
		
		return hasTeam;
	}
	
	public String getTeamName(final String email) {
		
		String teamName = teamRepository.getTeam(email);
		return teamName;
	}
	
	public int getTeamLevel(final String email) throws NoResultException {
		return teamRepository.getTeamLevel(email);
	}

	public boolean changeSquad(ArrayList<Integer> playerIdList, String email) {
		return teamRepository.changeSquad(playerIdList, email);
	}
}
