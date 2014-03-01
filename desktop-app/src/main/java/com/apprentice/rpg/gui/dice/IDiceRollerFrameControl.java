package com.apprentice.rpg.gui.dice;

import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.random.dice.Roll;

/**
 * Control for the {@link createDiceTextPanel}
 * 
 * @author theoklitos
 *
 */
public interface IDiceRollerFrameControl extends ControlForView<IDiceRollerFrame> {

	/**
	 * plays a dice roll sound
	 */
	void playDiceRollingSound();

	/**
	 * rolls the given {@link Roll} and returns the result
	 */
	int roll(Roll roll);

	/**
	 * reads the given (positive non-zero) number via TTS
	 * 
	 * @throws ApprenticeEx if the number if 0 or lower
	 */
	void tts(int result) throws ApprenticeEx;

}
