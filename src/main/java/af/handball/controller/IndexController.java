package af.handball.controller;

import java.io.PrintWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import af.handball.service.RegistrationService;

@Controller
public class IndexController {
	

	

	@RequestMapping("/main")
	public String index() {

		return "main";
	}

	

}
