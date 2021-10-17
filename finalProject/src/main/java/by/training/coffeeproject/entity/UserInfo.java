package by.training.coffeeproject.entity;

import java.util.List;
import java.util.Objects;

public class UserInfo extends Entity {

	private static final long serialVersionUID = 8383636608594789841L;

	private String name;
	private String email;
	private String information;
	private Country country;
	private String storagePath;
	private List<Recipe> recipes;

	public UserInfo(Integer iD, String name, String email, String information, Country country, String storagePath,
			List<Recipe> recipes) {
		super(iD);
		this.name = name;
		this.email = email;
		this.information = information;
		this.country = country;
		this.storagePath = storagePath;
		this.recipes = recipes;
	}

	public UserInfo() {
		super();
	}

	public UserInfo(Integer iD) {
		super(iD);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(country, email, information, name, recipes, storagePath);
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
		UserInfo other = (UserInfo) obj;
		return Objects.equals(country, other.country) && Objects.equals(email, other.email)
				&& Objects.equals(information, other.information) && Objects.equals(name, other.name)
				&& Objects.equals(recipes, other.recipes) && Objects.equals(storagePath, other.storagePath);
	}

	@Override
	public String toString() {
		return "UserInfo [name=" + name + ", email=" + email + ", information=" + information + ", country=" + country
				+ ", storagePath=" + storagePath + ", recipes=" + recipes + "]";
	}

}
