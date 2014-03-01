package com.apprentice.rpg.model.armor;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.durable.DurableItem;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * A piece of armor that is bound to a specific {@link BodyPart}
 * 
 * @author theoklitos
 * 
 */
public class ArmorPiece extends DurableItem implements ArmorPieceInstance, ArmorPiecePrototype {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ArmorPiece.class);

	private String bodyPartDesignator;
	private Roll damageReduction;

	public ArmorPiece(final String name, final int maxDurability, final Roll daamgeReduction,
			final String bodyPartDesignator) {
		super(name, new CurrentMaximumPair(maxDurability));
		this.bodyPartDesignator = bodyPartDesignator;
		this.damageReduction = daamgeReduction;
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
		return StringUtils.containsIgnoreCase(bodyPart.getName(), getBodyPartDesignator());
	}

	@Override
	public String getBodyPartDesignator() {
		return bodyPartDesignator;
	}

	@Override
	public Roll getDamageReductionRoll() {
		return damageReduction;
	}

	@Override
	public Collection<Roll> getDeterioratableRolls() {
		return Sets.newHashSet(damageReduction);
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(bodyPartDesignator);
	}

	@Override
	public int rollDamageReduction(final ApprenticeRandom random) {
		return random.roll(damageReduction);
	}

	@Override
	public void setBodyPartDesignator(final String bodyPartDesignator) {
		this.bodyPartDesignator = bodyPartDesignator;
	}

	@Override
	public void setDamageReductionRoll(final Roll newRoll) {
		this.damageReduction = newRoll;
		updateRollState();
	}

	@Override
	public String toString() {
		return getName() + ", base DR: " + getDamageReductionRoll() + ", designated for \"" + getBodyPartDesignator()
			+ "\". HP: " + getDurability();
	}

}
