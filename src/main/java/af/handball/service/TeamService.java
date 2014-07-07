package af.handball.service;

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
	
	
	
	public boolean newTeam(final String email, final String team_name) {
		/*boolean teamCreated = teamRepository.newTeam(email, team_name);*/
		
		// FIXME At the moment teamCreated will only fetch false;
		boolean teamCreated = leagueRepository.allocateTeamInLeague(email, team_name, 1);
		
		return teamCreated;
	}
	
	public boolean hasTeam(final String email) {
		
		boolean hasTeam = teamRepository.teamExists(email);
		
		return hasTeam;
	}
	
	public String getTeamName(final String email) {
		
		String teamName = teamRepository.getTeam(email);
		return teamName;
		
	}
}
