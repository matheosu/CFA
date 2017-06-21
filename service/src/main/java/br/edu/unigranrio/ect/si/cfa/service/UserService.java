package br.edu.unigranrio.ect.si.cfa.service;

import br.edu.unigranrio.ect.si.cfa.model.Role;
import br.edu.unigranrio.ect.si.cfa.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends Service {

	/**
	 * Find User by Email
	 * @param email a unique email for user
	 * @return User
     */
	Optional<User> findUserByEmail(String email);

	/**
	 * Find a Users by name
	 * @param name name
	 * @return List Users
     */
	List<User> findUsersLikeByName(String name);

	/**
	 * Find Users by Role
	 * @param role role
	 * @return List Users
     */
	List<User> findUsersByRole(Role role);

	/**
	 * Find Users by activity
	 * @param activity Boolean param
	 * @return List Users
     */
	List<User> findUserByActivity(Boolean activity);


	Long getTotalActiveUsers();

	default List<User> findActiveUsers(){
		return findUserByActivity(Boolean.TRUE);
	}

	default List<User> findInactiveUsers(){
		return findUserByActivity(Boolean.FALSE);
	}

}
