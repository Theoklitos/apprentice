package com.apprentice.rpg.gui.vault.weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;

public class AmmunitionVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	public AmmunitionVaultFrame(final IGlobalWindowState globalWindowState, final IWindowManager windowManager,
			final IVaultFrameControl vaultFrameControl, final boolean shouldShowWarningOnNameChange) {
		super(globalWindowState, windowManager, ItemType.AMMUNITION, vaultFrameControl, shouldShowWarningOnNameChange);
	}

	@Override
	public void initComponents(final JPanel vaultTablePanel) {
		setButtons();
		getContentPane().add(vaultTablePanel);
	}

	@Override
	public void refreshFromModelSubclass() {
		// nothing
	}

	/**
	 * enables the create/edit/delete buttons to work with {@link AmmunitionType}s, via optionpanes
	 */
	private void setButtons() {
		final JButton createButton = getCreateButton();
		createButton.removeActionListener(createButton.getActionListeners()[0]);
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				final Box<AmmunitionType> emtpy = Box.empty();
				final Box<AmmunitionType> ammoTypeBox = getWindowUtils().showAmmunitionTypeDialog(getControl(), emtpy);
				if (ammoTypeBox.hasContent()) {
					getControl().createOrUpdate(ammoTypeBox.getContent(), ItemType.AMMUNITION);
				}
				refreshFromModel();
			}
		});
		final JButton editButton = getEditButton();
		editButton.removeActionListener(createButton.getActionListeners()[0]);
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				final Box<String> selectedAmmoTypeNameBox = getSelectedItemName();
				if (selectedAmmoTypeNameBox.hasContent()) {
					final AmmunitionType ammunitionType =
						getControl().getVault().getUniqueNamedResult(selectedAmmoTypeNameBox.getContent(),
								AmmunitionType.class);
					getWindowUtils().showAmmunitionTypeDialog(getControl(), Box.with(ammunitionType));
					refreshFromModel();
				}
			}
		});		
	}
}
