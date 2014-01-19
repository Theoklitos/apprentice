package com.apprentice.rpg.gui;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.ParsingEx;

/**
 * A textfield that validates if a correct {@link PlayerLevels} string is inputed
 * 
 * @author theoklitos
 * 
 */
public final class PlayerLevelsTextfield extends JTextField {

	private static final long serialVersionUID = 1L;

	public PlayerLevelsTextfield(final ApprenticeParser parser) {

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
			 * is this a {@link PlayerLevels} string? Change text colour depending.
			 */
			private void validateStringAndChangeColor() {
				if (getText().length() == 0) {
					setForeground(Color.black);
					return;
				}
				try {
					parser.parseWithoutExperience(getText());
					setForeground(Color.black);
				} catch (final ParsingEx e) {
					setForeground(Color.red);
				}
			}
		});
	}

}
