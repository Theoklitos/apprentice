package com.apprentice.rpg.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * test for the {@link Movement} class
 * 
 * @author theoklitos
 * 
 */
public final class TestMovement {

	@Test
	public void equality() {
		final Movement mov1 = new Movement(30);
		final Movement mov2 = new Movement(30);
		assertEquals(mov1, mov2);
		mov2.setMovement(29);
		assertThat(mov1, not(equalTo(mov2)));

	}

	@Test(expected = ApprenticeEx.class)
	public void erroneousInitialization() {
		new Movement(-10);
	}

	@Test(expected = ApprenticeEx.class)
	public void wrongSetting() {
		final Movement movement = new Movement(40);
		movement.setMovement(-1);
	}

}
