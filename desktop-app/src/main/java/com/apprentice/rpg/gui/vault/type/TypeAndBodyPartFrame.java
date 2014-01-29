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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

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
	private TitledBorder bodyPartsForTypePanelBorder;

	private VaultFrameTableModel typeTableModel;
	private ApprenticeTable typeTable;
	private VaultFrameTableModel bodyPartTableModel;
	private ApprenticeTable bodyPartTable;
	private VaultFrameTableModel bodyPartVaultTableModel;
	private ApprenticeTable bodyPartVaultTable;

	private final TypeAndBodyPartFrameState state;

	private final ITypeAndBodyPartFrameControl control;

	public TypeAndBodyPartFrame(final IGlobalWindowState globalWindowState, final ITypeAndBodyPartFrameControl control) {
		super(new GlobalWindowState(), "Types and Body Parts"); // TODO jigger
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
		// TODO
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
				// TODO
				break;
			case BODY_PART:
				control.create(new BodyPart(name));				
			default:
			}
		} catch (final ItemAlreadyExistsEx e) {
			WindowUtils.showErrorMessage("A " + type + " with name \"" + name + "\" already exists!");
			// call the click again - Deprecated
			// final int buttonNumber = type.equals(ItemType.TYPE) ? 0 : 1;
			// ((JMenuItem) popup.getComponent(buttonNumber)).doClick();
		}
	}

	private void deleteItem() {
		// TODO
		//WindowUtils.showConfigrmationDialog("Are you sure you want to delete the \"" + element.getName()
		//		+ "\" " + itemType + "?\nThis will probably have repercussions on Player Characters that use it.",
		//			"Confirm Deletion"))		
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

	private void initComponents() {
		typeTableModel = new VaultFrameTableModel();
		typeTable = new ApprenticeTable(typeTableModel);
		typeTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) {
				if (event.getType() == TableModelEvent.UPDATE) {
					// TODO name change
				}
			}
		});
		typeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				
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
		bodyPartTableModel = new VaultFrameTableModel("Body Part", "Strike %", 2);
		bodyPartTable = new ApprenticeTable(bodyPartTableModel);
		scrollPaneBodyParts.setViewportView(bodyPartTable);
		bodyPartTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) { // TODO
				final IntegerRange newRange =
					new IntegerRange(bodyPartTableModel.getValueAt(bodyPartTable.getSelectedRow(), 1).toString());
				System.out.println(newRange);
			}
		});

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
		bodyPartVaultTableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(final TableModelEvent event) {
				if (event.getType() == TableModelEvent.UPDATE) {
					// TODO
				}
			}
		});
		bodyPartVaultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				
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
				final String jsonText = control.getJsonForAllTypesAndBodyParts();
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
		buttonPanel.add(btnDelete);
	}

	/**
	 * calls the backend (control) and updates the data in the tables
	 */
	public void refreshFromModel() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				state.setTypes(control.getTypes());
				state.setBodyParts(control.getBodyParts());
				// TODO!
			}
		});
	}

	/**
	 * removes the selected body part from the current type
	 */
	private void removePartFromType() {		
		// TODO		
	}	

	/**
	 * changes the name of this bodypart/type, throws error message if not working
	 */
	private void tryToChangeElementName(final ApprenticeTable table, final List<? extends Nameable> buffer,
			final ItemType type) {
		if (table.getSelectedName().isEmpty()) {
			return;
		}
		final String newName = table.getSelectedName().getContent();		
		final Nameable elementWithChangedName = buffer.get(table.getSelectedRow());
		elementWithChangedName.setName(newName);
		try {
			control.update(elementWithChangedName, type);
		} catch (final NameAlreadyExistsEx e) {
			WindowUtils.showErrorMessage("A " + type + " with name \"" + newName + "\" already exists!");
		}
	}

	/**
	 * based on which {@link IType} is selected, updates the middle table.
	 */
	private void updateBodyPartsTableForSelectedType() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				// set title
				//bodyPartsForTypePanel.revalidate();
				//bodyPartsForTypePanel.repaint();
			}
		});
	}

}
