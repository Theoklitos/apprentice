package com.apprentice.rpg.gui.vault.weapon;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.gui.vault.VaultPanel;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * List of {@link IWeaponPrototype}s and {@link AmmunitionType}s along with various controls
 * 
 * @author theoklitos
 * 
 */
public class WeaponAndAmmunitionVaultFrame extends ApprenticeInternalFrame<IServiceLayer> implements ControllableView {

	private static final long serialVersionUID = 1L;

	private final VaultPanel weaponVaultPanel;
	private final VaultPanel ammoVaultPanel;

	public WeaponAndAmmunitionVaultFrame(final IServiceLayer control) {
		super(control, "Weapon Vault");
		weaponVaultPanel = new VaultPanel(control, ItemType.WEAPON, true, false);
		ammoVaultPanel = getCreateAmmunitionVaultPanel(control);
		initComponents();
		refreshFromModel();
	}

	/**
	 * creates the panel and sets the buttons
	 */
	private VaultPanel getCreateAmmunitionVaultPanel(final IServiceLayer control) {
		final VaultPanel vaultPanel = new VaultPanel(control, ItemType.AMMUNITION, true, false);
		AmmunitionVaultFrame.initComponents(vaultPanel, this);
		return vaultPanel;
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(470, 380);
	}

	public void initComponents() {
		final JTabbedPane tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Weapons", weaponVaultPanel);
		tabbedPanel.addTab("Ammunitions", ammoVaultPanel);
		getContentPane().add(tabbedPanel);
	}

	@Override
	public void refreshFromModel() {
		weaponVaultPanel.refreshFromModel();
		ammoVaultPanel.refreshFromModel();
	}

}
