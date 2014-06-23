package af.handball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping("/main")
	public String index() {
		
		return "main";
		//return "/WEB-INF/jsp/index.jsp";
	}
	

}
