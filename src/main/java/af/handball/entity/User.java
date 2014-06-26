package af.handball.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="app_user")
public class User {

	@Id
	@GeneratedValue
	private BigDecimal user_id;
	
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public BigDecimal getId() {
		return user_id;
	}

	public void setId(BigDecimal userId) {
		this.user_id = userId;
	}
}
