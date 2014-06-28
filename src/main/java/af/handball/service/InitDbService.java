/*package af.handball.service;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af.handball.entity.User;
import af.handball.repository.UserRepository;

@Transactional
@Service
public class InitDbService {

	@PersistenceContext
	@Autowired
	private UserRepository userRepos;
	
	
	@PostConstruct
	public void init() {
		System.out.println("HELLO FROM INIT DB SERVICE");
		User user = new User();
		user.setEmail("example@ex.com");
		user.setId(new BigDecimal(100));
		
		userRepos.save(user);
		
		
		
	}
}
*/