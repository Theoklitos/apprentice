package com.apprentice.rpg.gui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.config.IApplicationConfiguration;
import com.apprentice.rpg.dao.CouldNotLoadDatabaseEx;
import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.gui.character.player.creation.INewPlayerCharacterFrameControl;
import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktop;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.apprentice.rpg.gui.log.ILogFrameControl;
import com.apprentice.rpg.gui.log.LogFrame;
import com.apprentice.rpg.gui.main.IEventBarControl;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.gui.util.WindowUtils;
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
	private final INewPlayerCharacterFrameControl newPlayerCharacterFrameControl;

	private final IApplicationConfiguration configuration;
	private final GlobalWindowState globalWindowState;
	private final DatabaseConnection database;

	@Inject
	public WindowManager(final Injector injector) {
		this.database = injector.getInstance(DatabaseConnection.class);
		initializeDatabase(injector, ApplicationConfiguration.getDefaultDatabaseFile());
		this.configuration = injector.getInstance(IApplicationConfiguration.class);
		this.globalWindowState = database.getGlobalWindowState();

		mainControl = injector.getInstance(IMainControl.class);
		desktopControl = injector.getInstance(IApprenticeDesktopControl.class);
		eventBarControl = injector.getInstance(IEventBarControl.class);
		logFrameControl = injector.getInstance(ILogFrameControl.class);
		newPlayerCharacterFrameControl = injector.getInstance(INewPlayerCharacterFrameControl.class);
	}

	/**
	 * for closures
	 */
	private final IWindowManager getReferenceToSelf() {
		return this;
	}

	/**
	 * all the necessary steps for the user to chose and load a database or simply to use the default one
	 */
	private void initializeDatabase(final Injector injector, final File databaseFile) {
		String message = "";
		try {
			database.loadDatabaseFromFile(databaseFile);
			return;
		} catch (final CouldNotLoadDatabaseEx e) {
			// corrupt, not a database file, locked
			message =
				"Internal error opening the database. Either another instance of Apprentice is using it, or it is seriously corrupt.";
		} catch (final FileNotFoundException e) {
			message = "File " + databaseFile + " does not exist.";
		}
		WindowUtils.showErrorMessage(message, "Database Load Error");
		final JFileChooser fileChooser = new JFileChooser();
		final int result = fileChooser.showDialog(null, "Choose Apprentice Database");
		if (result == JFileChooser.APPROVE_OPTION) {
			initializeDatabase(injector, fileChooser.getSelectedFile());
		} else {
			// use default
		}
	}

	@Override
	public void initializeMainFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				desktop = new ApprenticeDesktop(desktopControl);
				desktopControl.setView(desktop);
				desktopControl.setBackgroundFromConfig();
				final MainFrame mainFrame = new MainFrame(getReferenceToSelf(), mainControl, eventBarControl, desktop);
				mainFrame.setVisible(true);
			}
		});
	}

	@Override
	public void openLogFrame() {
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
	public void openNewCharacterFrame() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final NewPlayerCharacterFrame newPCFrame =
					new NewPlayerCharacterFrame(globalWindowState, newPlayerCharacterFrameControl);
				newPlayerCharacterFrameControl.setView(newPCFrame);
				desktop.add(newPCFrame);
			}
		});
	}
}
