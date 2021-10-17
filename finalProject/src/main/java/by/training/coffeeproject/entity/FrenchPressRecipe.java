package by.training.coffeeproject.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FrenchPressRecipe extends Recipe {

	private static final long serialVersionUID = -6208395306694891106L;

	private String recipeName;
	private int frechPressVolume;
	private float massOfCoffee;
	private float grindSettings;
	private String coffeeGrinder;
	private int capBreakingTime;
	private int plungerLoweringTime;
	private int totalTime;
	private String disription;
	private List<Infusion> infusions;

	public List<Infusion> getInfusions() {
		return infusions;
	}

	public void setInfusions(List<Infusion> infusions) {
		this.infusions = infusions;
	}

	public FrenchPressRecipe() {
		super();
	}

	public FrenchPressRecipe(Integer iD, CoffeeType coffeeType, int authorUserId, boolean common, Date dateOfCreating,
			List<Comment> comments, RecipeType recipeType) {
		super(iD, coffeeType, authorUserId, common, dateOfCreating, comments, recipeType);
	}

	public FrenchPressRecipe(CoffeeType coffeeType, int authorUserId, boolean common, RecipeType recipeType) {
		super(coffeeType, authorUserId, common, recipeType);
	}

	public FrenchPressRecipe(Integer iD) {
		super(iD);
	}

	public FrenchPressRecipe(Integer iD, CoffeeType coffeeType, int authorUserId, boolean common, Date dateOfCreating,
			List<Comment> comments, RecipeType recipeType, String recipeName, int frechPressVolume, float massOfCoffee,
			float grindSettings, String coffeeGrinder, int capBreakingTime, int plungerLoweringTime, int totalTime,
			String disription, List<Infusion> infusions) {
		super(iD, coffeeType, authorUserId, common, dateOfCreating, comments, recipeType);
		this.recipeName = recipeName;
		this.frechPressVolume = frechPressVolume;
		this.massOfCoffee = massOfCoffee;
		this.grindSettings = grindSettings;
		this.coffeeGrinder = coffeeGrinder;
		this.capBreakingTime = capBreakingTime;
		this.plungerLoweringTime = plungerLoweringTime;
		this.totalTime = totalTime;
		this.disription = disription;
		this.infusions = infusions;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public int getFrechPressVolume() {
		return frechPressVolume;
	}

	public void setFrechPressVolume(int frechPressVolume) {
		this.frechPressVolume = frechPressVolume;
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

	public int getCapBreakingTime() {
		return capBreakingTime;
	}

	public void setCapBreakingTime(int capBreakingTime) {
		this.capBreakingTime = capBreakingTime;
	}

	public int getPlungerLoweringTime() {
		return plungerLoweringTime;
	}

	public void setPlungerLoweringTime(int plungerLoweringTime) {
		this.plungerLoweringTime = plungerLoweringTime;
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
		result = prime * result + Objects.hash(capBreakingTime, coffeeGrinder, disription, frechPressVolume,
				grindSettings, infusions, massOfCoffee, plungerLoweringTime, recipeName, totalTime);
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
		FrenchPressRecipe other = (FrenchPressRecipe) obj;
		return capBreakingTime == other.capBreakingTime && Objects.equals(coffeeGrinder, other.coffeeGrinder)
				&& Objects.equals(disription, other.disription) && frechPressVolume == other.frechPressVolume
				&& Float.floatToIntBits(grindSettings) == Float.floatToIntBits(other.grindSettings)
				&& Objects.equals(infusions, other.infusions)
				&& Float.floatToIntBits(massOfCoffee) == Float.floatToIntBits(other.massOfCoffee)
				&& plungerLoweringTime == other.plungerLoweringTime && Objects.equals(recipeName, other.recipeName)
				&& totalTime == other.totalTime;
	}

	@Override
	public String toString() {
		return "FrenchPressRecipe [recipeName=" + recipeName + ", frechPressVolume=" + frechPressVolume
				+ ", massOfCoffee=" + massOfCoffee + ", grindSettings=" + grindSettings + ", coffeeGrinder="
				+ coffeeGrinder + ", capBreakingTime=" + capBreakingTime + ", plungerLoweringTime="
				+ plungerLoweringTime + ", totalTime=" + totalTime + ", disription=" + disription + ", infusions="
				+ infusions + "]";
	}

}
