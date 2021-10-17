package by.training.coffeeproject.service;

import java.util.List;

import by.training.coffeeproject.entity.Country;
import by.training.coffeeproject.entity.User;

public interface UserService {

	public User authorization(String login, String password) throws ServiceException;

	public boolean createUserInDB(String login, String password, String name, String email, String information,
			Country country, String storagePath) throws ServiceException;

	public boolean updateUserInDB(Integer ID, String login, String password, String name, String email,
			String information, Country country, String storagePath) throws ServiceException;

	public boolean deleteUserByID(Integer ID);

	public List<User> takeAllUsers() throws ServiceException;

	public User takeUserByID(Integer ID) throws ServiceException;

}
