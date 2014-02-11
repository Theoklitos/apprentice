package com.apprentice.rpg.model.weapon;

import java.util.Collection;
import java.util.List;

import com.apprentice.rpg.model.durable.DurableItemInstance;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.google.common.collect.Lists;

/**
 * A {@link Weapon} that is handed out to a player character
 * 
 * @author theoklitos
 * 
 */
public final class WeaponInstance extends DurableItemInstance implements IWeaponInstance {

	private final Weapon prototype;

	public WeaponInstance(final Weapon prototype) {
		super(prototype);
		this.prototype = prototype;
	}

	@Override
	public DamageRoll getBaseDamage() {
		return new DamageRoll(getCurrentRoll(), prototype.getOriginalBaseDamage().getType());
	}

	@Override
	public Collection<DamageRoll> getExtraDamages() {
		return Lists.newArrayList(prototype.getExtraDamages());
	}

	@Override
	public List<Damage> rollDamage(final ApprenticeRandom random) {
		final List<Damage> result = Lists.newArrayList();
		result.add(random.roll(getBaseDamage()));
		for (final DamageRoll extraDamage : prototype.getExtraDamages()) {
			result.add(random.roll(extraDamage));
		}
		return result;
	}

	@Override
	public String toString() {
		return prototype.toString() + ", health: " + getDurability() + ". Current base dmg: " + getBaseDamage();
	}

}
