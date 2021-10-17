package by.training.coffeeproject.service;

import by.training.coffeeproject.entity.PouroverRecipe;
import by.training.coffeeproject.entity.Recipe;

public interface PouroverRecipeService {

	/**
	 * Return PouroverRecipe with all its infusions
	 * 
	 * @param ID
	 * @return
	 * @throws ServiceException
	 */
	public PouroverRecipe takePouroverRecipeByID(Integer ID) throws ServiceException;

	/**
	 * get two PouroverRecipe, recipe1 with all fields of Recipe and recipe2 with
	 * all fields of PouroverRecipe. Assign all values of recipe1 to recipe2
	 * 
	 * @param recipe1
	 * @param recipe2
	 * @return
	 */
	public PouroverRecipe uniteTwoRecipes(Recipe recipe1, PouroverRecipe recipe2) throws ServiceException;
}
