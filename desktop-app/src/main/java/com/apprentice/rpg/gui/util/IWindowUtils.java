package com.apprentice.rpg.gui.util;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;

/**
 * helper functions for the swing gui, ie option panes, icons, etc
 * 
 */
public interface IWindowUtils {

	/**
	 * centers this component based on the size of the (first) screen
	 */
	void centerComponent(Component component, int verticalOffset);

	/**
	 * centers this component inside a {@link JDesktopPane}
	 */
	void centerInternalComponent(Component parent, Component component, int verticalOffset);

	/**
	 * returns the default backround image (that famous spanish picture)
	 * 
	 * @throws {@link ApprenticeEx} in any case of an error
	 */
	Image getDefaultDesktopBackgroundImage() throws ApprenticeEx;

	/**
	 * makes an {@link Image} out of this path
	 */
	Image getImageFromPath(String path);

	/**
	 * will set this frame's icon to the "default" one. which is a scary wizard
	 */
	void setDefaultIcon(JFrame frame);

	/**
	 * will set this frame's icon to the "information" one, which is a small open book
	 */
	void setInformationIcon(JInternalFrame frame);

	/**
	 * shows two jtables, one for types and one for bodyparts, and saves the selection in the
	 * {@link ExportConfigurationObject}
	 * 
	 * @return true if the user confirmed (press OK), false if he cancelled
	 */
	boolean showTypeAndBodyPartNameSelection(ExportConfigurationObject config, final Vault vault);

}
