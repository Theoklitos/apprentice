package com.apprentice.rpg.model.body;

import com.apprentice.rpg.model.Nameable;

/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
public interface IType extends Nameable {

	/**
	 * Returns a the body parts and the interger ranges they correspond to.
	 */
	BodyPartToRangeMap getParts();

	/**
	 * Will try to set the mapping for this type. An exception will be thrown if the mapping is not
	 * consecutive from 1 to 100
	 * 
	 * @throws BodyPartMappingEx
	 */
	void setBodyPartMapping(BodyPartToRangeMap mapping) throws BodyPartMappingEx;

}
