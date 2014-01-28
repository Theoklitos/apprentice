package com.apprentice.rpg.gui.vault.type;

import java.util.List;

import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.gui.vault.type.TypeAndBodyPartFrameControl.ItemType;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

/**
 * Controls the {@link TypeAndBodyPartFrame}
 * 
 * @author theoklitos
 * 
 */
public interface ITypeAndBodyPartFrameControl extends ControlForView {

	/**
	 * creates a fresh new body part or type, with the given name
	 */
	void create(String name, final ItemType itemType);

	/**
	 * removes this body part or type from the repository. Might affect existing players.
	 */
	void delete(Nameable item, ItemType itemType);

	/**
	 * returns all the existing {@link BodyPart}s
	 */
	List<BodyPart> getBodyParts();

	/**
	 * returns all the existing {@link IType}s
	 */
	List<IType> getTypes();

	/**
	 * used to update an existing body part or a type
	 */
	void update(Nameable item, ItemType itemType);

}
