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
@Table(name="match")
@NamedQueries({ 
	@NamedQuery(name = "Match.getSchedule", query = "SELECT m FROM Match m, MatchDay d WHERE (m.home_team = :teamId OR m.away_team = :teamId) AND m.match_day_id = d.match_day_id AND d.league_id = :league_id ORDER BY m.match_date"),
	/*@NamedQuery(name = "Team.getTeamByEmail", query = "SELECT t FROM Team t WHERE t.email = :email")*/
	/*@NamedQuery(name = "League.getAvailableLeagueByLevel", query = "SELECT l FROM League l WHERE l.league_level = :league_level AND l.available_slots > 0 AND l.locked = false")*/

})
public class Match implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int match_id;
	//foreign
	private int match_day_id;
	private int home_team;
	private int away_team;
	private String home_team_name;
	private String away_team_name;
	private boolean match_started;
	private boolean match_finished;
	private int match_duration_in_seconds;
	private int updates_per_minute;
	private java.sql.Timestamp match_date;

	

	public int getUpdates_per_minute() {
		return updates_per_minute;
	}

	public void setUpdates_per_minute(int updates_per_minute) {
		this.updates_per_minute = updates_per_minute;
	}

	public int getMatch_duration_in_seconds() {
		return match_duration_in_seconds;
	}

	public void setMatch_duration_in_seconds(int match_duration_in_seconds) {
		this.match_duration_in_seconds = match_duration_in_seconds;
	}

	public boolean isMatch_finished() {
		return match_finished;
	}

	public void setMatch_finished(boolean match_finished) {
		this.match_finished = match_finished;
	}

	public boolean isMatch_started() {
		return match_started;
	}

	public void setMatch_started(boolean match_started) {
		this.match_started = match_started;
	}

	public String getHome_team_name() {
		return home_team_name;
	}

	public void setHome_team_name(String home_team_name) {
		this.home_team_name = home_team_name;
	}

	public String getAway_team_name() {
		return away_team_name;
	}

	public void setAway_team_name(String away_team_name) {
		this.away_team_name = away_team_name;
	}

	
	
	public java.sql.Timestamp getMatch_date() {
		return match_date;
	}
	
	public void setMatch_date(java.sql.Timestamp date) {
		match_date = date;
	}
	
	public Integer getMatch_id() {
		return match_id;
	}
	public void setMatch_id(Integer match_id) {
		this.match_id = match_id;
	}
	public Integer getMatch_day_id() {
		return match_day_id;
	}
	public void setMatch_day_id(Integer match_day_id) {
		this.match_day_id = match_day_id;
	}
	public Integer getHome_team() {
		return home_team;
	}
	public void setHome_team(Integer home_team) {
		this.home_team = home_team;
	}
	public Integer getAway_team() {
		return away_team;
	}
	public void setAway_team(Integer away_team) {
		this.away_team = away_team;
	}
	
	
	
	   
}

