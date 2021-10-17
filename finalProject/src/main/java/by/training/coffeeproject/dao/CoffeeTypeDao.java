package by.training.coffeeproject.dao;

import java.util.List;

import by.training.coffeeproject.entity.CoffeeType;

public interface CoffeeTypeDao extends Dao<CoffeeType> {
	
	public List<String> findAllRoasters() throws DaoException;

}
