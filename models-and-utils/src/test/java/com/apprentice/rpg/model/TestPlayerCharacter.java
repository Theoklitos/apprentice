package com.apprentice.rpg.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.random.dice.RollWithSuffix;

public final class TestPlayerCharacter {

	private static final int MOVEMENT_FT = 30;
	private static final String NAME = "cripple";
	private PlayerCharacter pc;
	private SavingThrows saves;
	private StatBundle stats;
	private CharacterType characterType;
	private DataFactory factory;
	
	@Test
	public void changeArmorTypeAndRefit() {
		final IPlayerCharacter rogueDaemon = factory.getPlayerCharacter2();
		final Armor armor = factory.getArmors().get(1);
		armor.setType(rogueDaemon.getCharacterType().getType());
		final ArmorPiece wingArmor = new ArmorPiece("Wing armor", 20, new RollWithSuffix("D4+1"), "wing");
		final BodyPart leftWing = new BodyPart("Left Wing");
		armor.setArmorPieceForBodyPart(leftWing, wingArmor);
		rogueDaemon.giveArmorToPlayer(armor);
		assertEquals(wingArmor, armor.getArmorPieceForBodyPart(leftWing).getContent());
	}

	@Test
	public void equality() {
		final IPlayerCharacter pc2 = factory.getPlayerCharacter1();
		final IPlayerCharacter pc1 = factory.getPlayerCharacter1();
		assertEquals(pc2, pc1);
	}
	
	@Test
	public void isCorrectlyInitialized() {
		assertEquals(NAME, pc.getName());
		pc.getStat(StatType.STRENGTH).equals(stats.getStat(StatType.STRENGTH));
		pc.getStat(StatType.DEXTERITY).equals(stats.getStat(StatType.DEXTERITY));
		assertEquals(pc.getCharacterType(), characterType);
	}

	@Test
	public void noEquality() {		
		final IPlayerCharacter pc1 = factory.getPlayerCharacter1();
		final IPlayerCharacter pc2 = factory.getPlayerCharacter2();
		assertThat(pc1, not(equalTo(pc2)));
	}

	@Test
	public void ovewriteArmor() {
		final Armor plateArmor = factory.getArmors().get(0);
		pc.giveArmorToPlayer(plateArmor);
		assertEquals(plateArmor, pc.getArmor());
		final Armor leatherArmor = factory.getArmors().get(1);
		pc.giveArmorToPlayer(leatherArmor);
		assertEquals(leatherArmor, pc.getArmor());
	}

	@Test
	public void setGetRemoveSkill() {
		final Skill listen = new Skill("LISTEN", new Stat(StatType.WISDOM, 8), 10);
		assertEquals(9,listen.getTotalBonus());
		pc.addSkill(listen);
		final Skill listenModified = pc.getSkill("listen").getContent();		
		assertEquals(11,listenModified.getTotalBonus());
		assertFalse(pc.removeSkill("l i sten"));
		assertEquals(1,pc.getSkills().size());
		assertTrue(pc.removeSkill("listen "));
		assertEquals(0,pc.getSkills().size());		
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		stats = new StatBundle(16, 12, 10, 18, 12, 4);
		characterType = new CharacterType(factory.getTypes().get(0), Size.MEDIUM);
		saves = new SavingThrows(10, 5, -4);
		pc = new PlayerCharacter(NAME, 10, stats, characterType, MOVEMENT_FT, saves);
	}

	@Test(expected = ApprenticeEx.class)
	public void throwsExOnErroneousInitialization() {
		pc = new PlayerCharacter(NAME, 10, null, characterType, MOVEMENT_FT, saves);
	}

}
