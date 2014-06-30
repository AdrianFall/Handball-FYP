package af.handball.repository;

import af.handball.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NewTeamRepository /*extends JpaRepository<User, Integer>*/
{
	
	boolean newTeam(String email, String teamName);
	
	boolean teamExists(String email);
	
	

}
