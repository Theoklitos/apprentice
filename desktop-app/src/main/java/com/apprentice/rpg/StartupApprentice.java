package com.apprentice.rpg;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.config.ITextConfigFileManager;
import com.apprentice.rpg.config.TextConfigFileManager;
import com.apprentice.rpg.gui.FrameAlreadyOpenEx;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.guice.GuiceConfigGui;
import com.apprentice.rpg.model.guice.GuiceConfigBackend;
import com.apprentice.rpg.util.Box;
import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ext.Db4oException;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Init class for the apprentice desktop app, contains the main() method
 * 
 * @author theoklitos
 * 
 */
public final class StartupApprentice {

	private static Logger LOG = Logger.getLogger(StartupApprentice.class);

	/**
	 * Uses a {@link JFileChooser} to get a database location
	 */
	private static String chooseDatabaseLocation(final IWindowUtils windowUtils) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Database");
		fileChooser.setApproveButtonText("Use this Database");
		final int result = fileChooser.showDialog(null, null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		} else {
			if (windowUtils
					.showWarningQuestionMessage(
							"Apprentice needs a database!\nDo you want to go back and select or create a new one?\nOtherwise the program will terminate.",
							"Database")) {
				return chooseDatabaseLocation(windowUtils);
			} else {
				LOG.info("Shut down due to lack of database.");
				System.exit(0);
			}
		}
		return ""; // kinda unreachable
	}

	/**
	 * figures out where the database should be at
	 */
	private static String determineDatabaseLocation(final IWindowUtils windowUtils,
			final ITextConfigFileManager textfileManager) {
		// look for text config file
		final Box<File> configFile = textfileManager.getConfigFileBasedOnOS();
		String databasePath = null;
		// use it to load the db
		if (configFile.hasContent() && configFile.getContent().exists()) {
			final Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(configFile.getContent()));
				databasePath = properties.getProperty(TextConfigFileManager.DATABASE_PROPERTY_IN_CONFIG);
				LOG.debug("Database location was set to " + databasePath);
			} catch (final FileNotFoundException e) {
				// code should proceed, a new file will be chosen
			} catch (final IOException e) {
				// likewise above
			}
		}
		if (StringUtils.isEmpty(databasePath)) {
			databasePath = ApprenticeConfiguration.getDefaultDatabasePath();
			if (!new File(databasePath).exists()) {
				databasePath = getDatabaseLocationWhenExpectedFileWasNotFound(databasePath, windowUtils);
			}
		}
		return databasePath;
	}

	/**
	 * We expected to find a db file, but nothing was there. Ask.
	 */
	private static String getDatabaseLocationWhenExpectedFileWasNotFound(final String expectedDatabasePath,
			final IWindowUtils windowUtils) {
		LOG.warn("Expected database  " + expectedDatabasePath + " not found!");
		final int response =
			windowUtils
					.showQuestionMessage(
							"Could not find a database file!\nWould you like to initialize a new new (emtpy) one, or locate a database file yourself?\nNote that the database location can be changed at any time.",
							new String[] { "New", "Find Existing" }, "No Database Found");
		if (response == 0) {
			return expectedDatabasePath;
		} else {
			return chooseDatabaseLocation(windowUtils);
		}
	}

	/**
	 * Used to catch exceptions from the EventQueue
	 * 
	 * @param windowUtils
	 */
	private static void handleUncaughtExceptions(final IWindowUtils windowUtils) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(final Thread thread, final Throwable e) {
				if (e.getClass().equals(FrameAlreadyOpenEx.class)) {
					// this is "expected", do nothing
					return;
				}
				if (StringUtils.isNotBlank(e.getMessage())) {
					windowUtils.showErrorMessage(e.getMessage());
				} else {
					windowUtils.showErrorMessage("Null pointer exception!");
				}
				LOG.error("Uncaught Exception!", e);
			}
		});
	}

	/**
	 * Initializes the database (showing various popups and warnings if the location is not ok) and creates
	 * the {@link Injector}
	 */
	private static Injector initializeDependencies(final String databasePath, final IWindowUtils windowUtils) {
		EmbeddedObjectContainer container = null;
		try {
			container = Db4oEmbedded.openFile(databasePath);
		} catch (final Db4oException e) {
			windowUtils
					.showErrorMessage(
							"Either your database is currently being used, or is seriouly corrupted, or you didn't choose a database file!\nYou need to either select or create a database.",
							"Database Load Error");
			return initializeDependencies(chooseDatabaseLocation(windowUtils), windowUtils);
		}
		return Guice.createInjector(new GuiceConfigBackend(container, databasePath), new GuiceConfigGui());
	}

	public static final void main(final String args[]) {
		final IWindowUtils windowUtils = new WindowUtils();
		final ITextConfigFileManager textfileManager = new TextConfigFileManager();

		final String dabtabasePath = determineDatabaseLocation(windowUtils, textfileManager);
		final Injector injector = initializeDependencies(dabtabasePath, windowUtils);
		textfileManager.writeDatabaseLocation(dabtabasePath);
		handleUncaughtExceptions(windowUtils);
		startGui(injector);
	}

	private static void startGui(final Injector injector) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					injector.getInstance(IWindowManager.class).initializeMainFrame(); // this starts it all!
				} catch (final Throwable e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
