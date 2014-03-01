package com.apprentice.rpg.gui.util;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.ControlWithVault;
import com.apprentice.rpg.gui.dice.LoadedRoll;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.weapon.AmmunitionTypeWithRange;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.model.weapon.Range;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.strike.StrikeType;
import com.apprentice.rpg.util.Box;

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
	 * will display the dice rolling GIF in this Jlabel
	 */
	void setDiceRollingImage(JLabel jlabel);

	/**
	 * will set this frame's icon to the "information" one, which is a small open book
	 */
	void setInformationIcon(JInternalFrame frame);

	/**
	 * Shows a dialog where one chooses an existing {@link AmmunitionType} and its corresponding {@link Range}
	 */
	Box<AmmunitionTypeWithRange> showAmmunitionTypeAndRangeDialog(ControlWithVault control);

	/**
	 * Popups a dialog where the user can create/edit a an {@link AmmunitionType}
	 * 
	 * @param empty
	 *            defaults
	 */
	Box<AmmunitionType> showAmmunitionTypeDialog(IVaultFrameControl control, Box<AmmunitionType> existingType);

	/**
	 * Shows an option pane with a bigger textfield and returns the text the user inputed in a box, if any.
	 * Empty box otherwise
	 */
	Box<String> showBigTextFieldDialog(String title, String preExistingContent);

	/**
	 * Shows a simple yes/no dialog. Returns true if yes.
	 */
	boolean showConfigrmationDialog(String question, String title);

	/**
	 * will popup a small window from which the user can select {@link StrikeType} + {@link Roll}
	 */
	Box<DamageRoll> showDamageRollDialog(final String mesasge, final ControlWithVault control);

	/**
	 * Displays a simple error message
	 */
	void showErrorMessage(String errorMessage);

	/**
	 * Displays a simple error message
	 */
	void showErrorMessage(String errorMessage, String title);

	/**
	 * presents a file chooser and returns a box with the file URL the user chose. Emtpy box if user pressed
	 * on the cancel/X button etc
	 */
	Box<String> showFileChooser(String title, String buttonText);

	/**
	 * Displays a simple information message with only an OK button
	 */
	void showInformationMessage(String infoMessage, String title);

	/**
	 * show a JInputDialog with a jtextfield and returns a box with anything the user inputed. Emtpy box if he
	 * pressed "cancel"
	 */
	Box<String> showInputDialog(String message, String title);

	/**
	 * Shows an interface where a fake roll can be created
	 */
	Box<LoadedRoll> showLoadedRollDialog();

	/**
	 * Shows a question message which displays the given options. Return number if the array position # of the
	 * option that the user chose.
	 */
	int showQuestionMessage(String question, String[] options, String title);

	/**
	 * shows two jtables, one for types and one for bodyparts, and saves the selection in the
	 * {@link ExportConfigurationObject}
	 * 
	 * @return true if the user confirmed (press OK), false if he cancelled
	 */
	boolean showTypeAndBodyPartNameSelection(ExportConfigurationObject config, final Vault vault);

	/**
	 * shows a yes/no warning question. Returns true if yes.
	 */
	void showWarningMessage(String warningMessage, String title);

	/**
	 * shows a yes/no warning question. Returns true if yes.
	 */
	boolean showWarningQuestionMessage(String question, String title);

}
