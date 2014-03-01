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

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ApprenticeTable;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.description.DescriptionPanel;
import com.apprentice.rpg.gui.description.ModifiableTextFieldPanel.DescriptionPanelType;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.ApprenticeReflectionUtils;
import com.apprentice.rpg.util.Box;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Shows a table of nameable items that exist in the database, along with some controls
 * 
 * @author theoklitos
 * 
 */
public abstract class AbstractVaultFrame extends ApprenticeInternalFrame implements IAbstractVaultFrame {

	private static final String DEFAULT_DESCRIPTION_PANEL_TITLE = "Description";

	private static final long serialVersionUID = 1L;

	/**
	 * returns the proper title based on the item at hand
	 */
	private static String getTitleForType(final ItemType itemType) {
		final String result = StringUtils.capitalize(itemType.toString()) + " Vault";
		final String uneededPart = " piece";
		if (result.contains(uneededPart)) {
			return StringUtils.remove(result, " piece");
		} else {
			return result;
		}
	}

	private VaultFrameTableModel tableModel;

	private final IWindowManager windowManager;
	private final IVaultFrameControl control;
	private ApprenticeTable table;
	private final boolean shouldShowCreateButton;
	private final ItemType itemType;
	private String selectedItemName;
	private int selectedItemIndex;
	private DescriptionPanel descriptionPanel;
	private JPanel vaultTablePanel;
	private final boolean shouldShowWarningOnNameChange;

	private JButton btnDelete;

	private JButton btnEdit;

	private JButton btnCreate;

	/**
	 * @wbp.parser.constructor
	 */
	public AbstractVaultFrame(final IGlobalWindowState globalWindowState, final IWindowManager windowManager,
			final ItemType itemType, final IVaultFrameControl vaultFrameControl,
			final boolean shouldShowWarningOnNameChange) {
		this(globalWindowState, windowManager, itemType, vaultFrameControl, shouldShowWarningOnNameChange,
				DEFAULT_DESCRIPTION_PANEL_TITLE, true);
	}

	public AbstractVaultFrame(final IGlobalWindowState globalWindowState, final IWindowManager windowManager,
			final ItemType itemType, final IVaultFrameControl vaultFrameControl,
			final boolean shouldShowWarningOnNameChange, final String descriptionPanelTitle,
			final boolean shouldShowCreateButton) {
		super(globalWindowState, getTitleForType(itemType));
		this.windowManager = windowManager;
		this.itemType = itemType;
		this.control = vaultFrameControl;
		this.shouldShowCreateButton = shouldShowCreateButton;
		this.shouldShowWarningOnNameChange = shouldShowWarningOnNameChange;
		initComponentsAbstractClass(descriptionPanelTitle);
		initComponents(vaultTablePanel);
		refreshFromModel();
	}

	/**
	 * for when the user clicks on the create vault item button
	 */
	private void createItem() {
		ApprenticeReflectionUtils.callMethodOnObject(getWindowManagerMethodNameForType(), windowManager, "");
	}

	/**
	 * for when the user clicks on the Edit vault item button
	 */
	private void editItem(final String selectedItemName) {
		ApprenticeReflectionUtils.callMethodOnObject(getWindowManagerMethodNameForType(), windowManager,
				selectedItemName);
	}

	/**
	 * returns the control for this frame
	 */
	public IVaultFrameControl getControl() {
		return control;
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

	@Override
	public Dimension getInitialSize() {
		return new Dimension(470, 370);
	}

	public Box<String> getSelectedItemName() {
		if (selectedItemName == null) {
			return Box.with(selectedItemName);
		} else {
			return Box.empty();
		}
	}

	/**
	 * returns the "central" panel that has the table with the vaultitems
	 */
	public JPanel getVaultTablePanel() {
		return vaultTablePanel;
	}

	/**
	 * returns the reference tothe {@link IWindowManager}
	 */
	public IWindowManager getWindowManager() {
		return windowManager;
	}

	/**
	 * returns the appropriate showXXXFrame() method name
	 */
	private String getWindowManagerMethodNameForType() {
		return "show" + StringUtils.capitalize(itemType.toString()) + "Frame";
	}

	/**
	 * init the subclass components using the main vault table panel
	 */
	public abstract void initComponents(JPanel vaultTablePanel);

	private void initComponentsAbstractClass(final String descriptionPanelTitle) {
		vaultTablePanel = new JPanel();
		vaultTablePanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC, FormFactory.PREF_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("fill:default:grow"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25dlu"), }));

		tableModel = new VaultFrameTableModel();
		table = new ApprenticeTable(tableModel);
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(0, 200));
		vaultTablePanel.add(scrollPane, "2, 2, fill, fill");
		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) {
				if (event.getType() == TableModelEvent.UPDATE) {
					if (table.getSelectedName().hasContent()) {
						final String newName = table.getSelectedName().getContent();
						if (shouldShowWarningOnNameChange) {
							if (!getWindowUtils()
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
						} catch (final NameAlreadyExistsEx e) {
							getWindowUtils().showErrorMessage(
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
					final Nameable selectedItem =
						control.getVault().getUniqueNamedResult(selectedItemName, itemType.type);
					descriptionPanel.setNameable(selectedItem);;
				}
			}
		});

		descriptionPanel =
			new DescriptionPanel(control, getWindowUtils(), descriptionPanelTitle, DescriptionPanelType.LABEL);
		vaultTablePanel.add(descriptionPanel, "2, 4, fill, fill");

		final JPanel buttonPanel = new JPanel();
		vaultTablePanel.add(buttonPanel, "2, 6, fill, fill");

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
				editItem(selectedItemName);
			}
		});
		buttonPanel.add(btnEdit);
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				control.deleteNameable(selectedItemName, itemType);
			}
		});
		buttonPanel.add(btnDelete);
	}

	@Override
	public void refreshFromModel() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				tableModel.clearAllRows();
				final Vault vault = control.getVault();
				final Collection<? extends Nameable> items = vault.getAllNameables(itemType.type);
				for (final Nameable item : items) {
					tableModel.addRow(new Object[] { item.getName(), vault.getPrettyUpdateTime(item) });
				}
				if (selectedItemName != null) {
					table.getSelectionModel().setSelectionInterval(selectedItemIndex, selectedItemIndex);
				}
				refreshFromModelSubclass();
			}
		});
	}

	/**
	 * refresh any components from vaultframe subclasses
	 */
	public abstract void refreshFromModelSubclass();
}
