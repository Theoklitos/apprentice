package com.apprentice.rpg.model.factories;

import java.util.List;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.simple.SimpleVault;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.armor.ArmorPiecePrototype;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.model.weapon.DamageRoll;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
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
	public List<ArmorPiece> getArmorPieces() {
		final List<ArmorPiece> result = Lists.newArrayList();
		for (final ArmorPiece type : getAllNameables(ArmorPiecePrototype.class)) {
			result.add(type);
		}
		return result;
	}

	public List<BodyPart> getBodyParts() {
		return Lists.newArrayList(getAllNameables(BodyPart.class));
	}

	public IPlayerCharacter getPlayerCharacter() {
		final StatBundle stats = new StatBundle(16, 12, 10, 18, 12, 4);
		stats.getStat(StatType.STRENGTH).setValue(6);
		stats.getStat(StatType.INTELLIGENCE).setValue(22);
		final List<BodyPart> parts = Lists.newArrayList();
		parts.add(new BodyPart("head"));
		parts.add(new BodyPart("left arm"));
		parts.add(new BodyPart("right arm"));
		parts.add(new BodyPart("torso"));
		parts.add(new BodyPart("left leg"));
		parts.add(new BodyPart("right leg"));
		final CharacterType characterType = new CharacterType(getTypes().get(0), Size.MEDIUM);
		characterType.setRace("Elf");
		final PlayerCharacter pc = new PlayerCharacter("Anonymous Hero", 10, stats, characterType);
		pc.setDescription("Character made only for testing!");
		pc.getHitPoints().setCurrentHitPoints(5);
		pc.getLevels().addLevels("Fighter", 2);
		pc.getLevels().addLevels("wizard", 5);

		return pc;
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
	 * 0 is simple sword
	 */
	public List<Weapon> getWeapons() {
		final List<Weapon> result = Lists.newArrayList();
		for (final WeaponPrototype type : getAllNameables(WeaponPrototype.class)) {
			result.add(type);
		}
		return result;
	}

	private void initializeArmors() {
		final ArmorPiece breastplate = new ArmorPiecePrototype("Breastplate", 30, new Roll("D6+1"), "Torso");
		final ArmorPiece greatHelm = new ArmorPiecePrototype("Helm", 20, new Roll("2D4+1"), "Head");
		add(breastplate);
		add(greatHelm);
	}

	private void initializeData() {
		initializeParts();
		initializeTypes();
		initializeWeapons();
		initializeArmors();
	}

	private void initializeParts() {
		final BodyPart head = new BodyPart("Head");
		head.setDescription("A very important part");
		add(head); // 0
		add(new BodyPart("Torso")); // 1
		add(new BodyPart("Left Arm")); // 2
		add(new BodyPart("Right Arm")); // 3
		add(new BodyPart("Left Leg")); // 4
		add(new BodyPart("Right Leg")); // 5
		add(new BodyPart("Left Wing")); // 6
		add(new BodyPart("Right Wing")); // 7
		final BodyPart tail = new BodyPart("Tail");
		tail.setDescription("Like a snake.");
		add(tail); // 8
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
		final Type human = new Type("Humanoid", humanMapping);
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
		final Type daemon = new Type("Daemon", wingedHumanoidMapping);
		daemon.setDescription("Mean dude");
		add(daemon);
	}

	private void initializeWeapons() {
		final Weapon longsowrd = new WeaponPrototype("Longsword", 20, new DamageRoll("D8", new StrikeType("Slashing")));
		final Weapon greatsword =
			new WeaponPrototype("Magical Greatsword", 30, new DamageRoll("2D6+1", new StrikeType("Slashing")));
		greatsword.getExtraDamages().add(new DamageRoll("1D6", new StrikeType("Fire")));
		greatsword.getExtraDamages().add(new DamageRoll("1D10", new StrikeType("Cold")));
		add(longsowrd);
		add(greatsword);
	}

	@Override
	public void update(final Nameable item) throws NameAlreadyExistsEx {
		add(item);
	}
}
