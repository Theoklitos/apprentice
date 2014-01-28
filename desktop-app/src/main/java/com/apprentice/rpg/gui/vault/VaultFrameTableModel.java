package com.apprentice.rpg.gui.vault;

import javax.swing.table.DefaultTableModel;

/**
 * Model for the table in the {@link GenericVaultFrame}
 * 
 * @author theoklitos
 * 
 */
public class VaultFrameTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private final String column2Name;
	private final String column1Name;

	/**
	 * uses default column names
	 */
	public VaultFrameTableModel() {
		this("Name", "Created or Last Modified");
	}

	/**
	 * use those 2 column names for the 2 columns
	 */
	public VaultFrameTableModel(final String column1Name, final String column2Name) {
		this.column1Name = column1Name;
		this.column2Name = column2Name;
		setColumnCount(2);
	}

	@Override
	public String getColumnName(final int columnNumber) {
		if (columnNumber == 0) {
			return column1Name;
		} else {
			return column2Name;
		}
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}
}
