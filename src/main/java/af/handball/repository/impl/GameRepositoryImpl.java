package af.handball.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.Player;
import af.handball.entity.Skill;
import af.handball.repository.GameRepository;

@Component("GameRepository")
@Repository
public class GameRepositoryImpl implements GameRepository {

	@PersistenceContext
	EntityManager emgr;

	@Override
	public List<Player> getUserTeam(String email) {
		List<Player> playerList = new ArrayList<Player>();
		TypedQuery<Player> userPlayersQuery = emgr.createNamedQuery("Player.getUsersPlayers", Player.class);
		userPlayersQuery.setParameter("email", email);
		
		try {
		playerList = userPlayersQuery.getResultList();
		System.out.println("player list size = " + playerList.size());
		} catch (Exception e) {
			System.out.println("Error when obtaining player list. Exception - " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		
		return playerList;
	}

	@Override
	public Map<String, Skill> getAllPlayersSkills(List<Player> listOfPlayers) {
		Map<String, Skill> playersSkillsMap = new HashMap<String, Skill>();
		
		for (int i = 0; i < listOfPlayers.size(); i++) {
			int tempPlayerId = listOfPlayers.get(i).getPlayer_id();
			TypedQuery<Skill> playerSkillsQuery = emgr.createNamedQuery("Skill.getPlayerSkills", Skill.class);
			playerSkillsQuery.setParameter("player_id", tempPlayerId);
			Skill skill = playerSkillsQuery.getSingleResult();
			playersSkillsMap.put(Integer.toString(tempPlayerId), skill);
		}
		
		return playersSkillsMap;
	}

	@Override
	public Skill getPlayerSkills(int playerId) {
		return emgr.createNamedQuery("Skill.getPlayerSkills", Skill.class).setParameter("player_id", playerId).getSingleResult();
	}

	

}
