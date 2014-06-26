package af.handball.controller;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import af.handball.entity.User;


@Controller
public class IndexController {
	
	@PersistenceContext(name = "handball-game")
	private EntityManager emgr;
	
	@RequestMapping("/main")
	public String index() {

		return "main";
		// return "/WEB-INF/jsp/index.jsp";
	}

	@RequestMapping("/dbtest")
	public String dbtest() {
		System.out.println("Hello from dbtest");
		
		User user = emgr.find(User.class, new BigDecimal(1));
		
		System.out.println("User's email = " + user.getEmail());
		
		return "main";
	}

}
