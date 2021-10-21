package by.training.coffeeproject.service;

import by.training.coffeeproject.service.impl.*;

public class ServiceFactory {

	private ServiceFactory() {
	}

	private static ServiceFactory instance = new ServiceFactory();

	public static ServiceFactory getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return UserServiceImpl.getInstance();
	}
	
	public InfusionService getInfusionService() {
		return InfusionServiceImpl.getInstance();
	}
	
	public PouroverRecipeService getPouroverRecipeService() {
		return PouroverRecipeServiceImpl.getInstance();
	}
	
	public FrenchPressRecipeService getFrenchPressRecipeService() {
		return FrenchPressRecipeServiceImpl.getInstance();
	}
	
	public RecipeService getRecipeService() {
		return RecipeServiceImpl.getInstance();
	}
	
	public UserInfoService getUserInfoService() {
		return UserInfoServiceImpl.getInstance();
	}
	
	public CountryService getCountryService() {
		return CountryServiceImpl.getInstance();
	}
	
	public CoffeeTypeService getCoffeeTypeService() {
		return CoffeeTypeServiceImpl.getInstance();
	}
	
	public UserRecipeService getUserRecipeService() {
		return UserRecipeServiceImpl.getInstance();
	}
	
}
