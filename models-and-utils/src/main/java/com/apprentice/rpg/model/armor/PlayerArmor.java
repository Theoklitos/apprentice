package com.apprentice.rpg.model.armor;

import java.util.List;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.body.IType;
import com.google.common.collect.Lists;

/**
 * Represents what the player is wearing on his body parts.
 * 
 * @author theoklitos
 * 
 */
public final class PlayerArmor extends Armor {

	private final List<IArmorPiece> extraPieces;

	public PlayerArmor(final IType type) {
		super(type);
		setPrototype(false);
		extraPieces = Lists.newArrayList();
	}
	
	/**
	 * Adds the given piece as an extra (non body part designated) armor piece.
	 * 
	 * @throws ApprenticeEx
	 *             if the given armor piece has a designator
	 */
	public void addExtraPiece(final IArmorPiece armorPiece) {
		if (armorPiece.getBodyPartDesignator().hasContent()) {
			throw new ApprenticeEx("Armor piece \"" + armorPiece.getName() + "\" is designated for \""
				+ armorPiece.getBodyPartDesignator().getContent() + "\", cannot be an extra one.");
		}
		if (armorPiece.isPrototype()) {
			extraPieces.add(armorPiece.clone());
		} else {
			extraPieces.add(armorPiece);
		}
	}

	/**
	 * returns any extra (non designated) armor pieces the character has
	 */
	public List<IArmorPiece> getExtraPieces() {
		return extraPieces;
	}

}
