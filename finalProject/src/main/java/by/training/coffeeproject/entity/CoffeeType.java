package by.training.coffeeproject.entity;

import java.util.Objects;

public class CoffeeType extends Entity {

	private static final long serialVersionUID = -6528602165634184306L;

	private String name;
	private Country country;
	private int arabicPercent;
	private int robustaPercent;
	private ProcessingMethod processingMethod;
	private String roaster;
	private RoastDegree roastDegree;
	private String information;

	public CoffeeType(Integer iD, String name, Country country, int arabicPercent, int robustaPercent,
			ProcessingMethod processingMethod, String roaster, RoastDegree roastDegree, String information) {
		super(iD);
		this.name = name;
		this.country = country;
		this.arabicPercent = arabicPercent;
		this.robustaPercent = robustaPercent;
		this.processingMethod = processingMethod;
		this.roaster = roaster;
		this.roastDegree = roastDegree;
		this.information = information;
	}
	
	public CoffeeType( String name, Country country, int arabicPercent, int robustaPercent,
			ProcessingMethod processingMethod, String roaster, RoastDegree roastDegree, String information) {
		super();
		this.name = name;
		this.country = country;
		this.arabicPercent = arabicPercent;
		this.robustaPercent = robustaPercent;
		this.processingMethod = processingMethod;
		this.roaster = roaster;
		this.roastDegree = roastDegree;
		this.information = information;
	}

	public CoffeeType() {
		super();
	}

	public CoffeeType(Integer iD) {
		super(iD);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getArabicPercent() {
		return arabicPercent;
	}

	public void setArabicPercent(int arabicPercent) {
		this.arabicPercent = arabicPercent;
	}

	public int getRobustaPercent() {
		return robustaPercent;
	}

	public void setRobustaPercent(int robustaPercent) {
		this.robustaPercent = robustaPercent;
	}

	public ProcessingMethod getProcessingMethod() {
		return processingMethod;
	}

	public void setProcessingMethod(ProcessingMethod processingMethod) {
		this.processingMethod = processingMethod;
	}

	public String getRoaster() {
		return roaster;
	}

	public void setRoaster(String roaster) {
		this.roaster = roaster;
	}

	public RoastDegree getRoastDegree() {
		return roastDegree;
	}

	public void setRoastDegree(RoastDegree roastDegree) {
		this.roastDegree = roastDegree;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(arabicPercent, country, information, name, processingMethod, roastDegree,
				roaster, robustaPercent);
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
		CoffeeType other = (CoffeeType) obj;
		return arabicPercent == other.arabicPercent && Objects.equals(country, other.country)
				&& Objects.equals(information, other.information) && Objects.equals(name, other.name)
				&& processingMethod == other.processingMethod && roastDegree == other.roastDegree
				&& Objects.equals(roaster, other.roaster) && robustaPercent == other.robustaPercent;
	}

	@Override
	public String toString() {
		return "CoffeeType [name=" + name + ", country=" + country + ", arabicPercent=" + arabicPercent
				+ ", robustaPercent=" + robustaPercent + ", processingMethod=" + processingMethod + ", roaster="
				+ roaster + ", roastDegree=" + roastDegree + ", information=" + information + "]";
	}

}