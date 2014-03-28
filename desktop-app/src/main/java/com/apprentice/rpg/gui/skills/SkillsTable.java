package com.apprentice.rpg.gui.skills;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.apprentice.rpg.model.playerCharacter.Skill;
import com.apprentice.rpg.model.playerCharacter.Stat;

/**
 * {@link JTable} for {@link Skill}s
 */
public class SkillsTable extends JTable {

	private static final long serialVersionUID = 1L;

	public SkillsTable(final SkillsTableModel model, final JComboBox<Stat> cmbboxStat) {
		super(model);
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getColumn(SkillsTableModel.STAT_COLUMN_NAME).setCellEditor(new DefaultCellEditor(cmbboxStat));
		getColumnModel().getColumn(2).setMinWidth(100);				
	}

	/**
	 * removes all the rows, one by one
	 */
	public final void clearRows() {
		for (int i = 0; i < getModel().getRowCount(); i++) {
			((DefaultTableModel) getModel()).removeRow(i);
		}
	}

}
