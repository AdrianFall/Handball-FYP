package af.handball.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.Player;
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

	

}
