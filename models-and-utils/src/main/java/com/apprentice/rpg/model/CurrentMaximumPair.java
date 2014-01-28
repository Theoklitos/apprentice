package com.apprentice.rpg.model;

import com.google.common.base.Objects;

/**
 * Two connected numbers, one being a "current" value that can range between 0 and a "maximum" value. Both are
 * integers.
 * 
 * @author theoklitos
 * 
 */
public class CurrentMaximumPair {

	private int maximum;
	private int current;

	/**
	 * sets both current and maximum to =initialvalue
	 */
	public CurrentMaximumPair(final int initialValue) {
		setMaximum(initialValue);
		setCurrent(initialValue);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof CurrentMaximumPair) {
			final CurrentMaximumPair otherPair = (CurrentMaximumPair) other;
			return current == otherPair.current && maximum == otherPair.maximum;
		} else {
			return false;
		}
	}

	public int getCurrent() {
		return current;
	}

	public int getMaximum() {
		return maximum;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(new Integer(current), new Integer(maximum));
	}

	public void setCurrent(final int current) {
		if (current > maximum) {
			this.current = maximum;
		} else {
			this.current = current;
			if (this.current < 0) {
				this.current = 0;
			}
		}
	}

	public void setMaximum(final int maximum) {
		if (maximum < 0) {
			this.maximum = 0;
		} else {
			this.maximum = maximum;
		}
		if (current > this.maximum) {
			setCurrent(this.maximum);
		}
	}

	@Override
	public String toString() {
		return getCurrent() + "/" + getMaximum();
	}

}
