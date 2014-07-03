package af.handball.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "app_user")
@NamedQueries({ 
	@NamedQuery(name = "Category.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name ="User.emailExists", query = "SELECT u.email FROM User u WHERE u.email = :email"),
	@NamedQuery(name = "User.authenticateUser", query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password")

})
public class User implements Serializable {

	@Id
	@GeneratedValue
	private String email;

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
