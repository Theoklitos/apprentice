package com.apprentice.rpg.model.durable;

import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * item with a max durability, name and description
 * 
 * @author theoklitos
 * 
 */
public abstract class DurableItemPrototype extends BaseApprenticeObject implements DurableItem {

	private int maxDurability;
	private Roll baseRoll;

	public DurableItemPrototype(final String name, final int durability, final Roll baseRoll) {
		super(name);
		Checker.checkNonNull("Durable prototype item needs a roll", true, baseRoll);
		this.baseRoll = baseRoll;
		this.maxDurability = durability;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof DurableItemPrototype) {
			final DurableItemPrototype otherDurableItem = (DurableItemPrototype) other;
			return super.equals(otherDurableItem) && (maxDurability == otherDurableItem.maxDurability)
				&& Objects.equal(baseRoll, otherDurableItem.baseRoll);
		} else {
			return false;
		}
	}

	@Override
	public Roll getBaseRoll() {
		return baseRoll;
	}

	@Override
	public int getMaximumDurability() {
		return maxDurability;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(baseRoll);
	}

	@Override
	public void setBaseRoll(final Roll baseRoll) {
		this.baseRoll = baseRoll;
	}

	@Override
	public void setMaximumDurability(final int maxDurability) {
		if (maxDurability < 0) {
			this.maxDurability = 0;
		} else {
			this.maxDurability = maxDurability;
		}
	}

}
