package com.apprentice.rpg;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.guice.GuiceBackendConfig;
import com.apprentice.rpg.guice.GuiceGuiConfig;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.parsing.JsonParser;
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

	public static final void main(final String args[]) {
		final JsonParser p = new JsonParser();
		final PlayerCharacter pc = new PlayerCharacter("dude", 20, null);
		LOG.info(p.getAsJsonString(pc));

		startGui();
	}

	private static void startGui() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				final Injector injector = Guice.createInjector(new GuiceBackendConfig(), new GuiceGuiConfig());
				final ApplicationConfiguration config = injector.getInstance(ApplicationConfiguration.class);
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					injector.getInstance(IMainControl.class); // this starts it all
				} catch (final Throwable e) {
					LOG.error(e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}
}
