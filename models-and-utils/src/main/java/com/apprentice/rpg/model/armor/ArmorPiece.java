package com.apprentice.rpg.model.armor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.durable.DurableItem;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.random.dice.RollWithSuffix;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Objects;
import com.rits.cloning.Cloner;

/**
 * A piece of armor that is bound to a specific {@link BodyPart}
 * 
 * @author theoklitos
 * 
 */
public class ArmorPiece extends DurableItem implements IArmorPiece {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ArmorPiece.class);

	private String bodyPartDesignator;
	private RollWithSuffix damageReduction;

	/**
	 * if this is a miscellaneous type of armor, ie ring or amulet
	 */
	public ArmorPiece(final String name, final int maxDurability, final RollWithSuffix damageReduction) {
		this(name, maxDurability, damageReduction, null);
	}

	public ArmorPiece(final String name, final int maxDurability, final RollWithSuffix damageReduction,
			final String bodyPartDesignator) {
		super(name, new CurrentMaximumPair(maxDurability));
		this.bodyPartDesignator = bodyPartDesignator;
		this.damageReduction = damageReduction;
	}

	@Override
	public IArmorPiece clone() {
		if (!isPrototype()) {
			throw new ApprenticeEx("Tried to clone non-protoype armor " + getName());
		}
		final IArmorPiece clone = new Cloner().deepClone(this);
		clone.setPrototype(false);
		return clone;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof ArmorPiece) {
			final ArmorPiece otherArmorPiece = (ArmorPiece) other;
			return super.equals(otherArmorPiece)
				&& Objects.equal(bodyPartDesignator, otherArmorPiece.bodyPartDesignator);
		} else {
			return false;
		}
	}

	@Override
	public boolean fits(final BodyPart bodyPart) {
		if (getBodyPartDesignator().isEmpty()) {
			return true;
		} else {
			return StringUtils.containsIgnoreCase(bodyPart.getName(), getBodyPartDesignator().getContent());
		}
	}

	@Override
	public Box<String> getBodyPartDesignator() {
		if (StringUtils.isBlank(bodyPartDesignator)) {
			return Box.empty();
		} else {
			return Box.with(bodyPartDesignator);
		}
	}

	@Override
	public RollWithSuffix getDamageReduction() {
		final Roll newRoll = getModifiedRollForDeterioration(damageReduction.getRoll());
		return new RollWithSuffix(newRoll, damageReduction.getSuffix());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(bodyPartDesignator);
	}

	@Override
	public void setBodyPartDesignator(final String bodyPartDesignator) {
		this.bodyPartDesignator = bodyPartDesignator;
	}

	@Override
	public void setDamageReductionRoll(final RollWithSuffix newRoll) {
		this.damageReduction = newRoll;
	}

	@Override
	public String toString() {
		String designationString = ", non-designated"; 
		if(getBodyPartDesignator().hasContent()) {
			designationString = ", designated for \"" + getBodyPartDesignator().getContent() + "\"";
		}
		return getName() + ", base DR: " + getDamageReduction() + designationString + ". HP: " + getDurability();
	}

}
