package com.apprentice.rpg.gui.character.player.creation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.IGlobalWindowState;
import com.apprentice.rpg.gui.NumericTextfield;
import com.apprentice.rpg.gui.PlayerLevelsTextfield;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.Stat;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.google.common.collect.Lists;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Window that is used to create new PCs
 * 
 * @author theoklitos
 * 
 */
public final class NewPlayerCharacterFrame extends ApprenticeInternalFrame {

	private static final long serialVersionUID = 1L;

	private final INewPlayerCharacterFrameControl control;
	private JTextField txtfldName;
	private JTextField txtfldHitpoints;
	private JTextField txtfldExperience;
	private JTextField txtfldFortitude;
	private JTextField txtfldReflex;
	private JTextField txtfldWill;
	private final JPanel genericCharacterInformation;

	private JComboBox<Stat> cmbStrength;
	private JComboBox<Stat> cmbDexterity;
	private JComboBox<Stat> cmbConstitution;
	private JComboBox<Stat> cmbIntelligence;
	private JComboBox<Stat> cmbWisdom;

	private JComboBox<Stat> cmbCharisma;
	private PlayerLevelsTextfield txtfldLevels;
	private JTextField txtfldRace;

	private final ApprenticeParser parser;

	private JComboBox<Size> cmbSize;
	private JTextField textField;

	public NewPlayerCharacterFrame(final IGlobalWindowState globalWindowState,
			final INewPlayerCharacterFrameControl control, final ApprenticeParser parser) {
		super(globalWindowState);
		this.parser = parser;
		this.control = control;
		setTitle("New Player Character Creation");

		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("min:grow"),
					FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("fill:min"), RowSpec.decode("pref:grow"), FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("35px"), }));

		genericCharacterInformation = new JPanel();
		genericCharacterInformation.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)),
				"General Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		genericCharacterInformation.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("right:max(45dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
			FormFactory.DEFAULT_COLSPEC, ColumnSpec.decode("pref:grow"), ColumnSpec.decode("8dlu"),
			ColumnSpec.decode("right:pref"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
			FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("pref:grow"), FormFactory.RELATED_GAP_COLSPEC, },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, RowSpec.decode("8dlu"), FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC, }));

		getContentPane().add(genericCharacterInformation, "2, 2, fill, fill");

		populateGenericInformationPanel(genericCharacterInformation);

		final JPanel equipmentPanel = new JPanel();
		equipmentPanel.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)),
				"Equipment", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Equipment", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(equipmentPanel, "2, 3, fill, fill");
		equipmentPanel.setLayout(new FormLayout(new ColumnSpec[] {}, new RowSpec[] {}));

		final JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, "2, 5, fill, top");

		final JButton btnCreate = new JButton("Create");
		southPanel.add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent action) {
				try {
					verifyAllAttributes();
					final PlayerCharacter newPlayer = assemblePlayerCharater();
					// one last confirmation
					if (WindowUtils.getConfigrmationDialog("Your player will be named \"" + newPlayer.getName()
						+ "\" forever,  for names cannot change. Are you sure you want to proceed?",
							"Confirm New Player Creation")) {
						try {
							control.createCharacter(newPlayer);
						} catch (final PlayerNameConflictEx e) {
							WindowUtils.showErrorMessage("A player with the name \"" + newPlayer.getName()
								+ "\" already exsits! Player not created.");

						}
					}
				} catch (final NewCharacterCreationEx e) {
					WindowUtils.showErrorMessage(e.getMessage(), "Wrong or Missing Value");
				}
			}
		});

		setVisible(true);
	}

	/**
	 * Collects the information from the gui in order to create the PC
	 */
	private PlayerCharacter assemblePlayerCharater() {
		final String name = txtfldName.getName();
		final Integer hitPoints = Integer.valueOf(txtfldHitpoints.getText());
		final CharacterType type = new CharacterType(null, (Size) cmbSize.getSelectedItem(), txtfldRace.getText());
		final PlayerCharacter newGuy = new PlayerCharacter(name, hitPoints, getStatBundle(), type);
		final Integer experiencePoints = Integer.valueOf(txtfldExperience.getText());
		newGuy.addExperience(experiencePoints);

		return newGuy;
	}

	private JComboBox<Stat> createComboBoxForType(final StatType type) {
		final List<Stat> stats = Lists.newArrayList();
		for (int i = 1; i < 26; i++) {
			stats.add(new Stat(type, i));
		}
		final JComboBox<Stat> result =
			new JComboBox<Stat>(new DefaultComboBoxModel<Stat>(stats.toArray(new Stat[stats.size()])));
		result.setSelectedIndex(9);
		return result;
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(500, 400);
	}

	private StatBundle getStatBundle() {
		return new StatBundle(cmbStrength.getItemAt(cmbStrength.getSelectedIndex()),
				cmbDexterity.getItemAt(cmbDexterity.getSelectedIndex()), cmbConstitution.getItemAt(cmbConstitution
						.getSelectedIndex()), cmbIntelligence.getItemAt(cmbIntelligence.getSelectedIndex()),
				cmbWisdom.getItemAt(cmbWisdom.getSelectedIndex()),
				cmbCharisma.getItemAt(cmbCharisma.getSelectedIndex()));
	}

	private void populateGenericInformationPanel(final JPanel genericCharacterInformation) {
		final JLabel lblName = new JLabel("Name:");
		genericCharacterInformation.add(lblName, "2, 2, right, default");

		txtfldName = new JTextField();
		genericCharacterInformation.add(txtfldName, "5, 2, 7, 1, fill, default");
		txtfldName.setColumns(10);

		final JLabel lblLevels = new JLabel("Level(s):");
		genericCharacterInformation.add(lblLevels, "2, 4, right, default");

		txtfldLevels = new PlayerLevelsTextfield(parser);
		genericCharacterInformation.add(txtfldLevels, "5, 4, fill, default");
		txtfldLevels.setColumns(10);

		final JLabel lblRace = new JLabel("Race:");
		genericCharacterInformation.add(lblRace, "7, 4, right, default");

		txtfldRace = new JTextField();
		genericCharacterInformation.add(txtfldRace, "11, 4, fill, default");
		txtfldRace.setColumns(10);

		final JLabel lblHitPoints = new JLabel("Hit Points:");
		genericCharacterInformation.add(lblHitPoints, "2, 6, right, default");

		txtfldHitpoints = new NumericTextfield();
		genericCharacterInformation.add(txtfldHitpoints, "5, 6, left, default");
		txtfldHitpoints.setColumns(10);

		final JLabel lblExperience = new JLabel("Experience:");
		genericCharacterInformation.add(lblExperience, "7, 6, right, default");

		txtfldExperience = new NumericTextfield();
		genericCharacterInformation.add(txtfldExperience, "11, 6, left, default");
		txtfldExperience.setColumns(10);

		final JLabel lblStrength = new JLabel("Strength:");
		genericCharacterInformation.add(lblStrength, "2, 8, right, default");

		final JPanel movementPanel = new JPanel();
		genericCharacterInformation.add(movementPanel, "11, 10, fill, fill");

		textField = new JTextField();
		movementPanel.add(textField);
		textField.setColumns(10);

		final JLabel lblNewLabel = new JLabel("ft.");
		movementPanel.add(lblNewLabel);

		cmbStrength = createComboBoxForType(StatType.STRENGTH);
		genericCharacterInformation.add(cmbStrength, "5, 8");

		final JLabel lblType = new JLabel("Type:");
		genericCharacterInformation.add(lblType, "7, 8, right, default");

		final JComboBox cmbType = new JComboBox();
		genericCharacterInformation.add(cmbType, "11, 8, fill, default");

		final JLabel lblDexterity = new JLabel("Dexterity:");
		genericCharacterInformation.add(lblDexterity, "2, 10, right, default");

		cmbDexterity = createComboBoxForType(StatType.DEXTERITY);
		genericCharacterInformation.add(cmbDexterity, "5, 10");

		final JLabel lblMovement = new JLabel("Movement:");
		genericCharacterInformation.add(lblMovement, "7, 10, right, default");

		final JLabel lblConstitution = new JLabel("Constitution:");
		genericCharacterInformation.add(lblConstitution, "2, 12, right, default");

		cmbConstitution = createComboBoxForType(StatType.CONSTITUTION);
		genericCharacterInformation.add(cmbConstitution, "5, 12");

		final JLabel lblSize = new JLabel("Size:");
		genericCharacterInformation.add(lblSize, "7, 12, right, default");

		cmbSize = new JComboBox<Size>(new DefaultComboBoxModel<Size>(Size.values()));
		genericCharacterInformation.add(cmbSize, "11, 12, fill, default");
		cmbSize.setSelectedIndex(3);

		final JLabel lblIntelligence = new JLabel("Intelligence:");
		genericCharacterInformation.add(lblIntelligence, "2, 14, right, default");

		cmbIntelligence = createComboBoxForType(StatType.INTELLIGENCE);
		genericCharacterInformation.add(cmbIntelligence, "5, 14, fill, default");

		final JLabel lblFort = new JLabel("Fortitude:");
		genericCharacterInformation.add(lblFort, "7, 14, right, default");

		txtfldFortitude = new JTextField();
		genericCharacterInformation.add(txtfldFortitude, "11, 14, left, default");
		txtfldFortitude.setColumns(10);

		final JLabel lblWisdom = new JLabel("Wisdom:");
		genericCharacterInformation.add(lblWisdom, "2, 16, right, default");

		cmbWisdom = createComboBoxForType(StatType.WISDOM);
		genericCharacterInformation.add(cmbWisdom, "5, 16, fill, default");

		final JLabel lblRef = new JLabel("Reflex:");
		genericCharacterInformation.add(lblRef, "7, 16, right, default");

		txtfldReflex = new JTextField();
		genericCharacterInformation.add(txtfldReflex, "11, 16, left, default");
		txtfldReflex.setColumns(10);

		final JLabel lblCharisma = new JLabel("Charisma:");
		genericCharacterInformation.add(lblCharisma, "2, 18, right, default");

		cmbCharisma = createComboBoxForType(StatType.CHARISMA);
		genericCharacterInformation.add(cmbCharisma, "5, 18, fill, default");

		final JLabel lblWill = new JLabel("Will:");
		genericCharacterInformation.add(lblWill, "7, 18, right, default");

		txtfldWill = new JTextField();
		genericCharacterInformation.add(txtfldWill, "11, 18, left, default");
		txtfldWill.setColumns(10);
	}

	/**
	 * Verifies all inputs and shows appropriate error messages
	 */
	protected void verifyAllAttributes() {
		if (StringUtils.isBlank(txtfldName.getText())) {
			throw new NewCharacterCreationEx("A name is needed.");
		}
		try {
			if (StringUtils.isNotBlank(txtfldExperience.getText())) {
				Integer.valueOf(txtfldExperience.getText());
			}
		} catch (final NumberFormatException e) {
			throw new NewCharacterCreationEx("Experience value \"" + txtfldExperience.getText() + "\" is not a number.");
		}
		try {
			if (StringUtils.isNotBlank(txtfldHitpoints.getText())) {
				Integer.valueOf(txtfldHitpoints.getText());
			}
			if (StringUtils.isBlank(txtfldHitpoints.getText()) || Integer.valueOf(txtfldHitpoints.getText()) < 1) {
				throw new NewCharacterCreationEx("You need at least one hit point.");
			}
		} catch (final NumberFormatException e) {
			throw new NewCharacterCreationEx("Hit point value \"" + txtfldHitpoints.getText() + "\" is not a number.");
		}
		if (StringUtils.isBlank(txtfldLevels.getText())) {
			throw new NewCharacterCreationEx(
					"You are not a commoner, are you? Enter some levels, ex. Fighter10/Wizard10");
		}

	}
}
