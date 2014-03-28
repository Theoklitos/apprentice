package com.apprentice.rpg.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.apprentice.rpg.model.playerCharacter.Nameable;

/**
 * {@link DefaultListCellRenderer} that displays only the names of {@link Nameable}s
 * 
 * @author theoklitos
 * 
 */
public class NameableListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {
		Object item = value;

		if (item != null && Nameable.class.isAssignableFrom(item.getClass())) {
			item = ((Nameable) item).getName();
		}
		return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
	}

}
