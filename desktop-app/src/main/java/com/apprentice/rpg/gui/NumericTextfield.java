package com.apprentice.rpg.gui;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.apache.commons.lang3.StringUtils;

/**
 * A textfield that allows only number to be inputed
 * 
 * @author theoklitos
 * 
 */
public final class NumericTextfield extends JTextField {

	/**
	 * Keeps only numbers from inputs
	 * 
	 * @author theoklitos
	 * 
	 */
	class OnlyNumbersFilter extends DocumentFilter {
		@Override
		public void insertString(final DocumentFilter.FilterBypass filterBypass, final int offset, final String string,
				final AttributeSet attributeSet) throws BadLocationException {
			// remove non-digits
			filterBypass.insertString(offset, string.replaceAll("\\D++", ""), attributeSet);
		}

		@Override
		public void replace(final DocumentFilter.FilterBypass filterBypass, final int offset, final int length,
				final String string, final AttributeSet attributeSet) throws BadLocationException {
			filterBypass.replace(offset, length, string.replaceAll("\\D++", ""), attributeSet);
		}
	}

	private static final long serialVersionUID = 1L;

	public NumericTextfield() {
		this(-1, 0);
	}

	public NumericTextfield(final int columns) {
		this(-1, columns);
	}

	public NumericTextfield(final int initialNumber, final int columns) {
		super(columns);
		((AbstractDocument) getDocument()).setDocumentFilter(new OnlyNumbersFilter());
		if (initialNumber != -1) {
			setText(Integer.toString(initialNumber));
		}
	}

	/**
	 * If no text is inputted, will return 0
	 */
	public int getTextAsInteger() {
		if (StringUtils.isBlank(getText().trim())) {
			return 0;
		} else {
			return Integer.valueOf(getText());
		}
	}

}
