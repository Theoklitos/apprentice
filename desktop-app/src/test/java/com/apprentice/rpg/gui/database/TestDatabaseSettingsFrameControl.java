package com.apprentice.rpg.gui.database;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.config.ITextConfigFileManager;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.AbstractServiceLayerTest;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

/**
 * tests for the {@link DatabaseSettingsFrameControl}
 * 
 * @author theoklitos
 * 
 */
public final class TestDatabaseSettingsFrameControl extends AbstractServiceLayerTest{

	private IServiceLayer serviceLayer;
	private Mockery mockery;
	private Vault vault;
	private DatabaseConnection database;
	private IDatabaseSettingsFrame view;
	private IWindowManager windowManager;
	private ITextConfigFileManager textfileManager;
	private ApprenticeEventBus eventBus;

	@Test
	public void changeDatabaseLocation() {
		final String databaseLocation = "testLocation";
		mockery.checking(new Expectations() {
			{
				oneOf(database).setDatabase(databaseLocation);
				oneOf(textfileManager).writeDatabaseLocation(databaseLocation);
				
			}
		});
		//serviceLayer.changeDatabaseLocationUnsafe(databaseLocation);
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
		//serviceLayer.databaseChanged(new ApprenticeEvent(EventType.CREATE,null));
	}

	@Before
	public void setup() {
		mockery = getMockery();
		view = mockery.mock(IDatabaseSettingsFrame.class);
		database = mockery.mock(DatabaseConnection.class);
		windowManager = mockery.mock(IWindowManager.class);
		serviceLayer = mockery.mock(IServiceLayer.class);
		textfileManager = mockery.mock(ITextConfigFileManager.class);
		vault = mockery.mock(Vault.class);
		eventBus = mockery.mock(ApprenticeEventBus.class);		
		serviceLayer = new DatabaseSettingsFrameControl(serviceLayer, windowManager, database, textfileManager);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

}
