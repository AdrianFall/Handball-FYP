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
@Table(name = "player")
@NamedQueries({
	@NamedQuery(name = "Player.getUsersPlayers", query = "SELECT p FROM Player p, Contract c WHERE p.player_id = c.player_id AND c.team_id = (SELECT t.team_id FROM Team t WHERE t.email = :email)"),
	@NamedQuery(name = "Player.getTeamPlayers", query = "SELECT p FROM Player p, Contract c WHERE p.player_id = c.player_id AND c.team_id = :team_id"),
})
public class Player implements Serializable {

	public static final int PLAYER_HAND_RIGHT = 1;
	public static final int PLAYER_HAND_LEFT = 2;
	public static final int PLAYER_HAND_BOTH = 3;
	public static final String FORMATION_FIRST_SQUAD = "1st_squad";
	public static final String FORMATION_BENCH = "bench";
	public static final String FORMATION_RESERVES = "reserves";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int player_id;

	private double player_quality;

	private int form;

	private int age;
	
	private int number;
	
	private String first_squad_play_position;

	public String getFirst_squad_play_position() {
		return first_squad_play_position;
	}

	public void setFirst_squad_play_position(String first_squad_play_position) {
		this.first_squad_play_position = first_squad_play_position;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	private String play_position;

	private String name;

	private int height;

	private int weight;

	private int handed;
	
	private int skill_gain;
	
	private int skill_points;
	
	private int intensity;

	private String nationality;

	private String special_ability;

	private int market_value;

	private int morale;

	private int condition;
	
	private String formation;

	private int injury_days;

	private String injury_cause;

	public String getFormation() {
		return formation;
	}

	public void setFormation(String formation) {
		this.formation = formation;
	}
	
	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public double getPlayer_quality() {
		return player_quality;
	}

	public void setPlayer_quality(double player_quality) {
		this.player_quality = player_quality;
	}

	public int getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPlay_position() {
		return play_position;
	}

	public void setPlay_position(String play_position) {
		this.play_position = play_position;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHanded() {
		return handed;
	}

	public void setHanded(int handed) {
		this.handed = handed;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getSpecial_ability() {
		return special_ability;
	}

	public void setSpecial_ability(String special_ability) {
		this.special_ability = special_ability;
	}

	public int getMarket_value() {
		return market_value;
	}

	public void setMarket_value(int market_value) {
		this.market_value = market_value;
	}

	public int getMorale() {
		return morale;
	}

	public void setMorale(int morale) {
		this.morale = morale;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public int getInjury_days() {
		return injury_days;
	}

	public void setInjury_days(int injury_days) {
		this.injury_days = injury_days;
	}

	public String getInjury_cause() {
		return injury_cause;
	}

	public void setInjury_cause(String injury_cause) {
		this.injury_cause = injury_cause;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSkill_gain() {
		return skill_gain;
	}

	public void setSkill_gain(int skill_gain) {
		this.skill_gain = skill_gain;
	}

	public int getSkill_points() {
		return skill_points;
	}

	public void setSkill_points(int skill_points) {
		this.skill_points = skill_points;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	
}
