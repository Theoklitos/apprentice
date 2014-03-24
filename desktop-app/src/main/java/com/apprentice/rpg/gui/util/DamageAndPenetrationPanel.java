package com.apprentice.rpg.gui.util;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.apprentice.rpg.gui.EnumCellRenderer;
import com.apprentice.rpg.gui.NumericTextfield;
import com.apprentice.rpg.gui.TextfieldWithColorWarning;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.damage.Penetration;
import com.apprentice.rpg.model.damage.Penetration.PenetrationType;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.strike.StrikeType;
import com.apprentice.rpg.util.ApprenticeStringUtils;

/**
 * Panel with damage dice textfield and a penetration selection panel
 * 
 * @author theoklitos
 * 
 */
public class DamageAndPenetrationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final TextfieldWithColorWarning txtfldRoll;
	private final JComboBox<StrikeType> cmbboxStrikes;
	private final JComboBox<String> cmbboxPenetrationType;
	private final JCheckBox chkboxType;

	private final NumericTextfield txtfldPenetrationHP;

	public DamageAndPenetrationPanel(final Collection<StrikeType> strikes) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		cmbboxStrikes = new JComboBox<>(new Vector<>(strikes));
		cmbboxStrikes.setRenderer(new EnumCellRenderer());

		final JPanel damageRollPanel = new JPanel(new FlowLayout());
		txtfldRoll = new TextfieldWithColorWarning(Roll.class);
		final JLabel lblDamageDice = new JLabel("Damage Dice:");
		damageRollPanel.add(lblDamageDice);
		getRollTextfield().setColumns(7);
		damageRollPanel.add(getRollTextfield());

		final JPanel penetrationParentPanel = new JPanel();
		penetrationParentPanel.setLayout(new BoxLayout(penetrationParentPanel, BoxLayout.Y_AXIS));
		final JPanel penetrationHitPoints = new JPanel(new FlowLayout());
		final JLabel lblPenetration = new JLabel("Penetration Hit Points:");
		penetrationHitPoints.add(lblPenetration);
		txtfldPenetrationHP = new NumericTextfield(true);
		txtfldPenetrationHP.setColumns(3);
		penetrationHitPoints.add(txtfldPenetrationHP);
		penetrationParentPanel.add(penetrationHitPoints);
		final JPanel penetrationType = new JPanel(new FlowLayout());

		cmbboxPenetrationType =
			new JComboBox<String>(new Vector<String>(ApprenticeStringUtils.getReadableEnumList(PenetrationType.class)));
		cmbboxPenetrationType.setEnabled(false);
		
		chkboxType = new JCheckBox("Special Penetration Type:");
		chkboxType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent event) {
				cmbboxPenetrationType.setEnabled(chkboxType.isSelected());
				txtfldPenetrationHP.setEnabled(!chkboxType.isSelected());
			}
		});
		penetrationType.add(chkboxType);
		penetrationType.add(cmbboxPenetrationType);
		penetrationParentPanel.add(penetrationType);
		
		add(cmbboxStrikes);
		add(damageRollPanel);
		add(new JSeparator(JSeparator.HORIZONTAL));
		add(penetrationParentPanel);
	}

	/**
	 * @throws ParsingEx
	 */
	public DamageRoll getDamageRoll() throws ParsingEx {
		Penetration penetration;
		if (chkboxType.isSelected()) {
			penetration =
				new Penetration(PenetrationType.valueOf(cmbboxPenetrationType.getSelectedItem().toString()
						.toUpperCase()));
		} else {
			penetration = new Penetration(txtfldPenetrationHP.getTextAsInteger());
		}
		return new DamageRoll(getRollTextfield().getText(), penetration, (StrikeType) getStrikesCombobox()
				.getSelectedItem());
	}

	public TextfieldWithColorWarning getRollTextfield() {
		return txtfldRoll;
	}

	public JComboBox<StrikeType> getStrikesCombobox() {
		return cmbboxStrikes;
	}
}
