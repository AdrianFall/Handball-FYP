package af.handball.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import af.handball.entity.Contract;
import af.handball.entity.Match;
import af.handball.entity.MatchHighlight;
import af.handball.entity.Player;
import af.handball.entity.Skill;

public interface GameRepository
{
	
	List<Player> getUserTeam(String email);

	List<Skill> getAllPlayersSkills(List<Player> listOfPlayers);
	
	Map<String, Object> getUserSchedule(int teamId, int leagueId);

	Skill getPlayerSkills(int playerId);

	Player getPlayer(int playerId);

	Contract getPlayerContract(int playerId);

	Map<String, String> getCaptainsMap(String email);

	List<Player> getTeamPlayers(int teamId);

	boolean setMatchIsPlayed(boolean isPlayed, int matchId);

	Match getMatchById(int matchId);

	List<MatchHighlight> getMatchHighlights(int matchId);

	void deleteMatchHighlights(int matchId);

	

}
