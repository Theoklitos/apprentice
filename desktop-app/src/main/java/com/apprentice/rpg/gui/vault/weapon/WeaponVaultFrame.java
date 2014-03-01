package com.apprentice.rpg.gui.vault.weapon;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * List of {@link WeaponPrototype}s along with various controls
 * 
 * @author theoklitos
 * 
 */
public class WeaponVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	public WeaponVaultFrame(final IGlobalWindowState globalWindowState, final IVaultFrameControl vaultFrameControl,
			final IWindowManager windowManager) {
		super(globalWindowState, windowManager, ItemType.WEAPON, vaultFrameControl, false);
	}

	@Override
	public void initComponents(final JPanel vaultTablePanel) {
		final AmmunitionVaultFrame ammoVaultFrame =
			new AmmunitionVaultFrame(getGlobalWindowState(), getWindowManager(), getControl(), false);
		final JTabbedPane tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Weapons", vaultTablePanel);
		tabbedPanel.addTab("Ammunitions", ammoVaultFrame.getVaultTablePanel());
		getContentPane().add(tabbedPanel);
	}

	@Override
	public void refreshFromModelSubclass() {
		// nothing
	}

}
