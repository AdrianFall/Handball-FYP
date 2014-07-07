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
@Table(name="field_player_skills")
@NamedQueries({ 
	/*@NamedQuery(name = "Team.getTeamByEmail", query = "SELECT t FROM Team t WHERE t.email = :email")*/
	@NamedQuery(name = "League.getAvailableLeagueByLevel", query = "SELECT l FROM League l WHERE l.league_level = :league_level AND l.available_slots > 0 AND l.locked = false")
})
public class FieldPlayerSkills implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int fp_id;
	
	private int player_id;
	
	private double blocking;
	private double positioning;
	private double handling;
	private double marking;
	private double passing;
	private double one_on_one;
	private double seven_m_shoot;
	private double nine_m_shoot;
	private double wing_shoot;
	private double fitness;
	private double creativity;
	private double speed;
	private double strength;
	private double aggression;
	public int getFp_id() {
		return fp_id;
	}
	public void setFp_id(int fp_id) {
		this.fp_id = fp_id;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public double getBlocking() {
		return blocking;
	}
	public void setBlocking(double blocking) {
		this.blocking = blocking;
	}
	public double getPositioning() {
		return positioning;
	}
	public void setPositioning(double positioning) {
		this.positioning = positioning;
	}
	public double getHandling() {
		return handling;
	}
	public void setHandling(double handling) {
		this.handling = handling;
	}
	public double getMarking() {
		return marking;
	}
	public void setMarking(double marking) {
		this.marking = marking;
	}
	public double getPassing() {
		return passing;
	}
	public void setPassing(double passing) {
		this.passing = passing;
	}
	public double getOne_on_one() {
		return one_on_one;
	}
	public void setOne_on_one(double one_on_one) {
		this.one_on_one = one_on_one;
	}
	public double getSeven_m_shoot() {
		return seven_m_shoot;
	}
	public void setSeven_m_shoot(double seven_m_shoot) {
		this.seven_m_shoot = seven_m_shoot;
	}
	public double getNine_m_shoot() {
		return nine_m_shoot;
	}
	public void setNine_m_shoot(double nine_m_shoot) {
		this.nine_m_shoot = nine_m_shoot;
	}
	public double getWing_shoot() {
		return wing_shoot;
	}
	public void setWing_shoot(double wing_shoot) {
		this.wing_shoot = wing_shoot;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getCreativity() {
		return creativity;
	}
	public void setCreativity(double creativity) {
		this.creativity = creativity;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getStrength() {
		return strength;
	}
	public void setStrength(double strength) {
		this.strength = strength;
	}
	public double getAggression() {
		return aggression;
	}
	public void setAggression(double aggression) {
		this.aggression = aggression;
	}
	
	
	
	   
}

