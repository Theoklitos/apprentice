package com.apprentice.rpg.gui;

import java.util.Set;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.apprentice.rpg.util.ApprenticeStringUtils;
import com.apprentice.rpg.util.Box;
import com.google.common.collect.Sets;

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

	public ApprenticeTable(final TableModel model) {
		super(model);
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * removes all the rows, one by one
	 */
	public final void clearRows() {
		for (int i = 0; i < getModel().getRowCount(); i++) {
			((DefaultTableModel) getModel()).removeRow(i);
		}
	}

	/**
	 * makes the selected row (if any) bold
	 */
	public void enboldenSelectedRow() {
		if (getSelectedRow() != -1) {
			final String cell1Content =
				ApprenticeStringUtils.removeHtmlTags(getValueAt(getSelectedRow(), 0).toString());
			setValueAt(surroundWithHtmlBold(cell1Content), getSelectedRow(), 0);
			final String cell2Content =
				ApprenticeStringUtils.removeHtmlTags(getValueAt(getSelectedRow(), 1).toString());
			setValueAt(surroundWithHtmlBold(cell2Content), getSelectedRow(), 1);
		}
	}

	/**
	 * returns the first column of the selected row, as a string
	 */
	public final Box<String> getSelectedName() {
		if (getSelectedRow() == -1) {
			return Box.empty();
		} else {
			final String name = getModel().getValueAt(getSelectedRow(), 0).toString();
			return Box.with(ApprenticeStringUtils.removeHtmlTags(name.trim()));
		}
	}

	/**
	 * returns a collection with all the selected names
	 */
	public final Set<String> getSelectedNames() {
		final Set<String> result = Sets.newHashSet();
		for (final int selectedRow : getSelectedRows()) {
			final String name = ApprenticeStringUtils.removeHtmlTags(getValueAt(selectedRow, 0).toString());
			result.add(name);
		}
		return result;
	}

	/**
	 * all html tags are wiped from the table
	 */
	public void removeAllBoldness() {
		for (int i = 0; i < getRowCount(); i++) {
			setValueAt(ApprenticeStringUtils.removeHtmlTags(getValueAt(i, 0).toString()), i, 0);
			setValueAt(ApprenticeStringUtils.removeHtmlTags(getValueAt(i, 1).toString()), i, 1);
		}
	}

	private String surroundWithHtmlBold(final String text) {
		return "<html><b>" + text + "</b></html>";
	}
}
