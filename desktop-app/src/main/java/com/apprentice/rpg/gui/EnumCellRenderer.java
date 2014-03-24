package com.apprentice.rpg.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.apache.commons.lang3.StringUtils;

/**
 * {@link DefaultListCellRenderer} that displays the value of the enum in lower case + capitalized
 * 
 * @author theoklitos
 * 
 */
public class EnumCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {
		Object item = value;
		
		if (item != null) {
			item = StringUtils.capitalize(value.toString().toLowerCase());
		}
		return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
	}

}
