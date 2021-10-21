package by.training.coffeeproject.dao;

import by.training.coffeeproject.dao.impl.*;

public class DaoFabric {

	private DaoFabric() {
	}

	private static DaoFabric instance = new DaoFabric();

	public static DaoFabric getInstance() {
		return instance;
	}

	public UserDao getUserDao() {
		return UserDaoImpl.getInstance();
	}

	public UserInfoDao getUserInfoDao() {
		return UserInfoDaoImpl.getInstance();
	}

	public CountryDao getCountryDao() {
		return CountryDaoImpl.getInstance();
	}

	public RecipeDao getRecipeDao() {
		return RecipeDaoImpl.getInstance();
	}

	public PouroverRecipeDao getPouroverRecipeDao() {
		return PouroverRecipeDaoImpl.getInstance();
	}

	public FrenchPressRecipeDao getFrenchPressRecipeDao() {
		return FrenchPressRecipeDaoImpl.getInstance();
	}

	public InfusionDao getInfusionDao() {
		return InfusionDaoImpl.getInstance();
	}
	
	public CoffeeTypeDao getCoffeeTypeDao() {
		return CoffeeTypeDaoImpl.getInstance();
	}
	
	public UserRecipeDao getUserRecipeDao() {
		return UserRecipeDaoImpl.getInstance();
	}
	
}
