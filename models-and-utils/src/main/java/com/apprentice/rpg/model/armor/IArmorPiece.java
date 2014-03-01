package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.random.dice.Roll;

/**
 * A piece of armor that is bound to a specific {@link BodyPart}
 * 
 * @author theoklitos
 * 
 */
public interface IArmorPiece extends Nameable {

	/**
	 * returns true if this armor piece can be equipped on the given body part
	 */
	boolean fits(BodyPart bodyPart);

	/**
	 * Returns a string that will be matched to the name of {@link BodyPart}s, to see if they fit
	 */
	String getBodyPartDesignator();

	/**
	 * Returns the dice roll that is used for DR
	 */
	Roll getDamageReductionRoll();

	/**
	 * Sets the designator string that will be matched to the names of {@link BodyPart}s to see if the armor
	 * fits them
	 */
	void setBodyPartDesignator(String bodyPartDesignator);

	/**
	 * sets the damage redution for this armor piece
	 */
	void setDamageReductionRoll(Roll roll);

}
