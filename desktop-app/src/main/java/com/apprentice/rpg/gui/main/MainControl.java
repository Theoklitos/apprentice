package com.apprentice.rpg.gui.main;

import com.apprentice.rpg.IShutdownHook;
import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.google.inject.Inject;

public final class MainControl extends AbstractControlForView<MainFrame> implements IMainControl {

	private final IShutdownHook shutdownHook;
	private final IApprenticeDesktopControl desktopControl;

	@Inject
	public MainControl(final IServiceLayer serviceLayer, final IApprenticeDesktopControl desktopControl,
			final IWindowManager windowManager, final IShutdownHook shutdownHook) {
		super(serviceLayer);
		this.desktopControl = desktopControl;
		this.shutdownHook = shutdownHook;
	}

	@Override
	public IApprenticeDesktopControl getDesktopControl() {
		return desktopControl;
	}

	@Override
	public void shutdownGracefully() {
		shutdownHook.shutdownGracefully();
	}
}
