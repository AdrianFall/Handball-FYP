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
		System.out.println("GET USER TEAM");
		List<Player> playerList = new ArrayList<Player>();
		TypedQuery<Player> userPlayersQuery = emgr.createNamedQuery(
				"Player.getUsersPlayers", Player.class);
		userPlayersQuery.setParameter("email", email);

		// An array list to hold the sorted players by formation
		ArrayList<Player> sortedPlayerList = new ArrayList<Player>();

		HashMap<String, Player> playerMap = new HashMap<String, Player>();

		try {
			playerList = userPlayersQuery.getResultList();
			System.out.println("PLAYER LIST = " + playerList);
			
			int countReserves = 0;
			int countBench = 0;
			
			// Sort the list of Player entities
			for (int i = 0; i < playerList.size(); i++) {
				Player tempPlayer = playerList.get(i);
				String tempFormation = tempPlayer.getFormation();
				System.out.println("tempFormation (" + (i) + ") = " + tempFormation);

				// Sorting rules:
				// The first 7 players in the array must be the first squad
				// players
				// where the first is GK, second LW, third RW,
				// fourth is CB, fifth is RB, sixth is LB
				// and seventh is PV
				// The following 7 players have to be a bench players
				// FIXME currently having no sorting order
				
				// The other players are reserves and
				// FIXME currently having no sorting order
				

				// START Sorting the player list
				if (tempFormation.equals(Player.FORMATION_FIRST_SQUAD)) {
					String tempCurrentFirstSquadPlayerPlayPosition = tempPlayer
							.getFirst_squad_play_position();

					if (tempCurrentFirstSquadPlayerPlayPosition.equals("GK")) {
						playerMap.put("GK", tempPlayer);
					} else if (tempCurrentFirstSquadPlayerPlayPosition
							.equals("LW")) {
						playerMap.put("LW", tempPlayer);
					} else if (tempCurrentFirstSquadPlayerPlayPosition
							.equals("RW")) {
						playerMap.put("RW", tempPlayer);
					} else if (tempCurrentFirstSquadPlayerPlayPosition
							.equals("CB")) {
						playerMap.put("CB", tempPlayer);
					} else if (tempCurrentFirstSquadPlayerPlayPosition
							.equals("RB")) {
						playerMap.put("RB", tempPlayer);
					} else if (tempCurrentFirstSquadPlayerPlayPosition
							.equals("LB")) {
						playerMap.put("LB", tempPlayer);
					} else if (tempCurrentFirstSquadPlayerPlayPosition
							.equals("PV")) {
						playerMap.put("PV", tempPlayer);
					} else {
						System.out.println("Houston, we got a problem");
					}

				} else if (tempFormation.equals(Player.FORMATION_BENCH)) {
					playerMap.put("BP" + (countBench+1), tempPlayer);
					countBench += 1;
					
				} else if (tempFormation.equals(Player.FORMATION_RESERVES)) {
					playerMap.put("RP" + (countReserves+1), tempPlayer);
					countReserves += 1;
					
				}

				// END Sorting the player list
			} // END Loop for player entities
			System.out.println("Player map size = " + playerMap.size());
			System.out.println("Player map = " + playerMap);
			// Obtain the mapped players and insert into sortedPlayerList
			for (int i = 0; i < playerMap.size(); i++) {
				
				System.out.println("MAP ITERATION = " + (i+1));
				
				
				if (i >= 0 && i < 7) {
					int d = new Integer(i);
					switch (d) {
					case 0:
						sortedPlayerList.add(playerMap.get("GK"));
						break;
					case 1: 
						sortedPlayerList.add(playerMap.get("LW"));
						break;
					case 2:
						sortedPlayerList.add(playerMap.get("RW"));
						break;
					case 3: 
						sortedPlayerList.add(playerMap.get("CB"));
						break;
					case 4: 
						sortedPlayerList.add(playerMap.get("RB"));
						break;
					case 5:
						sortedPlayerList.add(playerMap.get("LB"));
						break;
					case 6:
						sortedPlayerList.add(playerMap.get("PV"));
						break;
						
					}
				}
				else if (i > 6 && i < 14) {
					System.out.println("BENCH PLAYER!!!!!!  " + playerMap.get("BP" + (i-6)));
					sortedPlayerList.add(playerMap.get("BP" + (i-6)));
				
				} else if (i > 13) {
					System.out.println("RESERVE PLAYER!!!!!");
					System.out.println("RP .... " + playerMap.get("RP" + (i-13)));
					sortedPlayerList.add(playerMap.get("RP" + (i-13)));
				}
				
			} // END loop for player map

			System.out.println("player list size = " + playerList.size());
		} catch (Exception e) {
			System.out.println("Error when obtaining player list. Exception - "
					+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		System.out.println("Sorted player list = " + sortedPlayerList);
		return sortedPlayerList;
	}

	@Override
	public Map<String, Skill> getAllPlayersSkills(List<Player> listOfPlayers) {
		Map<String, Skill> playersSkillsMap = new HashMap<String, Skill>();

		for (int i = 0; i < listOfPlayers.size(); i++) {
			int tempPlayerId = listOfPlayers.get(i).getPlayer_id();
			TypedQuery<Skill> playerSkillsQuery = emgr.createNamedQuery(
					"Skill.getPlayerSkills", Skill.class);
			playerSkillsQuery.setParameter("player_id", tempPlayerId);
			Skill skill = playerSkillsQuery.getSingleResult();
			playersSkillsMap.put(Integer.toString(tempPlayerId), skill);
		}

		return playersSkillsMap;
	}

	@Override
	public Skill getPlayerSkills(int playerId) {
		return emgr.createNamedQuery("Skill.getPlayerSkills", Skill.class)
				.setParameter("player_id", playerId).getSingleResult();
	}

	@Override
	public Player getPlayer(int playerId) {
		
		return emgr.find(Player.class, playerId);
	}

}
