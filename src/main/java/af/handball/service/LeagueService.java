package af.handball.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af.handball.entity.User;
import af.handball.repository.LeagueRepository;
import af.handball.repository.TeamRepository;
import af.handball.repository.UserRepository;

@Service
@Transactional
public class LeagueService {
	
	@Autowired
	private LeagueRepository leagueRepository;

	public int allocateTeamInLeague(String email, String teamName, int teamLevel) {
		// TODO Auto-generated method stub
		int teamId = -1;
		teamId = leagueRepository.allocateTeamInLeague(email, teamName, teamLevel);		
		return teamId;
	}
	
	
	/*public List<User> findAll() {
		return userRepository.findAll();
	}*/
	
	
	
}
