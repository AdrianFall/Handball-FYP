/*package af.handball.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af.handball.entity.User;
import af.handball.repository.UserRepository;

@Service
public class InitDbService {

	@Autowired
	private UserRepository userRepos;
	
	@PostConstruct
	public void init() {
		User user = new User();
		user.setEmail("example@ex.com");
		user.setPassword("passExample");
		
		userRepos.save(user);
	}
}
*/