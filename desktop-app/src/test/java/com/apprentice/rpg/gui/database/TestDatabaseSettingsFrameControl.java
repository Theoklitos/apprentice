package com.apprentice.rpg.gui.database;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.config.ITextConfigFileManager;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.events.type.DatabaseModificationEvent;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

/**
 * tests for the {@link DatabaseSettingsFrameControl}
 * 
 * @author theoklitos
 * 
 */
public final class TestDatabaseSettingsFrameControl {

	private DatabaseSettingsFrameControl control;
	private Mockery mockery;
	private Vault vault;
	private DatabaseConnection database;
	private IDatabaseSettingsFrame view;
	private IWindowManager windowManager;
	private ITextConfigFileManager textfileManager;

	@Test
	public void changeDatabaseLocation() {
		final String databaseLocation = "testLocation";
		mockery.checking(new Expectations() {
			{
				oneOf(database).setDatabase(databaseLocation);
				oneOf(textfileManager).writeDatabaseLocation(databaseLocation);
				oneOf(windowManager).closeAllFrames(false);
				oneOf(windowManager).openFrame(new WindowStateIdentifier(view.getClass()));
			}
		});
		control.changeDatabaseLocationUnsafe(databaseLocation);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void databaseEventFired() {
		mockery.checking(new Expectations() {
			{
				oneOf(vault).getAllNameables(IPlayerCharacter.class);
				oneOf(vault).getAllNameables(IType.class);
				oneOf(vault).getAllNameables(BodyPart.class);
				oneOf(view).setDatabaseDescription(with(any(List.class)));
			}
		});
		control.databaseUpdated(new DatabaseModificationEvent<BodyPart>(null));
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		view = mockery.mock(IDatabaseSettingsFrame.class);
		database = mockery.mock(DatabaseConnection.class);
		windowManager = mockery.mock(IWindowManager.class);
		textfileManager = mockery.mock(ITextConfigFileManager.class);
		vault = mockery.mock(Vault.class);
		control = new DatabaseSettingsFrameControl(database, vault, windowManager, textfileManager);
		control.setView(view);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

}
