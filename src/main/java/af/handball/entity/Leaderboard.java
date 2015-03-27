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
@Table(name = "leaderboard")
@NamedQueries({

  @NamedQuery(name = "Leaderboard.getByLeagueId", query ="SELECT l FROM Leaderboard l WHERE l.league_id = :league_id")
 

})
public class Leaderboard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaderboard_id;
	
	private int league_id;

	public int getLeaderboard_id() {
		return leaderboard_id;
	}

	public void setLeaderboard_id(int leaderboard_id) {
		this.leaderboard_id = leaderboard_id;
	}

	public int getLeague_id() {
		return league_id;
	}

	public void setLeague_id(int league_id) {
		this.league_id = league_id;
	}




}
