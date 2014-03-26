package com.apprentice.rpg.gui;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Rectangle;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.log.ILogFrameControl;
import com.apprentice.rpg.gui.log.LogFrameControl;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrame;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.gui.windowState.WindowState;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.util.Box;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

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
	private IMainControl mainControl;
	private ApprenticeEventBus eventBus;
	private Injector injector;

	private Injector createInjectorForMockedGlobalWindowState() {
		final Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(IMainControl.class).toInstance(mainControl);
				bind(IGlobalWindowState.class).toInstance(globalWindowState);
				bind(ApprenticeEventBus.class).toInstance(eventBus);
				bind(ILogFrameControl.class).toInstance(
						(LogFrameControl) Logger.getRootLogger().getAppender("logFrame"));
			}
		});
		return injector;
	}

	@Test
	public void restoreClosedWindowsOnStartup() {
		final Collection<WindowStateIdentifier> openFrameIds = Sets.newHashSet();
		final WindowStateIdentifier identifier = new WindowStateIdentifier(TypeAndBodyPartFrame.class);
		openFrameIds.add(identifier);
		final WindowState state = new WindowState(new Rectangle(), false);

		mockery.checking(new Expectations() {
			{
				oneOf(globalWindowState).getOpenInternalFrames();
				will(returnValue(openFrameIds));
				allowing(globalWindowState).getWindowState(new WindowStateIdentifier(TypeAndBodyPartFrame.class));
				will(returnValue(Box.with(state)));
				oneOf(globalWindowState).setWindowOpen(identifier, false);
			}
		});

		try {
			manager.restoreOpenFrames();
			fail("exception not thrown");
		} catch (final ApprenticeEx e) {
			assertTrue(e.getMessage().contains("ITypeAndBodyPartFrameControl was bound"));
		}
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		globalWindowState = mockery.mock(IGlobalWindowState.class);
		mainControl = mockery.mock(IMainControl.class);
		eventBus = mockery.mock(ApprenticeEventBus.class);
		mockery.checking(new Expectations() {
			{
				allowing(mainControl).getEventBus();
				will(returnValue(eventBus));
				allowing(eventBus).register(with(any(Object.class)));
			}
		});
		injector = createInjectorForMockedGlobalWindowState();
		manager = new WindowManager(injector);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}
}
