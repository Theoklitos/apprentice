package com.apprentice.rpg.model.combat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.rules.Ruleset;

/**
 * Tests for the {@link BonusSequence}
 * 
 * @author theoklitos
 * 
 */
public final class TestBonusSequence {

	private BonusSequence sequence;
	private Mockery mockery;
	private Ruleset ruleset;

	@Test
	public void addAndRemove() {
		sequence.modify(9);
		assertEquals("+26/+21/+16/+11/+6/+1", sequence.toStringAttack());
		sequence.modify(-14);
		assertEquals("+12/+7/+2", sequence.toStringAttack());
		sequence.modify(-10);
		assertEquals("+2", sequence.toStringAttack());
		sequence.modify(-30);
		assertEquals("-28", sequence.toStringAttack());
	}

	@Test
	public void correctParsing() {
		assertEquals(new BonusSequence(7), new BonusSequence("+7/+2"));
		assertEquals(new BonusSequence(11), new BonusSequence("11/6/1"));
		assertEquals(new BonusSequence(-8), new BonusSequence("-8"));
		assertEquals(new BonusSequence(0), new BonusSequence("0"));
	}

	@Test
	public void equality() {
		assertTrue(sequence.equals(new BonusSequence(17)));
		assertFalse(sequence.equals(new BonusSequence(16)));
	}

	@Test
	public void prettyPrinting() {
		assertEquals("+17/+12/+7/+2", sequence.toStringAttack());
		mockery.checking(new Expectations() {
			{
				oneOf(ruleset).getBaseBlockForSize(Size.LARGE);
				will(returnValue(8));
			}
		});
		assertEquals("25/20/15/10", sequence.toStringBlock(Size.LARGE));
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		ruleset = mockery.mock(Ruleset.class);
		sequence = new BonusSequence(17);
		sequence.setRuleset(ruleset);
	}

	@Test(expected = ParsingEx.class)
	public void wrongParsingNAN() {
		new BonusSequence("+1/a");
	}

	@Test(expected = ParsingEx.class)
	public void wrongParsingSequence() {
		new BonusSequence("+11/+7/+2");
	}
}
