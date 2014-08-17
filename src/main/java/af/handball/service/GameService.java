package af.handball.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af.handball.entity.Contract;
import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.repository.GameRepository;

@Service
@Transactional
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	public List<Player> getUsersPlayers(String email) {
		return gameRepository.getUserTeam(email);
	}
	
	public Map<String, Skill> getAllPlayersSkills(List<Player> listOfPlayers) {
		
		return gameRepository.getAllPlayersSkills(listOfPlayers);
	}

	public Skill getPlayerSkills(int playerId) {
		return gameRepository.getPlayerSkills(playerId);
	}
	
	public Player getPlayer(int playerId) {
		return gameRepository.getPlayer(playerId);
	}

	public Contract getPlayerContract(int playerId) {
		return gameRepository.getPlayerContract(playerId);
	}

	public Map<String, String> getCaptainsMap(String email) {
		
		return gameRepository.getCaptainsMap(email);
	}
	
	
	
	
	/*public List<User> findAll() {
		return userRepository.findAll();
	}*/
	
	
	
	/*public int preGenerateTeam(final String email, final String team_name) {
		boolean teamCreated = teamRepository.newTeam(email, team_name);
		
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
		
	}*/
}
