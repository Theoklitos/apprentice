package com.apprentice.rpg.gui.database;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.config.ITextConfigFileManager;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.gui.AbstractServiceLayerTest;

/**
 * tests for the {@link DatabaseSettingsFrameControl}
 * 
 * @author theoklitos
 * 
 */
public final class TestDatabaseSettingsFrameControl extends AbstractServiceLayerTest {

	private DatabaseSettingsFrameControl control;
	private Mockery mockery;
	private DatabaseConnection database;
	private ITextConfigFileManager textfileManager;

	@Test
	public void changeDatabaseLocation() {
		final String databaseLocation = "testLocation";
		mockery.checking(new Expectations() {
			{
				oneOf(database).setDatabase(databaseLocation);
				oneOf(textfileManager).writeDatabaseLocation(databaseLocation);
				oneOf(getEventBus()).postShutdownEvent(false);
				oneOf(getEventBus()).postShowFrameEvent(DatabaseSettingsFrame.class);
			}
		});
		control.changeDatabaseLocationUnsafe(databaseLocation);
	}

	@Before
	public void setup() {
		mockery = getMockery();
		database = mockery.mock(DatabaseConnection.class);
		textfileManager = mockery.mock(ITextConfigFileManager.class);
		control = new DatabaseSettingsFrameControl(getServiceLayer(), database, textfileManager);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

}
