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
@Table(name="match_outcome")
@NamedQueries({ 
	@NamedQuery(name = "MatchOutcome.getByMatch", query="SELECT mo FROM MatchOutcome mo WHERE mo.match_id = :match_id"),
	/*@NamedQuery(name = "Match.getSchedule", query = "SELECT m FROM Match m, MatchDay d WHERE (m.home_team = :teamId OR m.away_team = :teamId) AND m.match_day_id = d.match_day_id AND d.league_id = :league_id"),*/
	/*@NamedQuery(name = "Team.getTeamByEmail", query = "SELECT t FROM Team t WHERE t.email = :email")*/
	/*@NamedQuery(name = "League.getAvailableLeagueByLevel", query = "SELECT l FROM League l WHERE l.league_level = :league_level AND l.available_slots > 0 AND l.locked = false")*/

})
public class MatchOutcome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int match_outcome_id;
	//foreign
	private int match_id;
	
	public int getMatch_outcome_id() {
		return match_outcome_id;
	}

	public void setMatch_outcome_id(int match_outcome_id) {
		this.match_outcome_id = match_outcome_id;
	}

	public int getMatch_id() {
		return match_id;
	}

	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}

	public String getMatch_score() {
		return match_score;
	}

	public void setMatch_score(String match_score) {
		this.match_score = match_score;
	}

	private String match_score;
	
	   
}

