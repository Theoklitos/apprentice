package com.apprentice.rpg.guice;

import org.apache.log4j.Logger;

import com.apprentice.rpg.config.ApprenticeConfiguration;
import com.apprentice.rpg.config.IApprenticeConfiguration;
import com.apprentice.rpg.config.ITextConfigFileManager;
import com.apprentice.rpg.config.TextConfigFileManager;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.PublishSubscribeEventBus;
import com.apprentice.rpg.events.database.DataSynchronizer;
import com.apprentice.rpg.events.database.IDataSynchronizer;
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
import com.apprentice.rpg.gui.util.IWindowUtils;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.vault.VaultFrameControl;
import com.apprentice.rpg.gui.vault.type.ITypeAndBodyPartFrameControl;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrameControl;
import com.apprentice.rpg.gui.weapon.IWeaponFrameControl;
import com.apprentice.rpg.gui.weapon.WeaponFrameControl;
import com.apprentice.rpg.gui.windowState.GlobalWindowState;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.sound.ISoundManager;
import com.apprentice.rpg.sound.SoundManager;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;

public final class GuiceConfigGui implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.bind(IWindowManager.class).to(WindowManager.class);
		binder.bind(IWindowUtils.class).to(WindowUtils.class);
		binder.bind(ITextConfigFileManager.class).to(TextConfigFileManager.class);
		binder.bind(ApprenticeEventBus.class).to(PublishSubscribeEventBus.class).in(Scopes.SINGLETON);
		binder.bind(IDataSynchronizer.class).to(DataSynchronizer.class);
		binder.bind(IApprenticeDesktopControl.class).to(ApprenticeDesktopControl.class);
		binder.bind(IMainControl.class).to(MainControl.class);
		binder.bind(IDatabaseSettingsFrameControl.class).to(DatabaseSettingsFrameControl.class);
		binder.bind(INewPlayerCharacterFrameControl.class).to(NewPlayerCharacterFrameControl.class);
		binder.bind(ITypeAndBodyPartFrameControl.class).to(TypeAndBodyPartFrameControl.class);
		binder.bind(IVaultFrameControl.class).to(VaultFrameControl.class);		
		binder.bind(IWeaponFrameControl.class).to(WeaponFrameControl.class);
		binder.bind(ISoundManager.class).to(SoundManager.class);

		binder.bind(ILogFrameControl.class)
				.toInstance((LogFrameControl) Logger.getRootLogger().getAppender("logFrame"));
		binder.bind(IEventBarControl.class)
				.toInstance((EventBarControl) Logger.getRootLogger().getAppender("eventBar"));

	}

	@Provides
	@Singleton
	@Inject
	public IApprenticeConfiguration getConfiguration(final Vault vault) {
		return vault.getUniqueObjectFromDB(ApprenticeConfiguration.class);
	}

	@Provides
	@Singleton
	@Inject
	public IGlobalWindowState getGlobalWindowState(final Vault vault, final IWindowUtils windowUtils) {
		try {
			return vault.getUniqueObjectFromDB(GlobalWindowState.class);
		} catch (final ApprenticeEx e) {
			// item does not exist, create it
			return new GlobalWindowState(windowUtils);
		}
	}

}
