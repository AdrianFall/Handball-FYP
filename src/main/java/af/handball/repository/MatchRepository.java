package af.handball.repository;

import java.util.Map;

import af.handball.entity.MatchOutcome;




public interface MatchRepository
{
	Map<String, Object> initializeMatch(int matchId);
	boolean generateMatchOutcome();
	boolean updateMatch();

	
	

}
