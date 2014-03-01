package com.apprentice.rpg.gui.dice;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JRadioButton;

/**
 * Sets the button to selected when the textfield is focused
 * 
 * @author theoklitos
 * 
 */
public class ButtonSelectionFocusListener implements FocusListener {

	private final JRadioButton button;

	public ButtonSelectionFocusListener(final JRadioButton button) {
		this.button = button;
	}

	@Override
	public void focusGained(final FocusEvent event) {
		button.setSelected(true);
	}

	@Override
	public void focusLost(final FocusEvent e) {
		// do nothing
	}

}
