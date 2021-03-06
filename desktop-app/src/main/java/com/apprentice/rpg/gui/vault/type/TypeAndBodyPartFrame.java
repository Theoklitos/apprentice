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
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.NoResultsFoundEx;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ApprenticeTable;
import com.apprentice.rpg.gui.description.DescriptionPanel;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.gui.vault.VaultTableModel;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMapping;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.IntegerRange;
import com.google.inject.util.Types;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * A kind of {@link AbstractVaultFrame} which is meant to update {@link Types} and {@link BodyPart}s
 * 
 * @author theoklitos
 * 
 */
public class TypeAndBodyPartFrame extends ApprenticeInternalFrame<ITypeAndBodyPartFrameControl> implements
		ITypeAndBodyPartFrame {

	private static Logger LOG = Logger.getLogger(TypeAndBodyPartFrame.class);

	private static final long serialVersionUID = 1L;

	private JPanel buttonPanel;
	private JPanel bodyPartsForTypePanel;

	private VaultTableModel typeTableModel;
	private ApprenticeTable typeTable;
	private VaultTableModel bodyPartTableModel;
	private ApprenticeTable bodyPartTable;
	private VaultTableModel bodyPartVaultTableModel;
	private ApprenticeTable bodyPartVaultTable;
	private DescriptionPanel descriptionPanel;

	private final TypeAndBodyPartFrameState state;	

	public TypeAndBodyPartFrame(final ITypeAndBodyPartFrameControl control) {
		super(control, "Types and Body Parts");
		this.state = getControl().getState();

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
		refreshFromModel();
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
			final Box<IType> newTypeBox =
				state.addNewPartToType(typeName,
						getControl().getVault().getUniqueNamedResult(bodyPartName, BodyPart.class));
			if (newTypeBox.hasContent()) {
				getControl().createOrUpdate(newTypeBox.getContent());
			} else {
				refreshFromModel();
			}
		}
	}

	/**
	 * when a new type or body part is created
	 */
	private final void createNewItem(final JPopupMenu popup, final ItemType type) {
		final Box<String> nameBox =
			getWindowUtils().showInputDialog("Enter a name for the new " + type + ":", "New " + type);
		if (nameBox.isEmpty()) {
			return;
		}
		final String name = nameBox.getContent();
		try {
			switch (type) {
			case TYPE:
				state.createType(name);
				state.setSelectedTypeName(name);
				refreshFromModel();
				break;
			case BODY_PART:
				try {
					getControl().getVault().getUniqueNamedResult(name, BodyPart.class);
					// to see if its already there
					throw new ItemAlreadyExistsEx();
				} catch (final NoResultsFoundEx e) {
					state.setSelectedBodyPartName(name);
					getControl().createOrUpdate(new BodyPart(name));
				}
			default:
			}
		} catch (final ItemAlreadyExistsEx e) {
			getWindowUtils().showErrorMessage("A " + type + " with name \"" + name + "\" already exists!");
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
			if (getWindowUtils().showConfigrmationDialog(
					"Are you sure you want to delete the \"" + name + "\" " + itemType
						+ "?\nThis will probably have repercussions on Player Characters that use it.",
					"Confirm Deletion")) {
				getControl().deleteNameable(name, itemType);
			}
		}

	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(600, 400);
	}

	private void initComponents() {
		typeTableModel = new VaultTableModel();
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
					try {
						descriptionPanel.setNameable(getControl().getVault().getUniqueNamedResult(selectedName,
								IType.class));
					} catch (final NoResultsFoundEx e) {
						// type is not fully formed, do nothing
					}
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
		bodyPartTableModel = new VaultTableModel("Body Part", "Strike %", 2);
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
							getControl().getVault().getUniqueNamedResult(bodyPartTable.getSelectedName().getContent(),
									BodyPart.class);
						try {
							final IntegerRange newRange =
								new IntegerRange(bodyPartTableModel.getValueAt(bodyPartTable.getSelectedRow(), 1)
										.toString());
							final Box<IType> newTypeBox =
								state.addPartToType(state.getActiveType().getContent(), selectedPart, newRange);
							if (newTypeBox.hasContent()) {
								getControl().createOrUpdate(newTypeBox.getContent());
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

		bodyPartVaultTableModel = new VaultTableModel();
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
					descriptionPanel.setNameable(getControl().getVault().getUniqueNamedResult(selectedName,
							BodyPart.class));
					typeTable.clearSelection();
				}
			}
		});
		scrollPaneBodyPartsVault.setViewportView(bodyPartVaultTable);

		descriptionPanel = new DescriptionPanel(getControl());
		descriptionPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Description",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(descriptionPanel, "2, 6, 7, 1, fill, fill");

		final JLabel descriptionLabel = new JLabel("");
		descriptionPanel.add(descriptionLabel);

		buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, "2, 8, 7, 1, fill, fill");

		addCreationButton(buttonPanel);

		final JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				deleteItem();
			}
		});
		buttonPanel.add(btnDelete);
	}

	@Override
	public void refreshFromModel() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// update
				state.setTypes(getControl().getVault().getAll(IType.class));
				state.setBodyParts(getControl().getVault().getAll(BodyPart.class));
				// set the tables
				typeTableModel.clearAllRows();
				for (final String typeName : state.getTypeNames()) {
					typeTableModel.addRow(new String[] { typeName,
						getControl().getLastUpdateTime(typeName, ItemType.TYPE) });
				}
				bodyPartVaultTableModel.clearAllRows();
				for (final BodyPart part : state.getBodyParts()) {
					final String bodyPartName = part.getName();
					bodyPartVaultTableModel.addRow(new String[] { bodyPartName,
						getControl().getLastUpdateTime(bodyPartName, ItemType.BODY_PART) });
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

	@Override
	public void removeBodyPartsInView(final BodyPart deletedBodyPart) {
		state.removePartFromAllTypes(deletedBodyPart);
		refreshFromModel();
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
				getControl().createOrUpdate(newTypeBox.getContent());
			} else {
				refreshFromModel();
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
			final VaultTableModel model) {
		try {
			final String newName = model.getValueAt(table.getSelectedRow(), 0).toString();
			if (newName.equals(oldName)) {
				return;
			}
			final Nameable oldItem = getControl().getVault().getUniqueNamedResult(oldName, type.type);
			if (type.equals(ItemType.BODY_PART) && state.shouldProceedWithBodyPartRenamingFlag()) {
				if (!getWindowUtils()
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
				getControl().createOrUpdate(oldItem);
			} catch (final NameAlreadyExistsEx e) {
				getWindowUtils().showErrorMessage("A " + type + " with name \"" + newName + "\" already exists!");
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
	private void tryToChangeTypeName(final String oldName, final ApprenticeTable table, final VaultTableModel model) {
		final String newName = model.getValueAt(table.getSelectedRow(), 0).toString();
		if (oldName.equals(newName)) {
			return;
		}
		try {
			state.changeTypeName(oldName, newName);
		} catch (final NameAlreadyExistsEx e) {
			getWindowUtils().showErrorMessage("A type with name \"" + newName + "\" already exists!");
		}
		if (getControl().getVault().doesNameExist(oldName, IType.class)) {
			// change is also a repo name
			tryToChangeItemName(oldName, ItemType.TYPE, table, model);
		}
		updateBodyPartsTableForActiveType();
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
					final BodyPartToRangeMapping parts = state.getBodyPartsForTypeName(name).getContent();					
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
