package af.handball.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import af.handball.entity.Player;
import af.handball.service.GameService;


@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;
	

	@RequestMapping("/squad")
	public String squad(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		List<Player> playerList = gameService.getUsersPlayers(email);
		Map<String, String> captainsMap = gameService.getCaptainsMap(email);
		request.setAttribute("captainsMap", captainsMap);
		request.setAttribute("playerList", playerList);
		/*Map<String, Skill> allPlayersSkills = gameService.getPlayersSkills(playerList);
		request.setAttribute("allPlayersSkills", allPlayersSkills);*/
		return "squad";
	}

	

}
