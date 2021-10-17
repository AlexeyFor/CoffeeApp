package by.training.coffeeproject.entity;

public enum RecipeType {
	POUROVER("pourover"), FRENCHPRESS("french press");

	private String name;

	public String getName() {
		return name;
	}

	RecipeType(String name) {
		this.name = name;
	}
}
