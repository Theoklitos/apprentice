package com.apprentice.rpg.gui.main;

import com.apprentice.rpg.ShutdownHook;
import com.apprentice.rpg.gui.IWindowManager;
import com.google.inject.Inject;

public final class MainControl implements IMainControl {

	private final ShutdownHook shutdownHook;

	@Inject
	public MainControl(final IWindowManager windowManager, final ShutdownHook shutdownHook) {
		this.shutdownHook = shutdownHook;
		windowManager.initializeMainFrame();
	}

	@Override
	public void shutdownGracefully() {
		shutdownHook.shutdownGracefully();
	}
}
