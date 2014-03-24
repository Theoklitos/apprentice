package com.apprentice.rpg.gui.dice;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.sound.ISoundManager;
import com.google.inject.Inject;

/**
 * Control for the {@link createDiceTextPanel}
 * 
 * @author theoklitos
 * 
 */
public class DiceRollerFrameControl extends AbstractControlForView<IDiceRollerFrame> implements IDiceRollerFrameControl {

	private final ApprenticeRandom random;	
	private final ISoundManager soundManager;

	@Inject
	public DiceRollerFrameControl(final IServiceLayer serviceLayer, final ApprenticeRandom random,
			final ISoundManager soundManager) {
		super(serviceLayer);
		this.random = random;
		this.soundManager = soundManager;
	}

	@Override
	public void playDiceRollingSound() {
		soundManager.playDiceRollSound();
	}

	@Override
	public int roll(final Roll roll) {
		final int result = random.roll(roll);		
		return result;		
	}

	@Override
	public void tts(final int result) throws ApprenticeEx {
		soundManager.utterNumber(result);
	}

}
