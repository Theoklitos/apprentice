package com.apprentice.rpg.sound;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * When anything related to sound, playback, tts etc goes wrong
 * 
 * @author theoklitos
 * 
 */
public class ApprenticeSoundEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public ApprenticeSoundEx(final String message) {
		super(message);
	}

	public ApprenticeSoundEx(final Throwable e) {
		super(e);
	}

}
