package by.training.coffeeproject.service;

import java.util.List;

import by.training.coffeeproject.entity.Infusion;

public interface InfusionService {

	public List <Infusion> takeInfusionsByRecipeID (Integer ID) throws ServiceException;
	
	public Integer createInfusionInDB(Infusion infusion) throws ServiceException;

}
