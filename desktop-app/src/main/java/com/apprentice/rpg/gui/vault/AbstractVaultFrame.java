package com.apprentice.rpg.gui.vault;

import java.awt.Dimension;

import javax.swing.JButton;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;

/**
 * Shows a table of nameable items that exist in the database, along with some controls
 * 
 * @author theoklitos
 * 
 */
public abstract class AbstractVaultFrame extends ApprenticeInternalFrame<IServiceLayer> implements IAbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * returns the proper title based on the item at hand
	 */
	private static String getTitleForType(final ItemType itemType) {
		final String result = StringUtils.capitalize(itemType.toString()) + " Vault";
		final String uneededPart = " piece";
		if (result.contains(uneededPart)) {
			return StringUtils.remove(result, " piece");
		} else {
			return result;
		}
	}

	private final boolean shouldShowCreateButton;

	private final boolean shouldShowWarningOnNameChange;
	private final ItemType itemType;
	private VaultPanel vaultPanel;

	/**
	 * wil use default description panel text
	 */
	public AbstractVaultFrame(final IServiceLayer control, final ItemType itemType,
			final boolean shouldShowWarningOnNameChange, final boolean shouldShowCreateButton) {
		this(control, itemType, VaultPanel.DEFAULT_DESCRIPTION_PANEL_TITLE, shouldShowWarningOnNameChange,
				shouldShowCreateButton);
	}

	public AbstractVaultFrame(final IServiceLayer control, final ItemType itemType, final String descriptionPanelTitle,
			final boolean shouldShowWarningOnNameChange, final boolean shouldShowCreateButton) {
		super(control, getTitleForType(itemType));
		this.itemType = itemType;
		this.shouldShowCreateButton = shouldShowCreateButton;
		this.shouldShowWarningOnNameChange = shouldShowWarningOnNameChange;
		initComponents(descriptionPanelTitle);
		refreshFromModel();
	}

	public JButton getCreateButton() {
		return vaultPanel.getCreateButton();
	}

	public JButton getDeleteButtion() {
		return vaultPanel.getDeleteButtion();
	}

	public JButton getEditButton() {
		return vaultPanel.getEditButton();
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(470, 370);
	}

	public Box<String> getSelectedItemName() {
		return vaultPanel.getSelectedItemName();
	}

	/**
	 * returns the internal main panel that contains the vault info
	 */
	public VaultPanel getVaultPanel() {
		return vaultPanel;
	}

	private void initComponents(final String descriptionPanelTitle) {
		vaultPanel =
			new VaultPanel(getControl(), descriptionPanelTitle, itemType, shouldShowCreateButton,
					shouldShowWarningOnNameChange);
		getContentPane().add(vaultPanel);
	}

	@Override
	public void refreshFromModel() {
		vaultPanel.refreshFromModel();
	}
}
