package com.apprentice.rpg.model.factories;

import java.util.List;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.simple.SimpleVault;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.SavingThrows;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.strike.StrikeType;
import com.google.common.collect.Lists;

/**
 * Builds predefined, prototypical {@link PlayerCharacter}s. Used for testing
 * 
 * @author theoklitos
 * 
 */
public final class DataFactory extends SimpleVault {

	public DataFactory() {
		initializeData();
	}

	/**
	 * #0 is breastplate
	 */
	public List<IArmorPiece> getArmorPieces() {
		final List<IArmorPiece> result = Lists.newArrayList();
		for (final IArmorPiece type : getAllNameables(ArmorPiece.class)) {
			result.add(type);
		}
		return result;
	}

	public List<BodyPart> getBodyParts() {
		return Lists.newArrayList(getAllNameables(BodyPart.class));
	}

	public IPlayerCharacter getPlayerCharacter() {
		return getAllNameables(PlayerCharacter.class).iterator().next();
	}

	/**
	 * #0 bludgeoning, #1 slashing, #2 piercing, #3 projectiles, #4 cold, #5 fire
	 */
	public List<StrikeType> getStrikeTypes() {
		final List<StrikeType> result = Lists.newArrayList();
		for (final StrikeType type : getAllNameables(StrikeType.class)) {
			result.add(type);
		}
		return result;
	}

	/**
	 * type #0 is the standard Human
	 */
	public List<IType> getTypes() {
		final List<IType> result = Lists.newArrayList();
		for (final Type type : getAllNameables(Type.class)) {
			result.add(type);
		}
		return result;
	}

	/**
	 * 0 is simple sword, 1 is greatsword, 3 is dagger, 4 is bow
	 */
	public List<Weapon> getWeapons() {
		final List<Weapon> result = Lists.newArrayList();
		for (final Weapon type : getAllNameables(Weapon.class)) {
			result.add(type);
		}
		return result;
	}

	private void initializeArmors() {
		final IArmorPiece breastplate = new ArmorPiece("Breastplate", 30, new Roll("D6+1"), "Torso");
		breastplate.setDescription("Good all around armor, for rogues or fighters.");
		final IArmorPiece greatHelm = new ArmorPiece("Helm", 20, new Roll("2D4+1"), "Head");
		greatHelm.setDescription("Will save your face, but you can't hear shit.");
		add(breastplate);
		add(greatHelm);
	}

	private void initializeBodyParts() {
		final BodyPart head = new BodyPart("Head");
		head.setDescription("A very important part");
		add(head); // 0
		final BodyPart torso = new BodyPart("Torso");
		add(torso); // 1
		torso.setDescription("Fat belly");
		add(new BodyPart("Left Arm")); // 2
		final BodyPart rightArm = new BodyPart("Right Arm");
		add(rightArm); // 3
		rightArm.setDescription("This is the good hand!");
		add(new BodyPart("Left Leg")); // 4
		add(new BodyPart("Right Leg")); // 5
		add(new BodyPart("Left Wing")); // 6
		add(new BodyPart("Right Wing")); // 7
		final BodyPart tail = new BodyPart("Tail");
		tail.setDescription("Like a snake.");
		add(tail); // 8
	}

	private void initializeData() {
		initializeBodyParts();
		initializeTypes();
		initializeStrikes();
		initializeWeapons();
		initializeArmors();
		initializePlayerCharacter();
	}

	private void initializePlayerCharacter() {
		final StatBundle stats = new StatBundle(16, 12, 10, 18, 12, 4);
		stats.getStat(StatType.STRENGTH).setValue(6);
		stats.getStat(StatType.INTELLIGENCE).setValue(22);
		final CharacterType characterType = new CharacterType(getTypes().get(0), Size.MEDIUM);
		characterType.setRace("Elf");
		final SavingThrows saves = new SavingThrows("+3/+1/+3");
		final IPlayerCharacter pc = new PlayerCharacter("Anonymous Hero", 10, stats, characterType, 30, saves);
		pc.setDescription("Character made only for testing!");
		pc.getHitPoints().setCurrentHitPoints(5);
		pc.getLevels().addLevels("Fighter", 2);
		pc.getLevels().addLevels("wizard", 5);

		//final WeaponInstance longswordInstance = new WeaponInstance(getWeapons().get(0));
		//longswordInstance.removeHitPoints(10);
		//final WeaponInstance greatswordInstance = new WeaponInstance(getWeapons().get(1));
		//pc.getCombatCapabilities().setWeaponForSequence(longswordInstance, new BonusSequence(7));
		//pc.getCombatCapabilities().setWeaponForSequence(greatswordInstance, new BonusSequence(2)); TODO

		add(pc);
	}

	private void initializeStrikes() {
		final StrikeType bludgeoning = new StrikeType("Bludgeoning");
		bludgeoning.setDescription("Blunt force traumas: maces, stones.");
		add(bludgeoning);
		final StrikeType slashing = new StrikeType("Slashing");
		slashing.setDescription("Wounds that cut, such as from swords.");
		add(slashing);
		final StrikeType piercing = new StrikeType("Piercing");
		piercing.setDescription("Piercing melee injuties like spears or rapiers.");
		add(piercing);
		final StrikeType projectiles = new StrikeType("Projectiles");
		projectiles.setDescription("Bulets, thrown daggers, arrows and t he like.");
		add(projectiles);
		final StrikeType cold = new StrikeType("Cold");
		cold.setDescription("Usually magical freezing effects.");
		add(cold);
		final StrikeType fire = new StrikeType("Fire");
		fire.setDescription("Evocations, fireballs!");
		add(fire);
	}

	private void initializeTypes() {
		final BodyPartToRangeMap humanMapping = new BodyPartToRangeMap();
		final List<BodyPart> bodyParts = getBodyParts();
		humanMapping.setPartForRange(1, 10, bodyParts.get(0));
		humanMapping.setPartForRange(11, 50, bodyParts.get(1));
		humanMapping.setPartForRange(51, 64, bodyParts.get(2));
		humanMapping.setPartForRange(65, 78, bodyParts.get(3));
		humanMapping.setPartForRange(79, 89, bodyParts.get(4));
		humanMapping.setPartForRange(90, 100, bodyParts.get(5));
		final IType human = new Type("Humanoid", humanMapping);
		human.setDescription("Simple dude, like us.");
		add(human);

		final BodyPartToRangeMap wingedHumanoidMapping = new BodyPartToRangeMap();
		wingedHumanoidMapping.setPartForRange(1, 10, bodyParts.get(0));
		wingedHumanoidMapping.setPartForRange(11, 50, bodyParts.get(1));
		wingedHumanoidMapping.setPartForRange(51, 60, bodyParts.get(2));
		wingedHumanoidMapping.setPartForRange(61, 70, bodyParts.get(3));
		wingedHumanoidMapping.setPartForRange(71, 75, bodyParts.get(4));
		wingedHumanoidMapping.setPartForRange(76, 80, bodyParts.get(5));
		wingedHumanoidMapping.setPartForRange(81, 87, bodyParts.get(6));
		wingedHumanoidMapping.setPartForRange(86, 94, bodyParts.get(7));
		wingedHumanoidMapping.setPartForRange(95, 100, bodyParts.get(8));
		final IType daemon = new Type("Daemon", wingedHumanoidMapping);
		daemon.setDescription("Mean dude");
		add(daemon);
	}

	private void initializeWeapons() {
		final Weapon longsword = new Weapon("Longsword", 20, new DamageRoll("D8", getStrikeTypes().get(1)));
		longsword.setDescription("Simple but effective weapon.");
		final Weapon greatsword =
			new Weapon("Magical Greatsword", 30, new DamageRoll("2D6+1", getStrikeTypes().get(1)));
		greatsword.setDescription("Awesome magical greatsword");
		greatsword.getExtraDamages().add(new DamageRoll("1D6", getStrikeTypes().get(5)));
		greatsword.getExtraDamages().add(new DamageRoll("1D10", getStrikeTypes().get(4)));
		final Weapon dagger = new Weapon("Dagger", 5, new DamageRoll("D4", getStrikeTypes().get(3)));
		dagger.setDescription("Small and sneaky.");
		dagger.setRangeAndOptimalThrownDamage("5/10/15", new DamageRoll("D6", getStrikeTypes().get(4)));

		// final Weapon bow = new WeaponPrototype("Bow", 5, new DamageRoll("D8", getStrikeTypes().get(3)), new
// Ra);
		// dagger.setDescription("Small and sneaky.");

		add(longsword);
		add(greatsword);
		add(dagger);
		// b,s,p
	}

	@Override
	public void update(final Nameable item) throws NameAlreadyExistsEx {
		add(item);
	}
}
