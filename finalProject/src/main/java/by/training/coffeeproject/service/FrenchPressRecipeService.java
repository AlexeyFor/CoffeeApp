package by.training.coffeeproject.service;

import by.training.coffeeproject.entity.FrenchPressRecipe;
import by.training.coffeeproject.entity.Recipe;

public interface FrenchPressRecipeService {

	/**
	 * Return FrenchPressRecipe with all its infusions
	 * @param ID
	 * @return
	 * @throws ServiceException
	 */
	public FrenchPressRecipe takeFrenchPressRecipeByID (Integer ID) throws ServiceException;

	/**
	 * get two FrenchPressRecipe, recipe1 with all fields of Recipe and recipe2 with
	 * all fields of FrenchPressRecipe. Assign all values of recipe1 to recipe2
	 * 
	 * @param recipe1
	 * @param recipe2
	 * @return
	 */
	public FrenchPressRecipe uniteTwoRecipes(Recipe recipe1, FrenchPressRecipe recipe2)
			throws ServiceException;
}
