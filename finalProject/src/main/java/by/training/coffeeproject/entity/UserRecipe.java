package by.training.coffeeproject.entity;

import java.util.Objects;

/**
 * 
 * @author AlexeySupruniuk
 *
 *         just to save pair user_id and his recipe_id. Doesn't has its own ID
 */
public class UserRecipe extends Entity {

	private static final long serialVersionUID = -7833145851622747048L;

	private Integer userId;
	private Integer recipeId;

	public UserRecipe(Integer iD, Integer userId, Integer recipeId) {
		super(iD);
		this.userId = userId;
		this.recipeId = recipeId;
	}

	public UserRecipe(Integer userId, Integer recipeId) {
		super();
		this.userId = userId;
		this.recipeId = recipeId;
	}
	
	public UserRecipe() {
		super();
	}

	public UserRecipe(Integer iD) {
		super(iD);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Integer recipeId) {
		this.recipeId = recipeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(recipeId, userId);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRecipe other = (UserRecipe) obj;
		return Objects.equals(recipeId, other.recipeId) && Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "UserRecipe [userId=" + userId + ", recipeId=" + recipeId + "]";
	}

}
