package com.apprentice.rpg.gui.armor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.GuiItemCreationEx;
import com.apprentice.rpg.gui.armorPiece.ArmorPieceFrame;
import com.apprentice.rpg.gui.description.DescriptionPanel;
import com.apprentice.rpg.gui.description.ModifiableTextFieldPanel.DescriptionPanelType;
import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.armor.ArmorDoesNotFitEx;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Handles creation and editing of {@link Armor}s
 * 
 * @author theoklitos
 * 
 */
public class ArmorFrame extends ApprenticeInternalFrame<IServiceLayer> implements IArmorFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtfldName;
	private BodyPartArmorPieceTable mappingTable;
	private BodyPartArmorPieceTableModel tableModel;
	private JComboBox<IType> cmbBoxType;
	private DescriptionPanel descriptionPanel;

	public ArmorFrame(final IServiceLayer control) {
		super(control, "New Armor");
		checkDoArmorPiecesExist();
		initComponents();
		refreshFromModel();
	}

	/**
	 * adds the armor pieces from the table one by one to the given armor
	 * 
	 * @throws GuiItemCreationEx
	 */
	private void addArmorPiecesToArmor(final Armor armor) throws GuiItemCreationEx {
		final LinkedHashMap<BodyPart, IArmorPiece> modelData = tableModel.getModelData();
		final Iterator<Entry<BodyPart, IArmorPiece>> iterator = modelData.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<BodyPart, IArmorPiece> entry = iterator.next();
			try {
				armor.setArmorPieceForBodyPart(entry.getKey(), entry.getValue());
			} catch (final ArmorDoesNotFitEx e) {
				throw new GuiItemCreationEx(e);
			}
		}
	}

	private void checkDoArmorPiecesExist() {
		if (getControl().getVault().getAllPrototypeNameables(IArmorPiece.class).size() == 0) {
			if (getWindowUtils()
					.showWarningQuestionMessage(
							"There are no armor pieces in the database.\nYou need to create at least one before proceeding.\nOpen the armor piece creation window now?",
							"No Armor Pieces Found")) {
				close();
				getControl().getEventBus().postShowFrameEvent(ArmorPieceFrame.class);
			}
		}
	}

	/**
	 * Reads the info in the table to a {@link Armor} and saves/updates
	 */
	private void collectionArmorDataAndUpdate() {
		Armor armor = null;
		try {
			final IType type = (IType) cmbBoxType.getSelectedItem();
			if (getItem().isEmpty()) {
				validateAllFields();
				armor = new Armor(type);
				armor.setPrototype(true);
			} else {
				armor = (Armor) getItem().getContent();
				armor.setType(type);
			}
			armor.setName(txtfldName.getText());
			armor.setDescription(descriptionPanel.getText());
			addArmorPiecesToArmor(armor);
			updateParameter(armor);
		} catch (final GuiItemCreationEx e) {
			getWindowUtils().showErrorMessage(e.getMessage(), "Wrong or Missing Value");
		} catch (final NameAlreadyExistsEx e) {
			getWindowUtils().showErrorMessage("There already exists an armor named " + armor.getName(), "Wrong Value");
		}
	}

	@Override
	public void display(final Armor armor) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				getReferenceToSelf().setTitle("Edit Armor");
				txtfldName.setText(armor.getName());
				descriptionPanel.setNameable(armor);
				tableModel.getModelData().clear();
				final Iterator<Entry<BodyPart, IArmorPiece>> iterator =
					armor.getArmorToBodyPartMapping().entrySet().iterator();
				while (iterator.hasNext()) {
					final Entry<BodyPart, IArmorPiece> entry = iterator.next();
					tableModel.getModelData().put(entry.getKey(), entry.getValue());
				}
				tableModel.fireTableDataChanged();
			}
		});

	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(500, 350);
	}

	@Override
	public ItemType getType() {
		return ItemType.ARMOR;
	}

	private void initComponents() {
		getContentPane()
				.setLayout(
						new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
							ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(50dlu;pref):grow"),
							FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25dlu"), }));

		final JPanel armorInfoPanel = new JPanel();
		getContentPane().add(armorInfoPanel, "2, 2, fill, fill");
		armorInfoPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
			FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
			FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("default:grow"), }));

		final JLabel lblName = new JLabel("Name:");
		armorInfoPanel.add(lblName, "2, 2, right, default");

		txtfldName = new JTextField();
		armorInfoPanel.add(txtfldName, "4, 2, fill, default");
		txtfldName.setColumns(10);

		final JLabel lblType = new JLabel("Type:");
		armorInfoPanel.add(lblType, "6, 2, right, default");

		cmbBoxType = new JComboBox<IType>();
		armorInfoPanel.add(cmbBoxType, "8, 2, fill, default");
		setRenderer(cmbBoxType);
		cmbBoxType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				typeChangedInCheckbox();
			}
		});

		tableModel = new BodyPartArmorPieceTableModel();
		mappingTable = new BodyPartArmorPieceTable(tableModel);
		final JScrollPane scrollPane = new JScrollPane(mappingTable);
		armorInfoPanel.add(scrollPane, "2, 4, 7, 1, fill, fill");

		descriptionPanel = new DescriptionPanel(getControl(), "Description", DescriptionPanelType.TEXTFIELD);
		descriptionPanel.setPreferredSize(new Dimension(0, 50));
		getContentPane().add(descriptionPanel, "2, 4, fill, fill");

		final JPanel panel = new JPanel();
		getContentPane().add(panel, "2, 6, fill, fill");

		final JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				collectionArmorDataAndUpdate();
			}
		});
		panel.add(btnSave);
	}

	@Override
	public void refreshFromModel() {
		// redraw the selected list
		final Vector<IType> armorPiecesVector = new Vector<IType>(getControl().getVault().getAllNameables(IType.class));
		cmbBoxType.setModel(new DefaultComboBoxModel<IType>(armorPiecesVector));
		cmbBoxType.setSelectedIndex(cmbBoxType.getSelectedIndex());
	}

	/**
	 * Sets a cell renderer that displays only the name of the IType
	 */
	@SuppressWarnings({ "rawtypes" })
	private void setRenderer(final JComboBox<IType> cmbboxType) {
		cmbboxType.setRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(final JList list, final Object value, final int index,
					final boolean isSelected, final boolean cellHasFocus) {
				Object item = value;

				if (item != null && IType.class.isAssignableFrom(item.getClass())) {
					item = ((IType) item).getName();
				}
				return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
			}
		});
	}

	private void typeChangedInCheckbox() {
		final Collection<IArmorPiece> allArmorPieces =
			getControl().getVault().getAllPrototypeNameables(IArmorPiece.class);
		final IType selectedType = (IType) cmbBoxType.getSelectedItem();
		mappingTable.setForArmorPiecesAndType(allArmorPieces, selectedType);
	}

	/**
	 * makes sure the inputed data makes sense
	 */
	private void validateAllFields() throws GuiItemCreationEx {
		if (StringUtils.isBlank(txtfldName.getText())) {
			throw new GuiItemCreationEx("A name is needed.");
		}
	}

}
