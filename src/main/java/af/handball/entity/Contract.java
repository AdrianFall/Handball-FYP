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
@Table(name="contract")
public class Contract implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int contract_id;
	
	private int team_id;
	
	private boolean expired;
	
	private int player_id;
	
	private int season_wage;
	
	private int years_left;

	public int getContract_id() {
		return contract_id;
	}

	public void setContract_id(int contract_id) {
		this.contract_id = contract_id;
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getSeason_wage() {
		return season_wage;
	}

	public void setSeason_wage(int season_wage) {
		this.season_wage = season_wage;
	}

	public int getYears_left() {
		return years_left;
	}

	public void setYears_left(int years_left) {
		this.years_left = years_left;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	   
}

