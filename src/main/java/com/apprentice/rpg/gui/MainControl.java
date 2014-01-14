package com.apprentice.rpg.gui;

import com.apprentice.rpg.ShutdownHook;
import com.apprentice.rpg.config.ApplicationConfiguration;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;
import com.google.inject.Inject;

public final class MainControl implements IMainControl {

	private final MainFrame mainFrame;
	private final ShutdownHook shutdownHook;
	private final ApplicationConfiguration config;

	@Inject
	public MainControl(final ShutdownHook shutdownHook, final ApplicationConfiguration config,
			final IApprenticeDesktopControl desktopControl) {
		this.shutdownHook = shutdownHook;
		this.config = config;

		this.mainFrame = new MainFrame(this);
		desktopControl.attachDesktopToMainFrame(mainFrame);
		mainFrame.setVisible(true);
	}

	@Override
	public boolean isMainFrameVisible() {
		return mainFrame.isVisible();
	}

	@Override
	public void openLoggingFrame() {
//		final LogFrame logFrame = new LogFrame(logger);
//		new LogListener("Log Listener", shutdownHook, logFrame, logger);
//		mainFrame.addInternalFrame(logFrame);
	}

	@Override
	public void shutdownGracefully() {
		shutdownHook.shutdownGracefully();
	}
}
