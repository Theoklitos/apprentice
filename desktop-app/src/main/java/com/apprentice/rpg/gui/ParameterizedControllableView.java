package com.apprentice.rpg.gui;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * A {@link ControlForView} that can display a specific {@link Nameable} and is thus "parameterized" by it
 * 
 * @author theoklitos
 * 
 */
public interface ParameterizedControllableView<T extends Nameable> extends ControllableView {

	/**
	 * will fill the fields in the frame with information from the given item
	 */
	void display(final T itemToDisplay);
	
	/**
	 * what is the parameter type of this frame  
	 */
	ItemType getType();
}
