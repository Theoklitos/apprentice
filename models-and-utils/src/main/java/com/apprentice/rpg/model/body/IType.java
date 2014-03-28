package com.apprentice.rpg.model.body;

import java.util.List;

import com.apprentice.rpg.model.playerCharacter.Nameable;
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
	 * returns a copy of the {@link BodyPart}s that this type is comprised of
	 */
	List<BodyPart> getBodyParts();

	/**
	 * What is the maximum range number for which mapped bodyparts exist? By default this is 100 for a range
	 * of [1,100]
	 */
	int getMaxRangeValue();

	/**
	 * Returns the body part for the given number. If you pass a number between 1 and 100, a result is
	 * guarantedd to be returned.
	 * 
	 * @ throws {@link BodyPartMappingEx} if a number outside 1-100 is given
	 */
	BodyPart getPartForNumber(int number) throws BodyPartMappingEx;

	/**
	 * Returns a copy of the body parts and the interger ranges they correspond to - changing this will not
	 * affect the type
	 */
	BodyPartToRangeMapping getPartMapping();

	/**
	 * Returns a box with any {@link IntegerRange} that maps to this body part name, if any
	 */
	Box<IntegerRange> getRangeForPartName(String bodyPartName);

	/**
	 * Will try to set the mapping for this type. An exception will be thrown if the mapping is not
	 * consecutive from 1 to max-value
	 * 
	 * @throws BodyPartMappingEx
	 */
	void setBodyPartMapping(BodyPartToRangeMapping mapping) throws BodyPartMappingEx;

}
