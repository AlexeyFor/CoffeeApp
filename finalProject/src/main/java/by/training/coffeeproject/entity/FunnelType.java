package by.training.coffeeproject.entity;

public enum FunnelType {
	HARIOV60("hario V60"), ORIGINALCHEMEX("original chemex");

	private String name;

	public String getName() {
		return name;
	}

	FunnelType(String name) {
		this.name = name;
	}
}
