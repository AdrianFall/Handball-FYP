package af.handball.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import af.handball.entity.Player;

public interface GameRepository
{
	
	List<Player> getUserTeam(String email);
	

}
