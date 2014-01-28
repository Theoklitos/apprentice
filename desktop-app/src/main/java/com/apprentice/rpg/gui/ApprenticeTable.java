package com.apprentice.rpg.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A {@link JTable} with some extra functionality
 * 
 * @author theoklitos
 * 
 */
public class ApprenticeTable extends JTable {

	private static final long serialVersionUID = 1L;

	public ApprenticeTable() {
		super();
	}

	public ApprenticeTable(final DefaultTableModel model) {
		super(model);
		getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * removes all the rows, one by one
	 */
	public void clearRows() {
		for (int i = 0; i < getModel().getRowCount(); i++) {
			((DefaultTableModel) getModel()).removeRow(i);
		}
	}
}
