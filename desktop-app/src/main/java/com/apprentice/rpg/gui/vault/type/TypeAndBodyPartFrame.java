package com.apprentice.rpg.gui.vault.type;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.ItemNotFoundEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ApprenticeTable;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.vault.GenericVaultFrame;
import com.apprentice.rpg.gui.vault.VaultFrameTableModel;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.IntegerRange;
import com.google.inject.util.Types;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * A kind of {@link GenericVaultFrame} which is meant to update {@link Types} and {@link BodyPart}s
 * 
 * @author theoklitos
 * 
 */
public class TypeAndBodyPartFrame extends ApprenticeInternalFrame {

	private static Logger LOG = Logger.getLogger(TypeAndBodyPartFrame.class);

	private static final long serialVersionUID = 1L;

	private JPanel buttonPanel;
	private JPanel bodyPartsForTypePanel;

	private VaultFrameTableModel typeTableModel;
	private ApprenticeTable typeTable;
	private VaultFrameTableModel bodyPartTableModel;
	private ApprenticeTable bodyPartTable;
	private VaultFrameTableModel bodyPartVaultTableModel;
	private ApprenticeTable bodyPartVaultTable;

	private final TypeAndBodyPartFrameState state;

	private final ITypeAndBodyPartFrameControl control;

	public TypeAndBodyPartFrame(final IGlobalWindowState globalWindowState, final ITypeAndBodyPartFrameControl control) {
		super(globalWindowState, "Types and Body Parts");
		this.control = control;
		this.state = new TypeAndBodyPartFrameState();

		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(60dlu;default):grow"),
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));

		initComponents();
		refreshFromModel(true);
	}

	/**
	 * makes the button show a small popup menu when clicked
	 * 
	 * @param buttonPanel
	 */
	private void addCreationButton(final JPanel buttonPanel) {
		final JPopupMenu popup = new JPopupMenu();
		popup.add(new JMenuItem(new AbstractAction("New Type") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent event) {
				createNewItem(popup, ItemType.TYPE);
			}

		}));
		popup.add(new JMenuItem(new AbstractAction("New Body Part") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent event) {
				createNewItem(popup, ItemType.BODY_PART);
			}
		}));
		final JButton btnNew = new JButton("Create...");
		buttonPanel.add(btnNew);
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * adds any selected vault part to the current type
	 */
	private void addPartToType() {
		if (state.getActiveType().hasContent()) {
			final String typeName = state.getActiveType().getContent();
			final String bodyPartName = state.getSelectedBodyPartName().getContent();
			final Box<IType> newTypeBox = state.addNewPartToType(typeName, control.getBodyPartForName(bodyPartName));
			if (newTypeBox.hasContent()) {
				control.createOrUpdate(newTypeBox.getContent(), ItemType.TYPE);
			} else {
				refreshFromModel(true);
			}
		}
	}

	/**
	 * when a new type or body part is created
	 */
	private final void createNewItem(final JPopupMenu popup, final ItemType type) {
		final String name = WindowUtils.showInputDialog("Enter a name for the new " + type + ":", "New " + type);
		if (StringUtils.isBlank(name)) {
			return;
		}
		try {
			switch (type) {
			case TYPE:
				state.createType(name);
				state.setSelectedTypeName(name);
				refreshFromModel(true);
				break;
			case BODY_PART:
				state.setSelectedBodyPartName(name);
				control.createOrUpdate(new BodyPart(name), ItemType.BODY_PART);
			default:
			}
		} catch (final ItemAlreadyExistsEx e) {
			WindowUtils.showErrorMessage("A " + type + " with name \"" + name + "\" already exists!");
			// call the click again - Deprecated
			// final int buttonNumber = type.equals(ItemType.TYPE) ? 0 : 1;
			// ((JMenuItem) popup.getComponent(buttonNumber)).doClick();
		}
	}

	/**
	 * deletes the selected item or bodypart
	 */
	private void deleteItem() {
		if (state.getLastSelectedItemType().hasContent()) {
			String name = null;
			final ItemType itemType = state.getLastSelectedItemType().getContent();
			switch (itemType) {
			case TYPE:
				name = state.getSelectedTypeName().getContent();
				break;
			case BODY_PART:
				name = state.getSelectedBodyPartName().getContent();
				break;
			default:
				break;
			}
			if (WindowUtils.showConfigrmationDialog("Are you sure you want to delete the \"" + name + "\" " + itemType
				+ "?\nThis will probably have repercussions on Player Characters that use it.", "Confirm Deletion")) {
				control.deleteByName(name, itemType);
			}
		}

	}

	/**
	 * shows the popup where the user chooses what he wants to export
	 */
	private void exportItems() throws ItemNotFoundEx, ParsingEx {
		final ExportConfigurationObject config = new ExportConfigurationObject();
		if (getGlobalWindowState().getWindowUtils().showTypeAndBodyPartNameSelection(config, control.getVault())) {
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Select File for Export");
			fileChooser.setApproveButtonText("Use this File");
			final int result = fileChooser.showDialog(null, null);
			if (result == JFileChooser.APPROVE_OPTION) {
				config.setFileLocation(fileChooser.getSelectedFile().getAbsolutePath());
				control.exportForConfiguration(config);
			}
		}
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(600, 400);
	}

	/**
	 * when the user imports items from a file
	 */
	private void importItems() {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select File to Import from");
		fileChooser.setApproveButtonText("Use this File");
		final int result = fileChooser.showDialog(null, null);
		if (result == JFileChooser.APPROVE_OPTION) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					try {
						control.importFromFile(fileChooser.getSelectedFile().getAbsolutePath());
					} catch (final Exception e) {
						WindowUtils.showErrorMessage(e.getMessage(), "Error during Import");
					}
				}
			});
		}
	}

	private void initComponents() {
		typeTableModel = new VaultFrameTableModel();
		typeTable = new ApprenticeTable(typeTableModel);
		typeTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) {
				if (event.getType() == TableModelEvent.UPDATE) {
					tryToChangeTypeName(state.getSelectedTypeName().getContent(), typeTable, typeTableModel);
				}
			}
		});
		typeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				if (typeTable.getSelectedName().hasContent()) {
					final String selectedName = typeTable.getSelectedName().getContent();
					state.setSelectedTypeName(selectedName);
					updateBodyPartsTableForActiveType();
					bodyPartVaultTable.clearSelection();
				}
			}
		});

		final JLabel lblTypes = new JLabel("Types:");
		lblTypes.setFont(lblTypes.getFont().deriveFont(Font.BOLD));
		getContentPane().add(lblTypes, "2, 2");

		bodyPartsForTypePanel = new JPanel();
		setTitleBorder("No Type Selected", Color.black);
		getContentPane().add(bodyPartsForTypePanel, "4, 2, 1, 3, fill, fill");
		bodyPartsForTypePanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"), }));

		final JScrollPane scrollPaneBodyParts = new JScrollPane();
		bodyPartsForTypePanel.add(scrollPaneBodyParts, "1, 1, 1, 3, fill, fill");
		bodyPartTableModel = new VaultFrameTableModel("Body Part", "Strike %", 2);
		bodyPartTable = new ApprenticeTable(bodyPartTableModel);
		bodyPartTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				if (bodyPartTable.getSelectedName().hasContent()) {
					final String selectedName = bodyPartTable.getSelectedName().getContent();
					state.setSelectedBodyPartForType(state.getSelectedTypeName().getContent(), selectedName);
				}
			}
		});
		scrollPaneBodyParts.setViewportView(bodyPartTable);
		bodyPartTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) {
				try {
					if (event.getType() == TableModelEvent.UPDATE) {
						final BodyPart selectedPart =
							control.getBodyPartForName(bodyPartTable.getSelectedName().getContent());
						try {
							final IntegerRange newRange =
								new IntegerRange(bodyPartTableModel.getValueAt(bodyPartTable.getSelectedRow(), 1)
										.toString());
							final Box<IType> newTypeBox =
								state.addPartToType(state.getActiveType().getContent(), selectedPart, newRange);
							if (newTypeBox.hasContent()) {
								control.createOrUpdate(newTypeBox.getContent(), ItemType.TYPE);
							} else {
								updateBodyPartsTableForActiveType();
							}
						} catch (final IllegalArgumentException e) {
							// wrong text, revert
							final IntegerRange oldRange =
								state.getBodyPartsForTypeName(state.getActiveType().getContent()).getContent()
										.getRangeForBodyPart(selectedPart).getContent();
							state.addPartToType(state.getActiveType().getContent(), selectedPart, oldRange);
							updateBodyPartsTableForActiveType();
						}

					}
				} catch (final Exception e) {
					LOG.warn(e.getMessage());
				}
			}
		});

		final JLabel lblBodyParts = new JLabel("All Body Parts:");
		lblBodyParts.setFont(lblBodyParts.getFont().deriveFont(Font.BOLD));
		getContentPane().add(lblBodyParts, "8, 2");
		final JScrollPane scrollPaneTypes = new JScrollPane(typeTable);
		getContentPane().add(scrollPaneTypes, "2, 4, fill, fill");

		final JPanel rightMiddlePanel = new JPanel();
		getContentPane().add(rightMiddlePanel, "6, 4, center, center");
		rightMiddlePanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("center:pref"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
			FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		final JButton btnAddPart = new JButton("<-");
		btnAddPart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				addPartToType();
			}

		});
		rightMiddlePanel.add(btnAddPart, "2, 2, fill, default");
		final JButton btnRemovePart = new JButton("->");
		btnRemovePart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				removePartFromType();
			}
		});
		rightMiddlePanel.add(btnRemovePart, "2, 4, center, default");

		final JScrollPane scrollPaneBodyPartsVault = new JScrollPane();
		getContentPane().add(scrollPaneBodyPartsVault, "8, 4, fill, fill");

		bodyPartVaultTableModel = new VaultFrameTableModel();
		bodyPartVaultTable = new ApprenticeTable(bodyPartVaultTableModel);
		bodyPartVaultTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) {
				if (event.getType() == TableModelEvent.UPDATE) {
					tryToChangeItemName(state.getSelectedBodyPartName().getContent(), ItemType.BODY_PART,
							bodyPartVaultTable, bodyPartVaultTableModel);
				}
			}
		});
		bodyPartVaultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				if (bodyPartVaultTable.getSelectedName().hasContent()) {
					final String selectedName = bodyPartVaultTable.getSelectedName().getContent();
					state.setSelectedBodyPartName(selectedName);
					typeTable.clearSelection();
				}
			}
		});
		scrollPaneBodyPartsVault.setViewportView(bodyPartVaultTable);

		final JPanel descriptionPanel = new JPanel();
		descriptionPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Description",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(descriptionPanel, "2, 6, 7, 1, fill, fill");

		final JLabel descriptionLabel = new JLabel("");
		descriptionPanel.add(descriptionLabel);

		buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, "2, 8, 7, 1, fill, fill");

		addCreationButton(buttonPanel);

		final JButton btnExport = new JButton("Export");
		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				exportItems();
			}

		});
		buttonPanel.add(btnExport);

		final JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				deleteItem();
			}
		});

		final JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				importItems();
			}
		});
		buttonPanel.add(btnImport);
		buttonPanel.add(btnDelete);
	}

	/**
	 * calls the backend (control) and updates the data in the tables
	 * 
	 * @param shouldHitDatabase
	 */
	public void refreshFromModel(final boolean shouldHitDatabase) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// update
				if (shouldHitDatabase) {
					state.setTypes(control.getTypes());
					state.setBodyParts(control.getBodyParts());
				}
				// set the tables
				typeTableModel.clearAllRows();
				for (final String typeName : state.getTypeNames()) {
					typeTableModel.addRow(new String[] { typeName, "TBI" });
				}
				bodyPartVaultTableModel.clearAllRows();
				for (final BodyPart part : state.getBodyParts()) {
					bodyPartVaultTableModel.addRow(new String[] { part.getName(), "TBI" });
				}
				final Box<ItemType> selected = state.getLastSelectedItemType();
				if (selected.hasContent()) {
					if (selected.getContent().equals(ItemType.TYPE)) {
						selectInTypeTable(state.getSelectedTypeName().getContent(), typeTable);
					} else if (selected.getContent().equals(ItemType.BODY_PART)) {
						selectInTypeTable(state.getSelectedBodyPartName().getContent(), bodyPartVaultTable);
					}
				}
				updateBodyPartsTableForActiveType();
			}
		});
	}

	/**
	 * removes this body part from the temporary types of the view, if exists
	 */
	public void removeBodyPartsInView(final BodyPart deletedBodyPart) {
		state.removePartFromAllTypes(deletedBodyPart);
		refreshFromModel(false);
	}

	/**
	 * removes the selected body part from the current type
	 */
	private void removePartFromType() {
		if (state.getActiveType().hasContent()) {
			final String typeName = state.getActiveType().getContent();
			final String bodyPartName = state.getSelectedBodyPartForTypeName(typeName).getContent();
			final Box<IType> newTypeBox = state.removePartFromType(typeName, new BodyPart(bodyPartName));
			if (newTypeBox.hasContent()) {
				control.createOrUpdate(newTypeBox.getContent(), ItemType.TYPE);
			} else {
				refreshFromModel(true);
			}
		}
	}

	/**
	 * tries to select the given element (based on name) on the given table
	 */
	private void selectInTypeTable(final String name, final ApprenticeTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (name.equals(table.getValueAt(i, 0).toString())) {
				table.getSelectionModel().setSelectionInterval(i, i);
			}
		}
	}

	/**
	 * changes the title of the body parts for type panelr
	 */
	private void setTitleBorder(final String borderTitleText, final Color color) {
		final TitledBorder bodyPartsForTypePanelBorder =
			new TitledBorder(new LineBorder(color), borderTitleText, TitledBorder.LEADING, TitledBorder.TOP, null,
					color);
		bodyPartsForTypePanel.setBorder(bodyPartsForTypePanelBorder);
	}

	/**
	 * attemps to change+store a {@link Nameable}'s name.
	 */
	private void tryToChangeItemName(final String oldName, final ItemType type, final ApprenticeTable table,
			final VaultFrameTableModel model) {
		try {
			final String newName = model.getValueAt(table.getSelectedRow(), 0).toString();
			final Nameable oldItem = control.getTypeOrBodyPartForName(oldName, type);
			if (type.equals(ItemType.BODY_PART) && state.shouldProceedWithBodyPartRenamingFlag()) {
				if (!WindowUtils
						.showConfigrmationDialog(
								"Are you sure you want to rename this body part?\nThis might affect Player Characters that use it, in regards to the armor they can equip.",
								"Change Body Part Name")) {
					state.setShouldProceedWithRenamingFlag(false);
					model.setValueAt(oldItem.getName(), table.getSelectedRow(), 0);
					return;
				}
			}
			oldItem.setName(newName);
			try {
				state.setSelectedItemName(newName, type);
				control.createOrUpdate(oldItem, type);
			} catch (final NameAlreadyExistsEx e) {
				WindowUtils.showErrorMessage("A " + type + " with name \"" + newName + "\" already exists!");
				state.setSelectedItemName(oldName, type);
			} finally {
				state.setShouldProceedWithRenamingFlag(true);
			}

		} catch (final Exception e) {
			// this even is triggered by all kinds of weird places
		}
	}

	/**
	 * attempt to change a {@link IType}s name, whether its already persisted or not
	 */
	private void tryToChangeTypeName(final String oldName, final ApprenticeTable table, final VaultFrameTableModel model) {
		final String newName = model.getValueAt(table.getSelectedRow(), 0).toString();
		if (!control.doesTypeNameExist(newName)) {
			// change is only a temp name
			try {
				state.changeTypeName(oldName, newName);
			} catch (final NameAlreadyExistsEx e) {
				WindowUtils.showErrorMessage("A type with name \"" + newName + "\" already exists!");
			}
			updateBodyPartsTableForActiveType();
			return;
		} else {
			// change is in the repo
			tryToChangeItemName(oldName, ItemType.TYPE, table, model);
		}

	}

	/**
	 * based on which {@link IType} is selected, updates the middle table.
	 */
	private void updateBodyPartsTableForActiveType() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// get the info
				final Box<String> activeTypeBox = state.getActiveType();
				if (activeTypeBox.hasContent()) {
					final String name = activeTypeBox.getContent();
					final BodyPartToRangeMap parts = state.getBodyPartsForTypeName(name).getContent();
					final boolean isMappingCorrect = state.isMappingCorrectForTypeName(name).getContent();
					// set
					if (isMappingCorrect) {
						setTitleBorder(name + " body parts:", Color.black);
					} else {
						setTitleBorder(name + " has inconsistent body parts!", Color.red);
					}
					final Box<String> selectedBodyPartForTypeName = state.getSelectedBodyPartForTypeName(name);
					bodyPartTableModel.clearAllRows();
					int count = 0;
					for (final Entry<IntegerRange, BodyPart> partMapping : parts.getSequentialMapping()) {
						final String bodyPartName = partMapping.getValue().getName();
						bodyPartTableModel.addRow(new String[] { bodyPartName, partMapping.getKey().toString() });
						if (selectedBodyPartForTypeName.hasContent()
							&& bodyPartName.equals(selectedBodyPartForTypeName.getContent())) {
							bodyPartTable.getSelectionModel().setSelectionInterval(count, count);
						}
						count++;
					}
				} else if (activeTypeBox.isEmpty()) {
					setTitleBorder("No Type Selected", Color.black);
				}
				// refresh
				bodyPartsForTypePanel.revalidate();
				bodyPartsForTypePanel.repaint();
			}
		});
	}

}
