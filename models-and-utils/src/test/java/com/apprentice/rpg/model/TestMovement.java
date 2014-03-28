package com.apprentice.rpg.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.apprentice.rpg.model.playerCharacter.Speed;

/**
 * test for the {@link Speed} class
 * 
 * @author theoklitos
 * 
 */
public final class TestMovement {

	@Test
	public void equality() {
		final Speed mov1 = new Speed(30);
		final Speed mov2 = new Speed(30);
		assertEquals(mov1, mov2);
		mov2.setSpeedMode("fly", 30);
		assertThat(mov1, not(equalTo(mov2)));

	}

	@Test(expected = ApprenticeEx.class)
	public void erroneousInitialization() {
		new Speed(-10);
	}

	@Test(expected = ApprenticeEx.class)
	public void wrongSetting() {
		final Speed movement = new Speed(40);
		movement.setSpeed(-1);
	}

}
