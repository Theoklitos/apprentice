package com.apprentice.rpg.gui;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.apprentice.rpg.parsing.ParsingEx;

/**
 * A textfield that allows only number to be inputed
 * 
 * @author theoklitos
 * 
 */
public final class SavingThrowTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	public SavingThrowTextField() {

		getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(final DocumentEvent event) {
				validateStringAndChangeColor();

			}

			@Override
			public void insertUpdate(final DocumentEvent event) {
				validateStringAndChangeColor();

			}

			@Override
			public void removeUpdate(final DocumentEvent event) {
				validateStringAndChangeColor();
			}

			/**
			 * is this a valid saving throw string? Change text colour depending.
			 */
			private void validateStringAndChangeColor() {
				if (getText().length() == 0) {
					setForeground(Color.black);
					return;
				}
				try {
					validateSingleSavingThrow(getText());
					setForeground(Color.black);
				} catch (final ParsingEx e) {
					setForeground(Color.red);
				}
			}
		});
	}

	private void validateSingleSavingThrow(final String text) {
		try {
			Integer.valueOf(text.trim());
		} catch (final IllegalArgumentException e) {
			throw new ParsingEx(e);
		}
	}

}
