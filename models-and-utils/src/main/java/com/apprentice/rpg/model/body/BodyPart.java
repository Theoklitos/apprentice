package com.apprentice.rpg.model.body;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
public final class BodyPart implements Nameable {

	private String name;

	// used by jackson
	@SuppressWarnings("unused")
	private BodyPart() {
		name = null;
	}

	/**
	 * Names are converted to a pretty, capitalized-first form. Ie "head" and "HEAd" become "Head".
	 */
	public BodyPart(final String name) {
		Checker.checkNonNull("A Body Part must have a name!", true, name);
		this.name = StringUtils.capitalize(name.toLowerCase());
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof BodyPart) {
			final BodyPart otherBodyPart = (BodyPart) other;
			return Objects.equal(getName(), otherBodyPart.getName());
		} else {
			return false;
		}
	}

	/**
	 * How is this body part named? ie. "left arm"
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName());
	}

	@Override
	public void setName(final String newName) {
		this.name = newName;
	}

	@Override
	public String toString() {
		return name;
	}

}
