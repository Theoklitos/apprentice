package com.apprentice.rpg.gui.vault.armor;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.gui.vault.VaultPanel;
import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Tabbed panel with controls for both {@link ArmorPiece}s and {@link Armor}s
 * 
 * @author theoklitos
 * 
 */
public class ArmorAndArmorPieceFrame extends ApprenticeInternalFrame<IServiceLayer> implements ControllableView {

	private static final long serialVersionUID = 1L;

	private final VaultPanel armorPieceVaultPanel;
	private final VaultPanel armorVaultPanel;

	public ArmorAndArmorPieceFrame(final IServiceLayer control) {
		super(control, "Armor Vault");
		armorPieceVaultPanel = new VaultPanel(control, ItemType.ARMOR_PIECE, true, false);
		armorVaultPanel = new VaultPanel(control, ItemType.ARMOR, true, false);
		initComponents();
		refreshFromModel();
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(470, 380);
	}

	public void initComponents() {
		final JTabbedPane tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Armor Pieces", armorPieceVaultPanel);
		tabbedPanel.addTab("Armors", armorVaultPanel);
		getContentPane().add(tabbedPanel);
	}

	@Override
	public void refreshFromModel() {
		armorPieceVaultPanel.refreshFromModel();
		armorVaultPanel.refreshFromModel();
	}

}
