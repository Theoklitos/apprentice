package com.apprentice.rpg.model.body;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * has a name and a description
 * 
 * @author theoklitos
 * 
 */
public abstract class BaseApprenticeObject implements Nameable {

	private String name;
	private String description;
	private boolean isPrototype;

	public BaseApprenticeObject(final String name) {
		this(name, "");
	}

	public BaseApprenticeObject(final String name, final String description) {
		Checker.checkNonNull("A " + getClass().getSimpleName() + " must have a name!", true, name);
		this.name = name.trim();
		this.description = description.trim();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof BaseApprenticeObject) {
			final BaseApprenticeObject otherBaseItem = (BaseApprenticeObject) other;			
			return Objects.equal(getName().toLowerCase(), otherBaseItem.getName().toLowerCase())
				&& Objects.equal(getDescription(), otherBaseItem.getDescription())
				&& isPrototype == otherBaseItem.isPrototype;
		} else {
			return false;
		}
	}

	@Override
	public final String getDescription() {
		return description;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName().toLowerCase(), getDescription(), isPrototype);
	}

	@Override
	public boolean isPrototype() {
		return isPrototype;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public void setPrototype(final boolean isPrototype) {
		this.isPrototype = isPrototype;
	}
}
