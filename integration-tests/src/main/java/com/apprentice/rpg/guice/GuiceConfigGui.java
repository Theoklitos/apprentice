package com.apprentice.rpg.guice;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.DatabaseConnection;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.WindowManager;
import com.apprentice.rpg.gui.character.player.creation.INewPlayerCharacterFrameControl;
import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrameControl;
import com.apprentice.rpg.gui.database.DatabaseSettingsFrameControl;
import com.apprentice.rpg.gui.database.IDatabaseSettingsFrameControl;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktopControl;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.apprentice.rpg.gui.log.ILogFrameControl;
import com.apprentice.rpg.gui.log.LogFrameControl;
import com.apprentice.rpg.gui.main.EventBarControl;
import com.apprentice.rpg.gui.main.IEventBarControl;
import com.apprentice.rpg.gui.main.IMainControl;
import com.apprentice.rpg.gui.main.MainControl;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public final class GuiceConfigGui implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.bind(IWindowManager.class).to(WindowManager.class);
		binder.bind(IApprenticeDesktopControl.class).to(ApprenticeDesktopControl.class);
		binder.bind(IMainControl.class).to(MainControl.class);
		binder.bind(INewPlayerCharacterFrameControl.class).to(NewPlayerCharacterFrameControl.class);
		binder.bind(IDatabaseSettingsFrameControl.class).to(DatabaseSettingsFrameControl.class);

		binder.bind(ILogFrameControl.class)
				.toInstance((LogFrameControl) Logger.getRootLogger().getAppender("logFrame"));
		binder.bind(IEventBarControl.class)
				.toInstance((EventBarControl) Logger.getRootLogger().getAppender("eventBar"));
	}

	@Provides
	@Singleton
	@Inject
	public IGlobalWindowState getGlobalWindowState(final DatabaseConnection database) {
		return database.getGlobalWindowState();
	}

}
