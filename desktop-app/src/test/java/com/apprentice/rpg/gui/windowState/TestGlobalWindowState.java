package com.apprentice.rpg.gui.windowState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.util.Collection;

import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.database.DatabaseSettingsFrame;
import com.apprentice.rpg.gui.log.LogFrame;
import com.apprentice.rpg.gui.util.IWindowUtils;

/**
 * tests for the {@link GlobalWindowState}
 * 
 * @author theoklitos
 * 
 */
public final class TestGlobalWindowState {

	private GlobalWindowState globalState;
	private IWindowUtils windowUtils;
	private Mockery mockery;

	@Test
	public void getAllStates() {
		final WindowStateIdentifier identifier = new WindowStateIdentifier(LogFrame.class, "variable");
		final WindowStateIdentifier identifier2 = new WindowStateIdentifier(LogFrame.class, "variable2");
		globalState.setWindowState(identifier, new Rectangle(), false);
		globalState.setWindowState(identifier2, new Rectangle(), false);

		final Collection<WindowStateIdentifier> allIds = globalState.getAllFrameIdentifiers();
		assertEquals(2, allIds.size());
		assertTrue(allIds.contains(identifier));
		assertTrue(allIds.contains(identifier2));
	}

	@Test
	public void getOpenFrames() {
		final WindowStateIdentifier identifier1 = new WindowStateIdentifier(LogFrame.class);
		final WindowStateIdentifier identifier2 =
			new WindowStateIdentifier(NewPlayerCharacterFrame.class, "playerName1");
		final WindowStateIdentifier identifier3 =
			new WindowStateIdentifier(NewPlayerCharacterFrame.class, "playerName2");
		globalState.setWindowState(identifier1, new Rectangle(100, 100), false);
		globalState.setWindowState(identifier2, new Rectangle(100, 100), true);
		globalState.setWindowState(identifier3, new Rectangle(100, 100), false);

		final Collection<WindowStateIdentifier> openFrames = globalState.getOpenInternalFrames();
		assertEquals(1, openFrames.size());
		assertTrue(NewPlayerCharacterFrame.class.equals(openFrames.iterator().next().getWindowClass()));
	}

	@Test
	public void setAndGetState() {
		final WindowStateIdentifier identifier = new WindowStateIdentifier(LogFrame.class, "variable");
		final Rectangle bounds = new Rectangle(1000, 300);
		final boolean isOpen = false;

		assertTrue(globalState.getWindowState(identifier).isEmpty());

		globalState.setWindowState(identifier, bounds, isOpen);

		final WindowState retrievedState = globalState.getWindowState(identifier).getContent();
		assertEquals(bounds, retrievedState.getBounds());
		assertEquals(isOpen, retrievedState.isOpen());
	}

	@Test
	public void setIsOpenGetState() {
		final WindowStateIdentifier identifier = new WindowStateIdentifier(DatabaseSettingsFrame.class);
		final Rectangle bounds = new Rectangle(100, 100);

		globalState.setWindowState(identifier, bounds, false);
		globalState.setWindowOpen(identifier, true);

		final WindowState retrievedState = globalState.getWindowState(identifier).getContent();
		assertTrue(retrievedState.isOpen());
	}

	@Test(expected = FrameNotOpenEx.class)
	public void setOpenWidnowThatDoesntExist() {
		globalState.setWindowOpen(new WindowStateIdentifier(LogFrame.class), true);
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		windowUtils = mockery.mock(IWindowUtils.class);
		globalState = new GlobalWindowState(windowUtils);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}
}
