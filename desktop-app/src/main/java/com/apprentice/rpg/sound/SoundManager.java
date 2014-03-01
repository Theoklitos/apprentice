package com.apprentice.rpg.sound;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.random.ApprenticePseudoRandom;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.google.inject.Inject;

/**
 * Stores information about, configures and plays sounds
 * 
 * @author theoklitos
 * 
 */
public final class SoundManager implements ISoundManager {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(SoundManager.class);

	private final static String AUDIO_NUMBERS_FOLDER = "audioNumbers";
	private final static String SOUND_EFFECTS_FOLDER = "soundEffects";
	private final static String DICEROLL_SOUND_FILENAME_PREFIX = "roll";

	public final static void main(final String args[]) {
		new SoundManager(new ApprenticePseudoRandom()).utterNumber(13);
	}

	private final AudioPlayer player;
	private final ApprenticeRandom random;

	@Inject
	public SoundManager(final ApprenticeRandom random) {
		this.random = random;
		player = new AudioPlayer();
	}

	@Override
	public void playDiceRollSound() {
		final int fileNumber = random.getRandomInteger(7) + 1;
		final URL fileLocation =
			ClassLoader.getSystemResource(SOUND_EFFECTS_FOLDER + File.separator + DICEROLL_SOUND_FILENAME_PREFIX
				+ fileNumber + ".mp3");
		player.play(fileLocation);
	}

	@Override
	public void utterNumber(final int number) throws ApprenticeEx {
		if (number < 0 || number > 999) {
			throw new ApprenticeEx("Text to speech supports values from 0 to 999 only.");
		}
		final int hundreds = number / 100;
		if (hundreds > 0) {
			// play hundreds first
			final URL hundredsFileLocation =
				ClassLoader.getSystemResource(SOUND_EFFECTS_FOLDER + File.separator + String.valueOf(hundreds)
					+ "00.wav");
			player.play(hundredsFileLocation);
		}
		final URL fileLocation =
			ClassLoader.getSystemResource(AUDIO_NUMBERS_FOLDER + File.separator + String.valueOf(number) + ".wav");
		player.play(fileLocation);
	}

}
