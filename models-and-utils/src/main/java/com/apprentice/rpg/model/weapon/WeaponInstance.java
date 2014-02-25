package com.apprentice.rpg.model.weapon;

import java.util.Collection;
import java.util.List;

import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.durable.DurableItemInstance;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.google.common.collect.Lists;

/**
 * A {@link Weapon} that is handed out to a player character
 * 
 * @author theoklitos
 * 
 */
public final class WeaponInstance extends DurableItemInstance<Weapon> implements IWeaponInstance {

	public WeaponInstance(final Weapon prototype) {
		super(prototype);
	}

	@Override
	public DamageRoll getBaseDamage() {
		return new DamageRoll(getCurrentRoll(), getPrototype().getOriginalBaseDamage().getType());
	}

	@Override
	public Collection<DamageRoll> getExtraDamages() {
		return Lists.newArrayList(getPrototype().getExtraDamages());
	}

	@Override
	public List<Damage> rollDamage(final ApprenticeRandom random) {
		final List<Damage> result = Lists.newArrayList();
		result.add(random.roll(getBaseDamage()));
		for (final DamageRoll extraDamage : getPrototype().getExtraDamages()) {
			result.add(random.roll(extraDamage));
		}
		return result;
	}

	@Override
	public String toString() {
		return getPrototype().toString() + ", health: " + getDurability() + ". Current base dmg: " + getBaseDamage();
	}

}
