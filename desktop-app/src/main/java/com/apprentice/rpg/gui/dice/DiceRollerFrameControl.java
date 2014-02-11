package com.apprentice.rpg.gui.dice;

import org.apache.log4j.Logger;

import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.google.inject.Inject;

/**
 * Control for the {@link createDiceTextPanel}
 * 
 * @author theoklitos
 * 
 */
public class DiceRollerFrameControl implements IDiceRollerFrameControl {

	private static Logger LOG = Logger.getLogger(DiceRollerFrameControl.class);

	private final ApprenticeRandom random;
	private IDiceRollerFrame view;

	@Inject
	public DiceRollerFrameControl(final ApprenticeRandom random) {
		this.random = random;
	}

	@Override
	public int roll(final Roll roll) {
		final int result = random.roll(roll);
		LOG.info("Rolled " + roll + " and got: " + result);
		return result;
	}

	@Override
	public void setView(final IDiceRollerFrame view) {
		this.view = view;
	}

}
