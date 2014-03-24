package com.apprentice.rpg.gui.vault.weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.gui.vault.VaultPanel;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;

public class AmmunitionVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * enables the create/edit/delete buttons to work with {@link AmmunitionType}s, via optionpanes
	 */
	public static void initComponents(final VaultPanel ammoVaultPanel, final ApprenticeInternalFrame<?> view) {
		final JButton createButton = ammoVaultPanel.getCreateButton();
		createButton.removeActionListener(createButton.getActionListeners()[0]);
		createButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				final Box<AmmunitionType> emtpy = Box.empty();
				final Box<AmmunitionType> ammoTypeBox =
					view.getWindowUtils().showAmmunitionTypeDialog(view.getControl(), emtpy);
				if (ammoTypeBox.hasContent()) {
					view.getControl().createOrUpdate(ammoTypeBox.getContent());
				}
				view.refreshFromModel();
			}
		});
		final JButton editButton = ammoVaultPanel.getEditButton();
		editButton.removeActionListener(createButton.getActionListeners()[0]);
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				final Box<String> selectedAmmoTypeNameBox = ammoVaultPanel.getSelectedItemName();
				if (selectedAmmoTypeNameBox.hasContent()) {
					final AmmunitionType ammunitionType =
						view.getControl().getVault()
								.getUniqueNamedResult(selectedAmmoTypeNameBox.getContent(), AmmunitionType.class);
					view.getWindowUtils().showAmmunitionTypeDialog(view.getControl(), Box.with(ammunitionType));
					ammoVaultPanel.refreshFromModel();
				}
			}
		});
	}

	public AmmunitionVaultFrame(final IServiceLayer control) {
		super(control, ItemType.AMMUNITION, false, true);
		initComponents(getVaultPanel(), this);
	}
}
