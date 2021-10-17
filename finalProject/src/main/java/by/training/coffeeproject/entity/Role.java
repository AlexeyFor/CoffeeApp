package by.training.coffeeproject.entity;

public enum Role {
	MODER("moder"), USER("user");

	private String name;

	public String getName() {
		return name;
	}

	Role(String name) {
		this.name = name;
	}
}
