package com.apprentice.rpg.gui;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.weapon.Range;
import com.apprentice.rpg.parsing.ParsingEx;

/**
 * A textfield that checks if the input make sense and turns the text into red otherwise
 * 
 * @author theoklitos
 * 
 */
public final class TextfieldWithColorWarning extends JTextField {

	private static final long serialVersionUID = 1L;

	/**
	 * @param toBeUsedFor
	 *            this must have one constructor which accepts a string
	 */
	public TextfieldWithColorWarning(final Class<?> toBeUsedFor) {

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
			 * is this a {@link Range} string? Change text colour depending.
			 */
			private void validateStringAndChangeColor() {
				if (getText().length() == 0) {
					setForeground(Color.black);
					return;
				}
				try {
					try {
						toBeUsedFor.getConstructor(String.class).newInstance(getText().trim());
					} catch (final InstantiationException e) {
						throw new ApprenticeEx(e);
					} catch (final IllegalAccessException e) {
						throw new ApprenticeEx(e);
					} catch (final IllegalArgumentException e) {
						throw new ApprenticeEx(e);
					} catch (final InvocationTargetException e) {
						throw new ParsingEx(e);
					} catch (final NoSuchMethodException e) {
						throw new ApprenticeEx(e);
					} catch (final SecurityException e) {
						throw new ApprenticeEx(e);
					}
					setForeground(Color.black);
				} catch (final ParsingEx e) {
					setForeground(Color.red);
				}
			}
		});
	}

}
