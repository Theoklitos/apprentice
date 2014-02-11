package com.apprentice.rpg.sound;

import com.google.inject.Inject;
/**
 * Stores information about, configures and plays sounds
 * 
 * @author theoklitos
 * 
 */
public final class SoundManager implements ISoundManager {

	public final static void main(final String args[]) {
		new SoundManager().playDiceRollSound();
	}

	private final AudioPlayer player;

	@Inject
	public SoundManager() {
		player = new AudioPlayer();
	}
		
	@Override
	public void playDiceRollSound() {
		player.play("/home/theoklitos/roll1.mp3");		
	}

	@Override
	public void utterNumber(final int number) {
		
	}

}
