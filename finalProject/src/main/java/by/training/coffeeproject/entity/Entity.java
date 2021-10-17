package by.training.coffeeproject.entity;

import java.io.Serializable;
import java.util.Objects;

abstract public class Entity implements Serializable {

	private static final long serialVersionUID = -8943562233359762742L;

	private Integer ID;

	public Entity(Integer iD) {
		super();
		ID = iD;
	}

	public Entity() {
		super();
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return Objects.equals(ID, other.ID);
	}

	@Override
	public String toString() {
		return "Entity [ID=" + ID + "]";
	}

}
