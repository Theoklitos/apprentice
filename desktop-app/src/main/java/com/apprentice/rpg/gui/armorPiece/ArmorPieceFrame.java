package com.apprentice.rpg.gui.armorPiece;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.GuiItemCreationEx;
import com.apprentice.rpg.gui.NumericTextfield;
import com.apprentice.rpg.gui.TextfieldWithColorWarning;
import com.apprentice.rpg.gui.description.DescriptionPanel;
import com.apprentice.rpg.gui.description.ModifiableTextFieldPanel.DescriptionPanelType;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.random.dice.RollWithSuffix;
import com.apprentice.rpg.util.Box;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Handles creation and editing of {@link ArmorPiece}s
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceFrame extends ApprenticeInternalFrame<IServiceLayer> implements IArmorPieceFrame {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ArmorPieceFrame.class);

	private static final long serialVersionUID = 1L;
	private JTextField txtfldName;
	private JTextField txtfldBodyPartDesignator;
	private DescriptionPanel descriptionPanel;
	private JCheckBox chkboxNoIdentifier;
	private NumericTextfield txtfldMaxHP;
	private TextfieldWithColorWarning txtfldDamageReduction;

	public ArmorPieceFrame(final IServiceLayer control) {
		super(control, "New Armor Piece Frame");
		initComponents();
	}

	@Override
	public void display(final IArmorPiece armorPiece) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				getReferenceToSelf().setTitle("Edit Armor Piece");
				txtfldName.setText(armorPiece.getName());
				txtfldMaxHP.setText(String.valueOf(armorPiece.getDurability().getMaximum()));
				descriptionPanel.setNameable(armorPiece);
				if (armorPiece.getBodyPartDesignator().isEmpty()) {
					chkboxNoIdentifier.setSelected(true);
					txtfldBodyPartDesignator.setEnabled(false);
				} else {
					txtfldBodyPartDesignator.setText(armorPiece.getBodyPartDesignator().getContent());
				}
				txtfldDamageReduction.setText(armorPiece.getDamageReduction().toString());
			}
		});
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(500, 340);
	}

	@Override
	public ItemType getType() {
		return ItemType.ARMOR_PIECE;
	}

	private void initComponents() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25dlu"), }));

		final JPanel armorInfoPanel = new JPanel();
		getContentPane().add(armorInfoPanel, "2, 2, fill, fill");
		armorInfoPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("pref:grow"), FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		final JLabel lblName = new JLabel("Name:");
		armorInfoPanel.add(lblName, "1, 1, right, default");

		txtfldName = new JTextField();
		armorInfoPanel.add(txtfldName, "3, 1, fill, default");
		txtfldName.setColumns(10);

		final JLabel lblMaxHP = new JLabel("Max Hit Points:");
		armorInfoPanel.add(lblMaxHP, "5, 1, right, default");

		txtfldMaxHP = new NumericTextfield(false);
		armorInfoPanel.add(txtfldMaxHP, "7, 1, left, default");
		txtfldMaxHP.setColumns(5);

		final JLabel lblIdentifier = new JLabel("Body Part Identifier:");
		armorInfoPanel.add(lblIdentifier, "1, 3, right, default");

		txtfldBodyPartDesignator = new JTextField();
		armorInfoPanel.add(txtfldBodyPartDesignator, "3, 3, 5, 1, fill, default");
		txtfldBodyPartDesignator.setColumns(10);

		chkboxNoIdentifier = new JCheckBox("Miscellaneous Armor Piece");
		armorInfoPanel.add(chkboxNoIdentifier, "1, 5, 5, 1, center, default");

		final JLabel lblNewLabel = new JLabel("Damage Reduction");
		armorInfoPanel.add(lblNewLabel, "1, 7, right, default");

		txtfldDamageReduction = new TextfieldWithColorWarning(RollWithSuffix.class);
		armorInfoPanel.add(txtfldDamageReduction, "3, 7, fill, default");
		txtfldDamageReduction.setColumns(10);

		final JButton btnSetDamageReduction = new JButton("Set D.R.");
		armorInfoPanel.add(btnSetDamageReduction, "5, 7, 3, 1");
		btnSetDamageReduction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				final Box<RollWithSuffix> result = getWindowUtils().showDamageReductionDialog();
				if (result.hasContent()) {
					txtfldDamageReduction.setText(result.getContent().toString());
				}
			}
		});

		chkboxNoIdentifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				txtfldBodyPartDesignator.setEnabled(!chkboxNoIdentifier.isSelected());
			}

		});

		descriptionPanel = new DescriptionPanel(getControl(), "Description", DescriptionPanelType.TEXTFIELD);
		descriptionPanel.setPreferredSize(new Dimension(0, 50));
		getContentPane().add(descriptionPanel, "2, 4, fill, fill");

		final JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, "2, 6, fill, fill");

		final JButton btnSave = new JButton("Save");
		buttonPanel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				IArmorPiece armorPiece = null;
				try {
					if (getItem().isEmpty()) {
						validateAllFields();
						armorPiece =
							new ArmorPiece(txtfldName.getText(), txtfldMaxHP.getTextAsInteger(), new RollWithSuffix(
									txtfldDamageReduction.getText()));
						armorPiece.setPrototype(true);
					} else {
						armorPiece = (IArmorPiece) getItem().getContent();
					}
					setArmorPieceFromGui(armorPiece);
					updateParameter(armorPiece);
				} catch (final GuiItemCreationEx e) {
					getWindowUtils().showErrorMessage(e.getMessage(), "Wrong or Missing Value");
				} catch (final NameAlreadyExistsEx e) {
					getWindowUtils().showErrorMessage(
							"There already exists an armor piece named " + armorPiece.getName(), "Wrong Value");
				}
			}
		});
	}

	@Override
	public void refreshFromModel() {
		// nothing
	}

	/**
	 * changes given armor piece based on what is set in the gui
	 */
	private void setArmorPieceFromGui(final IArmorPiece armorPiece) {
		armorPiece.setName(txtfldName.getText().trim());
		armorPiece.setDescription(descriptionPanel.getText());
		armorPiece.getDurability().setMaximum(txtfldMaxHP.getTextAsInteger());
		armorPiece.setDamageReductionRoll(new RollWithSuffix(txtfldDamageReduction.getText()));
		if (chkboxNoIdentifier.isSelected()) {
			armorPiece.setBodyPartDesignator(null);
		} else {
			armorPiece.setBodyPartDesignator(txtfldBodyPartDesignator.getText());
		}
	}

	/**
	 * makes sure the inputed data makes sense
	 */
	private void validateAllFields() throws GuiItemCreationEx {
		if (StringUtils.isBlank(txtfldName.getText())) {
			throw new GuiItemCreationEx("A name is needed.");
		}
		if (StringUtils.isBlank(txtfldMaxHP.getText()) || txtfldMaxHP.getTextAsInteger() < 1) {
			throw new GuiItemCreationEx("You need to enter a maximum armor HP value of at least 1.");
		}
		final GuiItemCreationEx drEx = new GuiItemCreationEx("Damage Reduction not set or not understood.");
		if (StringUtils.isBlank(txtfldDamageReduction.getText())) {
			throw drEx;
		}
		try {
			new RollWithSuffix(txtfldDamageReduction.getText());
		} catch (final ParsingEx e) {
			throw drEx;
		}
	}
}
