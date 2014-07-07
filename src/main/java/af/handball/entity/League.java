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
@Table(name="league")
@NamedQueries({ 
	/*@NamedQuery(name = "Team.getTeamByEmail", query = "SELECT t FROM Team t WHERE t.email = :email")*/
	@NamedQuery(name = "League.getAvailableLeagueByLevel", query = "SELECT l FROM League l WHERE l.league_level = :league_level AND l.available_slots > 0 AND l.locked = false")
})
public class League implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int league_id;
	private Integer available_slots;
	private boolean locked;
	
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public Integer getAvailable_slots() {
		return available_slots;
	}
	public void setAvailable_slots(Integer available_slots) {
		this.available_slots = available_slots;
	}
	public Integer getLeague_level() {
		return league_level;
	}
	public void setLeague_level(Integer league_level) {
		this.league_level = league_level;
	}
	private Integer league_level;
	
	public int getLeague_id() {
		return league_id;
	}
	
	   
}

