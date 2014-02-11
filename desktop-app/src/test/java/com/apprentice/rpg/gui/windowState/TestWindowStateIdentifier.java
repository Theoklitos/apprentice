package com.apprentice.rpg.gui.windowState;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.apprentice.rpg.gui.log.LogFrame;

/**
 * tests for the {@link WindowStateIdentifier}
 * 
 * @author theoklitos
 * 
 */
public final class TestWindowStateIdentifier {

	@Test
	public void equalityFalse() {
		final WindowStateIdentifier id1 = new WindowStateIdentifier(LogFrame.class, "log1");
		final WindowStateIdentifier id2 = new WindowStateIdentifier(LogFrame.class, "log2");
		assertThat(id1, is(not(equalTo(id2))));
	}

	@Test
	public void equalityTrue() {
		final WindowStateIdentifier id1 = new WindowStateIdentifier(LogFrame.class, "log1");
		final WindowStateIdentifier id2 = new WindowStateIdentifier(LogFrame.class, "log1");
		assertEquals(id1, id2);
	}

}
