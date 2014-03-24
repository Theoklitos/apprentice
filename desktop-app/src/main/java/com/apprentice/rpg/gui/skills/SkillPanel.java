package com.apprentice.rpg.gui.skills;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.Skill;
import com.apprentice.rpg.model.Stat;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Holds a list of skills
 * 
 * @author theoklitos
 * 
 */
public class SkillPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final StatBundle statBundle;
	private SkillsTableModel tableModel;
	private SkillsTable skillsTable;

	public SkillPanel(final StatBundle statBundle) {
		this.statBundle = statBundle;
		setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("22dlu"),
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("pref:grow"), }));

		addSkillButtonPanel();
		addSkillTablePanel();
	}

	/**
	 * Appends a new (empty) skill row
	 */
	public void addNewSkillRow() {
		tableModel.addNewSkill(new Skill("Unamed", statBundle.getStat(StatType.STRENGTH), 0));
	}

	/**
	 * Appends the given skill to the table
	 */
	public void addSkill(final Skill skill) {
		tableModel.addNewSkill(skill);
	}

	private void addSkillButtonPanel() {
		final JPanel skillButtonPanel = new JPanel();
		add(skillButtonPanel, "2, 2, fill, fill");
		final JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				addNewSkillRow();
			}
		});
		skillButtonPanel.add(btnAdd);

		final JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (skillsTable.getSelectedRow() != -1) {
					tableModel.removeSkill(skillsTable.getSelectedRow());
				}
			}
		});
		skillButtonPanel.add(btnDelete);
	}

	private void addSkillTablePanel() {
		final JPanel skillsTablePanel = new JPanel();
		skillsTablePanel.setLayout(new BoxLayout(skillsTablePanel, BoxLayout.X_AXIS));
		final JComboBox<Stat> cmbboxStat = new JComboBox<Stat>();
		cmbboxStat.setModel(new DefaultComboBoxModel<Stat>(new Vector<Stat>(statBundle.getAll())));
		setRenderer(cmbboxStat);

		tableModel = new SkillsTableModel();
		skillsTable = new SkillsTable(tableModel, cmbboxStat);
		final JScrollPane skillTableScrollPane = new JScrollPane(skillsTable);
		skillTableScrollPane.setPreferredSize(new Dimension(0, 30));
		skillsTablePanel.add(skillTableScrollPane);
		add(skillsTablePanel, "2, 4, fill, fill");
	}

	/**
	 * Returns a collection of skills based on what the user has chone on the panel
	 * 
	 * @throws ApprenticeEx
	 *             if the user has inputted wrong data
	 */
	public Collection<Skill> getSkills() throws ApprenticeEx {
		return tableModel.getSkills();
	}

	/**
	 * Sets a cell renderer that displays only the name of the Stat
	 */
	@SuppressWarnings({ "rawtypes" })
	private void setRenderer(final JComboBox<Stat> cmbboxStat) {
		cmbboxStat.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(final JList list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				Object item = value;

				if (item != null && Stat.class.isAssignableFrom(item.getClass())) {
					final StatType statType = ((Stat) item).getStatType();
					item = statBundle.getStat(statType).toStringDetailed();
				}
				return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
			}
		});
	}

}
