package com.apprentice.rpg.model.durable;

import java.util.Collection;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.Ruleset;
import com.google.common.collect.Lists;

/**
 * tests the abstract {@link DurableItem} and its durability modification
 * 
 * @author theoklitos
 * 
 */
public class TestDurableItemInstance {

	private DurableItem instance;
	private Mockery mockery;
	private Ruleset ruleset;
	private List<Roll> rolls;

	@Test
	public void deterioration() {
		instance.removeHitPoints(5);
		// assertTrue(ApprenticeCollectionUtils.areAllElementsEqual(rolls, prototype.getRolls()));

		expectDecreaseRoll(rolls.get(0), 1);
		expectDecreaseRoll(rolls.get(1), 1);
		instance.removeHitPoints(1);

		expectDecreaseRoll(rolls.get(0), 2);
		expectDecreaseRoll(rolls.get(1), 2);
		instance.removeHitPoints(6);

		instance.removeHitPoints(1);

		expectDecreaseRoll(rolls.get(0), 3);
		expectDecreaseRoll(rolls.get(1), 3);
		instance.removeHitPoints(5);

		expectDecreaseRollToZero(rolls.get(0));
		expectDecreaseRollToZero(rolls.get(1));
		instance.removeHitPoints(10000);
	}

	private void expectDecreaseRoll(final Roll roll, final int steps) {
		mockery.checking(new Expectations() {
			{
				oneOf(ruleset).decreaseRoll(roll, steps);
			}
		});
	}

	private void expectDecreaseRollToZero(final Roll roll) {
		mockery.checking(new Expectations() {
			{
				oneOf(ruleset).decreaseRollToZero(roll);
			}
		});
	}

	@Test
	public void reparation() {
		expectDecreaseRollToZero(rolls.get(0));
		expectDecreaseRollToZero(rolls.get(1));
		instance.removeHitPoints(10000);

		expectDecreaseRoll(rolls.get(0), 3);
		expectDecreaseRoll(rolls.get(1), 3);
		instance.addHitPoints(1);

		expectDecreaseRoll(rolls.get(0), 2);
		expectDecreaseRoll(rolls.get(1), 2);
		instance.addHitPoints(5);

		expectDecreaseRoll(rolls.get(0), 1);
		expectDecreaseRoll(rolls.get(1), 1);
		instance.addHitPoints(6);

		instance.addHitPoints(2);

		instance.addHitPoints(4);
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		ruleset = mockery.mock(Ruleset.class);
		rolls = Lists.newArrayList(new Roll("D10+1"), new Roll("D6"));
		mockery.checking(new Expectations() {
			{
				allowing(ruleset).getDeteriorationIncrementForType(with(any(DurableItem.class)));
				will(returnValue(3));
			}
		});
		final int originalDurability = 20;
		instance = new DurableItem("test item", new CurrentMaximumPair(originalDurability)) {

			@Override
			public Collection<Roll> getDeterioratableRolls() {
				return rolls;
			}
		};
		instance.setRuleset(ruleset);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

}
