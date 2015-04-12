package af.handball.repository;

import java.util.ArrayList;

import af.handball.entity.Team;

public interface TeamRepository
{
	
	boolean newTeam(String email, String teamName);
	
	boolean assignTeam(int teamId, String email, String teamName);
	
	boolean teamExists(String email);

	String getTeamName(String email);
	
	Team getTeam(String email);

	int getTeamLevel(String email);
	
	int getTeamId(String email);
	
	int getTeamLeagueId(String email);

	boolean changeSquad(ArrayList<Integer> playerIdList, ArrayList<Integer> captainsIdList, String email);
	
	

}
