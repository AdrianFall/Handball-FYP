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
@Table(name="skill")
@NamedQueries({ 
	@NamedQuery(name = "Skill.getPlayerSkills", query = "SELECT s FROM Skill s WHERE s.player_id = :player_id")
})
public class Skill implements Serializable {

	/**
	 * 
	 */
	public static final int NUMBER_OF_FIELD_PLAYER_SKILLS = 14;
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int skill_id;
	
	private int player_id;
	
	/* Physical Skills */
	private double acceleration;
	private double sprint_speed;
	private double jumping;
	private double balance;
	private double agility;
	private double stamina;
	private double strength;
	private double reactions;
	private double blocking;
	private double fitness;
	/* Mental Skills */
	private double aggression;
	private double interceptions;
	private double attack_position;
	private double vision;
	private double creativity;
	/* Goal Keeping Skills */
	private double reflexes;
	private double handling;
	private double positioning;
	private double leg_saves;
	private double penalty_saves;
	private double six_m_saves;
	private double nine_m_saves;
	private double communication;
	private double angles;
	private double catching;
	/* Technical Skills */
	private double ball_control;
	private double long_shots;
	private double fk_accuracy;
	private double shot_power;
	private double dribbling;
	private double short_passing;
	private double long_passing;
	private double stand_tackles;
	private double marking;
	private double penalties;
	private double curve;
	private double finishing;
	private double six_m_shots;
	private double nine_m_shots;
	private double lob_shots;
	
	/* Getters and Setters */
	public int getSkill_id() {
		return skill_id;
	}
	public void setSkill_id(int skill_id) {
		this.skill_id = skill_id;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public double getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}
	public double getSprint_speed() {
		return sprint_speed;
	}
	public void setSprint_speed(double sprint_speed) {
		this.sprint_speed = sprint_speed;
	}
	public double getJumping() {
		return jumping;
	}
	public void setJumping(double jumping) {
		this.jumping = jumping;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getAgility() {
		return agility;
	}
	public void setAgility(double agility) {
		this.agility = agility;
	}
	public double getStamina() {
		return stamina;
	}
	public void setStamina(double stamina) {
		this.stamina = stamina;
	}
	public double getStrength() {
		return strength;
	}
	public void setStrength(double strength) {
		this.strength = strength;
	}
	public double getReactions() {
		return reactions;
	}
	public void setReactions(double reactions) {
		this.reactions = reactions;
	}
	public double getBlocking() {
		return blocking;
	}
	public void setBlocking(double blocking) {
		this.blocking = blocking;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getAggression() {
		return aggression;
	}
	public void setAggression(double aggression) {
		this.aggression = aggression;
	}
	public double getInterceptions() {
		return interceptions;
	}
	public void setInterceptions(double interceptions) {
		this.interceptions = interceptions;
	}
	public double getAttack_position() {
		return attack_position;
	}
	public void setAttack_position(double attack_position) {
		this.attack_position = attack_position;
	}
	public double getVision() {
		return vision;
	}
	public void setVision(double vision) {
		this.vision = vision;
	}
	public double getCreativity() {
		return creativity;
	}
	public void setCreativity(double creativity) {
		this.creativity = creativity;
	}
	public double getReflexes() {
		return reflexes;
	}
	public void setReflexes(double reflexes) {
		this.reflexes = reflexes;
	}
	public double getHandling() {
		return handling;
	}
	public void setHandling(double handling) {
		this.handling = handling;
	}
	public double getPositioning() {
		return positioning;
	}
	public void setPositioning(double positioning) {
		this.positioning = positioning;
	}
	public double getLeg_saves() {
		return leg_saves;
	}
	public void setLeg_saves(double leg_saves) {
		this.leg_saves = leg_saves;
	}
	public double getPenalty_saves() {
		return penalty_saves;
	}
	public void setPenalty_saves(double penalty_saves) {
		this.penalty_saves = penalty_saves;
	}
	public double getSix_m_saves() {
		return six_m_saves;
	}
	public void setSix_m_saves(double six_m_saves) {
		this.six_m_saves = six_m_saves;
	}
	public double getNine_m_saves() {
		return nine_m_saves;
	}
	public void setNine_m_saves(double nine_m_saves) {
		this.nine_m_saves = nine_m_saves;
	}
	public double getBall_control() {
		return ball_control;
	}
	public void setBall_control(double ball_control) {
		this.ball_control = ball_control;
	}
	public double getLong_shots() {
		return long_shots;
	}
	public void setLong_shots(double long_shots) {
		this.long_shots = long_shots;
	}
	public double getFk_accuracy() {
		return fk_accuracy;
	}
	public void setFk_accuracy(double fk_accuracy) {
		this.fk_accuracy = fk_accuracy;
	}
	public double getShot_power() {
		return shot_power;
	}
	public void setShot_power(double shot_power) {
		this.shot_power = shot_power;
	}
	public double getDribbling() {
		return dribbling;
	}
	public void setDribbling(double dribbling) {
		this.dribbling = dribbling;
	}
	public double getShort_passing() {
		return short_passing;
	}
	public void setShort_passing(double short_passing) {
		this.short_passing = short_passing;
	}
	public double getLong_passing() {
		return long_passing;
	}
	public void setLong_passing(double long_passing) {
		this.long_passing = long_passing;
	}
	public double getStand_tackles() {
		return stand_tackles;
	}
	public void setStand_tackles(double stand_tackles) {
		this.stand_tackles = stand_tackles;
	}
	public double getMarking() {
		return marking;
	}
	public void setMarking(double marking) {
		this.marking = marking;
	}
	public double getPenalties() {
		return penalties;
	}
	public void setPenalties(double penalties) {
		this.penalties = penalties;
	}
	public double getCurve() {
		return curve;
	}
	public void setCurve(double curve) {
		this.curve = curve;
	}
	public double getFinishing() {
		return finishing;
	}
	public void setFinishing(double finishing) {
		this.finishing = finishing;
	}
	public double getSix_m_shots() {
		return six_m_shots;
	}
	public void setSix_m_shots(double six_m_shots) {
		this.six_m_shots = six_m_shots;
	}
	public double getNine_m_shots() {
		return nine_m_shots;
	}
	public void setNine_m_shots(double nine_m_shots) {
		this.nine_m_shots = nine_m_shots;
	}
	public static int getNumberOfFieldPlayerSkills() {
		return NUMBER_OF_FIELD_PLAYER_SKILLS;
	}
	public double getCommunication() {
		return communication;
	}
	public void setCommunication(double communication) {
		this.communication = communication;
	}
	public double getAngles() {
		return angles;
	}
	public void setAngles(double angles) {
		this.angles = angles;
	}
	public double getCatching() {
		return catching;
	}
	public void setCatching(double catching) {
		this.catching = catching;
	}
	public double getLob_shots() {
		return lob_shots;
	}
	public void setLob_shots(double lob_shots) {
		this.lob_shots = lob_shots;
	} 
	
	
}