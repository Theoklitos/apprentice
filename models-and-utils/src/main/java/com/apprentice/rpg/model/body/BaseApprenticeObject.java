package com.apprentice.rpg.model.body;

import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * has a name and a description
 * 
 * @author theoklitos
 * 
 */
public abstract class BaseApprenticeObject {

	private String name;
	private String description;

	public BaseApprenticeObject(final String name) {
		this(name, "");
	}

	public BaseApprenticeObject(final String name, final String description) {
		Checker.checkNonNull("A " + getClass().getSimpleName() + " must have a name!", true, name);
		this.name = name;
		this.description = description;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof BaseApprenticeObject) {
			final BaseApprenticeObject otherBaseItem = (BaseApprenticeObject) other;
			return Objects.equal(getName().toLowerCase(), otherBaseItem.getName().toLowerCase())
				&& Objects.equal(getDescription(), otherBaseItem.getDescription());
		} else {
			return false;
		}
	}

	public final String getDescription() {
		return description;
	}

	public final String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), getDescription());
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
