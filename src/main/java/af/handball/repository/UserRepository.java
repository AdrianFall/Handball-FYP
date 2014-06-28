package af.handball.repository;

import af.handball.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository /*extends JpaRepository<User, Integer>*/
{
	
	boolean newUser(String email, String password);
	
	boolean emailExists(String email);
	
	

}
