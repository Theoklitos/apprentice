package com.apprentice.rpg.gui.skills;

import java.util.Collection;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.apprentice.rpg.model.playerCharacter.Skill;
import com.apprentice.rpg.model.playerCharacter.SkillComparator;
import com.apprentice.rpg.model.playerCharacter.Stat;
import com.apprentice.rpg.util.ApprenticeStringUtils;
import com.apprentice.rpg.util.SortedList;

/**
 * Table model for a {@link JTable} that contains the player's skills
 * 
 * @author theoklitos
 * 
 */
public class SkillsTableModel extends DefaultTableModel implements TableModel {

	public static final String TOTAL_MODIFIER_COLUMN_NAME = "Total";
	public static final String SKILL_NAME_COLUMN_NAME = "Skill Name";
	public static final String STAT_COLUMN_NAME = "Stat";
	public static final String RANK_COLUMN_NAME = "Ranks";

	private static final long serialVersionUID = 1L;

	private final SortedList<Skill> skills;
	private final SkillComparator comparator;

	public SkillsTableModel() {
		setColumnCount(4);
		comparator = new SkillComparator();
		skills = new SortedList<Skill>(comparator);
	}

	/**
	 * appends the new skill to the model
	 */
	public void addNewSkill(final Skill skill) {
		skills.add(skill);
		fireTableDataChanged();
	}

	/**
	 * self-explanatory
	 */
	public void clearAllRows() {
		setRowCount(0);
	}

	@Override
	public String getColumnName(final int columnNumber) {
		if (columnNumber == 0) {
			return SKILL_NAME_COLUMN_NAME;
		} else if (columnNumber == 1) {
			return RANK_COLUMN_NAME;
		} else if (columnNumber == 2) {
			return STAT_COLUMN_NAME;
		} else if (columnNumber == 3) {
			return TOTAL_MODIFIER_COLUMN_NAME;
		} else {
			return "";
		}
	}

	@Override
	public int getRowCount() {
		if (getSkills() == null) {
			return 0;
		} else {
			return getSkills().size();
		}
	}

	/**
	 * Returns the skills that have been created so far
	 */
	public Collection<Skill> getSkills() {
		return skills;
	}

	@Override
	public Object getValueAt(final int row, final int column) {
		if (row >= getSkills().size()) {
			return "";
		}
		final Skill selectedSkill = skills.get(row);
		if (column == 0) {
			return selectedSkill.getName();
		} else if (column == 1) {
			return selectedSkill.getRank();
		} else if (column == 2) {
			return selectedSkill.getStat().toStringDetailed();
		} else {
			return ApprenticeStringUtils.getNumberWithOperator(selectedSkill.getTotalBonus());
		}
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return !(column == 3);
	}

	/**
	 * removes the skill with this #
	 */
	public void removeSkill(final int selectedRow) {
		skills.remove(selectedRow);
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(final Object value, final int row, final int column) {
		if (row >= getSkills().size()) {
			return;
		}
		final Skill selectedSkill = skills.get(row);
		if (column == 0) {
			selectedSkill.setName(value.toString());
		} else if (column == 1) {
			try {
				final int rank = Integer.valueOf(value.toString());
				if (rank >= 0) {
					selectedSkill.setRank(rank);
				}
			} catch (final NumberFormatException e) {
				// do nothing, will revert to old value
				return;
			}
		} else if (column == 2) {
			final Stat stat = (Stat) value;
			selectedSkill.setStat(stat);
		} else {
			// this is auto determined
		}
		Collections.sort(skills, comparator);
		fireTableDataChanged();
	}
}
