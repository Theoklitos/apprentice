package com.apprentice.rpg.gui.weapon;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.GuiItemCreationEx;
import com.apprentice.rpg.gui.NumericTextfield;
import com.apprentice.rpg.gui.TextfieldWithColorWarning;
import com.apprentice.rpg.gui.description.DescriptionPanel;
import com.apprentice.rpg.gui.description.ModifiableTextFieldPanel.DescriptionPanelType;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.windowState.GlobalWindowState;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.model.weapon.Range;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.util.Box;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Edits or creates {@link WeaponPrototype}s
 * 
 * @author theoklitos
 * 
 */
public final class WeaponFrame extends ApprenticeInternalFrame implements IWeaponFrame {

	private static final long serialVersionUID = 1L;

	private final IWeaponFrameControl control;
	private JTextField txtfldName;
	private JLabel lblMeleeDamageLabel;
	private final Box<WeaponPrototype> content;
	private JPanel weaponDataPanel;
	private DescriptionPanel descriptionPanel;
	private JButton btnSave;
	private JLabel lblExtraDamages;
	private JButton btnAddExtraDamage;
	private JButton btnRemoveExtraDamage;
	private JComboBox<DamageRoll> cmbboxExtraDamages;
	private JLabel lblThrownRange;
	private TextfieldWithColorWarning txtfldRange;
	private JLabel lblBlockModifier;
	private NumericTextfield txtfldBlockModifier;
	private JLabel lblThrownDamageLabel;
	private JLabel lblThrownDamage;
	private JButton btnSetThrownDamage;
	private JPanel buttonPanel;
	private JLabel lblHitPoints;
	private NumericTextfield txtfldMaxHP;
	private JComboBox<DamageRoll> cmbboxMeleeDamages;
	private JButton btnAddMeleeDamage;
	private JButton btnRemoveMeleeDamage;
	private JLabel lblAmmunition;
	private JComboBox<AmmunitionTypeWithRange> cmbboxAmmoTypes;
	private JButton btnAddAmmo;
	private JButton btnRemoveAmmo;

	private DamageRoll thrownDamageBuffer;
	private JButton btnRemoveThrownDamage;

	public WeaponFrame(final IGlobalWindowState globalWindowState, final IWeaponFrameControl control,
			final Box<WeaponPrototype> content) {
		super(new GlobalWindowState(new WindowUtils()), ItemType.WEAPON, content); // TODO
		this.control = control;
		this.content = content;

		initComponents();
		if (content.hasContent()) {
			setWeaponForEditing(content.getContent());
		}
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(550, 360);
	}

	private void initComponents() {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("fill:default:grow"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("pref:grow"),
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("25dlu"), }));

		weaponDataPanel = new JPanel();
		getContentPane().add(weaponDataPanel, "2, 2, fill, fill");
		weaponDataPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.PREF_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("pref:grow"), },
				new RowSpec[] { RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		final JLabel lblName = new JLabel("Name:");
		weaponDataPanel.add(lblName, "1, 1, right, default");

		txtfldName = new JTextField();
		weaponDataPanel.add(txtfldName, "3, 1, 2, 1, fill, default");
		txtfldName.setColumns(10);

		lblHitPoints = new JLabel("Max Hit Points:");
		weaponDataPanel.add(lblHitPoints, "5, 1, right, default");

		txtfldMaxHP = new NumericTextfield();
		weaponDataPanel.add(txtfldMaxHP, "7, 1, fill, default");
		txtfldMaxHP.setColumns(3);

		lblMeleeDamageLabel = new JLabel("Melee Damages:");
		weaponDataPanel.add(lblMeleeDamageLabel, "1, 3, right, default");

		cmbboxMeleeDamages = new JComboBox<DamageRoll>();
		weaponDataPanel.add(cmbboxMeleeDamages, "3, 3, fill, default");

		btnAddMeleeDamage = new JButton("Add");
		btnAddMeleeDamage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				showDialogAndAddDamageRoll("Add Melee Damage", cmbboxMeleeDamages);
			}

		});
		weaponDataPanel.add(btnAddMeleeDamage, "5, 3");

		btnRemoveMeleeDamage = new JButton("Remove");
		btnRemoveMeleeDamage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				removeSelectedItem(cmbboxMeleeDamages);
			}
		});
		weaponDataPanel.add(btnRemoveMeleeDamage, "7, 3");

		lblExtraDamages = new JLabel("Extra Damages:");
		weaponDataPanel.add(lblExtraDamages, "1, 5, right, default");

		cmbboxExtraDamages = new JComboBox<DamageRoll>();
		weaponDataPanel.add(cmbboxExtraDamages, "3, 5, fill, default");

		btnAddExtraDamage = new JButton("Add");
		btnAddExtraDamage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				showDialogAndAddDamageRoll("Add Extra Damage", cmbboxMeleeDamages);
			}

		});
		weaponDataPanel.add(btnAddExtraDamage, "5, 5");

		btnRemoveExtraDamage = new JButton("Remove");
		btnRemoveExtraDamage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				removeSelectedItem(cmbboxExtraDamages);
			}
		});
		weaponDataPanel.add(btnRemoveExtraDamage, "7, 5");

		lblThrownRange = new JLabel("Thrown Range:");
		weaponDataPanel.add(lblThrownRange, "1, 7, right, default");

		txtfldRange = new TextfieldWithColorWarning(Range.class);
		weaponDataPanel.add(txtfldRange, "3, 7, fill, default");
		weaponDataPanel.add(txtfldRange, "3, 7");
		txtfldRange.setColumns(10);

		lblBlockModifier = new JLabel("Block Modifier:");
		weaponDataPanel.add(lblBlockModifier, "5, 7, right, default");

		txtfldBlockModifier = new NumericTextfield(true);
		txtfldBlockModifier.setText("0");
		weaponDataPanel.add(txtfldBlockModifier, "7, 7");
		txtfldBlockModifier.setColumns(3);

		lblThrownDamageLabel = new JLabel("Thrown Damage:");
		weaponDataPanel.add(lblThrownDamageLabel, "1, 9, center, fill");

		lblThrownDamage = new JLabel();
		weaponDataPanel.add(lblThrownDamage, "3, 9, center, default");
		lblThrownDamage.setFont(lblThrownDamage.getFont().deriveFont(Font.ITALIC));
		setNoThrownDamage();

		btnSetThrownDamage = new JButton("Set");
		btnSetThrownDamage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				final Box<DamageRoll> thrownDamageBox =
					getWindowUtils().showDamageRollDialog("Set Thrown Damage", control);
				if (thrownDamageBox.hasContent()) {
					thrownDamageBuffer = thrownDamageBox.getContent();
					lblThrownDamage.setText(thrownDamageBox.getContent().toString());
				}
			}
		});
		weaponDataPanel.add(btnSetThrownDamage, "5, 9");

		btnRemoveThrownDamage = new JButton("Remove");
		btnRemoveThrownDamage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				setNoThrownDamage();
			}
		});
		weaponDataPanel.add(btnRemoveThrownDamage, "7, 9");

		lblAmmunition = new JLabel("Ammo Types:");
		weaponDataPanel.add(lblAmmunition, "1, 11, right, default");

		cmbboxAmmoTypes = new JComboBox<AmmunitionTypeWithRange>();
		weaponDataPanel.add(cmbboxAmmoTypes, "3, 11, fill, default");

		btnAddAmmo = new JButton("Add");
		btnAddAmmo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				final Box<AmmunitionTypeWithRange> ammoTypeWithRangeBox =
					getWindowUtils().showAmmunitionTypeAndRangeDialog(control);
				if (ammoTypeWithRangeBox.hasContent()) {
					cmbboxAmmoTypes.addItem(ammoTypeWithRangeBox.getContent());
				}
			}
		});
		weaponDataPanel.add(btnAddAmmo, "5, 11");

		btnRemoveAmmo = new JButton("Remove");
		btnRemoveAmmo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				removeSelectedItem(cmbboxAmmoTypes);
			}
		});
		weaponDataPanel.add(btnRemoveAmmo, "7, 11");

		descriptionPanel =
			new DescriptionPanel(control, getWindowUtils(), "Description", DescriptionPanelType.TEXTFIELD);
		descriptionPanel.setPreferredSize(new Dimension(0, 50));
		getContentPane().add(descriptionPanel, "2, 4, fill, fill");

		buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, "2, 6, fill, fill");
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				WeaponPrototype weaponAtHand = null;
				try {
					if (isNewItemFrame()) {
						validateAllFields();
						weaponAtHand = new Weapon(txtfldName.getText().trim(), txtfldMaxHP.getTextAsInteger());
					} else {
						weaponAtHand = content.getContent();
					}
					setWeaponFieldsFromGui(weaponAtHand);
					control.createOrUpdateWeapon(weaponAtHand);
				} catch (final GuiItemCreationEx e) {
					getWindowUtils().showErrorMessage(e.getMessage(), "Wrong or Missing Value");
				} catch (final NameAlreadyExistsEx e) {
					getWindowUtils().showErrorMessage("There already exists a weapon named " + weaponAtHand.getName(),
							"Wrong Value");
				}
			}

		});
		buttonPanel.add(btnSave);
	}

	/**
	 * is this frame for a new weapon?
	 */
	private boolean isNewItemFrame() {
		return content.isEmpty();
	}

	/**
	 * used to remove the selected item ina jcombobox
	 */
	private void removeSelectedItem(final JComboBox<?> fromWhereToRemove) {
		if (fromWhereToRemove.getSelectedIndex() != -1) {
			fromWhereToRemove.removeItem(fromWhereToRemove.getSelectedItem());
		}
	}

	private void setNoThrownDamage() {
		thrownDamageBuffer = null;
		lblThrownDamage.setText("No Thrown Damage Set");
	}

	/**
	 * changes given weapon based on what is set in the gui
	 */
	private void setWeaponFieldsFromGui(final WeaponPrototype weapon) {
		weapon.setName(txtfldName.getText().trim());
		weapon.setDescription(descriptionPanel.getText());
		weapon.getDurability().setMaximum(txtfldMaxHP.getTextAsInteger());
		for (int i = 0; i < cmbboxMeleeDamages.getItemCount(); i++) {
			weapon.addMeleeDamage(cmbboxExtraDamages.getItemAt(i));
		}
		if (StringUtils.isNotBlank(txtfldRange.getText()) && thrownDamageBuffer != null) {
			weapon.setRangeAndOptimalThrownDamage(txtfldRange.getText(), thrownDamageBuffer);
		}
		weapon.setBlockModifier(txtfldBlockModifier.getTextAsInteger());
		for (int i = 0; i < cmbboxAmmoTypes.getItemCount(); i++) {
			final AmmunitionTypeWithRange ammoTypeWithRange = cmbboxAmmoTypes.getItemAt(i);
			weapon.setAmmoType(ammoTypeWithRange.getAmmoType(), ammoTypeWithRange.getRange());
		}
	}

	@Override
	public void setWeaponForEditing(final WeaponPrototype weapon) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				txtfldName.setName(weapon.getName());
				descriptionPanel.setNameable(weapon);
				txtfldMaxHP.setText(String.valueOf(weapon.getDurability().getMaximum()));
				for (final DamageRoll meleeRoll : weapon.getMeleeDamageRolls()) {
					cmbboxMeleeDamages.addItem(meleeRoll);
				}
				if (weapon.isThrownWeapon()) {
					txtfldRange.setText(weapon.getRange().getContent().toString());
					lblThrownDamage.setText(weapon.getThrownDamage().getContent().toString());
				}
				txtfldBlockModifier.setText(String.valueOf(weapon.getBlockModifier()));
				for (final AmmunitionType ammoType : weapon.getAmmunitionsWithRange().keySet()) {
					final AmmunitionTypeWithRange ammoTypeWithRange =
						new AmmunitionTypeWithRange(ammoType, weapon.getAmmunitionsWithRange().get(ammoType));
					cmbboxAmmoTypes.addItem(ammoTypeWithRange);
				}
			}
		});
	}

	/**
	 * used in manipulating the jcombobox dialogs
	 */
	private void showDialogAndAddDamageRoll(final String message, final JComboBox<DamageRoll> whereToAdd) {
		final Box<DamageRoll> newDamage = getWindowUtils().showDamageRollDialog(message, control);
		if (newDamage.hasContent()) {
			whereToAdd.addItem(newDamage.getContent());
		}
	}

	/**
	 * Called before creating a new weapon to make sure all is OK
	 */
	private void validateAllFields() {
		if (StringUtils.isBlank(txtfldName.getText())) {
			throw new GuiItemCreationEx("A name is needed.");
		}
		if (StringUtils.isBlank(txtfldMaxHP.getText()) || txtfldMaxHP.getTextAsInteger() < 1) {
			throw new GuiItemCreationEx("You need to enter a maximum weapon HP value of at least 1.");
		}
		if (cmbboxMeleeDamages.getItemCount() == 0 && cmbboxAmmoTypes.getItemCount() == 0) {
			throw new GuiItemCreationEx("Every weapon needs at least one melee damage or one ammunition defined.");
		}
		if (StringUtils.isNotBlank(txtfldRange.getText())) {
			try {
				new Range(txtfldRange.getText());
			} catch (final Exception e) {
				throw new GuiItemCreationEx("The range you entered does not make sense.");
			}
		}
	}
}
