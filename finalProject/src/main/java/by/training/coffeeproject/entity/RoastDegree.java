package by.training.coffeeproject.entity;

public enum RoastDegree {
	LIGHT("light"), MEDIUM("medium"), DARK("dark");

	private String name;

	public String getName() {
		return name;
	}

	RoastDegree(String name) {
		this.name = name;
	}
}
