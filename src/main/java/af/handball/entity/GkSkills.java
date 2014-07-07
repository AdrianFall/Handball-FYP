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
@Table(name="gk_skills")
@NamedQueries({ 
	
})
public class GkSkills implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int gk_skills_ids;
	
	private int player_id;
	
	private double fitness;
	
	private double one_on_one_save;
	
	private double seven_m_save;
	
	private double nine_m_save;
	
	private double wing_save;

	public int getGk_skills_ids() {
		return gk_skills_ids;
	}

	public void setGk_skills_ids(int gk_skills_ids) {
		this.gk_skills_ids = gk_skills_ids;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getOne_on_one_save() {
		return one_on_one_save;
	}

	public void setOne_on_one_save(double one_on_one_save) {
		this.one_on_one_save = one_on_one_save;
	}

	public double getSeven_m_save() {
		return seven_m_save;
	}

	public void setSeven_m_save(double seven_m_save) {
		this.seven_m_save = seven_m_save;
	}

	public double getNine_m_save() {
		return nine_m_save;
	}

	public void setNine_m_save(double nine_m_save) {
		this.nine_m_save = nine_m_save;
	}

	public double getWing_save() {
		return wing_save;
	}

	public void setWing_save(double wing_save) {
		this.wing_save = wing_save;
	}

	
	
	   
}

