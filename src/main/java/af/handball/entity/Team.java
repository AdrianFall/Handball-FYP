package af.handball.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="team")
@NamedQueries({ 
	@NamedQuery(name = "Team.getTeamByEmail", query = "SELECT t FROM Team t WHERE t.email = :email")

})
public class Team implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int team_id;
	private String team_name;
	private String email;
	private Integer team_level;
	private Integer money;
	private Integer fans;
	private int league_id;
	
	public int getLeague_id() {
		return league_id;
	}

	public void setLeague_id(int league_id) {
		this.league_id = league_id;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getFans() {
		return fans;
	}

	public void setFans(Integer fans) {
		this.fans = fans;
	}

	public Integer getTeam_level() {
		return team_level;
	}

	public void setTeam_level(Integer team_level) {
		this.team_level = team_level;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	
	
	   
}

