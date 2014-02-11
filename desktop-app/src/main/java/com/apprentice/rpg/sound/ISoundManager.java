package com.apprentice.rpg.sound;

/**
 * Stores information about, configures and plays sounds
 * 
 * @author theoklitos
 * 
 */
public interface ISoundManager {

	/**
	 * Will play (at random) one of the several dice roll sounds
	 */
	void playDiceRollSound();

	/**
	 * will speak the given number out loud
	 */
	void utterNumber(final int number);

}
