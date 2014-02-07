package com.apprentice.rpg.model.factories;

import java.util.List;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.body.Type;
import com.google.common.collect.Lists;

/**
 * Builds predefined, prototypical {@link PlayerCharacter}s. Used for testing
 * 
 * @author theoklitos
 * 
 */
public final class DataFactory {

	private List<BodyPart> allParts;
	private List<IType> types;

	public DataFactory() {
		initializeParts();
		initializeTypes();
	}

	public List<BodyPart> getBodyParts() {
		return allParts;
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
		final CharacterType characterType = new CharacterType(types.get(0), Size.MEDIUM);
		final PlayerCharacter pc = new PlayerCharacter("Anonymous Hero", 10, stats, characterType);
		pc.getHitPoints().setCurrentHitPoints(5);
		pc.getLevels().addLevels("Fighter", 2);
		pc.getLevels().addLevels("wizard", 5);
		return pc;
	}

	/**
	 * type #0 is the standard Human
	 */
	public List<IType> getTypes() {
		return types;
	}

	private void initializeParts() {
		allParts = Lists.newArrayList();
		allParts.add(new BodyPart("Head")); // 0
		allParts.add(new BodyPart("Torso")); // 1
		allParts.add(new BodyPart("Left Arm")); // 2
		allParts.add(new BodyPart("Right Arm")); // 3
		allParts.add(new BodyPart("Left Leg")); // 4
		allParts.add(new BodyPart("Right Leg")); // 5
		allParts.add(new BodyPart("Left Wing")); // 6
		allParts.add(new BodyPart("Right Wing")); // 7
		allParts.add(new BodyPart("Tail")); // 8
	}

	private void initializeTypes() {
		types = Lists.newArrayList();
		final BodyPartToRangeMap humanMapping = new BodyPartToRangeMap();
		humanMapping.setPartForRange(1, 10, allParts.get(0));
		humanMapping.setPartForRange(11, 50, allParts.get(1));
		humanMapping.setPartForRange(51, 64, allParts.get(2));
		humanMapping.setPartForRange(65, 78, allParts.get(3));
		humanMapping.setPartForRange(79, 89, allParts.get(4));
		humanMapping.setPartForRange(90, 100, allParts.get(5));
		types.add(new Type("Human", humanMapping));

		final BodyPartToRangeMap wingedHumanoidMapping = new BodyPartToRangeMap();
		wingedHumanoidMapping.setPartForRange(1, 10, allParts.get(0));
		wingedHumanoidMapping.setPartForRange(11, 50, allParts.get(1));
		wingedHumanoidMapping.setPartForRange(51, 60, allParts.get(2));
		wingedHumanoidMapping.setPartForRange(61, 70, allParts.get(3));
		wingedHumanoidMapping.setPartForRange(71, 75, allParts.get(4));
		wingedHumanoidMapping.setPartForRange(76, 80, allParts.get(5));
		wingedHumanoidMapping.setPartForRange(81, 87, allParts.get(6));
		wingedHumanoidMapping.setPartForRange(86, 94, allParts.get(7));
		wingedHumanoidMapping.setPartForRange(95, 100, allParts.get(8));
		types.add(new Type("Daemon", wingedHumanoidMapping));
	}
}
