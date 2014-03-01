package com.apprentice.rpg.gui.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.StartupApprentice;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.ControlWithVault;
import com.apprentice.rpg.gui.NumericTextfield;
import com.apprentice.rpg.gui.TextfieldWithColorWarning;
import com.apprentice.rpg.gui.dice.LoadedRoll;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.weapon.AmmunitionTypeWithRange;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.model.weapon.Range;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.exportImport.ExportConfigurationObject;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.strike.StrikeType;
import com.apprentice.rpg.util.Box;
import com.google.common.collect.Lists;

/**
 * helper functions for the swing gui, ie option panes, icons, etc
 * 
 */
public final class WindowUtils implements IWindowUtils {

	public final static String DEFAULT_ICON_FILENAME = "wizard.png";
	public final static String DEFAULT_DESKTOP_FILENAME = "desktop.jpg";
	public final static String INFO_ICON_FILENAME = "information.png";
	public final static String DICE_ROLL_FILENAME = "rollingDice.gif";
	public final static String IMAGES_FOLDER = "/images";

	@Override
	public void centerComponent(final Component component, final int verticalOffset) {
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final Rectangle screenSize = ge.getScreenDevices()[0].getDefaultConfiguration().getBounds();
		final int w = component.getSize().width;
		final int h = component.getSize().height;
		final int x = (screenSize.width - w) / 2;
		final int y = (screenSize.height - h) / 2;
		component.setLocation(x, y - verticalOffset);
	}

	@Override
	public void centerInternalComponent(final Component parent, final Component component, final int verticalOffset) {
		final Dimension parentSize = parent.getSize();
		final int w = component.getSize().width;
		final int h = component.getSize().height;
		final int x = (parentSize.width - w) / 2;
		final int y = (parentSize.height - h) / 2;
		component.setLocation(x, y - verticalOffset);
	}

	@Override
	public Image getDefaultDesktopBackgroundImage() throws ApprenticeEx {
		Image image;
		try {
			image =
				ImageIO.read(new StartupApprentice().getClass().getResource(
						WindowUtils.IMAGES_FOLDER + File.separator + WindowUtils.DEFAULT_DESKTOP_FILENAME));
		} catch (final MalformedURLException e) {
			throw new ApprenticeEx(e);
		} catch (final IOException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(e);
		}

		return image;
	}

	@Override
	public Image getImageFromPath(final String path) {
		Image image;
		try {
			image = ImageIO.read(new File(path));
		} catch (final MalformedURLException e) {
			throw new ApprenticeEx(e);
		} catch (final IOException e) {
			throw new ApprenticeEx(e);
		} catch (final IllegalArgumentException e) {
			throw new ApprenticeEx(e);
		}

		if (image == null) {
			throw new ApprenticeEx("Selected file was probably not an image.");
		}

		return image;
	}

	@Override
	public void setDefaultIcon(final JFrame frame) {
		final String locationString = IMAGES_FOLDER + File.separator + DEFAULT_ICON_FILENAME;
		final URL imageURL = frame.getClass().getResource(locationString);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(imageURL));
	}

	@Override
	public void setDiceRollingImage(final JLabel jlabel) {
		final String locationString = "images" + File.separator + DICE_ROLL_FILENAME;		
		ImageIcon icon;
		try {	
			icon = new ImageIcon(ClassLoader.getSystemResource(locationString));
			jlabel.setText("");
			jlabel.setIcon(icon);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setInformationIcon(final JInternalFrame frame) {
		final String locationString = IMAGES_FOLDER + File.separator + INFO_ICON_FILENAME;
		final URL imageURL = frame.getClass().getResource(locationString);
		frame.setFrameIcon(new ImageIcon(imageURL));
	}

	@Override
	public Box<AmmunitionTypeWithRange> showAmmunitionTypeAndRangeDialog(final ControlWithVault control) {
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		final Collection<AmmunitionType> ammoTypes = control.getVault().getAllNameables(AmmunitionType.class);
		if (ammoTypes.size() == 0) {
			showErrorMessage("No ammuniton types have been created, therefore this weapon cannot utilize any.");
			return Box.empty();
		}
		final JComboBox<AmmunitionType> cmbboxAmmoTypes = new JComboBox<>(new Vector<>(ammoTypes));
		final JPanel rangePanel = new JPanel(new FlowLayout());
		final TextfieldWithColorWarning txtfldRange = new TextfieldWithColorWarning(Range.class);
		final JLabel lblRange = new JLabel("Range:");
		rangePanel.add(lblRange);
		txtfldRange.setColumns(15);
		rangePanel.add(txtfldRange);

		mainPanel.add(cmbboxAmmoTypes);
		mainPanel.add(rangePanel);

		final int option =
			JOptionPane.showConfirmDialog(null, mainPanel, "Set Ammo and Range", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.CANCEL_OPTION) {
			return Box.empty();
		} else {
			try {
				final Range range = new Range(txtfldRange.getText());
				final AmmunitionType selectedAmmoType = cmbboxAmmoTypes.getItemAt(cmbboxAmmoTypes.getSelectedIndex());
				return Box.with(new AmmunitionTypeWithRange(selectedAmmoType, range));
			} catch (final ApprenticeEx e) {
				showErrorMessage("Range text not understood.");
				return Box.empty();
			}
		}
	}

	@Override
	public Box<AmmunitionType> showAmmunitionTypeDialog(final IVaultFrameControl control,
			final Box<AmmunitionType> existingAmmunitionTypeBox) {
		// final Collection<StrikeType> strikes = control.getVault().getAll(StrikeType.class); TODO
		// if (strikes.size() == 0) {
		// showErrorMessage("No strike types have been created, therefore no damage type can be chosen.");
		// return Box.empty();
		// }
		final Collection<StrikeType> strikes =
			Lists.newArrayList(new StrikeType("Slashing"), new StrikeType("Piercing"), new StrikeType("Bludgeoning"));

		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		final JPanel namePanel = new JPanel(new FlowLayout());
		final JTextField txtfldName = new JTextField();
		final JLabel lblName = new JLabel("Name:");
		namePanel.add(lblName);
		txtfldName.setColumns(15);
		namePanel.add(txtfldName);

		final JComboBox<StrikeType> cmbboxStrikes = new JComboBox<>(new Vector<>(strikes));

		final JPanel dicePanel = new JPanel(new FlowLayout());
		final TextfieldWithColorWarning txtfldRoll = new TextfieldWithColorWarning(Roll.class);
		final JLabel lblDamageDice = new JLabel("Damage Dice:");
		dicePanel.add(lblDamageDice);
		txtfldRoll.setColumns(15);
		dicePanel.add(txtfldRoll);

		mainPanel.add(namePanel);
		mainPanel.add(cmbboxStrikes);
		mainPanel.add(dicePanel);

		String title = "New Ammunition Type";
		if (existingAmmunitionTypeBox.hasContent()) {
			final AmmunitionType exinstingAmmoType = existingAmmunitionTypeBox.getContent();
			title = "Edit Ammunition Type";
			txtfldName.setText(exinstingAmmoType.getName());
			txtfldRoll.setText(exinstingAmmoType.getDamage().getRoll().toString());
			cmbboxStrikes.setSelectedItem(exinstingAmmoType.getDamage().getType());
		}

		final int option =
			JOptionPane.showConfirmDialog(null, mainPanel, title, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.CANCEL_OPTION) {
			return Box.empty();
		} else {
			final StrikeType strikeType = cmbboxStrikes.getItemAt(cmbboxStrikes.getSelectedIndex());
			try {
				final String name = txtfldName.getText().trim();
				if (StringUtils.isBlank(name)) {
					showErrorMessage("You need to enter a name.");
					return Box.empty();
				}
				final DamageRoll damageRoll = new DamageRoll(new Roll(txtfldRoll.getText().trim()), strikeType);
				if (existingAmmunitionTypeBox.hasContent()) {
					final AmmunitionType existingAmmoType = existingAmmunitionTypeBox.getContent();
					existingAmmoType.setName(name);
					existingAmmunitionTypeBox.getContent().setDamage(damageRoll);
					return Box.with(existingAmmunitionTypeBox.getContent());
				} else {
					return Box.with(new AmmunitionType(name, damageRoll));
				}
			} catch (final ApprenticeEx e) {
				showErrorMessage("Dice text not understood.");
				return Box.empty();
			}
		}
	}

	@Override
	public Box<String> showBigTextFieldDialog(final String title, final String preExistingContent) {
		final JScrollPane scrollPane = new JScrollPane();
		final JTextArea textArea = new JTextArea(10, 30);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setText(preExistingContent);
		scrollPane.getViewport().setView(textArea);
		final JPanel dialogPanel = new JPanel();
		dialogPanel.add(scrollPane);
		final int returnValue =
			JOptionPane.showConfirmDialog(null, scrollPane, title, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		if (returnValue == JOptionPane.CANCEL_OPTION) {
			return Box.empty();
		} else {
			return Box.with(textArea.getText());
		}
	}

	@Override
	public boolean showConfigrmationDialog(final String question, final String title) {
		return JOptionPane.showConfirmDialog(null, question, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	@Override
	public Box<DamageRoll> showDamageRollDialog(final String title, final ControlWithVault control) {
// final Collection<StrikeType> strikes = control.getVault().getAll(StrikeType.class); TODO
// if (strikes.size() == 0) {
// showErrorMessage("No strike types have been created, therefore no damage type can be chosen.");
// return Box.empty();
// }
		final Collection<StrikeType> strikes =
			Lists.newArrayList(new StrikeType("Slashing"), new StrikeType("Piercing"), new StrikeType("Bludgeoning"));

		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		final JComboBox<StrikeType> cmbboxStrikes = new JComboBox<>(new Vector<>(strikes));
		final TextfieldWithColorWarning txtfldRoll = new TextfieldWithColorWarning(Roll.class);
		final JLabel lblDamageDice = new JLabel("Damage Dice:");

		mainPanel.add(cmbboxStrikes);
		final JPanel dicePanel = new JPanel(new FlowLayout());
		dicePanel.add(lblDamageDice);
		txtfldRoll.setColumns(15);
		dicePanel.add(txtfldRoll);
		mainPanel.add(dicePanel);

		final int option =
			JOptionPane.showConfirmDialog(null, mainPanel, title, JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.CANCEL_OPTION) {
			return Box.empty();
		} else {
			final StrikeType strikeType = cmbboxStrikes.getItemAt(cmbboxStrikes.getSelectedIndex());
			try {
				final Roll roll = new Roll(txtfldRoll.getText());
				return Box.with(new DamageRoll(roll, strikeType));
			} catch (final ApprenticeEx e) {
				showErrorMessage("Dice text not understood.");
				return Box.empty();
			}
		}
	}

	@Override
	public void showErrorMessage(final String errorMessage) {
		showErrorMessage(errorMessage, "Error!");
	}

	@Override
	public void showErrorMessage(final String errorMessage, final String title) {
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public Box<String> showFileChooser(final String title, final String buttonText) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		fileChooser.setApproveButtonText(buttonText);
		final int result = fileChooser.showDialog(null, null);
		if (result == JFileChooser.APPROVE_OPTION) {
			return Box.with(fileChooser.getSelectedFile().getAbsolutePath());
		} else {
			return Box.empty();
		}
	}

	@Override
	public void showInformationMessage(final String infoMessage, final String title) {
		JOptionPane.showMessageDialog(null, infoMessage, title, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public Box<String> showInputDialog(final String message, final String title) {
		final String result = JOptionPane.showInputDialog(null, message, title, JOptionPane.NO_OPTION);
		if (StringUtils.isBlank(result)) {
			return Box.empty();
		} else {
			return Box.with(result);
		}
	}

	@Override
	public Box<LoadedRoll> showLoadedRollDialog() {
		final JPanel mainPanel = new JPanel(new FlowLayout());
		final TextfieldWithColorWarning txtfldRoll = new TextfieldWithColorWarning(Roll.class);
		txtfldRoll.setColumns(10);
		final JLabel lblDice = new JLabel("Dice:");
		mainPanel.add(lblDice);
		mainPanel.add(txtfldRoll);

		final JLabel lblResult = new JLabel("Result:");
		final NumericTextfield txtfldResult = new NumericTextfield(true);
		txtfldResult.setColumns(2);
		mainPanel.add(lblResult);
		mainPanel.add(txtfldResult);

		final int option =
			JOptionPane.showConfirmDialog(null, mainPanel, "Loaded Roll", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.CANCEL_OPTION) {
			return Box.empty();
		} else {
			if (StringUtils.isBlank(txtfldResult.getText())) {
				return Box.empty();
			} else {
				final LoadedRoll result = new LoadedRoll(txtfldResult.getTextAsInteger());
				try {
					if (!StringUtils.isBlank(txtfldRoll.getText())) {
						final Roll roll = new Roll(txtfldRoll.getText());
						result.setRoll(roll);
					}
					return Box.with(result);
				} catch (final ApprenticeEx e) {
					showErrorMessage("Dice text not understood.");
					return Box.empty();
				}
			}
		}
	}

	@Override
	public int showQuestionMessage(final String question, final String[] options, final String title) {
		return JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, null);
	}

	@Override
	public boolean showTypeAndBodyPartNameSelection(final ExportConfigurationObject config, final Vault vault) {
		final JPanel mainPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		final ItemForExportChooserTable typeTable = new ItemForExportChooserTable(ItemType.TYPE, config, vault);
		final JPanel typePanel = new JPanel();
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
		typePanel.add(new JLabel("Types"));
		typePanel.add(typeTable);
		final JScrollPane typeScrollPane = new JScrollPane();
		typeScrollPane.setViewportView(typePanel);

		final ItemForExportChooserTable bodyPartTable =
			new ItemForExportChooserTable(ItemType.BODY_PART, config, vault);
		final JPanel bodyPartPanel = new JPanel();
		bodyPartPanel.setLayout(new BoxLayout(bodyPartPanel, BoxLayout.Y_AXIS));
		bodyPartPanel.add(new JLabel("Body Parts"));
		bodyPartPanel.add(bodyPartTable);
		final JScrollPane bodyPartScrollPane = new JScrollPane();
		bodyPartScrollPane.setViewportView(bodyPartPanel);

		final JLabel infoLabel = new JLabel("Note: Every type will be exported along with its required body parts.");

		final JPanel topPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		topPanel.add(typeScrollPane);
		topPanel.add(bodyPartScrollPane);

		mainPanel.add(topPanel);
		mainPanel.add(infoLabel);

		int option =
			JOptionPane.showConfirmDialog(null, mainPanel, "Choose Items to Export", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
		if (config.size() == 0) {
			option = JOptionPane.CANCEL_OPTION;
		}
		return option == JOptionPane.YES_OPTION;
	}

	@Override
	public void showWarningMessage(final String warningMessage, final String title) {
		JOptionPane.showMessageDialog(null, warningMessage, title, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public boolean showWarningQuestionMessage(final String question, final String title) {
		return JOptionPane.showOptionDialog(null, question, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null, new String[] { "Yes", "No" }, null) == JOptionPane.YES_OPTION;
	}
}
