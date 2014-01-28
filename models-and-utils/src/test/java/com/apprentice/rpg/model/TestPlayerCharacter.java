package com.apprentice.rpg.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.model.factories.DataFactory;
import com.google.common.collect.Lists;

public final class TestPlayerCharacter {

	private static final String NAME = "cripple";
	private PlayerCharacter pc;
	private StatBundle stats;
	private CharacterType characterType;
	private DataFactory factory;

	@Test
	public void equality() {
		final IPlayerCharacter pc2 = factory.getPlayerCharacter();
		final IPlayerCharacter pc1 = factory.getPlayerCharacter();
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
		final IPlayerCharacter pc2 = factory.getPlayerCharacter();
		final IPlayerCharacter pc1 = factory.getPlayerCharacter();
		pc1.getCharacterType().setRace("Goblin");
		assertThat(pc1, not(equalTo(pc2)));
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		stats = new StatBundle(16, 12, 10, 18, 12, 4);
		final List<BodyPart> parts = Lists.newArrayList();
		parts.add(new BodyPart("left arm"));
		parts.add(new BodyPart("right arm"));
		characterType = new CharacterType(new Type("human", parts), Size.MEDIUM);
		pc = new PlayerCharacter(NAME, 10, stats, characterType);
	}

	@Test(expected = ApprenticeEx.class)
	public void throwsExOnErroneousInitialization() {
		pc = new PlayerCharacter(NAME, 10, null, characterType);
	}

}
