package af.handball.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="match_day")
@NamedQueries({ 
	/*@NamedQuery(name = "Team.getTeamByEmail", query = "SELECT t FROM Team t WHERE t.email = :email")*/
	/*@NamedQuery(name = "League.getAvailableLeagueByLevel", query = "SELECT l FROM League l WHERE l.league_level = :league_level AND l.available_slots > 0 AND l.locked = false")*/
})
public class MatchDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int match_day_id;
	//foreign
	private int league_id;
	private int day_number;
	
	
	

	
	public Integer getMatch_day_id() {
		return match_day_id;
	}
	public void setMatch_day_id(Integer match_day_id) {
		this.match_day_id = match_day_id;
	}
	public Integer getLeague_id() {
		return league_id;
	}
	public void setLeague_id(Integer league_id) {
		this.league_id = league_id;
	}
	public Integer getDay_number() {
		return day_number;
	}
	public void setDay_number(Integer day_number) {
		this.day_number = day_number;
	}
	
	
	   
}

