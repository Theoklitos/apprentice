package com.apprentice.rpg.model.body;

import java.util.List;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.IntegerRange;

/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
public interface IType extends Nameable {

	/**
	 * Returns a copy of the body parts and the interger ranges they correspond to - changing this will not affect the type
	 */
	BodyPartToRangeMap getPartMapping();

	/**
	 * returns a copy of the {@link BodyPart}s that this type is comprised of
	 */
	List<BodyPart> getParts();

	/**
	 * Returns a box with any {@link IntegerRange} that maps to this body part name, if any
	 */
	Box<IntegerRange> getRangeForPartName(String bodyPartName);

	/**
	 * Will try to set the mapping for this type. An exception will be thrown if the mapping is not
	 * consecutive from 1 to 100
	 * 
	 * @throws BodyPartMappingEx
	 */
	void setBodyPartMapping(BodyPartToRangeMap mapping) throws BodyPartMappingEx;

}
