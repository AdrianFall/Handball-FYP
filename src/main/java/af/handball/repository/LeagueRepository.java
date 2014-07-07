package af.handball.repository;


public interface LeagueRepository
{

	boolean allocateTeamInLeague(String email, String teamName, int teamLevel);
	
	
	

}
