package by.training.coffeeproject.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PouroverRecipe extends Recipe {

	private static final long serialVersionUID = 845567716681261162L;

	private String recipeName;
	private FunnelType funnelType;
	private float massOfCoffee;
	private float grindSettings;
	private String coffeeGrinder;
	private int totalTime;
	private String disription;
	private List<Infusion> infusions;

	public PouroverRecipe() {
		super();
	}

	public PouroverRecipe(Integer iD, CoffeeType coffeeType, int authorUserId, boolean common, Date dateOfCreating,
			List<Comment> comments, RecipeType recipeType) {
		super(iD, coffeeType, authorUserId, common, dateOfCreating, comments, recipeType);
	}

	public PouroverRecipe(CoffeeType coffeeType, int authorUserId, boolean common, RecipeType recipeType) {
		super(coffeeType, authorUserId, common, recipeType);
	}

	public PouroverRecipe(Integer iD, CoffeeType coffeeType, int authorUserId, boolean common, Date dateOfCreating,
			List<Comment> comments, RecipeType recipeType, String recipeName, FunnelType funnelType, float massOfCoffee,
			float grindSettings, String coffeeGrinder, int totalTime, String disription, List<Infusion> infusions) {
		super(iD, coffeeType, authorUserId, common, dateOfCreating, comments, recipeType);
		this.recipeName = recipeName;
		this.funnelType = funnelType;
		this.massOfCoffee = massOfCoffee;
		this.grindSettings = grindSettings;
		this.coffeeGrinder = coffeeGrinder;
		this.totalTime = totalTime;
		this.disription = disription;
		this.infusions = infusions;
	}

	public PouroverRecipe(Integer iD,String recipeName, FunnelType funnelType, float massOfCoffee, float grindSettings,
			String coffeeGrinder, int totalTime, String disription) {
		super(iD);
		this.recipeName = recipeName;
		this.funnelType = funnelType;
		this.massOfCoffee = massOfCoffee;
		this.grindSettings = grindSettings;
		this.coffeeGrinder = coffeeGrinder;
		this.totalTime = totalTime;
		this.disription = disription;
	}

	public List<Infusion> getInfusions() {
		return infusions;
	}

	public void setInfusions(List<Infusion> infusions) {
		this.infusions = infusions;
	}

	public PouroverRecipe(Integer iD) {
		super(iD);
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public FunnelType getFunnelType() {
		return funnelType;
	}

	public void setFunnelType(FunnelType funnelType) {
		this.funnelType = funnelType;
	}

	public float getMassOfCoffee() {
		return massOfCoffee;
	}

	public void setMassOfCoffee(float massOfCoffee) {
		this.massOfCoffee = massOfCoffee;
	}

	public float getGrindSettings() {
		return grindSettings;
	}

	public void setGrindSettings(float grindSettings) {
		this.grindSettings = grindSettings;
	}

	public String getCoffeeGrinder() {
		return coffeeGrinder;
	}

	public void setCoffeeGrinder(String coffeeGrinder) {
		this.coffeeGrinder = coffeeGrinder;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public String getDisription() {
		return disription;
	}

	public void setDisription(String disription) {
		this.disription = disription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(coffeeGrinder, disription, funnelType, grindSettings, infusions,
				massOfCoffee, recipeName, totalTime);
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
		PouroverRecipe other = (PouroverRecipe) obj;
		return Objects.equals(coffeeGrinder, other.coffeeGrinder) && Objects.equals(disription, other.disription)
				&& funnelType == other.funnelType
				&& Float.floatToIntBits(grindSettings) == Float.floatToIntBits(other.grindSettings)
				&& Objects.equals(infusions, other.infusions)
				&& Float.floatToIntBits(massOfCoffee) == Float.floatToIntBits(other.massOfCoffee)
				&& Objects.equals(recipeName, other.recipeName) && totalTime == other.totalTime;
	}

	@Override
	public String toString() {
		return "PouroverRecipe [recipeName=" + recipeName + ", funnelType=" + funnelType + ", massOfCoffee="
				+ massOfCoffee + ", grindSettings=" + grindSettings + ", coffeeGrinder=" + coffeeGrinder
				+ ", totalTime=" + totalTime + ", disription=" + disription + ", infusions=" + infusions + "] "
				+ super.toString();
	}

}
