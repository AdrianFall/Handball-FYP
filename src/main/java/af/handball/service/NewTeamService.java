package af.handball.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af.handball.entity.User;
import af.handball.repository.NewTeamRepository;
import af.handball.repository.UserRepository;

@Service
@Transactional
public class NewTeamService {
	
	@Autowired
	private NewTeamRepository newTeamRepository;
	
	/*public List<User> findAll() {
		return userRepository.findAll();
	}*/
	
	
	
	public boolean newTeam(final String email, final String team_name) {
		boolean teamCreated = newTeamRepository.newTeam(email, team_name);
		
		return teamCreated;
	}
	
	public boolean hasTeam(final String email) {
		
		boolean hasTeam = newTeamRepository.teamExists(email);
		
		return hasTeam;
	}
}
