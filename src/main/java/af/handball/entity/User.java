package af.handball.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue
	private int userId;

	public int getId() {
		return userId;
	}

	public void setId(int userId) {
		this.userId = userId;
	}
}
