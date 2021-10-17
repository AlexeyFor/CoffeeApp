package by.training.coffeeproject.service;

import java.util.List;

import by.training.coffeeproject.entity.CoffeeType;

public interface CoffeeTypeService {

	public List <String> findAllRoasters() throws ServiceException;
	
	public List <CoffeeType> findAllCoffeeTypes() throws ServiceException;

	public Integer  createCoffeeTypeInDataBase (CoffeeType offeeType) throws ServiceException;

}
