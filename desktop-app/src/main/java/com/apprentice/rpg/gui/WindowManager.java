package com.apprentice.rpg.gui;

import java.awt.EventQueue;

import com.apprentice.rpg.config.IApprenticeConfiguration;
import com.apprentice.rpg.database.DatabaseConnection;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.database.IDataSynchronizer;
import com.apprentice.rpg.gui.character.player.creation.INewPlayerCharacterFrameControl;
import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.database.DatabaseSettingsFrame;
import com.apprentice.rpg.gui.database.IDatabaseSettingsFrameControl;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktop;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.apprentice.rpg.gui.log.ILogFrameControl;
import com.apprentice.rpg.gui.log.LogFrame;
import com.apprentice.rpg.gui.main.IEventBarControl;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.gui.vault.type.ITypeAndBodyPartFrameControl;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrame;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Guiced up factory for frames and windows, maintains list of active windows
 * 
 * @author theoklitos
 * 
 */
public final class WindowManager implements IWindowManager {

	private ApprenticeDesktop desktop;
	private final IMainControl mainControl;
	private final IApprenticeDesktopControl desktopControl;
	private final IEventBarControl eventBarControl;
	private final ILogFrameControl logFrameControl;
	private final IDatabaseSettingsFrameControl databaseSettingsFrameControl;
	private final INewPlayerCharacterFrameControl newPlayerCharacterFrameControl;
	private final ITypeAndBodyPartFrameControl typeAndBodyPartFrameControl;

	private final IApprenticeConfiguration configuration;
	private final IGlobalWindowState globalWindowState;
	private final ApprenticeParser parser;
	private final DatabaseConnection database;
	private final Injector injector;
	private final ApprenticeEventBus eventBus;

	@Inject
	public WindowManager(final Injector injector) {
		this.injector = injector;
		database = injector.getInstance(DatabaseConnection.class);
		configuration = injector.getInstance(IApprenticeConfiguration.class);
		globalWindowState = injector.getInstance(IGlobalWindowState.class);
		parser = injector.getInstance(ApprenticeParser.class);
		eventBus = injector.getInstance(ApprenticeEventBus.class);

		mainControl = injector.getInstance(IMainControl.class);
		desktopControl = injector.getInstance(IApprenticeDesktopControl.class);
		eventBarControl = injector.getInstance(IEventBarControl.class);
		logFrameControl = injector.getInstance(ILogFrameControl.class);
		databaseSettingsFrameControl = injector.getInstance(IDatabaseSettingsFrameControl.class);
		newPlayerCharacterFrameControl = injector.getInstance(INewPlayerCharacterFrameControl.class);
		typeAndBodyPartFrameControl = injector.getInstance(ITypeAndBodyPartFrameControl.class);
		registerEventHandlers();
	}

	/**
	 * for closures
	 */
	private final IWindowManager getReferenceToSelf() {
		return this;
	}

	@Override
	public void initializeMainFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				desktop = new ApprenticeDesktop(desktopControl);
				desktopControl.setView(desktop);
				desktopControl.setBackgroundFromConfig();
				final MainFrame mainFrame =
					new MainFrame(globalWindowState, getReferenceToSelf(), mainControl, eventBarControl, desktop);
				mainFrame.setVisible(true);
			}
		});
	}

	/**
	 * registers handlers on the {@link ApprenticeEventBus}
	 */
	private void registerEventHandlers() {
		eventBus.register(databaseSettingsFrameControl);
		eventBus.register(newPlayerCharacterFrameControl);
		eventBus.register(typeAndBodyPartFrameControl);
		eventBus.register(injector.getInstance(IDataSynchronizer.class));
	}

	@Override
	public void showDatabaseSettingsFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final DatabaseSettingsFrame databaseFrame =
					new DatabaseSettingsFrame(globalWindowState, databaseSettingsFrameControl);
				databaseSettingsFrameControl.setView(databaseFrame);
				desktop.add(databaseFrame);
			}
		});
	}

	@Override
	public void showLogFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final LogFrame logFrame = new LogFrame(globalWindowState);
				logFrameControl.setView(logFrame);
				desktop.add(logFrame);
			}
		});
	}

	@Override
	public void showNewCharacterFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final NewPlayerCharacterFrame newPCFrame =
					new NewPlayerCharacterFrame(globalWindowState, newPlayerCharacterFrameControl, parser);
				newPlayerCharacterFrameControl.setView(newPCFrame);
				desktop.add(newPCFrame);
			}
		});
	}

	@Override
	public void showTypeAndBodyPartFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final TypeAndBodyPartFrame tbpFrame =
					new TypeAndBodyPartFrame(globalWindowState, typeAndBodyPartFrameControl);
				typeAndBodyPartFrameControl.setView(tbpFrame);
				desktop.add(tbpFrame);
			}
		});

	}
}
