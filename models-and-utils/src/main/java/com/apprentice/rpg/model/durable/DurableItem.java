package com.apprentice.rpg.model.durable;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.Ruleset;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Objects;

/**
 * item with name and description, and a durability that can be changed (like hit points) that affects the
 * items dice(s)
 * 
 * @author theoklitos
 * 
 */
public abstract class DurableItem extends BaseApprenticeObject implements IDurableItem {

	private static Logger LOG = Logger.getLogger(DurableItem.class);

	private final CurrentMaximumPair hitPoints;
	private transient Ruleset ruleset;

	public DurableItem(final String name, final CurrentMaximumPair hitPoints) {
		super(name);
		this.hitPoints = hitPoints;
	}

	@Override
	public void addHitPoints(final int hpToAdd) {
		setHitPoints(hitPoints.getCurrent() + hpToAdd);
	}
	
	@Override
	public boolean equals(final Object other) {
		if (other instanceof DurableItem) {
			final DurableItem otherDurableItem = (DurableItem) other;
			return Objects.equal(otherDurableItem.getDurability(), getDurability());

		} else {
			return false;
		}
	}


	/**
	 * how many steps is this roll worse off? If the item is destroyed, returns -1
	 */
	public int getDeteriorationSteps(final CurrentMaximumPair hitPoints) {
		if (getRuleset().isEmpty()) {
			return 0;
		} else {
			if (hitPoints.getCurrent() == 0) {
				return -1;
			}
			final int increment =
				hitPoints.getMaximum() / getRuleset().getContent().getDeteriorationIncrementForType(this);
			final int difference = hitPoints.getMaximum() - hitPoints.getCurrent();
			final int stepsBelowOptimal = difference / increment;
			return stepsBelowOptimal;
		}
	}

	@Override
	public CurrentMaximumPair getDurability() {
		return hitPoints;
	}

	/**
	 * looks up the {@link Ruleset} and changes (decreases) the {@link Roll} based on the current item HPs
	 */
	public Roll getModifiedRollForDeterioration(final Roll roll) {
		final Roll copy = new Roll(roll);
		final int deteriorationSteps = getDeteriorationSteps(getDurability());
		if (deteriorationSteps == -1) {
			// fully deteriorate
			getRuleset().getContent().decreaseRollToZero(copy);
		} else if (deteriorationSteps != 0) {
			// partial deteriorate
			getRuleset().getContent().decreaseRoll(copy, deteriorationSteps);		
		}
		return copy;
	}

	/**
	 * returns a box with a {@link Ruleset} if one is defined
	 */
	public Box<Ruleset> getRuleset() {
		if (ruleset == null) {
			return Box.empty();
		} else {
			return Box.with(ruleset);
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(getDurability());
	}

	@Override
	public void removeHitPoints(final int hpToRemove) {
		setHitPoints(hitPoints.getCurrent() - hpToRemove);
	}

	@Override
	public void setHitPoints(final int current) {		
		hitPoints.setCurrent(current);		
	}
	
	@Override
	public void setRuleset(final Ruleset ruleset) {
		LOG.debug(getName() + " is using ruleset: " + ruleset);
		this.ruleset = ruleset;
	}

}
