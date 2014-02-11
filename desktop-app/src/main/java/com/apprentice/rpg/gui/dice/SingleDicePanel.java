package com.apprentice.rpg.gui.dice;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.apprentice.rpg.gui.NumericTextfield;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.random.dice.RollException;

/**
 * Small dice panel used to select a dice type in the {@link createDiceTextPanel}
 * 
 * @author theoklitos
 * 
 */
public class SingleDicePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final NumericTextfield number;
	private final JTextField modifier;
	private final String roll;
	private final JRadioButton button;

	/**
	 * @throws RollException
	 */
	public SingleDicePanel(final ButtonGroup group, final String roll) throws RollException {
		this.roll = roll;
		new Roll(roll);

		button = new JRadioButton(roll.toString());
		button.setFont(button.getFont().deriveFont(Font.BOLD));
		group.add(button);
		final JPanel radioButtonPanel = new JPanel();		
		radioButtonPanel.add(button);
		radioButtonPanel.setPreferredSize(new Dimension(57, 35));
		add(radioButtonPanel);

		final JPanel controlPanel = new JPanel();

		final JLabel title = new JLabel("#:");
		controlPanel.add(title);
		number = new NumericTextfield(1, 2);
		controlPanel.add(number);

		final JLabel modifierTitle = new JLabel("Modifier:");
		controlPanel.add(modifierTitle);
		modifier = new JTextField(3);
		controlPanel.add(modifier);

		add(controlPanel);
		// setBorder(new LineBorder(Color.BLACK));
	}
	
	/**
	 * how many die should be thrown?
	 */
	public int getNumber() {
		return number.getTextAsInteger();
	}

	/**
	 * returns the dice roll + any modifier
	 * 
	 * @throws RollException
	 *             if the user entered crap
	 */
	public Roll getRoll() {
		return new Roll(roll + modifier.getText());
	}

	/**
	 * is the radio button in this panel selected?
	 */
	public boolean isSelected() {
		return button.isSelected();
	}

}
