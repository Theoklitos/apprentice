package com.apprentice.rpg.model.durable;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.util.Checker;

/**
 * item with a max durability, name and description
 * 
 * @author theoklitos
 * 
 */
public abstract class DurableItemPrototype extends BaseApprenticeObject implements DurableItem {

	private final CurrentMaximumPair durability;
	private Roll baseRoll;

	public DurableItemPrototype(final String name, final int durability, final Roll baseRoll) {
		super(name);
		Checker.checkNonNull("Durable prototype item needs a roll", true, baseRoll);
		this.baseRoll = baseRoll;
		this.durability = new CurrentMaximumPair(durability);
	}

	@Override
	public Roll getBaseRoll() {
		return baseRoll;
	}

	@Override
	public int getMaximumDurability() {
		return durability.getMaximum();
	}

	@Override
	public void setBaseRoll(final Roll baseRoll) {
		this.baseRoll = baseRoll;
	}

	@Override
	public void setMaximumDurability(final int maxDurability) {
		durability.setMaximum(maxDurability);
	}

}
