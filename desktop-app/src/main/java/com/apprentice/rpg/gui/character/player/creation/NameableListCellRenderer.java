package com.apprentice.rpg.gui.character.player.creation;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.apprentice.rpg.model.Nameable;

/**
 * {@link DefaultListCellRenderer} that displays only the names of {@link Nameable}s
 * 
 * @author theoklitos
 * 
 */
public class NameableListCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") final JList list, final Object value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {
		Object item = value;

		// if the item to be rendered is Proveedores then display it's Name
		if (Nameable.class.isAssignableFrom(item.getClass())) {
			item = ((Nameable) item).getName();
		}
		return super.getListCellRendererComponent(list, item, index, isSelected, cellHasFocus);
	}

}
