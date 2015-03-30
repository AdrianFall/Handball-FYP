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
@Table(name = "leaderboard_team")
@NamedQueries({

  @NamedQuery(name = "LeaderboardTeam.getByLeaderboardIdAndTeamId", query = "SELECT lt FROM LeaderboardTeam lt WHERE lt.leaderboard_id = :leaderboard_id AND lt.team_id = :team_id"),
  @NamedQuery(name = "LeaderboardTeam.getByLeagueId", query = "SELECT lt FROM LeaderboardTeam lt, Leaderboard l WHERE l.leaderboard_id = lt.leaderboard_id AND l.league_id = :league_id ORDER BY lt.points DESC")
 
 

})
public class LeaderboardTeam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaderboard_team_id;
	
	// fk
	private int leaderboard_id;
	//fk
	private int team_id;
	
	private int matches_played;
	private int wins;
	private int draws;
	private int loses;
	private int goals_scored;
	private int goals_conceded;
	private int goals_difference;
	private int points;
	private String form;
	
	public int getLeaderboard_team_id() {
		return leaderboard_team_id;
	}
	public void setLeaderboard_team_id(int leaderboard_team_id) {
		this.leaderboard_team_id = leaderboard_team_id;
	}
	public int getLeaderboard_id() {
		return leaderboard_id;
	}
	public void setLeaderboard_id(int leaderboard_id) {
		this.leaderboard_id = leaderboard_id;
	}
	public int getTeam_id() {
		return team_id;
	}
	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}
	public int getMatches_played() {
		return matches_played;
	}
	public void setMatches_played(int matches_played) {
		this.matches_played = matches_played;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getDraws() {
		return draws;
	}
	public void setDraws(int draws) {
		this.draws = draws;
	}
	public int getLoses() {
		return loses;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}
	public int getGoals_scored() {
		return goals_scored;
	}
	public void setGoals_scored(int goals_scored) {
		this.goals_scored = goals_scored;
	}
	public int getGoals_conceded() {
		return goals_conceded;
	}
	public void setGoals_conceded(int goals_conceded) {
		this.goals_conceded = goals_conceded;
	}
	public int getGoals_difference() {
		return goals_difference;
	}
	public void setGoals_difference(int goal_difference) {
		this.goals_difference = goal_difference;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	
	
}
