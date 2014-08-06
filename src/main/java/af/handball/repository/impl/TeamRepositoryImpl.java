package af.handball.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.exceptions.TransactionException;
import org.eclipse.persistence.internal.libraries.antlr.runtime.EarlyExitException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import af.handball.entity.Player;
import af.handball.entity.Team;
import af.handball.repository.TeamRepository;

@Component("TeamRepository")
@Repository
public class TeamRepositoryImpl implements TeamRepository {

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
				System.out.println("Exception when persisting team: "
						+ teamName + " of user " + email + " .. Exception = "
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

		TypedQuery<Team> teamExistsQuery = emgr.createNamedQuery(
				"Team.getTeamByEmail", Team.class);
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

	@Override
	public String getTeam(String email) {

		String teamName = "default";

		TypedQuery<Team> teamQuery = emgr.createNamedQuery(
				"Team.getTeamByEmail", Team.class);
		teamQuery.setParameter("email", email);
		try {
			Team team = (Team) teamQuery.getSingleResult();

			if (team != null) {
				System.out.println("Team exists for user " + email);
				teamName = team.getTeam_name();
			}

		} catch (NoResultException nre) {
			// User doesn't have a team yet.
			teamName = "error";
			System.out.println("Team does not exist for user " + email);
		}
		return teamName;
	}

	@Override
	public boolean assignTeam(int teamId, String email, String teamName) {
		boolean assigned = false;
		try {
			Team team = emgr.find(Team.class, teamId);
			team.setEmail(email);
			team.setTeam_name(teamName);
			emgr.persist(team);
			emgr.flush();
			assigned = true;
		} catch (Exception e) {
			System.out.println("Couldn't find/persist a team with id: "
					+ teamId + ". Exception - " + e.getLocalizedMessage());
			e.printStackTrace();
		}

		return assigned;
	}

	@Override
	public int getTeamLevel(String email) throws NoResultException {
		TypedQuery<Team> teamQuery = emgr.createNamedQuery(
				"Team.getTeamByEmail", Team.class);
		teamQuery.setParameter("email", email);

		Team team = teamQuery.getSingleResult();
		int teamLevel = team.getTeam_level();
		return teamLevel;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean changeSquad(ArrayList<Integer> playerIdList, String email) {
		boolean squadChanged = false;
		
		// Obtain the current list of players from the persistent storage
		TypedQuery<Player> playersQuery = emgr.createNamedQuery("Player.getUsersPlayers", Player.class);
		playersQuery.setParameter("email", email);
		
		
		
		List<Player> currentPlayerList = playersQuery.getResultList();
		
		// Make a copy of the list obtained as method parameter
		ArrayList<Integer> playerIdListCopy = new ArrayList<Integer>(playerIdList);
		
		// Make a copy of the list obtained from persistent storage
		ArrayList<Player> currentPlayerListCopy = new ArrayList<Player>(currentPlayerList);
		System.out.println("current player list copy = " + currentPlayerListCopy);
		
		
		
		// Iterate through each player from the currentPlayerList
		for (int i = 0; i < currentPlayerListCopy.size(); i++) {
			// On each iteration check if the playerIdList contains currently iterated 
			// Player's id, if so then eliminate it from both lists
			int playerId = currentPlayerListCopy.get(i).getPlayer_id();
			
			if (playerIdListCopy.contains(playerId)) {
				currentPlayerListCopy.remove(i);
				playerIdListCopy.remove(playerIdListCopy.indexOf(playerId));
				i--;
			}
			
		}
		// IF copy of current player list AND playerId list is empty, proceed
		if (currentPlayerListCopy.isEmpty() && playerIdListCopy.isEmpty()) {
			System.out.println("currentPlayerListCopy && playerIdListCopy = empty");
			
			// Make the squad changes
			
			// The first 7 players in the array are first squad players
			// where the first is GK, second LW, third RW,
			// fourth is CB, fifth is RB, sixth is LB
			// and seventh is PV
			
			
			
			for (int j = 0; j < currentPlayerList.size(); j++) {
				
				Player tempPlayer = currentPlayerList.get(j);
				int tempPlayerId = tempPlayer.getPlayer_id();
				
				
				// LOOP for all players from the client side list
				for (int n = 0; n < playerIdList.size(); n++) {
					if (playerIdList.get(n) == tempPlayerId) {
						switch (n) {
						case 0: 
							// GK
							tempPlayer.setFirst_squad_play_position("GK");
							tempPlayer.setFormation(Player.FORMATION_FIRST_SQUAD);
							
							break;
						case 1:
							// LW
							tempPlayer.setFirst_squad_play_position("LW");
							tempPlayer.setFormation(Player.FORMATION_FIRST_SQUAD);
							break;
						case 2: 
							// RW
							tempPlayer.setFirst_squad_play_position("RW");
							tempPlayer.setFormation(Player.FORMATION_FIRST_SQUAD);
							break;
						case 3: 
							// CB
							tempPlayer.setFirst_squad_play_position("CB");
							tempPlayer.setFormation(Player.FORMATION_FIRST_SQUAD);
							break;
						case 4:
							// RB
							tempPlayer.setFirst_squad_play_position("RB");
							tempPlayer.setFormation(Player.FORMATION_FIRST_SQUAD);
							break;
						case 5: 
							// LB
							tempPlayer.setFirst_squad_play_position("LB");
							tempPlayer.setFormation(Player.FORMATION_FIRST_SQUAD);
							break;
						case 6: 
							// PV
							tempPlayer.setFirst_squad_play_position("PV");
							tempPlayer.setFormation(Player.FORMATION_FIRST_SQUAD);
							break;
					
							
						} // END switch (n)
						if (n > 6 && n <= 13) {
							//  Bench
							tempPlayer.setFirst_squad_play_position("");
							tempPlayer.setFormation(Player.FORMATION_BENCH);
						} else if (n > 13) {
							// RESERVES
							tempPlayer.setFirst_squad_play_position("");
							tempPlayer.setFormation(Player.FORMATION_RESERVES);
						}
					} 
				} // END loop for all players from the client side list 
				
				emgr.persist(tempPlayer);
				emgr.flush();
				if (j == 13) {
					System.out.println("j13, throwing exception");
					
						
					
				}
			} // END loop for the player from the server side list
			
			
			
		} else {
			System.out.println("Seems like somebody messed up with the values.");
		}
		
		
		
		
		

		return squadChanged;
	}

}
