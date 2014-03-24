package com.apprentice.rpg.model.factories;

import java.util.List;
import java.util.Set;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.simple.SimpleVault;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.SavingThrows;
import com.apprentice.rpg.model.Skill;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMapping;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.model.combat.BonusSequence;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.damage.Penetration;
import com.apprentice.rpg.model.weapon.AmmunitionType;
import com.apprentice.rpg.model.weapon.IWeapon;
import com.apprentice.rpg.model.weapon.Range;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.random.dice.RollWithSuffix;
import com.apprentice.rpg.strike.StrikeType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
	 * #0 is breastplate, #1 is helm, #3 and #4 are legs/arms
	 */
	public List<IArmorPiece> getArmorPieces() {
		final List<IArmorPiece> result = Lists.newArrayList();
		for (final IArmorPiece type : getAllNameables(ArmorPiece.class)) {
			result.add(type);
		}
		return result;
	}

	/**
	 * #0 is a simple plate, #1 is leather
	 */
	public List<Armor> getArmors() {
		final List<Armor> result = Lists.newArrayList();
		for (final Armor type : getAllNameables(Armor.class)) {
			result.add(type);
		}
		return result;
	}

	public List<BodyPart> getBodyParts() {
		return Lists.newArrayList(getAllNameables(BodyPart.class));
	}

	public IPlayerCharacter getPlayerCharacter1() {
		return Lists.newArrayList(getAllNameables(IPlayerCharacter.class)).get(0);
	}

	public IPlayerCharacter getPlayerCharacter2() {
		return Lists.newArrayList(getAllNameables(IPlayerCharacter.class)).get(1);
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
	 * 0 is longsword, 1 is greatsword, 2 is dagger, 3 is bow, 4 is musket
	 */
	public List<IWeapon> getWeaponPrototypes() {
		final List<IWeapon> result = Lists.newArrayList();
		for (final IWeapon type : getAllPrototypeNameables(Weapon.class)) {
			result.add(type);
		}
		return result;
	}

	private void initializeArmors() {
		final ArmorPiece breastplate = new ArmorPiece("Breastplate", 30, new RollWithSuffix("D6+1"), "Torso");
		breastplate.setDescription("Good all around armor, for rogues or fighters.");
		breastplate.setPrototype(true);
		final ArmorPiece greatHelm = new ArmorPiece("Helm", 20, new RollWithSuffix("2D4+1"), "Head");
		greatHelm.setDescription("Will save your face, but you can't hear shit.");
		greatHelm.setPrototype(true);
		final ArmorPiece legPlate = new ArmorPiece("Leg Plate", 10, new RollWithSuffix("D4+1"), "Leg");
		legPlate.setPrototype(true);
		final ArmorPiece armPlate = new ArmorPiece("Arm Plate", 10, new RollWithSuffix("D4+1"), "Arm");
		armPlate.setPrototype(true);
		add(breastplate);
		add(greatHelm);
		add(legPlate);
		add(armPlate);

		// to test magical DR
		final ArmorPiece magicalChainmail =
			new ArmorPiece("Magical Chainshirt +3", 25, new RollWithSuffix("D5+1", 3), "Torso");
		magicalChainmail.setDescription("Wow! +3 magic item!");
		magicalChainmail.setPrototype(true);
		add(magicalChainmail);

		final Armor simplePlate = new Armor("Simple Plate", getTypes().get(0));
		simplePlate.setDescription("Simple plate armor, made for humanoids.");
		simplePlate.addArmorPiece(greatHelm);
		simplePlate.addArmorPiece(breastplate);
		simplePlate.setArmorPieceForBodyPart("left leg", legPlate);
		simplePlate.setArmorPieceForBodyPart("right leg", legPlate);
		simplePlate.setArmorPieceForBodyPart("left arm", armPlate);
		simplePlate.setArmorPieceForBodyPart("right arm", armPlate);
		add(simplePlate);

		final Armor leatherArmor = new Armor("Leather Armor", getTypes().get(0));
		leatherArmor.setDescription("Nimble armor made for rogues.");
		final ArmorPiece studdedLeather =
			new ArmorPiece("Studded Leather Torso", 10, new RollWithSuffix("D4+1"), "Torso");
		studdedLeather.setPrototype(true);
		final ArmorPiece legLeather = new ArmorPiece("Leg Leather", 10, new RollWithSuffix("D2"), "Leg");
		legLeather.setPrototype(true);
		final ArmorPiece armLeather = new ArmorPiece("Arm Leather", 10, new RollWithSuffix("D2"), "Arm");
		armLeather.setPrototype(true);
		add(studdedLeather);
		add(legLeather);
		add(armLeather);

		leatherArmor.addArmorPiece(studdedLeather);
		leatherArmor.setArmorPieceForBodyPart("left leg", legLeather);
		leatherArmor.setArmorPieceForBodyPart("right leg", legLeather);
		leatherArmor.setArmorPieceForBodyPart("left arm", armLeather);
		leatherArmor.setArmorPieceForBodyPart("right arm", armLeather);
		add(leatherArmor);
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
		initializeWeaponsAndAmmo();
		initializeArmors();
		initializePlayerCharacters();
	}

	private void initializePlayerCharacter1() throws ParsingEx {
		final StatBundle stats = new StatBundle(16, 12, 10, 18, 12, 4);
		stats.getStat(StatType.STRENGTH).setValue(6);
		stats.getStat(StatType.INTELLIGENCE).setValue(22);
		final CharacterType characterType = new CharacterType(getTypes().get(0), Size.MEDIUM);
		characterType.setRace("Elf");
		final SavingThrows saves = new SavingThrows("+3/+1/+3");
		final IPlayerCharacter pc1 = new PlayerCharacter("Anonymous Hero", 10, stats, characterType, 30, saves);
		pc1.setDescription("Original testing character.");
		pc1.getHitPoints().setCurrentHitPoints(5);
		pc1.getLevels().addLevels("Fighter", 2);
		pc1.getLevels().addLevels("wizard", 5);
		pc1.addSkill(new Skill("Spellcraft", stats.getStat(StatType.INTELLIGENCE), 3));
		pc1.addSkill(new Skill("Knowledge(crap)", stats.getStat(StatType.WISDOM), 7));

		final IWeapon longswordInstance = getWeaponPrototypes().get(0).clone();
		longswordInstance.removeHitPoints(10);
		pc1.getCombatCapabilities().setWeaponForSequence(longswordInstance, new BonusSequence(6));
		final IWeapon bowInstance = getWeaponPrototypes().get(3).clone();
		bowInstance.removeHitPoints(1);
		pc1.getCombatCapabilities().setWeaponForSequence(bowInstance, new BonusSequence(15));

		pc1.giveArmorToPlayer(getArmors().get(0));

		add(pc1);
	}

	private void initializePlayerCharacter2() throws ParsingEx {
		final StatBundle stats = new StatBundle(16, 12, 10, 18, 12, 4);
		stats.getStat(StatType.STRENGTH).setValue(10);
		stats.getStat(StatType.DEXTERITY).setValue(22);
		stats.getStat(StatType.INTELLIGENCE).setValue(12);
		final CharacterType characterType = new CharacterType(getTypes().get(1), Size.MEDIUM);
		characterType.setRace("Daemonic");
		final SavingThrows saves = new SavingThrows("+8/1/-5");
		final IPlayerCharacter pc2 =
			new PlayerCharacter("Anonymous Sneaky Bastard", 7, stats, characterType, 50, saves);
		pc2.setDescription("2nd character made only for testing.");
		pc2.getHitPoints().setCurrentHitPoints(5);
		pc2.getLevels().addLevels("Rogue", 7);

		pc2.addSkill(new Skill("Sleight of Hand", stats.getStat(StatType.DEXTERITY), 8));
		pc2.addSkill(new Skill("Diplomacy", stats.getStat(StatType.CHARISMA), 2));

		final IWeapon daggerInstance = getWeaponPrototypes().get(2).clone();
		daggerInstance.removeHitPoints(1);
		pc2.getCombatCapabilities().setWeaponForSequence(daggerInstance, new BonusSequence(2));
		final IWeapon musketInstance = getWeaponPrototypes().get(4).clone();
		pc2.getCombatCapabilities().setWeaponForSequence(musketInstance, new BonusSequence(9));

		pc2.giveArmorToPlayer(getArmors().get(1));
		add(pc2);
	}

	private void initializePlayerCharacters() {
		initializePlayerCharacter1();
		initializePlayerCharacter2();
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
		final BodyPartToRangeMapping humanMapping = new BodyPartToRangeMapping();
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

		final BodyPartToRangeMapping wingedHumanoidMapping = new BodyPartToRangeMapping();
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

	private void initializeWeaponsAndAmmo() {
		final AmmunitionType flightArrow =
			new AmmunitionType("Flight Arrows", new DamageRoll("D6", new Penetration(4), getStrikeTypes().get(3)));
		final AmmunitionType sheafArrow =
			new AmmunitionType("Sheaf Arrows", new DamageRoll("D8", getStrikeTypes().get(3)));
		final AmmunitionType bullet = new AmmunitionType("Bullet", new DamageRoll("2D8", getStrikeTypes().get(3)));
		add(flightArrow);
		add(sheafArrow);
		add(bullet);

		final Weapon longsword = new Weapon("Longsword", 20, new DamageRoll("D8", getStrikeTypes().get(1)));
		longsword.setDescription("Simple but effective weapon.");
		longsword.setPrototype(true);
		final Weapon greatsword =
			new Weapon("Magical Greatsword", 30, new DamageRoll("2D6+1", new Penetration(2), getStrikeTypes().get(1)));
		greatsword.setDescription("Awesome magical greatsword");
		greatsword.setPrototype(true);
		final Set<DamageRoll> extraDamages =
			Sets.newHashSet(new DamageRoll("1D6", getStrikeTypes().get(5)), new DamageRoll("1D10", getStrikeTypes()
					.get(4)));
		greatsword.setExtraDamages(extraDamages);
		final Weapon dagger = new Weapon("Dagger", 5, new DamageRoll("D4", getStrikeTypes().get(3)));
		dagger.setDescription("Small and sneaky.");
		dagger.setRangeAndThrownDamage("5/10/15", new DamageRoll("D6", getStrikeTypes().get(4)));
		dagger.setPrototype(true);
		final Weapon bow = new Weapon("Bow", 3);
		bow.setPrototype(true);
		bow.setAmmoType(flightArrow, new Range("200/400/800"));
		bow.setAmmoType(sheafArrow, new Range("100/200/300"));

		final Weapon musket = new Weapon("Musket", 3, new DamageRoll("D6", getStrikeTypes().get(0)));
		musket.setAmmoType(bullet, new Range("75/150/400"));
		musket.setPrototype(true);

		add(longsword);
		add(greatsword);
		add(dagger);
		add(bow);
		add(musket);
	}

	@Override
	public void update(final Nameable item) throws NameAlreadyExistsEx {
		add(item);
	}
}
