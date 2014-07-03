package af.handball.repository.impl;

import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import af.handball.entity.User;
import af.handball.repository.UserRepository;

@Component("UserRepository")
@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	EntityManager emgr;

	/*
	 * @Transactional
	 * 
	 * @Override public void registerUser(User user) { // TODO Auto-generated
	 * method stub System.out.println("Registering user."); }
	 */

	/*
	 * @Override public void deleteAllInBatch() { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * @Override public void deleteInBatch(Iterable<User> arg0) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public List<User> findAll() { // TODO Auto-generated method
	 * stub TypedQuery<User> findAllQuery =
	 * emgr.createNamedQuery("Category.findAll", User.class); List<User>
	 * userList = (List<User>) findAllQuery.getResultList(); return userList; }
	 * 
	 * @Override public List<User> findAll(Sort arg0) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public List<User> findAll(Iterable<Integer> arg0) { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public void flush() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public User getOne(Integer arg0) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public <S extends User> List<S> save(Iterable<S> arg0) { //
	 * TODO Auto-generated method stub return null; }
	 * 
	 * @Override public User saveAndFlush(User arg0) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public Page<User> findAll(Pageable arg0) { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public long count() { // TODO Auto-generated method stub return
	 * 0; }
	 * 
	 * @Override public void delete(Integer arg0) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 * 
	 * @Override public void delete(User arg0) { // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * @Override public void delete(Iterable<? extends User> arg0) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void deleteAll() { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public boolean exists(Integer arg0) { // TODO Auto-generated
	 * method stub return false; }
	 * 
	 * @Override public User findOne(Integer arg0) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public <S extends User> S save(S arg0) { // TODO Auto-generated
	 * method stub return null; }
	 */

	@Override
	public boolean emailExists(String email) {
		/*
		 * TypedQuery<String> emailExistsQuery =
		 * emgr.createNamedQuery("User.emailExists", User.class);
		 * emailExistsQuery.setParameter("email", email); String emailObtained =
		 * (String) emailExistsQuery.getSingleResult();
		 * System.out.println(" useremail = " + user.getEmail());
		 */
		boolean emailExists = false;
		User user = emgr.find(User.class, email);
		if (user == null) {
			System.out.println("User object is null");
		} else {
			System.out.println("User object exists.");
			emailExists = true;
		}
		return emailExists;
	}

	@Override
	public boolean newUser(String email, String password) {
		boolean userCreated = false;
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		try {
			emgr.persist(user);
			System.out.println("Persisted new user.");
			userCreated = true;
		} catch (Exception e) {
			System.out.println("Exception when persisting new user... "
					+ e.getLocalizedMessage());
			e.printStackTrace();
		}
		return userCreated;
	}

	@Override
	public boolean authenticateUser(String email, String password) {
		boolean userAuthenticated = false;

		TypedQuery<User> authenticateUserQuery = emgr.createNamedQuery(
				"User.authenticateUser", User.class);

		authenticateUserQuery.setParameter("email", email);
		authenticateUserQuery.setParameter("password", password);

		try {
			User user = authenticateUserQuery.getSingleResult();
			if (user != null) {
				userAuthenticated = true;
			}

		} catch (NoResultException nre) {
			System.out.println("Exception while authenticatingUser... "
					+ nre.getLocalizedMessage());
			nre.printStackTrace();
		}

		return userAuthenticated;
	}

}
