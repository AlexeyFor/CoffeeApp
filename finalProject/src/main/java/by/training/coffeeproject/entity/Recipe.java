package by.training.coffeeproject.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class Recipe extends Entity {

	private static final long serialVersionUID = 8823583488754278633L;

	private CoffeeType coffeeType;
	private int authorUserId;
	private boolean common;
	private Date dateOfCreating;
	private List<Comment> comments;
	private RecipeType recipeType;

	public Recipe(Integer iD, CoffeeType coffeeType, int authorUserId, boolean common, Date dateOfCreating,
			List<Comment> comments, RecipeType recipeType) {
		super(iD);
		this.coffeeType = coffeeType;
		this.authorUserId = authorUserId;
		this.common = common;
		this.dateOfCreating = dateOfCreating;
		this.comments = comments;
		this.recipeType = recipeType;
	}

	public Recipe(CoffeeType coffeeType, int authorUserId, boolean common, RecipeType recipeType) {
		super();
		this.coffeeType = coffeeType;
		this.authorUserId = authorUserId;
		this.common = common;
		this.recipeType = recipeType;
	}

	public Recipe() {
		super();
	}

	public Recipe(Integer iD) {
		super(iD);
	}

	public CoffeeType getCoffeeType() {
		return coffeeType;
	}

	public void setCoffeeType(CoffeeType coffeeType) {
		this.coffeeType = coffeeType;
	}

	public int getAuthorUserId() {
		return authorUserId;
	}

	public void setAuthorUserId(int authorUserId) {
		this.authorUserId = authorUserId;
	}

	public boolean isCommon() {
		return common;
	}

	public void setCommon(boolean common) {
		this.common = common;
	}

	public Date getDateOfCreating() {
		return dateOfCreating;
	}

	public void setDateOfCreating(Date dateOfCreating) {
		this.dateOfCreating = dateOfCreating;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public RecipeType getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(RecipeType recipeType) {
		this.recipeType = recipeType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(authorUserId, coffeeType, comments, common, dateOfCreating, recipeType);
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
		Recipe other = (Recipe) obj;
		return authorUserId == other.authorUserId && Objects.equals(coffeeType, other.coffeeType)
				&& Objects.equals(comments, other.comments) && common == other.common
				&& Objects.equals(dateOfCreating, other.dateOfCreating) && recipeType == other.recipeType;
	}

	@Override
	public String toString() {
		return "Recipe [coffeeType=" + coffeeType + ", authorUserId=" + authorUserId + ", common=" + common
				+ ", dateOfCreating=" + dateOfCreating + ", comments=" + comments + ", recipeType=" + recipeType + "]";
	}

}