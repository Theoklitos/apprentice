package com.apprentice.rpg.gui;

import com.apprentice.rpg.dao.ItemNotFoundEx;
import com.apprentice.rpg.gui.database.DatabaseSettingsFrame;
import com.apprentice.rpg.gui.main.MainFrame;
import com.apprentice.rpg.gui.windowState.WindowStateIdentifier;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.weapon.WeaponPrototype;

/**
 * Guiced up factory for frames and windows, maintains list of active windows
 * 
 * @author theoklitos
 * 
 */
public interface IWindowManager {

	/**
	 * closes all the internal frames
	 * 
	 * @param openState
	 *            set to true during shutdown, this will store the frames' state and cause them to reopen at
	 *            startup
	 */
	void closeAllFrames(boolean openState);

	/**
	 * Creates and shows the {@link MainFrame}
	 */
	void initializeMainFrame();

	/**
	 * will display the corresponding internal frame of this identifier
	 */
	void openFrame(WindowStateIdentifier openFrameIdentifier);

	/**
	 * Shows the frame where all the {@link IArmorPiece}s can be viewed/edited/deleted
	 */
	void showArmorPieceVaultFrame();

	/**
	 * Shows the {@link DatabaseSettingsFrame}
	 */
	void showDatabaseSettingsFrame();

	/**
	 * Shows the dice roller frame
	 */
	void showDiceRollerFrame();

	/**
	 * Shows the log4j displayer frame
	 */
	void showLogFrame();

	/**
	 * Shows the frame used to create a new {@link PlayerCharacter}
	 */
	void showNewPlayerCharacterFrame();

	/**
	 * Shows the
	 */
	void showPlayerVaultFrame();

	/**
	 * Shows the {@link TypeAndBodyPartFrameP}
	 */
	void showTypeAndBodyPartFrame();

	/**
	 * Shows the weapon frame. Leave empty or null name for a new weapon frame, or enter a name to load the
	 * weapon for editing
	 * 
	 * @throws ItemNotFoundEx
	 *             if no such weapon exists
	 */
	void showWeaponFrame(String weaponName) throws ItemNotFoundEx;

	/**
	 * Shows the frame where all the {@link WeaponPrototype}s can be viewed/edited/deleted
	 */
	void showWeaponVaultFrame();

}
