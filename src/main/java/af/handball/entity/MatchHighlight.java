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
@Table(name="match_highlight")
@NamedQueries({ 
	@NamedQuery(name = "MatchHighlight.getHighlightByUpdateNumber", query = "SELECT mh FROM MatchHighlight mh WHERE mh.update_number = :update_number AND mh.match_id = :match_id"),
	@NamedQuery(name = "MatchHighlight.getHighlightsOfMatch", query = "SELECT mh FROM MatchHighlight mh WHERE mh.match_id = :match_id ORDER BY mh.update_number"),
	@NamedQuery(name = "MatchHighlight.deleteHighlightsOfMatch", query = "DELETE FROM MatchHighlight mh WHERE mh.match_id = :match_id"),
	/*@NamedQuery(name = "Match.getSchedule", query = "SELECT m FROM Match m, MatchDay d WHERE (m.home_team = :teamId OR m.away_team = :teamId) AND m.match_day_id = d.match_day_id AND d.league_id = :league_id"),*/
	/*@NamedQuery(name = "Team.getTeamByEmail", query = "SELECT t FROM Team t WHERE t.email = :email")*/
	/*@NamedQuery(name = "League.getAvailableLeagueByLevel", query = "SELECT l FROM League l WHERE l.league_level = :league_level AND l.available_slots > 0 AND l.locked = false")*/

})
public class MatchHighlight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int highlight_id;
	//foreign
	private int match_id;
	
	private int update_number;
	private String highlight_text;
	
	
	public int getHighlight_id() {
		return highlight_id;
	}
	public void setHighlight_id(int highlight_id) {
		this.highlight_id = highlight_id;
	}
	public int getMatch_id() {
		return match_id;
	}
	public void setMatch_id(int match_id) {
		this.match_id = match_id;
	}
	public int getUpdate_number() {
		return update_number;
	}
	public void setUpdate_number(int update_number) {
		this.update_number = update_number;
	}
	public String getHighlight_text() {
		return highlight_text;
	}
	public void setHighlight_text(String highlight_text) {
		this.highlight_text = highlight_text;
	}
	   
	
}

