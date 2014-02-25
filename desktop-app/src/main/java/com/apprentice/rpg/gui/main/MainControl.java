package com.apprentice.rpg.gui.main;

import com.apprentice.rpg.ShutdownHook;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.IWindowManager;
import com.google.inject.Inject;

public final class MainControl implements IMainControl {

	private final ShutdownHook shutdownHook;
	private final Vault vault;

	@Inject
	public MainControl(final IWindowManager windowManager, final ShutdownHook shutdownHook, final Vault vault) {
		this.shutdownHook = shutdownHook;
		this.vault = vault;
		windowManager.initializeMainFrame();
	}

	@Override
	public Vault getVault() {
		return vault;
	}

	@Override
	public void shutdownGracefully() {
		shutdownHook.shutdownGracefully();
	}
}
