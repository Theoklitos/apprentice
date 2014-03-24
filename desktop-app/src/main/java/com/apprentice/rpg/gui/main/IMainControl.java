package com.apprentice.rpg.gui.main;

import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.gui.desktop.IApprenticeDesktopControl;

public interface IMainControl extends ControlForView<MainFrame> {

	/**
	 * returns a reference to the {@link IApprenticeDesktopControl} which is used in the main frame
	 */
	IApprenticeDesktopControl getDesktopControl();

	/**
	 * calls the shutdown hook
	 */
	void shutdownGracefully();

}
