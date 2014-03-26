package com.apprentice.rpg.model.weapon;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.rits.cloning.Cloner;

/**
 * A weapon a PC uses in order to inflict damage
 * 
 * @author theoklitos
 * 
 */
public class Weapon extends DurableItem implements IWeapon {

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
	}

	@Override
	public IWeapon clone() {
		if (!isPrototype()) {
			throw new ApprenticeEx("Tried to clone non-protoype weapon " + getName());
		}
		final Weapon clone = new Cloner().deepClone(this);
		clone.setPrototype(false);
		return clone;
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
	public Collection<DamageRoll> getExtraDamages() {
		return getModifiedDamagedRollCollection(extraDamages);
	}

	@Override
	public List<DamageRoll> getMeleeDamages() {
		return Lists.newArrayList(getModifiedDamagedRollCollection(meleeDamages));
	}

	/**
	 * modifies the {@link Roll}s inside all the {@link DamageRoll}s
	 */
	private Collection<DamageRoll> getModifiedDamagedRollCollection(final Collection<DamageRoll> damageRollCollection) {
		final Set<DamageRoll> modifiedDamageRolls = Sets.newHashSet();
		for (final DamageRoll damageRoll : damageRollCollection) {
			final Roll modifiedRoll = getModifiedRollForDeterioration(damageRoll.getRoll());
			final DamageRoll modifiedDamagedRoll =
				new DamageRoll(modifiedRoll, damageRoll.getPenetration(), damageRoll.getType());
			modifiedDamageRolls.add(modifiedDamagedRoll);
		}
		return modifiedDamageRolls;
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
			final DamageRoll modifiedDamage =
				new DamageRoll(getModifiedRollForDeterioration(thrownDamage.getRoll()), thrownDamage.getType());
			return Box.with(modifiedDamage);
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
	public void setExtraDamages(final Collection<DamageRoll> extraDamages) {
		this.extraDamages.clear();
		this.extraDamages.addAll(extraDamages);
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
	public void setRangeAndThrownDamage(final Range range, final DamageRoll thrownDamage) {
		this.range = range;
		this.thrownDamage = thrownDamage;
	}

	@Override
	public void setRangeAndThrownDamage(final String rangeAsString, final DamageRoll thrownDamage) throws ParsingEx {
		setRangeAndThrownDamage(new Range(rangeAsString), thrownDamage);
	}

	@Override
	public void setThrownDamage(final DamageRoll thrownDamage) throws ApprenticeEx {
		if (getRange().isEmpty()) {
			throw new ApprenticeEx("Tried to set ranged damage, but no range is set.");
		} else {
			setRangeAndThrownDamage(range, thrownDamage);
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
		String name = getName();
		if (isPrototype()) {
			name = "[PROTOTYPE] " + name;
		}
		return name + meleeDamages + extraDamages + thrownInfo + " HP: " + getDurability() + blockMod;
	}

}
