package by.training.coffeeproject.dao;

import by.training.coffeeproject.entity.UserRecipe;

public interface UserRecipeDao extends Dao<UserRecipe> {

	boolean deleteWithTwoInt(Integer userId, Integer recipeId) throws DaoException;

}
