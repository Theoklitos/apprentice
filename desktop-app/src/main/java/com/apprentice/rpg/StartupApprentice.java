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
import com.apprentice.rpg.gui.main.IMainControl;
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
	private static String chooseDatabaseLocation() {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select Database");
		fileChooser.setApproveButtonText("Use this Database");
		final int result = fileChooser.showDialog(null, null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getAbsolutePath();
		} else {
			if (WindowUtils
					.showWarningQuestionMessage(
							"Apprentice needs a database!\nDo you want to go back and select or create a new one?\nOtherwise the program will terminate.",
							"Database")) {
				return chooseDatabaseLocation();
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
	private static String determineDatabaseLocation() {
		// look for text config file
		final Box<File> configFile = TextConfigFileManager.getConfigFileBasedOnOS();
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
				databasePath = getDatabaseLocationWhenExpectedFileWasNotFound(databasePath);
			}
		}
		return databasePath;
	}

	/**
	 * We expected to find a db file, but nothing was there. Ask.
	 */
	private static String getDatabaseLocationWhenExpectedFileWasNotFound(final String expectedDatabasePath) {
		LOG.warn("Expected database  " + expectedDatabasePath + " not found!");
		final int response =
			WindowUtils
					.showQuestionMessage(
							"Could not find a database file!\nWould you like to initialize a new new (emtpy) one, or locate a database file yourself?\nNote that the database location can be changed at any time.",
							new String[] { "New", "Find Existing" }, "No Database Found");
		if (response == 0) {
			return expectedDatabasePath;
		} else {
			return chooseDatabaseLocation();
		}
	}

	/**
	 * Initializes the database (showing various popups and warnings if the location is not ok) and creates
	 * the {@link Injector}
	 */
	private static Injector initializeDependencies(final String databasePath) {
		EmbeddedObjectContainer container = null;
		try {
			//final EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
			//configuration.common().updateDepth(Integer.MAX_VALUE);
			container = Db4oEmbedded.openFile(databasePath);
		} catch (final Db4oException e) {
			WindowUtils
					.showErrorMessage(
							"Either your database is currently being used, or is seriouly corrupted, or you didn't choose a database file!\nYou need to either select or create a database.",
							"Database Load Error");
			return initializeDependencies(chooseDatabaseLocation());
		}
		return Guice.createInjector(new GuiceConfigBackend(container, databasePath), new GuiceConfigGui());
	}

	public static final void main(final String args[]) {
		final String dabtabasePath = determineDatabaseLocation();
		final Injector injector = initializeDependencies(dabtabasePath);
		TextConfigFileManager.writeDatabaseLocation(dabtabasePath);
		startGui(injector);
	}

	private static void startGui(final Injector injector) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					injector.getInstance(IMainControl.class); // this starts it all!
				} catch (final Throwable e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
