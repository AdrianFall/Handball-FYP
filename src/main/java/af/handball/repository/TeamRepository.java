package af.handball.repository;

import java.util.ArrayList;

import af.handball.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TeamRepository
{
	
	boolean newTeam(String email, String teamName);
	
	boolean assignTeam(int teamId, String email, String teamName);
	
	boolean teamExists(String email);

	String getTeam(String email);

	int getTeamLevel(String email);
	
	int getTeamId(String email);
	
	int getTeamLeagueId(String email);

	boolean changeSquad(ArrayList<Integer> playerIdList, ArrayList<Integer> captainsIdList, String email);
	
	

}
