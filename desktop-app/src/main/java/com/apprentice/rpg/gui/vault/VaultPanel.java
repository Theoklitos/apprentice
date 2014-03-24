package com.apprentice.rpg.gui.vault;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ApprenticeTable;
import com.apprentice.rpg.gui.armor.ArmorFrame;
import com.apprentice.rpg.gui.armorPiece.ArmorPieceFrame;
import com.apprentice.rpg.gui.description.DescriptionPanel;
import com.apprentice.rpg.gui.description.ModifiableTextFieldPanel.DescriptionPanelType;
import com.apprentice.rpg.gui.weapon.WeaponFrame;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Contains a connection to the vault and a list of {@link Nameable} items, plus controls
 * 
 * @author theoklitos
 * 
 */
public final class VaultPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_DESCRIPTION_PANEL_TITLE = "Description";

	private final IServiceLayer control;

	private VaultTableModel tableModel;
	private ApprenticeTable table;
	private final boolean shouldShowCreateButton;
	private final ItemType itemType;
	private String selectedItemName;
	private int selectedItemIndex;
	private DescriptionPanel descriptionPanel;
	private final boolean shouldShowWarningOnNameChange;

	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnCreate;

	/**
	 * Will use default description panel titlte
	 */
	public VaultPanel(final IServiceLayer control, final ItemType itemType, final boolean shouldShowCreateButton,
			final boolean shouldShowWarningOnNameChange) {
		this(control, DEFAULT_DESCRIPTION_PANEL_TITLE, itemType, shouldShowCreateButton, shouldShowWarningOnNameChange);
	}

	public VaultPanel(final IServiceLayer control, final String descriptionPanelTitle, final ItemType itemType,
			final boolean shouldShowCreateButton, final boolean shouldShowWarningOnNameChange) {
		this.control = control;
		this.itemType = itemType;
		this.shouldShowCreateButton = shouldShowCreateButton;
		this.shouldShowWarningOnNameChange = shouldShowWarningOnNameChange;
		initComponents(descriptionPanelTitle);
	}

	/**
	 * for when the user clicks on the create vault item button
	 */

	private void createItem() {
		control.getEventBus().postShowFrameEvent(getFrameClassFromType());
	}

	/**
	 * for when the user clicks on the Edit vault item button
	 */
	private void editItem(final String selectedItemName) {
		control.getEventBus().postShowFrameEvent(getFrameClassFromType(), selectedItemName);
	}

	public JButton getCreateButton() {
		return btnCreate;
	}

	public JButton getDeleteButtion() {
		return btnDelete;
	}

	public JButton getEditButton() {
		return btnEdit;
	}

	/**
	 * Gets the appropriate {@link Class} for the item type this frame handles. Warning! Ugly hack, no time a
	 * more elegant solution.
	 */
	private Class<? extends ApprenticeInternalFrame<?>> getFrameClassFromType() throws ApprenticeEx {
		switch (itemType) {
		case WEAPON:
			return WeaponFrame.class;
		case ARMOR_PIECE:
			return ArmorPieceFrame.class;
		case ARMOR:
			return ArmorFrame.class;
		case PLAYER_CHARACTER:
			throw new ApprenticeEx("Implement me!");
		default:
			throw new ApprenticeEx("Item Type \"" + itemType + "\" has no frame assigned to it.");
		}
	}

	public Box<String> getSelectedItemName() {
		if (selectedItemName == null) {
			return Box.with(selectedItemName);
		} else {
			return Box.empty();
		}
	}

	private void initComponents(final String descriptionPanelTitle) {
		setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
			FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("fill:default:grow"),
			FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25dlu"), }));

		tableModel = new VaultTableModel();
		table = new ApprenticeTable(tableModel);
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(0, 200));
		add(scrollPane, "2, 2, fill, fill");
		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) {
				if (event.getType() == TableModelEvent.UPDATE) {
					if (table.getSelectedName().hasContent()
						&& !selectedItemName.equals(table.getSelectedName().getContent())) {
						final String newName = table.getSelectedName().getContent().trim();
						if (shouldShowWarningOnNameChange) {
							if (!control
									.getGlobalWindowState()
									.getWindowUtils()
									.showConfigrmationDialog(
											"Renaming this "
												+ itemType.toString()
												+ " might affect players that are currently using it.\nAre you sure you want to continue?",
											"Change Name")) {
								return;
							}
						}
						try {
							control.renameNamebale(selectedItemName, newName, itemType);
							selectedItemName = newName;
						} catch (final NameAlreadyExistsEx e) {
							control.getGlobalWindowState()
									.getWindowUtils()
									.showErrorMessage(
											"A " + itemType.toString() + " named \"" + newName + "\" already exists.");
						}
					}
				}
			}
		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				if (table.getSelectedName().hasContent()) {
					selectedItemName = table.getSelectedName().getContent();
					selectedItemIndex = table.getSelectedRow();
					Nameable selectedItem;
					if(itemType.isPrototypical()) {
						selectedItem = control.getVault().getPrototype(selectedItemName, itemType.type);
					} else {
						selectedItem =  control.getVault().getUniqueNamedResult(selectedItemName, itemType.type);
					}
					descriptionPanel.setNameable(selectedItem);
				}
			}
		});

		descriptionPanel = new DescriptionPanel(control, descriptionPanelTitle, DescriptionPanelType.LABEL);
		add(descriptionPanel, "2, 4, fill, fill");

		final JPanel buttonPanel = new JPanel();
		add(buttonPanel, "2, 6, fill, fill");

		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				createItem();
			}
		});
		if (shouldShowCreateButton) {
			buttonPanel.add(btnCreate);
		}

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (StringUtils.isNotBlank(selectedItemName)) {
					editItem(selectedItemName);
				}
			}
		});
		buttonPanel.add(btnEdit);
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (StringUtils.isNotBlank(selectedItemName)) {
					control.deleteNameable(selectedItemName, itemType);
				}
			}
		});
		buttonPanel.add(btnDelete);
	}

	/**
	 * Reads data from the model and refreshed the rows of the table
	 */
	public void refreshFromModel() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				tableModel.clearAllRows();
				final Vault vault = control.getVault();
				final Collection<? extends Nameable> items;
				if (itemType.isPrototypical()) {
					items = vault.getAllPrototypeNameables(itemType.type);
				} else {
					items = vault.getAllNameables(itemType.type);
				}
				for (final Nameable item : items) {
					tableModel.addRow(new Object[] { item.getName(), vault.getPrettyUpdateTime(item) });
				}
				if (selectedItemName != null) {
					table.getSelectionModel().setSelectionInterval(selectedItemIndex, selectedItemIndex);
				}
			}
		});
	}
}
