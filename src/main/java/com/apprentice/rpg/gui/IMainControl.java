package com.apprentice.rpg.gui;

import com.apprentice.rpg.ShutdownHook;

public interface IMainControl {

	/**
	 * Self-explanatory
	 */
	public boolean isMainFrameVisible();

	/**
	 * Displays an internal frame that shows the log
	 */
	public void openLoggingFrame();

	/**
	 * Call to the {@link ShutdownHook}
	 */
	public void shutdownGracefully();

}
