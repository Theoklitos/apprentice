package com.apprentice.rpg.gui.log;

import javax.swing.table.DefaultTableModel;

/**
 * A simple 3-column uneditable model, used for logging.
 * 
 */
public final class LogFrameTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -1L;

	public LogFrameTableModel() {
		setColumnCount(3);		
	}

	@Override
	public String getColumnName(final int columnNumber) {
		if (columnNumber == 0) {
			return "Time";
		} else if (columnNumber == 1) {
			return "Type";
		} else {
			return "Message";
		}
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

}
