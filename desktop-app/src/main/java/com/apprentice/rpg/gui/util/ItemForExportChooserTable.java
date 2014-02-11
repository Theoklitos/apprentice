package com.apprentice.rpg.gui.util;

import java.util.Set;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.ApprenticeTable;
import com.apprentice.rpg.gui.vault.VaultFrameTableModel;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;

public class ItemForExportChooserTable extends ApprenticeTable {

	private static final long serialVersionUID = 1L;

	public ItemForExportChooserTable(final ItemType type, final ExportConfigurationObject config, final Vault vault) {
		final VaultFrameTableModel model = new VaultFrameTableModel(-1);		
		model.setColumnCount(1);
		setModel(model);		
		for (final Nameable item : vault.getAllNameables(type.type)) {
			model.addRow(new String[] { item.getName()});
		}
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);		
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent event) {
				final Set<String> selectedNames = getSelectedNames();				
				config.setNamesForExport(type, selectedNames);
				System.out.println("Config changed: " + config);
			}
		});
		this.repaint();
		this.revalidate();		
	}

}
