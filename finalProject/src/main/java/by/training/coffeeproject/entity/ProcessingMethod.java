package by.training.coffeeproject.entity;

public enum ProcessingMethod {
	DRY("dry"), WASHED("washed"), HONEY("honey"), OTHER("other");

	private String name;

	public String getName() {
		return name;
	}

	ProcessingMethod(String name) {
		this.name = name;
	}
}
