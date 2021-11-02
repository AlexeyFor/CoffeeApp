package by.training.coffeeproject.entity;

public enum SortType {

	NAME("name", "name"), COUNTRY("country_of_origin_id", "country of origin"),
	ARABICPERCENT("arabic_percent", "arabic percent"), ROBUSTAPERCENT("robusta_percent", "robusta percent"),
	PROCESSINGMETHOD("processing_method", "processing method"), ROASTER("roaster", "roaster"),
	ROASTDEGREE("roast_degree", "roast degree"), INFORMATION("information", "information"),
	NONE("id_coffee_type", "none");

	private String name;
	private String displayName;

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	SortType(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;

	}
}
