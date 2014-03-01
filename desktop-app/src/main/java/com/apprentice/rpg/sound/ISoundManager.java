package com.apprentice.rpg.sound;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * Stores information about, configures and plays sounds
 * 
 * @author theoklitos
 * 
 */
public interface ISoundManager {

	/**
	 * Will play (at random) one of the several dice roll sounds
	 * 
	 */
	void playDiceRollSound();

	/**
	 * will speak the given number out loud
	 * 
	 * @thwos {@link ApprenticeEx} if the number is < 0 or > 999
	 */
	void utterNumber(final int number) throws ApprenticeEx;

}
