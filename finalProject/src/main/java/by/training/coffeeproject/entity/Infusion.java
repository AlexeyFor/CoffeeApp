package by.training.coffeeproject.entity;

import java.util.Objects;

public class Infusion extends Entity {

	private static final long serialVersionUID = -7868580659047488896L;

	private int recipeId;
	private int timeStart;
	private int waterVolume;
	private int timeEnd;
	private int waterTemperature;

	public Infusion(Integer iD, int recipeId, int timeStart, int waterVolume, int timeEnd, int waterTemperature) {
		super(iD);
		this.recipeId = recipeId;
		this.timeStart = timeStart;
		this.waterVolume = waterVolume;
		this.timeEnd = timeEnd;
		this.waterTemperature = waterTemperature;
	}

	public Infusion(int recipeId, int timeStart, int waterVolume, int timeEnd, int waterTemperature) {
		super();
		this.recipeId = recipeId;
		this.timeStart = timeStart;
		this.waterVolume = waterVolume;
		this.timeEnd = timeEnd;
		this.waterTemperature = waterTemperature;
	}

	public Infusion() {
		super();
	}

	public Infusion(Integer iD) {
		super(iD);
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public int getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(int timeStart) {
		this.timeStart = timeStart;
	}

	public int getWaterVolume() {
		return waterVolume;
	}

	public void setWaterVolume(int waterVolume) {
		this.waterVolume = waterVolume;
	}

	public int getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(int timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getWaterTemperature() {
		return waterTemperature;
	}

	public void setWaterTemperature(int waterTemperature) {
		this.waterTemperature = waterTemperature;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(recipeId, timeEnd, timeStart, waterTemperature, waterVolume);
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
		Infusion other = (Infusion) obj;
		return timeEnd == other.timeEnd && timeStart == other.timeStart && waterTemperature == other.waterTemperature
				&& waterVolume == other.waterVolume && recipeId == other.recipeId;
	}

	@Override
	public String toString() {
		return "Infusion [recipeId=" + recipeId + ", timeStart=" + timeStart + ", waterVolume=" + waterVolume
				+ ", timeEnd=" + timeEnd + ", waterTemperature=" + waterTemperature + "]";
	}

}
