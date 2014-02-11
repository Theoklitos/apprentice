package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.durable.DurableItem;

/**
 * A piece of armor that is bound to a specific {@link BodyPart}
 * 
 * @author theoklitos
 * 
 */
public interface ArmorPiece extends DurableItem {

	/**
	 * returns true if this armor piece can be equipped on the given body part
	 */
	boolean fits(BodyPart bodyPart);

	/**
	 * Returns a string that will be matched to the name of {@link BodyPart}s, to see if they fit
	 */
	String getBodyPartDesignator();

	/**
	 * Sets the designator string that will be matched to the names of {@link BodyPart}s to see if the armor
	 * fits them
	 */
	void setBodyPartDesignator(String bodyPartDesignator);

}
