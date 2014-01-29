package com.apprentice.rpg.gui.vault.type;

import java.util.List;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
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
	 * stores a new {@link BodyPart}
	 * 
	 * @throws ItemAlreadyExistsEx
	 */
	void create(BodyPart newPart) throws ItemAlreadyExistsEx;

	/**
	 * stores a new {@link IType}
	 * 
	 * @throws ItemAlreadyExistsEx
	 */
	void create(IType newType) throws ItemAlreadyExistsEx;

	/**
	 * removes this body part or type from the repository. Might affect existing players.
	 */
	void delete(Nameable item, ItemType itemType);

	/**
	 * returns true if a {@link IType} with the given name exists in the database
	 */
	boolean doesTypeNameExist(String name);

	/**
	 * returns all the existing {@link BodyPart}s
	 */
	List<BodyPart> getBodyParts();

	/**
	 * returns a json string containing an object mapped to an array with all the body parts and one more to
	 * allt he types
	 */
	String getJsonForAllTypesAndBodyParts();

	/**
	 * returns all the existing {@link IType}s
	 */
	List<IType> getTypes();

	/**
	 * used to update an existing body part or a type
	 */
	void update(Nameable item, ItemType itemType);

}
