package com.apprentice.rpg.model.body;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.parsing.jackson.TypeDeserializer;
import com.apprentice.rpg.parsing.jackson.TypeSerializer;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
@JsonSerialize(using = TypeSerializer.class)
@JsonDeserialize(using = TypeDeserializer.class)
public final class Type implements IType {

	private String name;
	private BodyPartToRangeMap parts;

	// used by jackson
	@SuppressWarnings("unused")
	private Type() {
		name = null;
		parts = null;
	}

	/**
	 * @throws BodyPartMappingEx
	 *             if the {@link BodyPartToRangeMap} is not consecutively mapped from 1 to 100
	 */
	public Type(final String name, final BodyPartToRangeMap parts) {
		Checker.checkNonNull("Type initialized with empty/null values", true, name, parts);
		this.name = name;
		setBodyPartMapping(parts);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Type) {
			final Type type = (Type) other;
			return Objects.equal(getName(), type.getName()) && Objects.equal(getParts(), type.getParts());
		} else {
			return false;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the body part for the given number. If you pass a number between 1 and 100, a result is
	 * guarantedd to be returned.
	 * 
	 * @ throws {@link BodyPartMappingEx} if a number outside 1-100 is given
	 */
	public BodyPart getPartForNumber(final int number) throws ApprenticeEx {
		final List<BodyPart> parts = this.parts.getPartsForNumber(number);
		if (parts.size() == 1) {
			return parts.get(0);
		} else {
			// this cannot happen, but just in case...
			throw new BodyPartMappingEx("Type \"" + name + "\" had either 0 or >1 mappings for number " + number
				+ "! Check your type verification code.");
		}
	}

	@Override
	public BodyPartToRangeMap getParts() {
		return parts;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), getParts());
	}

	@Override
	public void setBodyPartMapping(final BodyPartToRangeMap mapping) throws BodyPartMappingEx {
		verifyMapping(mapping);
		this.parts = mapping;
	}

	@Override
	public void setName(final String newName) {
		this.name = newName;
	}

	@Override
	public String toString() {
		return name + ", has bodyparts: " + parts.toString();
	}

	/**
	 * verifies that parts have consecutive ranges from 1 to 100
	 * 
	 * @throw throws BodyPartMappingEx
	 */
	private void verifyMapping(final BodyPartToRangeMap mapping) throws BodyPartMappingEx {
		if (mapping.getInternalMapping().firstKey().getMin() != 1) {
			throw new BodyPartMappingEx("Body Part mapping must start from 1");
		}
		if (mapping.getInternalMapping().lastKey().getMax() != 100) {
			throw new BodyPartMappingEx("Body Part mapping must end at 100");
		}
		for (int i = 1; i < 101; i++) {
			if (mapping.getPartsForNumber(i).isEmpty()) {
				throw new BodyPartMappingEx("Body Part has no mapping for number " + i);
			}
		}
	}

}
