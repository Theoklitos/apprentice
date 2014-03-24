package com.apprentice.rpg.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * Represents how much a character can move in feet
 * 
 * @author theoklitos
 * 
 */
public final class Speed {

	public final String DEFAULT_MOVEMENT_MODE = "Walk";
	private final Map<String, Integer> movementModes;

	/**
	 * sets the default "walk" type of movement
	 */
	public Speed(final int movementFeet) {
		movementModes = Maps.newHashMap();
		setSpeed(movementFeet);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Speed) {
			final Speed otherMovement = (Speed) other;
			return ApprenticeCollectionUtils.areAllElementsEqual(movementModes, otherMovement.movementModes);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(movementModes);
	}

	/**
	 * Sets the default (walk) speed
	 */
	public void setSpeed(final int feet) {
		if (feet < 0) {
			throw new ApprenticeEx("Speed must be at least 0ft, was " + feet);
		}
		movementModes.put(DEFAULT_MOVEMENT_MODE, feet);
	}

	/**
	 * Sets
	 */
	public void setSpeedMode(final String modeName, final int feet) {
		if (feet < 0) {
			throw new ApprenticeEx("Speed must be at least 0ft, was " + feet);
		}
		movementModes.put(modeName, feet);
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		result.append("Speed: " + movementModes.get(DEFAULT_MOVEMENT_MODE) + "ft");
		if (movementModes.size() == 1) {
			result.append(".");
		} else {
			final Iterator<Entry<String, Integer>> iterator = movementModes.entrySet().iterator();
			while (iterator.hasNext()) {
				final Entry<String, Integer> entry = iterator.next();
				result.append(" ," + entry.getKey() + ":" + entry.getValue() + "ft");
			}
		}
		return result.toString();
	}

}
