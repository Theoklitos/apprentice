package com.apprentice.rpg.model.durable;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.Ruleset;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Joiner;
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
	 * the rolls that can be affected by durability
	 */
	public abstract Collection<Roll> getDeterioratableRolls();

	/**
	 * how many steps is this roll worse off? If the item is destroyed, returns -1
	 */
	private int getDeteriorationSteps(final CurrentMaximumPair hitPoints) {
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
		final int deteriorationSteps = getDeteriorationSteps(hitPoints);
		hitPoints.setCurrent(current);
		updateState(deteriorationSteps);
	}

	@Override
	public void setRuleset(final Ruleset ruleset) {
		LOG.debug(getName() + " is using ruleset: " + ruleset);
		this.ruleset = ruleset;
		updateRollState();
	}

	/**
	 * updates the item's current roll, call this when there is no hit point change
	 */
	public void updateRollState() {
		updateState(-2); // so that an update is triggered
	}

	/**
	 * updates the item's current roll
	 * 
	 * @param deteriorationStepsBeforeUpdate
	 *            -1 means full deterioration, -2 means no log.
	 */
	private void updateState(final int deteriorationStepsBeforeUpdate) {
		if (getRuleset().isEmpty()) {
			return; // no roll change
		} else {
			final int deteriorationStepsCurrent = getDeteriorationSteps(hitPoints);
			if (deteriorationStepsCurrent != deteriorationStepsBeforeUpdate) {
				final Collection<Roll> rollsToModify = getDeterioratableRolls();				
				for (final Roll roll : rollsToModify) {
					if (deteriorationStepsCurrent == -1) {
						// fully deteriorate
						getRuleset().getContent().decreaseRollToZero(roll);
					} else if (deteriorationStepsCurrent != 0) {
						// partial deteriorate
						getRuleset().getContent().decreaseRoll(roll, deteriorationStepsCurrent);
					}
				}
				// also log a nice message
				if (deteriorationStepsBeforeUpdate != -2) {
					String logMessage;
					if (deteriorationStepsCurrent == -1) {
						logMessage = getName() + " has fully deteriorated (" + Joiner.on(",").join(rollsToModify) + ")";
					} else {
						String stepWord = " steps ";
						if (deteriorationStepsCurrent == 1) {
							stepWord = " step ";
						}
						if (deteriorationStepsCurrent == 0) {
							logMessage = getName() + " is in optimal condition.";
						} else {
							logMessage =
								getName() + " is " + deteriorationStepsCurrent + stepWord + "below its optimal state ("
									+ Joiner.on(",").join(rollsToModify) + ")";
						}
					}
					LOG.info(logMessage);
				}
			}
		}
	}
}
