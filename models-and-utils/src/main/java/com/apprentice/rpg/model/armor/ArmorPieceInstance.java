package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.model.durable.DurableItemInstance;
import com.apprentice.rpg.random.ApprenticeRandom;

/**
 * Instance of {@link ArmorPiece} that can be handed out to a player, and is durable(can be damaged/repaired)
 * etc
 * 
 * @author theoklitos
 * 
 */
public final class ArmorPieceInstance extends DurableItemInstance implements IArmorPieceInstance {

	private final ArmorPiece prototype;

	public ArmorPieceInstance(final ArmorPiece prototype) {
		super(prototype);
		this.prototype = prototype;
	}

	@Override
	public int rollDamageReduction(final ApprenticeRandom random) {
		return random.roll(getCurrentRoll());
	}

	@Override
	public String toString() {
		return prototype.toString() + ", health: " + getDurability() + ". Current DR: " + getCurrentRoll();
	}

}
