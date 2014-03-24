package com.apprentice.rpg.gui;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.GuiceConfigBackendForVault;
import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.config.IApprenticeConfiguration;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.PublishSubscribeEventBus;
import com.apprentice.rpg.events.database.DataSynchronizer;
import com.apprentice.rpg.events.database.IDataSynchronizer;
import com.apprentice.rpg.gui.database.DatabaseSettingsFrameControl;
import com.apprentice.rpg.gui.database.IDatabaseSettingsFrameControl;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktopControl;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.apprentice.rpg.gui.log.ILogFrameControl;
import com.apprentice.rpg.gui.log.LogFrameControl;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.gui.main.MainControl;
import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.vault.type.ITypeAndBodyPartFrameControl;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrame;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrameControl;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;

/**
 * tests for the {@link WindowManager}
 * 
 * @author theoklitos
 * 
 */
public final class TestWindowManager {

	private WindowManager manager;
	private Mockery mockery;
	private IGlobalWindowState globalWindowState;
	private Vault vault;
	private DatabaseConnection database;

	private Injector createInjectorForMockedGlobalWindowState() {
		final Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(IWindowManager.class).to(WindowManager.class);
				bind(IWindowUtils.class).to(WindowUtils.class);
				bind(ApprenticeEventBus.class).to(PublishSubscribeEventBus.class).in(Scopes.SINGLETON);
				bind(IDataSynchronizer.class).to(DataSynchronizer.class);
				bind(IApprenticeDesktopControl.class).to(ApprenticeDesktopControl.class);
				bind(IMainControl.class).to(MainControl.class);
				bind(IDatabaseSettingsFrameControl.class).to(DatabaseSettingsFrameControl.class);
				bind(ITypeAndBodyPartFrameControl.class).to(TypeAndBodyPartFrameControl.class);
				bind(IApprenticeConfiguration.class).to(ApprenticeConfiguration.class);
				bind(IGlobalWindowState.class).toInstance(globalWindowState);

				bind(ILogFrameControl.class).toInstance(
						(LogFrameControl) Logger.getRootLogger().getAppender("logFrame"));				

				install(new GuiceConfigBackendForVault(database, vault));
			}
		});
		return injector;
	}

	@Test
	public void restoreClosedWindowsOnStartup() throws InterruptedException {		
		final Injector injector = createInjectorForMockedGlobalWindowState();
		final Collection<WindowStateIdentifier> openFrameIds = Sets.newHashSet();
		final WindowStateIdentifier identifier = new WindowStateIdentifier(TypeAndBodyPartFrame.class);
		openFrameIds.add(identifier);
		
		mockery.checking(new Expectations() {
			{
				oneOf(globalWindowState).getOpenInternalFrames();
				will(returnValue(openFrameIds));
				oneOf(globalWindowState).setWindowOpen(identifier, false);
			}
		});
		manager = new WindowManager(injector);
		manager.restoreOpenFrames();		
	}

	@Before
	public void setup() {		
		mockery = new Mockery();
		globalWindowState = mockery.mock(IGlobalWindowState.class);
		database = mockery.mock(DatabaseConnection.class);
		vault = mockery.mock(Vault.class);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}
}
