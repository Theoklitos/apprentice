package com.apprentice.rpg.gui.dice;

import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.random.dice.Roll;

/**
 * Control for the {@link createDiceTextPanel}
 * 
 * @author theoklitos
 *
 */
public interface IDiceRollerFrameControl extends ControlForView<IDiceRollerFrame> {

	/**
	 * rolls the given {@link Roll} and returns the result
	 */
	int roll(Roll roll);

}
