package af.handball.repository;

import java.util.List;
import java.util.Map;

import af.handball.entity.Contract;
import af.handball.entity.Player;
import af.handball.entity.Skill;

public interface GameRepository
{
	
	List<Player> getUserTeam(String email);

	Map<String, Skill> getAllPlayersSkills(List<Player> listOfPlayers);

	Skill getPlayerSkills(int playerId);

	Player getPlayer(int playerId);

	Contract getPlayerContract(int playerId);
	

}
