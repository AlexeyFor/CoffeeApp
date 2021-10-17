package by.training.coffeeproject.dao;

import java.util.List;

import by.training.coffeeproject.entity.Infusion;

public interface InfusionDao extends Dao<Infusion> {

	List<Infusion> findByRecipeId(Integer RecipeId) throws DaoException;

}
