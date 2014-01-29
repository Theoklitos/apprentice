package com.apprentice.rpg.gui.vault;

import javax.swing.table.DefaultTableModel;

import com.apprentice.rpg.model.ApprenticeEx;

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
	private final int editableColumn;

	/**
	 * uses default column names, only 1st column editable
	 */
	public VaultFrameTableModel() {
		this("Name", "Created or Last Modified", 1);
	}

	/**
	 * use those 2 column names for the 2 columns
	 */
	public VaultFrameTableModel(final String column1Name, final String column2Name, final int editableColumn) {
		if (editableColumn < 1 || editableColumn > 2) {
			throw new ApprenticeEx("Column number must be 1 or 2");
		}
		this.editableColumn = editableColumn;
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
		return column == editableColumn - 1;
	}
}
