package com.apprentice.rpg.guice;

import com.apprentice.rpg.gui.IMainControl;
import com.apprentice.rpg.gui.MainControl;
import com.apprentice.rpg.gui.desktop.ApprenticeDesktopControl;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public final class GuiceGuiConfig implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.bind(IApprenticeDesktopControl.class).to(ApprenticeDesktopControl.class)
				.in(Scopes.SINGLETON);
		binder.bind(IMainControl.class).to(MainControl.class).in(Scopes.SINGLETON);
	}

}
