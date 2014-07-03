package af.handball.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import af.handball.entity.User;
import af.handball.repository.UserRepository;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	private UserRepository userRepository;
	
	/*public List<User> findAll() {
		return userRepository.findAll();
	}*/
	
	public boolean emailExists(final String email) {
		boolean emailExists = userRepository.emailExists(email);
		
		return emailExists;
	}
	
	public boolean authenticateUser(final String email, final String password) {
		boolean userAuthenticated = false;
		
		userAuthenticated = userRepository.authenticateUser(email, password);
		
		if (userAuthenticated) System.out.println("User ( " + email + ") authenticated");
		else System.out.println("User ( " + email + ") not authenticated.");
		
		return userAuthenticated;
	}
}
