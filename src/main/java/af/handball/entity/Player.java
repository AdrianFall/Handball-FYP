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

})
public class Player implements Serializable {

	public static final int PLAYER_HAND_RIGHT = 1;
	public static final int PLAYER_HAND_LEFT = 2;
	public static final int PLAYER_HAND_BOTH = 3;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int player_id;

	private double player_quality;

	private int form;

	private int age;

	private String play_position;

	private String name;

	private int height;

	private int weight;

	private int handed;

	private String nationality;

	private String special_ability;

	private int market_value;

	private int morale;

	private int condition;

	private int injury_days;

	private String injury_cause;

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

}
