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
@Table(name = "captains")
@NamedQueries({
/*
 * @NamedQuery(name = "Contract.getContractByPlayerId", query =
 * "SELECT c FROM Contract c WHERE c.player_id = :player_id")
 */

})
public class Captains implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int team_id;
	
	private int captain_id_one;

	private int captain_id_two;

	private int captain_id_three;

	private int captain_id_four;

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getCaptain_id_one() {
		return captain_id_one;
	}

	public void setCaptain_id_one(int captain_id_one) {
		this.captain_id_one = captain_id_one;
	}

	public int getCaptain_id_two() {
		return captain_id_two;
	}

	public void setCaptain_id_two(int captain_id_two) {
		this.captain_id_two = captain_id_two;
	}

	public int getCaptain_id_three() {
		return captain_id_three;
	}

	public void setCaptain_id_three(int captain_id_three) {
		this.captain_id_three = captain_id_three;
	}

	public int getCaptain_id_four() {
		return captain_id_four;
	}

	public void setCaptain_id_four(int captain_id_four) {
		this.captain_id_four = captain_id_four;
	}

}
