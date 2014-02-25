package com.apprentice.rpg.model.durable;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.Ruleset;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * item with name and description, and a durability that can be changed (like hit points) that affects the
 * items dice(s)
 * 
 * @author theoklitos
 * 
 */
public class DurableItemInstance<T extends DurableItem> implements IDurableItemInstance<T> {

	private static Logger LOG = Logger.getLogger(DurableItemInstance.class);

	private final T prototype;
	private final CurrentMaximumPair hitPoints;
	private Roll originalRoll;
	private Roll currentRoll;

	private transient Ruleset ruleset;

	/**
	 * @param deteriorationIncrement
	 *            every 1/deteriorationIncrement of the total health loss, the baseRoll will deteriorate by
	 *            one cateogry
	 */
	public DurableItemInstance(final T prototype) {
		Checker.checkNonNull("No prototype defined for durable iten instance", true, prototype);
		this.prototype = prototype;
		this.hitPoints = new CurrentMaximumPair(prototype.getMaximumDurability());
		this.originalRoll = prototype.getBaseRoll();
		this.currentRoll = prototype.getBaseRoll();
	}

	@Override
	public void addHitPoints(final int hpToAdd) {
		setHitPoints(getHitPoints() + hpToAdd);
	}

	/**
	 * checks if something changed in the prototype first, then changes values
	 */
	private void adjustDurabilities() {
		if (prototype.getMaximumDurability() != hitPoints.getMaximum()) {
			hitPoints.setMaximum(prototype.getMaximumDurability());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(final Object other) {
		if (other instanceof DurableItemInstance) {
			final DurableItemInstance otherDurableItem = (DurableItemInstance) other;
			return Objects.equal(otherDurableItem.getDurability(), getDurability())
				&& Objects.equal(otherDurableItem.getCurrentRoll(), getCurrentRoll());
		} else {
			return false;
		}
	}

	/**
	 * The current state of the roll, which will vary based on deterioration
	 */
	public Roll getCurrentRoll() {
		if (!originalRoll.equals(prototype.getBaseRoll())) {
			// the prototype has changed
			originalRoll = prototype.getBaseRoll();
			updateState();
		}
		return currentRoll;
	}

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
		return getHitPointsAfterAdjusting();
	}

	@Override
	public int getHitPoints() {
		return getHitPointsAfterAdjusting().getCurrent();
	}

	/**
	 * use this to get the hitPoints object, dont access it directly
	 */
	private CurrentMaximumPair getHitPointsAfterAdjusting() {
		adjustDurabilities();
		return hitPoints;
	}

	@Override
	public String getName() {
		return getPrototype().getName();
	}

	@Override
	public T getPrototype() {
		return prototype;
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
		return Objects.hashCode(getDurability(), getCurrentRoll());
	}

	@Override
	public void removeHitPoints(final int hpToRemove) {
		setHitPoints(getHitPoints() - hpToRemove);
	}

	@Override
	public void setHitPoints(final int current) {
		final int deteriorationSteps = getDeteriorationSteps(getHitPointsAfterAdjusting());
		getHitPointsAfterAdjusting().setCurrent(current);
		updateState(deteriorationSteps);
	}

	@Override
	public void setRuleset(final Ruleset ruleset) {
		LOG.debug(prototype.getName() + " instance is using ruleset: " + ruleset);
		this.ruleset = ruleset;
		updateState();
	}

	/**
	 * updates the item's current roll, call this when there is no hit point change
	 */
	private void updateState() {
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
			String logMessage;
			final int deteriorationStepsCurrent = getDeteriorationSteps(getHitPointsAfterAdjusting());
			Roll bufferRoll = new Roll(prototype.getBaseRoll());
			if (deteriorationStepsCurrent != deteriorationStepsBeforeUpdate) {
				if (deteriorationStepsCurrent == -1) {
					// fully deteriorate
					bufferRoll = new Roll("D0" + currentRoll.getModifierAsString());
					logMessage = prototype.getName() + " has fully deteriorated (" + bufferRoll + ")";
				} else {
					bufferRoll = getRuleset().getContent().getDecreasedRoll(bufferRoll, deteriorationStepsCurrent);
					String stepWord = " steps ";
					if (deteriorationStepsCurrent == 1) {
						stepWord = " step ";
					}
					if (deteriorationStepsCurrent == 0) {
						logMessage = prototype.getName() + " is in optimal condition.";
					} else {
						logMessage =
							prototype.getName() + " is " + deteriorationStepsCurrent + stepWord
								+ "below its optimal state (" + bufferRoll + ")";
					}
				}
				this.currentRoll = bufferRoll;
				if (deteriorationStepsBeforeUpdate != -2) {
					LOG.info(logMessage);
				}
			}
		}
	}
}
