package com.apprentice.rpg.gui.dice;

import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.util.Box;

/**
 * Wraps around a {@link Roll} and an integer (the fake result)
 * 
 * @author theoklitos
 * 
 */
public final class LoadedRoll {

	private Roll roll;
	private final int result;

	public LoadedRoll(final int result) {
		this(null, result);
	}

	public LoadedRoll(final Roll roll, final int result) {
		this.roll = roll;
		this.result = result;
	}

	public int getResult() {
		return result;
	}

	public Box<Roll> getRoll() {
		if (roll == null) {
			return Box.empty();
		} else {
			return Box.with(roll);
		}
	}

	public void setRoll(final Roll roll) {
		this.roll = roll;
	}

}
