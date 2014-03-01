package com.apprentice.rpg.gui.character.player.creation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ApprenticeInternalFrame;
import com.apprentice.rpg.gui.GuiItemCreationEx;
import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.NumericTextfield;
import com.apprentice.rpg.gui.SavingThrowTextField;
import com.apprentice.rpg.gui.TextfieldWithColorWarning;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.windowState.GlobalWindowState;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.model.SavingThrows;
import com.apprentice.rpg.model.Stat;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.parsing.ParsingEx;
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
public final class NewPlayerCharacterFrame extends ApprenticeInternalFrame implements INewPlayerCharacterFrame {

	public static final String PLAYER_DESCRIPTION_PANEL_TITLE = "Description, Notes or Misc. Information";

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(NewPlayerCharacterFrame.class);

	private static final long serialVersionUID = 1L;

	private final INewPlayerCharacterFrameControl control;
	private final IWindowManager windowManager;

	private JTextField txtfldName;
	private JTextField txtfldHitpoints;
	private NumericTextfield txtfldExperience;
	private SavingThrowTextField txtfldFortitude;
	private SavingThrowTextField txtfldReflex;
	private SavingThrowTextField txtfldWill;
	private JPanel genericCharacterInformation;
	private JComboBox<Stat> cmbStrength;
	private JComboBox<Stat> cmbDexterity;
	private JComboBox<Stat> cmbConstitution;
	private JComboBox<Stat> cmbIntelligence;
	private JComboBox<Stat> cmbWisdom;
	private JComboBox<Stat> cmbCharisma;
	private TextfieldWithColorWarning txtfldLevels;
	private JTextField txtfldRace;
	private JComboBox<Size> cmbSize;
	private NumericTextfield txtfldMovement;
	private JTextArea txtareaDescription;
	private JComboBox<IType> cmbType;

	public NewPlayerCharacterFrame(final IGlobalWindowState globalWindowState,
			final INewPlayerCharacterFrameControl control, final IWindowManager windowManager) {
		super(new GlobalWindowState(new WindowUtils()), "New Player Character Creation");
		this.control = control;
		this.windowManager = windowManager;
		initComponents(control);
		refreshTypeDropdown();

		setVisible(true);
	}

	/**
	 * Collects the information from the gui in order to create the PC
	 */
	private PlayerCharacter assemblePlayerCharater() {
		final String name = txtfldName.getText();
		final Integer hitPoints = Integer.valueOf(txtfldHitpoints.getText());
		final CharacterType type =
			new CharacterType(cmbType.getItemAt(cmbType.getSelectedIndex()), cmbSize.getItemAt(cmbSize
					.getSelectedIndex()), txtfldRace.getText());
		final int movementInFeet = txtfldMovement.getTextAsInteger();
		final PlayerCharacter newGuy =
			new PlayerCharacter(name, new PlayerLevels(txtfldLevels.getText()), hitPoints, getStatBundle(), type,
					movementInFeet, getSavingThrows());
		newGuy.addExperience(txtfldExperience.getTextAsInteger());
		newGuy.setDescription(txtareaDescription.getText().trim());
		return newGuy;
	}

	private JComboBox<Stat> createComboBoxForStat(final StatType type) {
		final List<Stat> stats = Lists.newArrayList();
		for (int i = 1; i < 26; i++) {
			stats.add(new Stat(type, i));
		}
		final JComboBox<Stat> result =
			new JComboBox<Stat>(new DefaultComboBoxModel<Stat>(stats.toArray(new Stat[stats.size()])));
		result.setSelectedIndex(9);
		return result;
	}

	/**
	 * called when the new player button is pressed
	 */
	private final void createNewPlayer(final INewPlayerCharacterFrameControl control) {
		try {
			validateAllFields();
			final PlayerCharacter newPlayer = assemblePlayerCharater();
			try {
				control.createCharacter(newPlayer);
				getWindowUtils().showInformationMessage("New player succesfully created,", "New Player");
				this.setClosed(true);
				windowManager.showPlayerVaultFrame();
			} catch (final NameAlreadyExistsEx e) {
				getWindowUtils().showErrorMessage(
						"A player with the name \"" + newPlayer.getName() + "\" already exsits! Player not created.");
			} catch (final ItemAlreadyExistsEx e) {
				getWindowUtils().showErrorMessage("This player character already exists!");
			} catch (final PropertyVetoException e) {
				this.setVisible(false);
				throw new ApprenticeEx(e);
			}

		} catch (final GuiItemCreationEx e) {
			getWindowUtils().showErrorMessage(e.getMessage(), "Wrong or Missing Value");
		}
	}

	@Override
	public Dimension getInitialSize() {
		return new Dimension(520, 490);
	}

	private SavingThrows getSavingThrows() throws ParsingEx {
		try {
			final int fort = Integer.valueOf(txtfldFortitude.getText().trim());
			final int ref = Integer.valueOf(txtfldReflex.getText().trim());
			final int will = Integer.valueOf(txtfldWill.getText().trim());
			final SavingThrows savingThrows = new SavingThrows(fort, ref, will);
			return savingThrows;
		} catch (final IllegalArgumentException e) {
			throw new ParsingEx("One or more saving throw values are not correct, or missing");
		}
	}

	private StatBundle getStatBundle() {
		return new StatBundle(cmbStrength.getItemAt(cmbStrength.getSelectedIndex()),
				cmbDexterity.getItemAt(cmbDexterity.getSelectedIndex()), cmbConstitution.getItemAt(cmbConstitution
						.getSelectedIndex()), cmbIntelligence.getItemAt(cmbIntelligence.getSelectedIndex()),
				cmbWisdom.getItemAt(cmbWisdom.getSelectedIndex()),
				cmbCharisma.getItemAt(cmbCharisma.getSelectedIndex()));
	}

	private void initComponents(final INewPlayerCharacterFrameControl control) {
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("min:grow"),
					FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
					RowSpec.decode("fill:min"), RowSpec.decode("fill:pref:grow"), RowSpec.decode("30px"),
					FormFactory.RELATED_GAP_ROWSPEC, }));

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

		populateGenericInformationPanel();

		final JPanel descriptionPanel = new JPanel();
		descriptionPanel.setBorder(new TitledBorder(null, PLAYER_DESCRIPTION_PANEL_TITLE, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		getContentPane().add(descriptionPanel, "2, 3, fill, fill");
		descriptionPanel.setLayout(new BorderLayout());

		txtareaDescription = new JTextArea();
		final JScrollPane scrollPane = new JScrollPane(txtareaDescription);
		descriptionPanel.add(scrollPane, BorderLayout.CENTER);

		final JPanel southPanel = new JPanel();
		getContentPane().add(southPanel, "2, 4, fill, top");

		final JButton btnCreate = new JButton("Create");
		southPanel.add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent action) {
				createNewPlayer(control);
			}

		});
	}

	private void populateGenericInformationPanel() {
		final JLabel lblName = new JLabel("Name:");
		genericCharacterInformation.add(lblName, "2, 2, right, default");

		txtfldName = new JTextField();
		genericCharacterInformation.add(txtfldName, "5, 2, 7, 1, fill, default");
		txtfldName.setColumns(10);

		final JLabel lblLevels = new JLabel("Level(s):");
		genericCharacterInformation.add(lblLevels, "2, 4, right, default");

		txtfldLevels = new TextfieldWithColorWarning(PlayerLevels.class);
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

		final JPanel movementPanel = new JPanel(new BorderLayout());
		genericCharacterInformation.add(movementPanel, "11, 10, left, fill");
		txtfldMovement = new NumericTextfield();
		movementPanel.add(txtfldMovement);
		txtfldMovement.setColumns(10);
		final JLabel lblNewLabel = new JLabel("ft.");
		movementPanel.add(lblNewLabel, BorderLayout.EAST);

		cmbStrength = createComboBoxForStat(StatType.STRENGTH);
		genericCharacterInformation.add(cmbStrength, "5, 8");

		final JLabel lblType = new JLabel("Type:");
		genericCharacterInformation.add(lblType, "7, 8, right, default");

		cmbType = new JComboBox<IType>();
		cmbType.setRenderer(new NameableListCellRenderer());
		genericCharacterInformation.add(cmbType, "11, 8, fill, default");

		final JLabel lblDexterity = new JLabel("Dexterity:");
		genericCharacterInformation.add(lblDexterity, "2, 10, right, default");

		cmbDexterity = createComboBoxForStat(StatType.DEXTERITY);
		genericCharacterInformation.add(cmbDexterity, "5, 10");

		final JLabel lblMovement = new JLabel("Movement:");
		genericCharacterInformation.add(lblMovement, "7, 10, right, default");

		final JLabel lblConstitution = new JLabel("Constitution:");
		genericCharacterInformation.add(lblConstitution, "2, 12, right, default");

		cmbConstitution = createComboBoxForStat(StatType.CONSTITUTION);
		genericCharacterInformation.add(cmbConstitution, "5, 12");

		final JLabel lblSize = new JLabel("Size:");
		genericCharacterInformation.add(lblSize, "7, 12, right, default");

		cmbSize = new JComboBox<Size>(new DefaultComboBoxModel<Size>(Size.values()));
		genericCharacterInformation.add(cmbSize, "11, 12, fill, default");
		cmbSize.setSelectedIndex(3);

		final JLabel lblIntelligence = new JLabel("Intelligence:");
		genericCharacterInformation.add(lblIntelligence, "2, 14, right, default");

		cmbIntelligence = createComboBoxForStat(StatType.INTELLIGENCE);
		genericCharacterInformation.add(cmbIntelligence, "5, 14, fill, default");

		final JLabel lblFort = new JLabel("Fortitude:");
		genericCharacterInformation.add(lblFort, "7, 14, right, default");

		txtfldFortitude = new SavingThrowTextField();
		genericCharacterInformation.add(txtfldFortitude, "11, 14, left, default");
		txtfldFortitude.setColumns(10);

		final JLabel lblWisdom = new JLabel("Wisdom:");
		genericCharacterInformation.add(lblWisdom, "2, 16, right, default");

		cmbWisdom = createComboBoxForStat(StatType.WISDOM);
		genericCharacterInformation.add(cmbWisdom, "5, 16, fill, default");

		final JLabel lblRef = new JLabel("Reflex:");
		genericCharacterInformation.add(lblRef, "7, 16, right, default");

		txtfldReflex = new SavingThrowTextField();
		genericCharacterInformation.add(txtfldReflex, "11, 16, left, default");
		txtfldReflex.setColumns(10);

		final JLabel lblCharisma = new JLabel("Charisma:");
		genericCharacterInformation.add(lblCharisma, "2, 18, right, default");

		cmbCharisma = createComboBoxForStat(StatType.CHARISMA);
		genericCharacterInformation.add(cmbCharisma, "5, 18, fill, default");

		final JLabel lblWill = new JLabel("Will:");
		genericCharacterInformation.add(lblWill, "7, 18, right, default");

		txtfldWill = new SavingThrowTextField();
		genericCharacterInformation.add(txtfldWill, "11, 18, left, default");
		txtfldWill.setColumns(10);
	}

	@Override
	public void refreshTypeDropdown() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				cmbType.removeAll();
				final Collection<IType> types = control.getAllTypes();
				for (final IType type : types) {
					cmbType.addItem(type);
				}
			}
		});
	}

	/**
	 * Verifies all inputs and shows appropriate error messages
	 */
	protected void validateAllFields() {
		if (StringUtils.isBlank(txtfldName.getText())) {
			throw new GuiItemCreationEx("A name is needed.");
		}
		if (txtfldMovement.getTextAsInteger() <= 0) {
			throw new GuiItemCreationEx("Please enter a movement speed (other than 0ft).");
		}
		try {
			if (StringUtils.isNotBlank(txtfldHitpoints.getText())) {
				Integer.valueOf(txtfldHitpoints.getText());
			}
			if (StringUtils.isBlank(txtfldHitpoints.getText()) || Integer.valueOf(txtfldHitpoints.getText()) < 1) {
				throw new GuiItemCreationEx("You need at least one hit point.");
			}
		} catch (final NumberFormatException e) {
			throw new GuiItemCreationEx("Hit point value \"" + txtfldHitpoints.getText() + "\" is not a number.");
		}
		if (StringUtils.isBlank(txtfldLevels.getText())) {
			throw new GuiItemCreationEx("You are not a commoner, are you? Enter some levels, ex. Fighter10/Wizard10");
		}
		try {
			new PlayerLevels(txtfldLevels.getText());
		} catch (final ParsingEx e) {
			throw new GuiItemCreationEx(e.getMessage());
		}
		try {
			getSavingThrows();
		} catch (final ParsingEx e) {
			throw new GuiItemCreationEx("You did not input correct saving throws correctly (or at all).");
		}
	}
}
