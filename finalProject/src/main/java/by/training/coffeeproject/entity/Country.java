package by.training.coffeeproject.entity;

import java.util.Objects;

public class Country extends Entity {

	private static final long serialVersionUID = -6714270744376265953L;

	private String countryName;

	public Country(Integer iD, String countryName) {
		super(iD);
		this.countryName = countryName;
	}

	public Country() {
		super();
	}

	public Country(Integer iD) {
		super(iD);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(countryName);
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
		Country other = (Country) obj;
		return Objects.equals(countryName, other.countryName);
	}

	@Override
	public String toString() {
		return "Country [countryName=" + countryName + "]";
	}

}
