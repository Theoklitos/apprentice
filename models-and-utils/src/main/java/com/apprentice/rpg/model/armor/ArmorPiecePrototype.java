package com.apprentice.rpg.model.armor;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.durable.DurableItemPrototype;
import com.apprentice.rpg.random.dice.Roll;

/**
 * A piece of armor that is bound to a specific {@link BodyPart}
 * 
 * @author theoklitos
 * 
 */
public class ArmorPiecePrototype extends DurableItemPrototype implements ArmorPiece {

	private String bodyPartDesignator;

	public ArmorPiecePrototype(final String name, final int durability, final Roll baseRoll, final String bodyPartDesignator) {
		super(name, durability, baseRoll);
		this.bodyPartDesignator = bodyPartDesignator;
	}

	@Override
	public boolean fits(final BodyPart bodyPart) {
		return StringUtils.containsIgnoreCase(bodyPart.getName(), getBodyPartDesignator());
	}

	@Override
	public String getBodyPartDesignator() {
		return bodyPartDesignator;
	}

	@Override
	public void setBodyPartDesignator(final String bodyPartDesignator) {
		this.bodyPartDesignator = bodyPartDesignator;
	}

	@Override
	public String toString() {
		return getName() + ", base DR: " + getBaseRoll() + ", designated for \"" + getBodyPartDesignator()
			+ "\". Max dur.: " + getMaximumDurability();
	}

}
