package com.apprentice.rpg.model.weapon;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.durable.DurableItem;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * A weapon a PC uses in order to inflict damage
 * 
 * @author theoklitos
 * 
 */
public class Weapon extends DurableItem implements WeaponPrototype, WeaponInstance {

	private final Collection<DamageRoll> extraDamages;
	private final List<DamageRoll> meleeDamages;
	private DamageRoll thrownDamage;
	private Range range;
	private final Map<AmmunitionType, Range> ammunitions;
	private int blockModifier;

	public Weapon(final String name, final int maxDurability) {
		super(name, new CurrentMaximumPair(maxDurability));
		this.meleeDamages = Lists.newArrayList();
		this.extraDamages = Sets.newHashSet();
		this.ammunitions = Maps.newHashMap();
		this.blockModifier = 0;
	}

	/**
	 * will initialize this weapon with one initial melee damage
	 */
	public Weapon(final String name, final int durability, final DamageRoll meleeDamage) {
		this(name, durability);
		addMeleeDamage(meleeDamage);
	}

	@Override
	public void addMeleeDamage(final DamageRoll meleeDamage) throws ApprenticeEx {
		if (meleeDamages.contains(meleeDamage)) {
			throw new ApprenticeEx("Cannot add an identical melee damage \"" + meleeDamage + "\" to weapon "
				+ getName());
		}
		meleeDamages.add(meleeDamage);
		updateRollState();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Weapon) {
			final Weapon otherWeapon = (Weapon) other;
			return super.equals(otherWeapon)
				&& ApprenticeCollectionUtils.areAllElementsEqual(meleeDamages, otherWeapon.meleeDamages)
				&& ApprenticeCollectionUtils.areAllElementsEqual(extraDamages, otherWeapon.extraDamages)
				&& Objects.equal(thrownDamage, otherWeapon.thrownDamage) && Objects.equal(range, otherWeapon.range)
				&& blockModifier == otherWeapon.blockModifier
				&& ApprenticeCollectionUtils.areAllElementsEqual(ammunitions, otherWeapon.ammunitions);
		} else {
			return false;
		}
	}

	@Override
	public Map<AmmunitionType, Range> getAmmunitionsWithRange() {
		return ammunitions;
	}

	@Override
	public int getBlockModifier() {
		return blockModifier;
	}

	@Override
	public Collection<Roll> getDeterioratableRolls() {
		final Collection<Roll> result = Lists.newArrayList();
		for (final DamageRoll damage : meleeDamages) {
			result.add(damage.getRoll());
		}
		if (getThrownDamage().hasContent()) {
			System.out.println("getrhwon");
			result.add(getThrownDamage().getContent().getRoll());
		}
		return result;
	}

	@Override
	public Collection<DamageRoll> getExtraDamages() {
		return extraDamages;
	}

	@Override
	public List<DamageRoll> getMeleeDamageRolls() {
		return Lists.newArrayList(meleeDamages);
	}

	@Override
	public Box<Range> getRange() {
		if (range == null) {
			return Box.empty();
		} else {
			return Box.with(range);
		}
	}

	@Override
	public Box<DamageRoll> getThrownDamage() {
		if (thrownDamage == null) {
			return Box.empty();
		} else {
			return Box.with(thrownDamage);
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode()
			+ Objects.hashCode(extraDamages, meleeDamages, thrownDamage, range, blockModifier, ammunitions);
	}

	@Override
	public boolean isThrownWeapon() {
		return getRange().hasContent() && getThrownDamage().hasContent();
	}

	@Override
	public void removeMeleeDamage(final DamageRoll meleeDamage) throws ApprenticeEx {
		if (!meleeDamages.contains(meleeDamage)) {
			throw new ApprenticeEx("Damage \"" + meleeDamage
				+ "\" cannot be removed because it doesn't exist in weapon " + getName());
		}
		meleeDamages.remove(meleeDamage);
		updateRollState();
	}

	/**
	 * rolls damage and adds extra ones too
	 */
	private List<Damage> rollDamagePlusExtras(final DamageRoll damageRoll, final ApprenticeRandom random) {
		final List<Damage> result = Lists.newArrayList();
		result.add(random.roll(damageRoll));
		for (final DamageRoll extraDamage : extraDamages) {
			result.add(random.roll(extraDamage));
		}
		return result;
	}

	@Override
	public List<Damage> rollMeleeDamage(final DamageRoll damageRoll, final ApprenticeRandom random) throws ApprenticeEx {
		if (!meleeDamages.contains(damageRoll)) {
			throw new ApprenticeEx("Weapon " + getName() + " does not contain damage roll " + damageRoll);
		}
		return rollDamagePlusExtras(damageRoll, random);
	}

	@Override
	public List<Damage> rollMeleeDamage(final int number, final ApprenticeRandom random) throws ApprenticeEx {
		try {
			return rollDamagePlusExtras(meleeDamages.get(0), random);
		} catch (final IndexOutOfBoundsException e) {
			throw new ApprenticeEx("Weapon " + getName() + " does not have a # " + number + " damage roll.");
		}
	}

	@Override
	public void setAmmoType(final AmmunitionType type, final Range range) {
		ammunitions.put(type, range);
	}

	@Override
	public void setBlockModifier(final int modifier) {
		this.blockModifier = modifier;
	}

	@Override
	public void setRange(final Range range) {
		if (getThrownDamage().isEmpty()) {
			throw new ApprenticeEx("Tried to set range, but no range damage is set.");
		} else {
			this.range = range;
		}
	}

	@Override
	public void setRangeAndOptimalThrownDamage(final Range range, final DamageRoll thrownDamage) {
		this.range = range;
		this.thrownDamage = thrownDamage;
	}

	@Override
	public void setRangeAndOptimalThrownDamage(final String rangeAsString, final DamageRoll thrownDamage)
			throws ParsingEx {
		setRangeAndOptimalThrownDamage(new Range(rangeAsString), thrownDamage);
	}

	@Override
	public void setThrownDamage(final DamageRoll thrownDamage) throws ApprenticeEx {
		if (getRange().isEmpty()) {
			throw new ApprenticeEx("Tried to set ranged damage, but no range is set.");
		} else {
			setRangeAndOptimalThrownDamage(range, thrownDamage);
		}
	}

	@Override
	public String toString() {
		String meleeDamages = ", no melee dmg.";
		if (this.meleeDamages.size() > 0) {
			meleeDamages = ", melee dmg: " + Joiner.on(" or ").join(this.meleeDamages);
		}
		String extraDamages = "";
		if (this.extraDamages.size() > 0) {
			extraDamages = ", extra dmgs: " + Joiner.on(",").join(this.extraDamages);
		}
		String blockMod = ".";
		if (getBlockModifier() != 0) {
			final String operator = (getBlockModifier() > 0) ? "+" : "";

			blockMod = ". Modifier " + operator + getBlockModifier() + " to blocks.";
		}
		String thrownInfo = ".";
		if (isThrownWeapon()) {
			thrownInfo = ", thrown: " + getThrownDamage().getContent() + " at " + getRange().getContent() + ".";
		}
		return getName() + meleeDamages + extraDamages + thrownInfo + " HP: " + getDurability() + blockMod;
	}

}
