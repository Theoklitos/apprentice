package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.durable.IDurableItem;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.apprentice.rpg.model.playerCharacter.PlayerCharacter;
import com.apprentice.rpg.random.dice.RollWithSuffix;
import com.apprentice.rpg.util.Box;

/**
 * A piece of armor that is bound to a specific {@link BodyPart}
 * 
 * @author theoklitos
 * 
 */
public interface IArmorPiece extends IDurableItem, Nameable, Cloneable {

	/**
	 * deep copies this prototype into what is effectively an armor piece instance, ready to be handed out to
	 * a {@link PlayerCharacter}
	 */
	public IArmorPiece clone();

	/**
	 * returns true if this armor piece can be equipped on the given body part
	 */
	boolean fits(BodyPart bodyPart);

	/**
	 * Returns a string that will be matched to the name of {@link BodyPart}s, to see if they fit. If this
	 * armor is not made for a specific body part (ie ring or amulet) then returns empty box.
	 */
	Box<String> getBodyPartDesignator();

	/**
	 * Returns the dice roll that is used for DR
	 */
	RollWithSuffix getDamageReduction();

	/**
	 * Sets the designator string that will be matched to the names of {@link BodyPart}s to see if the armor
	 * fits them
	 */
	void setBodyPartDesignator(String bodyPartDesignator);

	/**
	 * sets the damage redution for this armor piece
	 */
	void setDamageReductionRoll(RollWithSuffix roll);

}
