package by.training.coffeeproject.dao;

import java.util.List;

import by.training.coffeeproject.entity.User;

public interface UserDao extends Dao<User> {

	List<User> findAllUsers() throws DaoException;

	List<User> findAllUsersByCountryName(String name) throws DaoException;

	Integer findUserIDByLogin(String name) throws DaoException;

	/**
	 * find user with such login and password, if smth is incorrect - doesn't
	 * display what exactly
	 * 
	 * @param login
	 * @param password
	 * @return
	 * @throws DaoException
	 */
	User authorization(String login, String password) throws DaoException;

}
