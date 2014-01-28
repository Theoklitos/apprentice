package com.apprentice.rpg.gui.vault.type;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.ApprenticeTable;
import com.apprentice.rpg.gui.GlobalWindowState;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.vault.GenericVaultFrame;
import com.apprentice.rpg.gui.vault.VaultFrameTableModel;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrameControl.ItemType;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.util.Box;
import com.google.common.collect.Lists;
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

	private VaultFrameTableModel typeTableModel;
	private ApprenticeTable typeTable;
	private VaultFrameTableModel bodyPartTableModel;
	private ApprenticeTable bodyPartTable;
	private VaultFrameTableModel bodyPartVaultTableModel;
	private ApprenticeTable bodyPartVaultTable;
	private final List<IType> typeBuffer;
	private final List<BodyPart> partBuffer;
	private IType lastSelectedType;
	private Nameable lastSelectedPart;

	private final ITypeAndBodyPartFrameControl control;
	private JPanel buttonPanel_1;

	private JPanel bodyPartsForTypePanel;

	private TitledBorder bodyPartsForTypePanelBorder;

	public TypeAndBodyPartFrame(final IGlobalWindowState globalWindowState, final ITypeAndBodyPartFrameControl control) {
		super(new GlobalWindowState(), "Types and Body Parts"); // TODO jigger
		this.control = control;
		typeBuffer = Lists.newArrayList();
		partBuffer = Lists.newArrayList();

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

		final JButton btnNewButton = new JButton("New button");
		buttonPanel_1.add(btnNewButton);
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
		if (getSelected(bodyPartVaultTable, partBuffer).isEmpty() || lastSelectedType == null) {
			return;
		}
		final BodyPart newPart = getSelectedBodyPart().getContent();
		if (!lastSelectedType.getParts().contains(newPart)) {
			lastSelectedType.getParts().add(newPart);
			control.update(lastSelectedType, ItemType.TYPE);
			LOG.info("Added body part \"" + newPart.getName() + "\" to " + lastSelectedType.getName() + " type.");
		}
	}

	/**
	 * when a new type or body part is created
	 */
	private final void createNewItem(final JPopupMenu popup, final ItemType type) {
		final String name = WindowUtils.showInputDialog("Enter a name for the new " + type + "", "New " + type);
		if (StringUtils.isBlank(name)) {
			return;
		}
		try {
			control.create(name, type);
		} catch (final ItemAlreadyExistsEx e) {
			WindowUtils.showErrorMessage("A " + type + " with name \"" + name + "\" already exists!");
			// call the click again
			final int buttonNumber = type.equals(ItemType.TYPE) ? 0 : 1;
			((JMenuItem) popup.getComponent(buttonNumber)).doClick();
		}
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(600, 400);
	}

	/**
	 * gets the selected name from the table and returns it if it exists inside the buffer
	 */
	private Box<Nameable> getSelected(final ApprenticeTable table, final List<? extends Nameable> buffer) {
		final int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			return Box.empty();
		} else {
			final String selectedName = (String) table.getModel().getValueAt(selectedRow, 0);
			for (final Nameable part : buffer) {
				if (selectedName.equals(part.getName())) {
					return Box.with(part);
				}
			}
			return Box.empty();
		}
	}

	/**
	 * similar to getSelected(), this method looks in the right-hand body part table and returns a
	 * {@link BodyPart} object
	 */
	private Box<BodyPart> getSelectedBodyPart() {
		final Box<Nameable> selected = getSelected(bodyPartVaultTable, partBuffer);
		if (selected.hasContent()) {
			for (final BodyPart part : partBuffer) {
				if (selected.getContent().getName().equals(part.getName())) {
					return Box.with(part);
				}
			}
		}
		return Box.empty();
	}

	/**
	 * returns any body part from the middle table that might be selected
	 */
	private Box<BodyPart> getSelectedBodyPartForType() {
		if (getSelectedType().isEmpty()) {
			return Box.empty();
		}
		if (bodyPartTable.getSelectedRow() != -1) {
			final String name = (String) bodyPartTable.getModel().getValueAt(bodyPartTable.getSelectedRow(), 0);
			for (final BodyPart typeBodyPart : getSelectedType().getContent().getParts()) {
				if (typeBodyPart.getName().equals(name)) {
					return Box.with(typeBodyPart);
				}
			}
		}
		return Box.empty();
	}

	/**
	 * returns any element that might be selected from the two edge tables (left or right)
	 */
	private Box<Nameable> getSelectedElement() {
		if (getSelected(bodyPartVaultTable, partBuffer).hasContent()) {
			return getSelected(bodyPartVaultTable, partBuffer);
		} else if (getSelected(typeTable, typeBuffer).hasContent()) {
			return getSelected(typeTable, typeBuffer);
		} else {
			return Box.empty();
		}
	}

	/**
	 * which of the two vault tables is selected? Empty box if neither
	 */
	final Box<ItemType> getSelectedTable() {
		if (typeTable.getSelectedRow() != -1) {
			return Box.with(ItemType.TYPE);
		} else if (bodyPartVaultTable.getSelectedRow() != -1) {
			return Box.with(ItemType.BODY_PART);
		}
		return Box.empty();
	}

	/**
	 * similar to getSelected(), this method looks in the left-hand Type table and returns a {@link IType}
	 * object
	 */
	private Box<IType> getSelectedType() {
		final Box<Nameable> selected = getSelected(typeTable, typeBuffer);
		if (selected.hasContent()) {
			for (final IType type : typeBuffer) {
				if (selected.getContent().getName().equals(type.getName())) {
					return Box.with(type);
				}
			}
		}
		return Box.empty();
	}

	private void initComponents() {
		typeTableModel = new VaultFrameTableModel();
		typeTable = new ApprenticeTable(typeTableModel);
		typeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				if (getSelectedType().hasContent()) {
					lastSelectedType = getSelectedType().getContent();
					updateBodyPartsTableForSelectedType();
					bodyPartVaultTable.clearSelection();
				}
			}
		});

		final JLabel lblTypes = new JLabel("Types:");
		getContentPane().add(lblTypes, "2, 2");

		bodyPartsForTypePanel = new JPanel();
		bodyPartsForTypePanelBorder =
			new TitledBorder(new LineBorder(new Color(184, 207, 229)), "No Type Selected", TitledBorder.LEADING,
					TitledBorder.TOP, null, null);
		bodyPartsForTypePanel.setBorder(bodyPartsForTypePanelBorder);
		getContentPane().add(bodyPartsForTypePanel, "4, 2, 1, 3, fill, fill");
		bodyPartsForTypePanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"), }));

		final JScrollPane scrollPaneBodyParts = new JScrollPane();
		bodyPartsForTypePanel.add(scrollPaneBodyParts, "1, 1, 1, 3, fill, fill");
		bodyPartTableModel = new VaultFrameTableModel("Body Part", "Strike %");
		bodyPartTable = new ApprenticeTable(bodyPartTableModel);
		scrollPaneBodyParts.setViewportView(bodyPartTable);

		final JLabel lblBodyParts = new JLabel("All Body Parts:");
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
		bodyPartVaultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				if (getSelected(bodyPartVaultTable, partBuffer).hasContent()) {
					lastSelectedPart = getSelected(bodyPartVaultTable, partBuffer).getContent();
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

		buttonPanel_1 = new JPanel();
		getContentPane().add(buttonPanel_1, "2, 8, 7, 1, fill, fill");

		addCreationButton(buttonPanel_1);

		final JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (getSelectedTable().isEmpty()) {
					return;
				}
				final ItemType itemType = getSelectedTable().getContent();
				if (getSelectedElement().isEmpty()) {
					return;
				}
				final Nameable element = getSelectedElement().getContent();
				final String newName =
					WindowUtils.showInputDialog(
							"Enter new name for the " + itemType + " \"" + element.getName() + "\"", "Name Change");
				element.setName(newName);
				try {
					control.update(element, itemType);
				} catch (final NameAlreadyExistsEx e) {
					WindowUtils.showErrorMessage("A " + itemType + " with this name already exists!");
				}
			}
		});
		buttonPanel_1.add(btnEdit);

		final JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				if (getSelectedTable().isEmpty()) {
					return;
				}
				final ItemType itemType = getSelectedTable().getContent();
				if (getSelectedElement().isEmpty()) {
					return;
				}
				final Nameable element = getSelectedElement().getContent();
				if (WindowUtils.showConfigrmationDialog("Are you sure you want to delete the \"" + element.getName()
					+ "\" " + itemType + "?\nThis will probably have repercussions on Player Characters that use it.",
						"Confirm Deletion")) {
					control.delete(element, itemType);
				}

			}
		});
		buttonPanel_1.add(btnDelete);
	}

	/**
	 * fills the body part table
	 */
	private void populateBodyPartsVault() {
		final List<BodyPart> parts = control.getBodyParts();
		bodyPartVaultTable.clearRows();
		partBuffer.clear();
		partBuffer.addAll(parts);
		for (final BodyPart part : parts) {
			bodyPartVaultTableModel.addRow(new Object[] { part.getName(), "TBI" });
		}
	}

	/**
	 * fills the type table
	 */
	private void populateTypes() {
		final List<IType> types = control.getTypes();
		typeBuffer.clear();
		typeBuffer.addAll(types);
		typeTable.clearRows();
		for (final Nameable type : typeBuffer) {
			typeTableModel.addRow(new Object[] { type.getName(), "TBI" });
		}
	}

	public void refreshFromModel() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				populateTypes();
				populateBodyPartsVault();
				updateBodyPartsTableForSelectedType();
			}
		});
	}

	/**
	 * removes the selected body part from the current type
	 */
	private void removePartFromType() {
		if (getSelectedBodyPartForType().isEmpty() || lastSelectedType == null) {
			return;
		}
		final BodyPart newPart = getSelectedBodyPartForType().getContent();
		if (lastSelectedType.getParts().contains(newPart)) {
			lastSelectedType.getParts().remove(newPart);
			control.update(lastSelectedType, ItemType.TYPE);
			LOG.info("Removed body part \"" + newPart.getName() + "\" from " + lastSelectedType.getName() + " type.");
		}
	}

	/**
	 * based on which {@link IType} is selected, updates the middle table.
	 */
	private void updateBodyPartsTableForSelectedType() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				bodyPartTable.clearRows();
				final IType selectedType;
				if (getSelectedType().hasContent()) {
					selectedType = getSelectedType().getContent();
				} else if (lastSelectedType != null) {
					selectedType = lastSelectedType;
				} else {
					return;
				}
				System.out.println("Will refresh for " + selectedType);
				bodyPartsForTypePanelBorder.setTitle(selectedType.getName() + " Body Parts:");
				for (final BodyPart part : selectedType.getParts()) {
					bodyPartTableModel.addRow(new Object[] { part.getName() });
				}
				bodyPartsForTypePanel.revalidate();
				bodyPartsForTypePanel.repaint();
			}
		});
	}

}
